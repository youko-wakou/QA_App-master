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

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by appu2 on 2017/12/22.
 */

public class FavoriteAdapter extends BaseAdapter {
   private LayoutInflater mLayoutInflater = null;
   private ArrayList<Question>mQuestionArrayList;

   public FavoriteAdapter(Context context){
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
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.favorite_detail_content,parent,false);
        }

        TextView favoTi = (TextView)convertView.findViewById(R.id.favoTitle);
        favoTi.setText(mQuestionArrayList.get(position).getTitle());
        TextView favoNa = (TextView)convertView.findViewById(R.id.favoName);
        favoNa.setText(mQuestionArrayList.get(position).getName());
        TextView favoRe = (TextView)convertView.findViewById(R.id.favoCount);
        int resFavo = mQuestionArrayList.get(position).getAnswers().size();
        favoRe.setText(String.valueOf(resFavo));

        byte[] bytes = mQuestionArrayList.get(position).getImageBytes();
        if(bytes.length !=0){
            Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length).copy(Bitmap.Config.ARGB_8888,true);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.favoImage);
            imageView.setImageBitmap(image);
        }
        return convertView;
    }

    public void  setQuestionArrayList(ArrayList<Question>questionArrayList){
        mQuestionArrayList = questionArrayList;
    }
}
