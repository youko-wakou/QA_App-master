package jp.techacademy.wakou.youko.qa_app;

import java.io.Serializable;

/**
 * Created by appu2 on 2018/01/09.
 */

public class FavoAnswer implements Serializable {
    private String mBody;
    private String mName;
    private String mUid;
    private String mAnswerUid;

    public FavoAnswer(String body,String name,String uid,String answerUid){
        mBody =body;
        mName = name;
        mUid = uid;
        mAnswerUid = answerUid;
    }
    public String getmBody(){
        return mBody;
    }
    public String getName(){
        return mName;
    }
    public String getUid(){
        return mUid;
    }
    public String getmAnswerUid(){
        return mAnswerUid;
    }
}
