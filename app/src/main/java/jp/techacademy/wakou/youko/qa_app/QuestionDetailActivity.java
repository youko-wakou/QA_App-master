package jp.techacademy.wakou.youko.qa_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    public Favorite favorite = new Favorite();
    public Map<String,Integer> memo;
    public HashMap hash;
    public String obs;
    public Object data;
    static Question genre;
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
    public void addFavo(Object data){
        String num = listNum();
        int genreI = genre();
         FirebaseUser favouser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference favoRef = databaseReference.child(Const.ContentsPATH).child(String.valueOf(genreI)).child(num).child(Const.FavoPATH).child(favouser.getUid()).child(num);
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

    }

    private ChildEventListener SecondListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Object ob = dataSnapshot.getValue();
            String obs = String.valueOf(ob);
            Log.d("kore",obs);
//リストの中で選択した詳細画面のお気に入りボタンの情報を0か１で受け取る
            favorite.num = obs;
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
            Object ob = dataSnapshot.getValue();
            String obs = String.valueOf(ob);

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
        boolean isfavorite;
        if(favorite.num == "0"){
            isfavorite = false;
        }else{
            isfavorite = true;
        }
//        Question mQuestion = new Question(mQuestion.getTitle(),mQuestion.getTitle(),mQuestion.getUid(),mQuestion.getGenre(),mQuestion.getImageBytes(),mQuestion.getAnswers(),isfavorite);
        //ここでリスト番号取得
        listNum = mQuestion;
        genre = mQuestion;
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
    }
    @Override
    public void onStart(){
       super.onStart();

        String num = listNum();
        int genreI = genre();
        FirebaseUser favouser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference favoRef = databaseReference.child(Const.ContentsPATH).child(String.valueOf(genreI)).child(num).child(Const.FavoPATH).child(favouser.getUid()).child(num);
        favoRef.addChildEventListener(SecondListener);

    }
    public static String listNum(){
       return listNum.getQuestionUid();
    }
    public static int genre(){
        return genre.getGenre();
    }

    public String obs(){
        String obs = favorite.num;
        return obs;
    }
}
