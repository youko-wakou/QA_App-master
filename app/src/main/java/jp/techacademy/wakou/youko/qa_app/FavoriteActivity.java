package jp.techacademy.wakou.youko.qa_app;

import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by appu2 on 2017/12/22.
 */

public class FavoriteActivity extends AppCompatActivity {
//    private Toolbar mToolbar;
    private int mGenre = 0;

    private DatabaseReference mDatabaseReference;
    private DatabaseReference mGenreRef;
    private ListView mListView;
    private ArrayList<Question>mQuestionArrayList;
    private QuestionsListAdapter mAdapter;

    private ChildEventListener mEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap favomap  = (HashMap)dataSnapshot.getValue();
            if(favomap.containsKey("favo") && favomap.containsValue(1)){
                String title = (String)favomap.get("title");
                String body = (String)favomap.get("body");
                String name = (String)favomap.get("name");
                String uid = (String)favomap.get("uid");
                String imageString= (String)favomap.get("image");
                byte[] bytes;
                if(imageString != null){
                    bytes= new byte[0];
                }

                ArrayList<Answer>answerArrayList = new ArrayList<Answer>();
                HashMap answerMap = (HashMap)favomap.get("answers");
                if(answerMap != null){
                    for(Object key : answerMap.KeySet()){
                        HashMap favotemp = (HashMap)answerMap.get((String)Key);
                        String answerBody = (String)favotemp.get("body");
                        String answerName = (String)favotemp.get("name");
                        String answerUid = (String)favotemp.get("uid");
                        Answer answer = new Answer(answerBody,answerName,answerUid,(String)Key);
                        answerArrayList.add(answer);
                    }
                    Question question = new Question(title,body,name,uid,dataSnapshot.getKey(),mGenre,bytes,answerArrayList);
                    mQuestionArrayList.add(question);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            HashMap favomap = (HashMap)dataSnapshot.getValue();

            for(Question question:mQuestionArrayList){
                if(dataSnapshot.getKey().equals(question.getQuestionUid())){
                    question.getAnswers().clear;
                    HashMap answerMap = (HashMap)favomap.get("answer");
                    if(answerMap != null){
                        for(Object Key:answerMap.KeySet()){
                            HashMap favotemp = (HashMap)answerMap.get((String)Key);
                            String answerBody = (String)favotemp.get("body");
                            String answerName = (String)favotemp.get("name");
                            String answerUid = (String)favotemp.get("uid");
                            Answer answer = new Answer(answerBody,answerName,answerUid,(String)Key);
                            question.getAnswers().add(answer);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
