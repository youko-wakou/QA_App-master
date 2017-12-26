package jp.techacademy.wakou.youko.qa_app;

import android.app.AlertDialog;
import android.content.Context;
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
import java.util.List;
import java.util.Map;

/**
 * Created by appu2 on 2017/12/20.
 */

public class QuestionDetailActivity extends AppCompatActivity implements DatabaseReference.CompletionListener{
    private ListView mListView;
    private Question mQuestion;
    private QuestionDetailListAdapter mAdapter;
    static Question listNum;
    public Map<String,Integer> memo;
    public HashMap hash;
    public Object data;
    public Object obk;
    public DataSnapshot testData;
    private DatabaseReference mAnswerRef;
    public static void favoAdd(Context context, String title, String message){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",null)
                .show();
    }
    public Object data(Object data){
        return data;
    }
//    firebaseに追加する
    public void addFavo(){
        QuestionDetailListAdapter QLA = new QuestionDetailListAdapter(this,mQuestion);
        QLA.gethash();
        FirebaseUser favouser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference favoRef = databaseReference.child(Const.FavoPATH).child(favouser.getUid());
        favoRef.removeValue();
        favoRef.setValue(data, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError != null){
                    System.out.println("Data could not be saved"+ databaseError.getMessage());
                }else{
                    System.out.println("Data saed successfully");
                }
            }
        });
//        if(hash.containsKey(obk)){
//            favoRef.removeValue();
//            favoRef.setValue(data, new DatabaseReference.CompletionListener() {
//                @Override
//                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                    if(databaseError != null){
//                        System.out.println("Data could not be saved"+ databaseError.getMessage());
//                    }else{
//                        System.out.println("Data saed successfully");
//                    }
//                }
//            });
//        }else{
//            favoRef.push().setValue(data, new DatabaseReference.CompletionListener() {
//                @Override
//                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                    if(databaseError != null){
//                        System.out.println("Data could not be saved" + databaseError.getMessage());
//                    }else{
//                        System.out.println("Data saved successfully");
//                    }
//                }
//            });
//        }
    }


    public HashMap callmap(){
        return hash;
    }
    public ChildEventListener CEL = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Object ob = dataSnapshot.getValue();
            Object obk = dataSnapshot.getKey();
            String obks = String.valueOf(obk);

            HashMap hash = (HashMap) dataSnapshot.getValue();
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


    private ChildEventListener mEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap)dataSnapshot.getValue();
            String answerUid = dataSnapshot.getKey();

            for(Answer answer : mQuestion.getAnswers()){
                if(answerUid.equals(answer.getAnswerUid())){
                    return;
                }
            }
            String body = (String)map.get("body");
            String name = (String)map.get("name");
            String uid = (String)map.get("uid");

            Answer answer = new Answer(body,name,uid,answerUid);
            mQuestion.getAnswers().add(answer);
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
    public void onComplete(DatabaseError databaseError,DatabaseReference databaseReference){

    }

    @Override
   public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        Bundle extras = getIntent().getExtras();
        mQuestion = (Question)extras.get("question");
        //ここでリスト番号取得
        listNum = mQuestion;
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
        DatabaseReference dataBaseReference = FirebaseDatabase.getInstance().getReference();
        mAnswerRef = dataBaseReference.child(Const.ContentsPATH).child(String.valueOf(mQuestion.getGenre())).child(mQuestion.getQuestionUid()).child(Const.AnswersPATH);
        mAnswerRef.addChildEventListener(mEventListener);
        FirebaseUser favouser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference favoRef = databaseReference.child(Const.FavoPATH).child(favouser.getUid());
        favoRef.addChildEventListener(CEL);
    }
    public static String listNum(){
       return listNum.getQuestionUid();
    }
}
