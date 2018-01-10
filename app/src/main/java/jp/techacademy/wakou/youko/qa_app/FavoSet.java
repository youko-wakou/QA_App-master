package jp.techacademy.wakou.youko.qa_app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by appu2 on 2017/12/27.
 */

public class FavoSet implements Serializable{
    public String muid;
    public byte[] mimage;
    public String mname;
    public String mtitle;
    public String mbody;
    public String mQuestionUid;
    public ArrayList<FavoAnswer>mFavoanswer;
    public String mGenre;

    //    public FavoSet(String title, String body, String name, String uid, byte[]bytes){
//        mTitle = title;
//        mBody = body;
//        mName = name;
//        mUid = uid;
//        mByte = bytes;
//    }
    public String getTitle(){
        return mtitle;
    }
    public String getBody(){
        return mbody;
    }
    public String getName(){
        return mname;
    }
    public String getUid(){
        return muid;
    }
    public String getQuestionUid(){
        return mQuestionUid;
    }
    public String getGenre(){
        return mGenre;
    }
    public byte[] getImageBytes(){
        return mimage;
    }
    public ArrayList<FavoAnswer>getAnswers(){
        return mFavoanswer;
    }
    public FavoSet(String body,String name,byte[] image,String title,String uid,ArrayList<FavoAnswer>favoanswer,String questionUid,String count){
        mQuestionUid = questionUid;
        mbody = body;
        mname = name;
        mFavoanswer = favoanswer;
        mimage = image;
        mtitle = title;
        muid = uid;
        mGenre = count;
    }
}
