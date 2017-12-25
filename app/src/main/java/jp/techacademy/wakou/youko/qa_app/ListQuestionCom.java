package jp.techacademy.wakou.youko.qa_app;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by appu2 on 2017/12/25.
 */

public class ListQuestionCom extends AppCompatActivity implements DatabaseReference.CompletionListener{
    public DatabaseReference.CompletionListener aa;
    public DatabaseReference.CompletionListener comp(Object object,DatabaseReference.CompletionListener listener){
        return listener;
    }

    @Override
    public void onComplete(DatabaseError databaseError,DatabaseReference databaseReference){

    }
}
