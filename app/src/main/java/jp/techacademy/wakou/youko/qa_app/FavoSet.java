package jp.techacademy.wakou.youko.qa_app;

import java.util.ArrayList;

/**
 * Created by appu2 on 2017/12/27.
 */

public class FavoSet {
    public String mTitle;
    public String mName;
    public String mUid;
    public String mBody;
    public byte[]mByte;
    ArrayList<Answer> mArraylist;
    public FavoSet(String title, String body, String name, String uid, byte[]bytes){
        mTitle = title;
        mBody = body;
        mName = name;
        mUid = uid;
        mByte = bytes;
    }
}
