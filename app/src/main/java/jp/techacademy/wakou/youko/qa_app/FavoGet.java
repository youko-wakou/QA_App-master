package jp.techacademy.wakou.youko.qa_app;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by appu2 on 2017/12/27.
 */

public class FavoGet implements Serializable {
    public String mvalue;
    public String mlist;
    public String mkey;
    public String mlisNum;
    public String getkey(){
        return mkey;
    }
    public HashMap<String,String>map = new HashMap<String,String>();
//    public HashMap<String,String> getfavomap(){
//         return aa;
//    }
    public HashMap<String,String> getfavomap(){
       return map;
    }
    public void setfavomap(String key,String value){
        mkey = key;
        mlisNum = value;
        map.put(mkey,mlisNum);
    }

//    public FavoGet(String value,String list){
//        mvalue = value;
//        mlist = list;
//    }
}
