package jp.techacademy.wakou.youko.qa_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by appu2 on 2018/01/10.
 */

public class FavoInfoAdapter extends BaseAdapter {
    private final static int TYPE_QUESTION =0;
    private final static int TYPE_ANSWER = 1;

    private LayoutInflater mLayoutInflater = null;
    private FavoSet mFavoset;

    public FavoInfoAdapter(Context context,FavoSet favoset){
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFavoset = favoset;
    }
    @Override
    public int getCount(){
        return 1 +mFavoset.getAnswers().size();
    }
    @Override
    public int getItemViewType(int position){
        if(position ==0){
            return TYPE_QUESTION;
        }else{
            return TYPE_ANSWER;
        }
    }
    @Override
    public int getViewTypeCount(){
        return 2;
    }
    @Override
    public Object getItem(int position){
        return mFavoset;
    }
    @Override
    public long getItemId(int position){
        return 0;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        if(getItemViewType(position)==TYPE_QUESTION){
            if(convertView == null){
                convertView = mLayoutInflater.inflate(R.layout.favorite_question_detail,parent,false);
            }
            String body = mFavoset.getBody();
            String name = mFavoset.getName();

            TextView bodyTextView = (TextView)convertView.findViewById(R.id.fovorite_question);
            TextView nameTextView = (TextView)convertView.findViewById(R.id.favorite_name);

            byte[] bytes = mFavoset.getImageBytes();
            if(bytes.length != 0){
                Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length).copy(Bitmap.Config.ARGB_8888,true);

                ImageView imageView = (ImageView)convertView.findViewById(R.id.favoImage);
                imageView.setImageBitmap(image);
            }
        }else{
            if(convertView == null){
                convertView = mLayoutInflater.inflate(R.layout.favo_answer,parent,false);
            }

            FavoAnswer answer = mFavoset.getAnswers().get(position -1);
            String body = answer.getmBody();
            String name = answer.getName();

            TextView bodyTextView = (TextView)convertView.findViewById(R.id.favo_answerBox);
            bodyTextView.setText(body);
            TextView nameTextView = (TextView)convertView.findViewById(R.id.favo_answerName);
            nameTextView.setText(name);
        }
        return convertView;
    }
}
