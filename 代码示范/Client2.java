package com.company.s3;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;


//客户端把图片发给服务器

public class Client2 {
    public static void main(String[] args)  throws Exception{
       String pngFile="touxiang/客户端.jpg";
        FileInputStream pngstream=new FileInputStream(new File(pngFile));//读取图片


        byte[] byteArray=new byte[2048];//一次发送的大小

        System.out.println("socket begin "+System.currentTimeMillis());



        Socket socket=new Socket("localhost",8088);//连接服务器

        System.out.println("socket end"+System.currentTimeMillis());

        OutputStream outputStream=socket.getOutputStream();//发送图片

        int readLength=pngstream.read(byteArray);//把读取到的图片发送给变量


        while (readLength!=-1){
            System.out.println("接收："+readLength);
            outputStream.write(byteArray,0,readLength);
            readLength=pngstream.read(byteArray);
        }
        outputStream.close();
        pngstream.close();
        socket.close();

    }
}