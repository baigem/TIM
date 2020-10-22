package com.qq.tools;

public class Agreement {


    public static String[] analysis(String str){
        char[] cstr = str.toCharArray();
        String[] mun = new String[3];
       // System.out.println("信息解析中 "+str);
        mun[0] = String.valueOf(cstr[0]);


        if(cstr[1]>48 && cstr[1]<57){
            mun[1] = str.substring(2,cstr[1]-'0'+2);//获取账号
            mun[2] = str.substring(cstr[1]-'0'+2);//获取消息内容
        }
        else{
            mun[1] = str.substring(2,cstr[1]-'W'+2);//获取账号
            mun[2] = str.substring(cstr[1]-'W'+2);//获取消息内容
        }
        //这样就将协议解析完毕
//        System.out.println("解析完成");
        return mun;
    }
}
