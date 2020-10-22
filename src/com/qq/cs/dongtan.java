package com.qq.cs;

import com.qq.server.Server;
import com.qq.server.chat.Server_Extension;

import java.io.File;
import java.util.StringTokenizer;

public class dongtan{
    public static void main(String[] args){
        //String data = "1A2844087515你好啊";

       // String[] a = Server_Extension.Analysis("1"+Integer.toHexString(10)+"2844087515你好啊");

//        System.out.println(a[0]);
//        System.out.println(a[1]);
//        System.out.println(a[2]);


//        System.out.println(System.getProperty());

        File[] file = (new File("/Users/baige/IT/java/QQ/data/Account number/123/data")).listFiles();
        for(File fi:file){
            if(fi.getName().equals("notice")){
                continue;//通知暂时不处理
            }
            //查询该账号是群聊还是私聊
            String a = (new StringTokenizer(fi.getName(),".").nextToken());

            System.out.println(a);

        }

    }





}