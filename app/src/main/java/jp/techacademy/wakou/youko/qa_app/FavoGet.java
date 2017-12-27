package jp.techacademy.wakou.youko.qa_app;

import java.io.Serializable;

/**
 * Created by appu2 on 2017/12/27.
 */

public class FavoGet implements Serializable {
    public String mBody;
    public String mName;
    public String mUid;
    public String mFavoUid;
    public FavoGet(String body,String name,String uid,String answerUid){
        mBody = body;
        mName = name;
        mUid = uid;
        mFavoUid = answerUid;
    }
}
