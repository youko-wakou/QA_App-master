package jp.techacademy.wakou.youko.qa_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by appu2 on 2017/12/22.
 */

public class FavoriteActivity extends AppCompatActivity {
    private ArrayList<FavoSet>mFavoriteArrayList;
    private FavoriteAdapter favoadap;
    private ChildEventListener favoriteLis = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap)dataSnapshot.getValue();

            String title = (String)map.get("title");
            String body = (String)map.get("body");
            String name = (String)map.get("name");
            String uid = (String)map.get("uid");
            String imageString = (String)map.get("image");

            byte[] bytes;
            if(imageString != null){
                bytes = Base64.decode(imageString,Base64.DEFAULT);
            }else{
                bytes = new byte[0];
            }

            ArrayList<FavoGet>favogetArrayList = new ArrayList<FavoGet>();
            HashMap favomap = (HashMap)map.get("favovalu");
            if(favomap != null){
                for(Object key: favomap.keySet()){
                    HashMap temp = (HashMap)favomap.get((String)key);
                    String favoBody = (String)temp.get("favorite");
                    FavoGet favoget = new FavoGet(favoBody,(String)key);
                    favogetArrayList.add(favoget);
                }
            }
            FavoSet favoset = new FavoSet(title, body,  name,  uid, bytes);
            mFavoriteArrayList.add(favoset);

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

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
}
