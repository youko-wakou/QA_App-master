package jp.techacademy.wakou.youko.qa_app;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuestionsListAdapter extends BaseAdapter {
    public Context questionListC;
    public String favoT;
    public String favoM;
    public int favoNum = 0;
//    QuestionListActivity activityQ;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<Question>mQuestionArrayList;
    public QuestionsListAdapter(Context context){
        questionListC = context;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount(){
        return mQuestionArrayList.size();
    }
    @Override
    public Object getItem(int position){
        return mQuestionArrayList.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
//    public void aaa(Context context){
//    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
          convertView = mLayoutInflater.inflate(R.layout.list_questions,parent,false);
        }
        TextView titleText = (TextView) convertView.findViewById(R.id.titleTextView);
        titleText.setText(mQuestionArrayList.get(position).getTitle());

        TextView nameText = (TextView)convertView.findViewById(R.id.nameTextView);
        nameText.setText(mQuestionArrayList.get(position).getName());

        TextView resText = (TextView) convertView.findViewById(R.id.resTextView);
        int resNum = mQuestionArrayList.get(position).getAnswers().size();
        resText.setText(String.valueOf(resNum));

        TextView favoData = (TextView)convertView.findViewById(R.id.favoData);
        favoData.setVisibility(View.GONE);

        final Button favoBT = (Button)convertView.findViewById(R.id.favobt);

        favoBT.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favoT = "お気に入り";
                Map<String,Integer> data = new HashMap<String,Integer>();
                if(favoNum == 0){
                     favoNum = 1;
                    data.put("favo",favoNum);
                    favoM = "お気に入りに登録しました";
                    QuestionListActivity.favoAdd(questionListC,favoT,favoM);
                    favoBT.setBackgroundResource(R.drawable.favo);

                }else{
                    favoNum = 0;
                    data.put("favo",favoNum);
                    favoM = "お気に入りを解除しました";
                    QuestionListActivity.favoAdd(questionListC,favoT,favoM);
                    favoBT.setBackgroundResource(R.drawable.favo_n);
                }
                Log.d("test", "テスト実行");
            }
        });

        byte[]bytes = mQuestionArrayList.get(position).getImageBytes();
        if(bytes.length != 0){
            Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length).copy(Bitmap.Config.ARGB_8888,true);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
            imageView.setImageBitmap(image);
        }
        return convertView;
    }
    public void setQuestionArrayList(ArrayList<Question> questionArrayList){
        mQuestionArrayList = questionArrayList;
    }


}
