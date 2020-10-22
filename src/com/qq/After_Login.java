package com.qq;
import com.qq.gui.Gqqlogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

/**
 * 登录功能
 *
 * 整个程序的入口
 *
 * 登录程序。可以进行登录
 *
 * 也可以从此入口进入注册入口
 *
 * 在之后还会匹配找回密码等入口
 */

public class After_Login extends Gqqlogin implements Runnable{
	SocketAddress addr;
    Socket client;
    Socket networks;
	DataOutputStream huaTong;
    DataInputStream tingTong;
    boolean inspectNetwork;//检查网络,true有网络，false无网络
    String IP = "127.0.0.1";

    //检测客户端所必须的文件夹
    static {
        File data = new File("data");
        File accountNumber = new File(System.getProperty("user.dir")+"/data/Account number");
        if(data.isDirectory()){
            if(!accountNumber.isDirectory())
                if(accountNumber.mkdir()){
                    System.out.println("创建成功");
                }
        }
        else{
            if(data.mkdir() && accountNumber.mkdir()){
                System.out.println("创建成功");
            }
        }
    }


	public After_Login(){
	    new Thread(this).start();//执行网络连接

		//注册账号鼠标移动事件
		butregisterAccount.addMouseListener(new Move(butregisterAccount));
		//找回密码鼠标移动事件
		butretrievePassword.addMouseListener(new Move(butretrievePassword));
		//登录点击事件
        butSign_in.addActionListener(new Sign_in());
        //在密码输入框按回车键事件
        txtPassword.addKeyListener(new Sign_in());

        //账号框回车事件
        txtUser.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
           public void keyTyped(KeyEvent a){}
           public void keyReleased(KeyEvent a){}//弹回执行
           public void keyPressed(KeyEvent a){//按下执行
            int key = a.getKeyCode();
                if(key == '\n') {
                    txtPassword.grabFocus();//获取焦点
                }
            }
    });

        //注册事件
        butregisterAccount.addActionListener(e -> {

        new After_Register(this);
        this.setVisible(false);//设置为不可见即可
//        dispose();//关闭本窗口，不需要
    });


        //设置
		butSet.addActionListener(e -> {
//          System.out.println("你点击了设置");
            new After_ipSetup(this);
        });
        //加载账号
      	new Load();

	}

    //负责网络连接和监控
	public void run(){
       	int i=0;
       	System.out.println("进行网络连接");

            for(inspectNetwork=false;true;inspectNetwork=false)
                try {
                    client = new Socket();
                    addr = new InetSocketAddress(IP, 9191);//获取服务器地址
                    client.connect(addr, 2000);
                    butSign_in.setForeground(Color.BLACK);//黑色
                    tingTong = new DataInputStream(client.getInputStream());
                    huaTong = new DataOutputStream(client.getOutputStream());
                    inspectNetwork = true;
                    Network net = new Network(IP);//进行实时网络监测
                    networks = net.network;
                    new Network(networks);
                    i = 0;
                    butSign_in.setForeground(Color.GRAY);//设置字体颜色为灰色
                } catch (IOException e) {
                    System.out.println(IP);
                    //e.printStackTrace();
                    if (i <= 0) {
                        JOptionPane.showMessageDialog(null, "网络连接失败请检查网络是否正常");
                        butSign_in.setForeground(Color.GRAY);//设置字体颜色为灰色
                        i++;
                    }
                    try {
                        Thread.sleep(2000);//延迟1s执行
                    } catch (Exception ignored) {}
                }
  }



  //账号加载机制
    class Load{
    public Load(){
        File usersDir = new File(System.getProperty("user.dir")+"/data/Account number");//在当前目录的文件夹下
        File[] allUser = usersDir.listFiles();//把users下的文件夹的名字给allUser数组
        assert allUser != null;
            //判断文件夹的时间，按时间排序
            Arrays.sort(allUser, (f1, f2) -> f1.lastModified()>=f2.lastModified()?-1:0);
            //把名字给下拉框
            for(File everyUser:allUser) {
                if (!everyUser.getName().equals(".DS_Store"))//剔除默认文件夹
                    txtUser.addItem(everyUser.getName());
            }

    }
  }
    //登录事件
    class Sign_in implements ActionListener,KeyListener{
        public void Sign_ins() {  //登录判断
            if (!inspectNetwork) {
                JOptionPane.showMessageDialog(null, "网络连接异常请检查网络");
                return;
            }
            String user = txtUser.getSelectedItem() + "";
            String password = new String(txtPassword.getPassword());
            if (user.equals("")) {
                JOptionPane.showMessageDialog(null, "账号不可为空");
                return;
            }
            if (password.equals("")) {
                JOptionPane.showMessageDialog(null, "密码不可为空");
                return;
            }


            try {
                huaTong.writeUTF("登录");//进入注册流程
                //判断是否已经注册过账号
                System.out.println("发送账号...");
                huaTong.writeUTF(user);//发送账号给服务器
                huaTong.flush();
                System.out.println("账号发送成功...");
                if (tingTong.readBoolean()) {
                    System.out.println("账号存在...\n验证密码中...");
                    huaTong.writeUTF(password);//发送密码
                    huaTong.flush();
                    if (tingTong.readBoolean()) {//密码正确
                        File userfile = new File(System.getProperty("user.dir")+"/data/Account number" + "/" + user+"/head_portrait");//保存头像
                        File datafile = new File(System.getProperty("user.dir")+"/data/Account number" + "/" + user+"/data");//保存本地数据
                        File Round_head = new File(System.getProperty("user.dir")+"/data/Account number" + "/" + user+"/head_portrait/Round_head/");//保存剪切好的头像
                        File Select_Avatar = new File(System.getProperty("user.dir")+"/data/Account number" + "/" + user+"/head_portrait/Select_Avatar/");//保存好友头像
                        File temporary = new File(System.getProperty("user.dir")+"/data/Account number" + "/" + user+"/head_portrait/temporary/");
                        if (!userfile.isDirectory()) //验证此账号是否为第一次登录
                            if(Round_head.mkdirs() && Select_Avatar.mkdir() && temporary.mkdir() && datafile.mkdir()){
                                System.out.println("此账号第一次登录");
                                //创建成功
                            }
                        System.out.println("密码验证成功，允许登录...");
                        //服务器查看是否有昵称
                        //关闭本窗口
                        if (!tingTong.readBoolean()) {
                            System.out.println("没有昵称，请输入");
                            new After_Nickname(huaTong,user, password, client, networks );//录入昵称并发送


                        }else
                        new qq(user, password, client, networks);
                        System.gc();//启动垃圾回收
                        dispose();//关闭本窗口

                    } else {//密码错误
                        System.out.println("密码错误，请重新输入密码\n");
                        JOptionPane.showMessageDialog(null, "密码错误");
                        //退出类
                    }
                } else {
                    System.out.println("账号不存在，请注册后登录...\n");
                    JOptionPane.showMessageDialog(null, "账号不存在，请注册后登录");
                    //退出类
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "服务器出现错误");
                //退出类
            }
        }

        @Override//按钮点击
        public void actionPerformed(ActionEvent e) {
            Sign_ins();
        }
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override//回车触发
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == '\n')
                Sign_ins();
        }
        @Override
        public void keyReleased(KeyEvent e) {}
    }

    //鼠标移动标题变色
    static class Move extends MouseAdapter{//鼠标移动变色事件
        JButton a;
        public Move(JButton a){
            this.a=a;
        }
        public void mouseEntered(MouseEvent e){
            // 鼠标进入字体变为蓝色
            a.setForeground(new Color(40,88,96));
        }
        public void mouseExited(MouseEvent e) {
            // 鼠标退出字体变为灰色
            a.setForeground(Color.GRAY);//颜色
        }
    }


//入口
public static void main(String[] args){
        new After_Login();
        
    }
















}