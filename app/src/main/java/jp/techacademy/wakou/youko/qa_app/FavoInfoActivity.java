package jp.techacademy.wakou.youko.qa_app;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by appu2 on 2018/01/10.
 */

public class FavoInfoActivity extends AppCompatActivity {
    private FavoSet mFavoset;
    private ListView mListView;
    private FavoInfoAdapter mAdapter;
    private DatabaseReference mAnswerRef;

    private ChildEventListener mEventListner = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map =(HashMap)dataSnapshot.getValue();
            String answerUid = dataSnapshot.getKey();
            for(FavoAnswer favoanswer: mFavoset.getAnswers()){
                if(answerUid.equals(favoanswer.getmAnswerUid())){
                    return;
                }
            }

            String body = (String)map.get("body");
            String name = (String)map.get("name");
            String uid = (String)map.get("uid");

            FavoAnswer favoanswer = new FavoAnswer(body,name,uid,answerUid);
            mFavoset.getAnswers().add(favoanswer);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_favo_detail);

        Bundle extras =  getIntent().getExtras();
        mFavoset = (FavoSet)extras.get("question");
        setTitle(mFavoset.getTitle());

        ListView mListView = (ListView)findViewById(R.id.favoListview);
        mAdapter = new FavoInfoAdapter(this,mFavoset);
        mListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        mAnswerRef = databaseReference.child(Const.ContentsPATH).child(mFavoset.getGenre()).child(mFavoset.getQuestionUid()).child(Const.AnswersPATH);
        mAnswerRef.addChildEventListener(mEventListner);
    }
}
