package com.qq;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Network{
    Socket network = new Socket();
    DataInputStream tingTong;
    DataOutputStream huaTong;


    public Network(String ip){
        try{
            network = new Socket(ip,9191);
            huaTong = new DataOutputStream(network.getOutputStream());
            tingTong = new DataInputStream(network.getInputStream());
            System.out.println("执行网络监测组件");
            huaTong.writeUTF("网络监测");
            String apply = tingTong.readUTF();
            if(apply.equals("1"))
                System.out.println("监测申请通过");
            else{
                System.out.println("监测申请不通过\n\n");
            }
        }catch(Exception ex){
          System.out.println("网络异常...net31"+ex);
        }

    }
    public Network(Socket qclient){
        try{
            tingTong = new DataInputStream(qclient.getInputStream());
            tingTong.readUTF();//一直阻塞这里，倾听服务器发来的信息，但凡断开就意味着网络断开
        }catch(Exception e){
            System.out.println("网络监测结束...net42");
        }
    }

    // public static void main(String[] args){
    //     new Network();
    // }
}