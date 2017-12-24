package jp.techacademy.wakou.youko.qa_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by appu2 on 2017/12/22.
 */

public class FavoriteActivity extends AppCompatActivity {
//   private int mGenre = 5;
    private ListView mListView;
   private Question mQuestion;
   private QuestionDetailListAdapter mAdapter;

   private DatabaseReference mAnswerRef;
   private ChildEventListener mEventListener = new ChildEventListener() {
       @Override
       public void onChildAdded(DataSnapshot dataSnapshot, String s) {
           HashMap favomap = (HashMap)dataSnapshot.getValue();

           String answerUid = dataSnapshot.getKey();
           for(Answer answer : mQuestion.getAnswers()){
               if(answerUid.equals(answer.getAnswerUid())){
                   return;
               }
           }
           if(favomap.containsKey("favo")&&favomap.containsValue(1)){
               String body = (String)favomap.get("body");
               String name = (String)favomap.get("name");
               String uid = (String)favomap.get("uid");

               Answer answer = new Answer(body,name,uid,answerUid);
               mQuestion.getAnswers().add(answer);
               mAdapter.notifyDataSetChanged();

           }
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
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.favorite_active);

//       Intent intent = this.getIntent();
       Bundle extras = getIntent().getExtras();
       mQuestion = (Question)extras.get("question");

       setTitle(mQuestion.getTitle());

       mListView = (ListView)findViewById(R.id.listView);
       mAdapter = new QuestionDetailListAdapter(this,mQuestion);
       mListView.setAdapter(mAdapter);
       mAdapter.notifyDataSetChanged();

       FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

               if(user == null){
                   Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                   startActivity(intent);
               }else{
                    Intent intent = new Intent(getApplicationContext(),AnswerSendActivity.class);
                    intent.putExtra("question",mQuestion);
                    startActivity(intent);
               }
           }
       });

       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
               mAnswerRef = databaseReference.child(Const.ContentsPATH).child(String.valueOf(mQuestion.getGenre())).child(mQuestion.getQuestionUid()).child(Const.AnswersPATH);
               mAnswerRef.addChildEventListener(mEventListener);

   }
}
