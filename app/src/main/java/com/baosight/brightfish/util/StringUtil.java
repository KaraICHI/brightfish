package com.baosight.brightfish.util;

/**
 * Created by Administrator on 2018/1/15.
 */

public class StringUtil {
    public static String replaceNullwithBlank(String str){
        if(str==null||str=="null"){
            return "";
        }else{
            return str;
        }
    }
}
