package jp.techacademy.wakou.youko.qa_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by appu2 on 2017/12/27.
 */

public class FavoGet implements Serializable {


    public String mkey;
    public String mlisNum;
    public String mKey;
    public Object mLisData;
//    public Object mLisnum;
//    public HashMap<FavoriteActivity> mhash;
    public String getkey(){
        return mkey;
    }
    public HashMap<String,String>map = new HashMap<String,String>();
    public HashMap<String,Object>maplist = new HashMap<String,Object>();
//    public HashMap<String,String> getfavomap(){
//         return aa;
//    }
    public HashMap<String,Object> getmaplist(){
        return maplist;
    }
    public void setmaplist(
            String key,
            Object value
    ){
        mKey = key;
        mLisData = value;
        maplist.put(mKey,mLisData);
    }
    public HashMap<String,String> getfavomap(){
       return map;
    }

    public void setfavomap(String key, String value){
        mkey = key;
        mlisNum = value;
        map.put(mkey,mlisNum);
    }


}
