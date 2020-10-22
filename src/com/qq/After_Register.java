package com.qq;
import com.qq.gui.Gqqregister;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


//注册界面
public class After_Register extends Gqqregister implements Runnable{
    After_Login login;//检查网络

    public void run(){
        while(true){
            if(!login.inspectNetwork)
                butRegister.setForeground(Color.GRAY);//设置字体颜色为灰色
            else
                butRegister.setForeground(Color.BLACK);//设置字体颜色为灰色
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


	public After_Register(After_Login login){
        this.login = login;
        new Thread(this).start();

        //登录按钮触发事件
        butSign_in.addActionListener(ae -> {
            login.setVisible(true);//重新使界面显示
            dispose();//关闭窗口
        });

        //注册按钮触发事件
        butRegister.addActionListener(ae -> {
          if(!login.inspectNetwork){
            JOptionPane.showMessageDialog(null,"网络连接异常请检查网络");
            return;
          }

          try{
          login.huaTong.writeUTF("注册");//进入注册流程
          System.out.println("正在进行注册申请");
          if(login.tingTong.readBoolean())
            System.out.println("注册申请通过\n");
          }catch(IOException ex){
            System.out.println("网络异常154，一般不可能在这里出现错误");
            return;
          }

          //实现注册
          String user = lblUser.getText();//获取账号
          String password = new String(lblPassword.getPassword());//获取密码
          String confirmpassword = new String(lblConfirmPassword.getPassword());//获取重复密码
          String mobile = lblMobile.getText();//获取手机号
          String email = lblEmail.getText();//获取邮箱


          if(user.equals("")){
            JOptionPane.showMessageDialog(null,"账号不可为空");
            return;
          }
          if(password.equals("")){
            JOptionPane.showMessageDialog(null,"密码不可为空");
            return;
          }
          if(!password.equals(confirmpassword)){
            JOptionPane.showMessageDialog(null,"密码不同，请重新填写");
            return;//密码错误，结束注册
          }
          if(mobile.equals("")){
            JOptionPane.showMessageDialog(null,"手机不可为空");
            return;//密码错误，结束注册
          }
          if(email.equals("")){
            JOptionPane.showMessageDialog(null,"邮箱不可为空");
            return;//密码错误，结束注册
          }

          System.out.println("正在注册");

          StringBuilder registInfo = new StringBuilder();
          registInfo.append(user);//把账号添加到字符串
          registInfo.append("|");//添加|分隔符
          registInfo.append(password);//添加密码
          registInfo.append("|");
          registInfo.append(mobile);//添加手机号
          registInfo.append("|");
          registInfo.append(email);//添加邮箱

          //获取性别
          String gender = lblMale.isSelected()?"男":"女";
          registInfo.append("|").append(gender);
          //获取爱好
          StringBuilder honer = new StringBuilder();
          if(lblSports.isSelected())//判断体育是否被选中
               honer.append("|").append(lblSports.getText());//如果选中则输入到honer字符串中
          if(lblRead.isSelected())//判断阅读是否被选中
               honer.append("|").append(lblRead.getText());
          if(lblTravel.isSelected())//判断旅游是否被选中
               honer.append("|").append(lblTravel.getText()).append("<");

          if(honer.length() > 0)
             registInfo.append(honer);//把honer里面的内容付给registInfo字符串
          else
             registInfo.append("|无|");

          try{
          //判断是否已经注册过账号
              System.out.println("发送账号...");
              login.huaTong.writeUTF(user);//发送账号给服务器
              login.huaTong.flush();
              System.out.println("账号发送成功...");
              if(login.tingTong.readBoolean()){
                 System.out.println("账号已存在...\n退出注册\n\n");
                 JOptionPane.showMessageDialog(null,"账号已存在");
                 //退出注册流程
              }
              else{
                    System.out.println("账号不存在正在注册...");
                    login.huaTong.writeUTF(registInfo.toString());//把账号具体信息发送给服务器
                    login.huaTong.flush();
                    System.out.println("账号信息发送成功...\n\n");
                    if(login.tingTong.readBoolean()) {
                        System.out.println("注册完成");
                        JOptionPane.showMessageDialog(null, user + " 注册成功");
                        //退出注册流程
                    }else{
                        System.out.println("注册失败");
                    }
              }

          }catch(IOException ex){
              JOptionPane.showMessageDialog(null, "" +
                      "一般你不可能看到这条提示\n" +
                      "你看到了,就代表你的网络延迟应该比较大\n," +
                      "偏偏延迟的时候服务器或者你的网络断了，" +
                      "你才能看到这条提示");

          }
        });
     
        //退出按钮触发事件
        butSign_out.addActionListener(ae -> {
          System.exit(0); //关闭窗口
        });

//
	}
}











