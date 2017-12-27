package jp.techacademy.wakou.youko.qa_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.TagLostException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class QuestionDetailListAdapter extends BaseAdapter implements DatabaseReference.CompletionListener{
    public Context questionListC;
    public String favoT;
    public String favoM;
    public int favoNum = 0;
    public DatabaseReference.CompletionListener ii;
    public DatabaseReference.CompletionListener rr;
    private Map<String,Integer> fdata = new HashMap<String,Integer>();
    private String obs;

    private final static int TYPE_QUESTION = 0;
    private final static int TYPE_ANSWER = 1;

    private LayoutInflater mLayoutInflater = null;
    private Question mQustion;
    private Favorite favorite;
    private final QuestionDetailActivity QDA = new QuestionDetailActivity();

    public QuestionDetailListAdapter(Context context, Question question) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mQustion = question;
        questionListC = context;
    }
    public void onComplete(DatabaseError databaseError,DatabaseReference databaseReference){

    }
     @Override
    public int getCount() {
        return 1 + mQustion.getAnswers().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_QUESTION;
        } else {
            return TYPE_ANSWER;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return mQustion;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == TYPE_QUESTION) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.list_question_detail, parent, false);
            }
            String body = mQustion.getBody();
            String name = mQustion.getName();

            TextView bodyTextView = (TextView) convertView.findViewById(R.id.bodyTextView);
            bodyTextView.setText(body);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            nameTextView.setText(name);

//            お気に入り機能
//            Favoriteクラス（Boolean）にボタンがtrueかfalseか保存
            final String obs = QDA.obs();
            favorite = new Favorite();
            final Button favoBT = (Button) convertView.findViewById(R.id.favoBT);
            if(obs=="1"){
                favoBT.setBackgroundResource(R.drawable.favo);
            }else{
                favoBT.setBackgroundResource(R.drawable.favo_n);
            }
            favoBT.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
//                  childEventListenerを呼び出す（QuestionDetailActivityで設定）
                    favoT = "お気に入り";
//                    ここでリスト一覧の選択したリストIDが取得できる
                    String lisId = QuestionDetailActivity.listNum();
                    //0が含まれるかfdataがまだ空だったらお気に入りしていない　
//                    favoNum1:お気に入りしている　favoNum0：お気に入りしていない
                    if(favorite.result==false||obs=="0"){
                            favoNum = 1;
                            favorite.result = true;
                            fdata.put("favorite",favoNum);
                        favoM = "お気に入りに登録しました";
                        QuestionDetailActivity.favoAdd(questionListC,favoT,favoM);
                        favoBT.setBackgroundResource(R.drawable.favo);
                    }else{
                            favorite.result=false;
                            favoNum = 0;
                            fdata.put("favorite",favoNum);
                            favoM = "お気に入りを解除しました";
//                        ダイアログ呼び出し
                        QuestionDetailActivity.favoAdd(questionListC,favoT,favoM);
                        favoBT.setBackgroundResource(R.drawable.favo_n);
                    }

//                    //favoNum情報の登録をfirebaseにする
                    QDA.addFavo(fdata);
                }
            });
            byte[] bytes = mQustion.getImageBytes();
            if (bytes.length != 0) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length).copy(Bitmap.Config.ARGB_8888, true);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
                imageView.setImageBitmap(image);
            }
        } else {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.list_answer, parent, false);
            }
            Answer answer = mQustion.getAnswers().get(position - 1);
            String body = answer.getBody();
            String name = answer.getName();
            TextView bodyTextView = (TextView) convertView.findViewById(R.id.bodyTextView);
            bodyTextView.setText(body);

            TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            nameTextView.setText(name);

        }
        return convertView;
    }

}
