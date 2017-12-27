package jp.techacademy.wakou.youko.qa_app;

import java.io.Serializable;

/**
 * Created by appu2 on 2017/12/27.
 */

public class FavoGet implements Serializable {
    public String mvalue;
    public String mlist;
    public FavoGet(String value,String list){
        mvalue = value;
        mlist = list;
    }
}
