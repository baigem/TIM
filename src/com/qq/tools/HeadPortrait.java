package com.qq.tools;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

import com.qq.qq;

public class HeadPortrait {

    //改头像Change your head
    public static void Cyh(String pash,JLabel xk,ImageIcon tx){
        Cyh(pash,xk,tx,100,100);//默认值就一百
    }
    public static void Cyh(String pash,JLabel xk,ImageIcon tx,int x,int y){

        File touxiangpath = new File(pash);
        tx = new ImageIcon(touxiangpath.toString());
        tx = new ImageIcon(tx.getImage().getScaledInstance(x,y, Image.SCALE_DEFAULT));
        xk.setIcon(tx);//添加头像

    }




    //user为自己的id，id为好友id
    public static void Download_Avatar(qq mi, String user, String id){//获取头像
        try{
            System.out.println("获取头像");
            mi.datahuaTong.writeUTF("3");
            //发送获取谁的头像
            mi.datahuaTong.writeUTF(id);
            long leng = mi.datatingTong.readLong();//文件大小
            byte[] bytelen = new byte[2048];


            int mun=0;
            File touxiangpath = new File(System.getProperty("user.dir")+"/data/Account number/"+user+"/head_portrait");
            FileOutputStream touxiang = new FileOutputStream(touxiangpath+"/"+id+".jpg");//储存地址

//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            int len;

//            System.out.println("共："+leng);
            while(mun != (int)leng){

                len = mi.datatingTong.read(bytelen);
                touxiang.write(bytelen,0,len);//存到个人头像文件夹
                touxiang.flush();
                mun = mun + len;
//                System.out.println("已存入 "+len+"\t共："+mun+"\t当前时间："+df.format(new Date()));
            }
            touxiang.close();//关闭文件
            System.out.println("头像接收完毕");


        }catch(Exception e){System.out.println("qq.200"+e);}

    }







}
