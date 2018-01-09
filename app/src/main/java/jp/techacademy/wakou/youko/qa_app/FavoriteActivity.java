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
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by appu2 on 2017/12/22.
 */

public class FavoriteActivity extends AppCompatActivity {
    private ArrayList<FavoSet>mFavoriteArrayList;
    private FavoriteAdapter favoadap;
    private DatabaseReference favoRef;
    private DatabaseReference favoCallRef;
    private FavoGet mFavoGet = new FavoGet();
    private FirebaseUser user;
    private ListView mListView;
    private int count;
//    private ArrayList<FavoSet> favoSetArrayList;
//    private ArrayList<FavoSave>favoSaveArrayList;
    private HashMap<String,String> hashTitle = new HashMap<String,String>();
//    お気に入り呼び出し
    private ChildEventListener favoriteLis = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d("wre","こんばんは");

            HashMap map = (HashMap)dataSnapshot.getValue();
            for(Object keys: map.values()){
//                Log.d("debug", keys + " = " + map.get((String)keys));
                System.out.println("こちら"+keys);

            }
            String info = (String)dataSnapshot.getKey();
            String favoValue = String.valueOf(map.get("favorite"));
            if (favoValue.equals("1")) {
                mFavoGet.setfavomap(info,favoValue);
                HashMap<String,String> favomap = new HashMap<String,String>();
                favomap.put(info,favoValue);
                for(Object key: favomap.keySet()){
                    Log.d("test","key = "+key);
                }
            }
            Log.d("test","aaaaaaaa");
            //            ここでリスナーを再び呼び出す
            DatabaseReference dataBaseReference = FirebaseDatabase.getInstance().getReference();
            for(int count = 1;count<=4;count++){
//                DatabaseReference dataBaseReference = FirebaseDatabase.getInstance().getReference();
                favoCallRef = dataBaseReference.child(Const.ContentsPATH).child(String.valueOf(count));
                favoCallRef.addChildEventListener(mCallfavo);
            }
//            favoCallRef = dataBaseReference.child(Const.ContentsPATH).child(String.valueOf(count));
//            favoCallRef = dataBaseReference.child(Const.ContentsPATH);
//            favoCallRef.addChildEventListener(mCallfavo);
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
//    ここでリスト呼び出し
    private ChildEventListener mCallfavo = new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        HashMap listmap = (HashMap)dataSnapshot.getValue();
        String dataKey = (String)dataSnapshot.getKey();

//        mFavoGet.setmaplist(dataKey,listmap);
//        String dataValue = String.valueOf(listmap.get(dataKey));
//        String dataValue = (String)dataSnapshot.getKey();
        HashMap testmap = mFavoGet.getfavomap();
//        HashMap mapGet = mFavoGet.getmaplist();
//        変更
//        HashMap mapQuestion = (HashMap)mapGet.get(dataKey);
//        HashMap mm = mapQuestion;
//        System.out.println("リスト"+mapQuestion);
//        もしtestmapにdatakey（質問リストid)が一致するものが含まれていた場合
//        testmapはすでにお気に入りしてあるリスト番号を指す
//        if(testmap.containsKey(dataKey)){
//            Log.d("test","えええええええええ");
//        }

//        ★ここを一度無効
//      変更
//        for(Object key: mapQuestion.keySet()) {
//            for (Object testmapKey : testmap.keySet()) {
//        変更
//            System.out.println("ここも"+key);
//                Log.d("getmap", "key =" + key);
//            mFavoGet.setmaplist("listmap",key);
                        // 変更
//                        HashMap listkeyMap = (HashMap) mapQuestion.get((String) key);

//                        for(Object listkey: listkeyMap.keySet()) {
//                            if (testmap.containsKey(dataKey)) {


                        String title = String.valueOf(listmap.get("title"));
                        String body = String.valueOf(listmap.get("body"));
                        String name = String.valueOf(listmap.get("name"));
                        String uid = String.valueOf(listmap.get("uid"));
                        String imageString= String.valueOf(listmap.get("image"));
                        byte[] bytes;
                        if(imageString != null){
                            bytes = Base64.decode(imageString,Base64.DEFAULT);
                        }else{
                            bytes = new byte[0];
                        }
//                    testmapの数だけHashMap（hashTitle）にtitleがputされるはず？
                        hashTitle.put("title", title);
                        Log.d("title", title);

                        ArrayList<FavoAnswer>answerArrayList = new ArrayList<FavoAnswer>();
                        HashMap answerMap = (HashMap)listmap.get("answers");
                        if(answerMap != null){
                            for(Object key: answerMap.keySet()){
                                HashMap tempAnswer = (HashMap)answerMap.get((String)key);
                                String Abody = (String)tempAnswer.get("body");
                                String Aname = (String)tempAnswer.get("name");
                                String Auid = (String)tempAnswer.get("uid");

                                FavoAnswer favoanswer = new FavoAnswer(Abody,Aname,Auid,(String)key);
                                answerArrayList.add(favoanswer);
                            }
                        }
//                        FavoSetクラスの引数にお気に入り済みの質問iD情報を引数として渡す
                        FavoSet favoset = new FavoSet(body,name,bytes,title,uid,answerArrayList);

//                        FavoSetの値を引き継いだArraylistにFavosetの値を渡す
                        if (testmap.containsKey(dataKey)) {
                            ArrayList<FavoSet>mFavoriteArrayList = new ArrayList<FavoSet>();
                            mFavoriteArrayList.add(favoset);
                            favoadap.setfavoArrayList(mFavoriteArrayList);
                            favoadap.notifyDataSetChanged();
                         }

//                    }
//                              変更
//                }
//            }
//        }
//        ★ここまで
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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_active);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String name = user.getDisplayName();
//            String uid = user.getUid();
        }
        Intent intent = getIntent();
        Log.d("wre","こんんちは");
        setTitle("お気に入りリスト");
        favoadap = new FavoriteAdapter(this);
        ArrayList<FavoSet>mFavoriteArrayList = new ArrayList<FavoSet>();
        mFavoriteArrayList.clear();
        favoadap.setfavoArrayList(mFavoriteArrayList);
//        ArrayList<FavoSave>favoSaveArrayList = new ArrayList<FavoSave>();
        mListView = (ListView) findViewById(R.id.favolist);
        mListView.setAdapter(favoadap);

        favoadap.notifyDataSetChanged();
       DatabaseReference dataBaseReference = FirebaseDatabase.getInstance().getReference();
//       favoRef = dataBaseReference.child(Const.ContentsPATH);
//        favoRef = dataBaseReference.child(Const.UsersPATH).child(user.getUid()).child(Const.FavoPATH);
//        favoRef = dataBaseReference.child(Const.UsersPATH).child(favouser.getUid()).child(Const.FavoPATH).child(num);
        favoRef = dataBaseReference.child(Const.FavoPATH).child(user.getUid());
       favoRef.addChildEventListener(favoriteLis);
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        DatabaseReference dataBaseReference = FirebaseDatabase.getInstance().getReference();
//        favoRef = dataBaseReference.child(Const.ContentsPATH);
//        favoRef.addChildEventListener(favoriteLis);
//
//    }
}

