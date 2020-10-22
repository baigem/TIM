package com.company.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Server2 {
    public static void main(String[] args)  throws Exception{
        byte[] byteArray=new byte[2048];
        ServerSocket serverSocket=new ServerSocket(8088);
        Socket socket=serverSocket.accept();

        InputStream inputStream=socket.getInputStream();//接收客户端发来的图片
        int readLength=inputStream.read(byteArray);//把接受的信息发送給变量
        String a = readLength+"";
        FileOutputStream pngoutputstream=new FileOutputStream(new File("touxiang/2.jpg"));//储存的地址

        while (readLength!=-1){
            readLength = Integer.parseInt(a);
            System.out.println("发送"+readLength);
            pngoutputstream.write(byteArray,0,readLength);//把接收的信息储存进去
            readLength=inputStream.read(byteArray);//重新接收
        }
        pngoutputstream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}
