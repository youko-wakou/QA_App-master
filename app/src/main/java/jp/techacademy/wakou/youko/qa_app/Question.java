package jp.techacademy.wakou.youko.qa_app;

import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by appu2 on 2017/12/19.
 */

public class Question implements Serializable{
    private String mTitle;
    private String mBody;
    private String mName;
    private String mUid;
    private String mQuestionUid;
    private int mGenre;
    private byte[]mBitmapArray;
    private boolean mIsfavorite;
    private ArrayList<Answer>mAnswerArrayList;

    public String getTitle(){
        return mTitle;
    }
    public boolean getfavorite(){
        return mIsfavorite;
    }
    public void setfavorite(boolean isFavorite){
        mIsfavorite = isFavorite;
    }
    public String getBody(){
        return mBody;
    }
    public String getName(){
        return mName;
    }
    public String getUid(){
        return mUid;
    }
    public String getQuestionUid(){
        return mQuestionUid;
    }
    public int getGenre(){
        return mGenre;
    }
    public byte[]getImageBytes(){
        return mBitmapArray;
    }
    public ArrayList<Answer>getAnswers(){
        return mAnswerArrayList;
    }
    public Question(String title, String body, String name, String uid, String questionUid, int genre, byte[]bytes, ArrayList<Answer>answers
//            ,boolean isfavorite
    ){
        mTitle = title;
        mBody = body;
        mName = name;
        mUid = uid;
        mQuestionUid = questionUid;
        mGenre = genre;
        mBitmapArray = bytes.clone();
        mAnswerArrayList = answers;
//        mFavo = isfavorite;
    }

}
