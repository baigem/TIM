package com.qq.lookup.gui;

import javax.swing.*;
import java.awt.*;

/**
 *
 * 查找到用户
 * 显示用户的信息
 *
 */



public class Front_user_profile extends JFrame{

    //____________好友资料________________________________________________________________________________
    public ImageIcon touxiang = new ImageIcon(); //头像
    public JLabel  headPortrait = new JLabel();  //承载头像的相框

    public JLabel txtName =new JLabel("昵称：");
    public JLabel  txtUser = new JLabel("账号：");
    public JLabel  txtMobile= new JLabel("手机：");
    public JLabel  txtEmail=new JLabel("邮箱:");
    public JLabel  txtGender=new JLabel("性别：");
    public JLabel  txtEnjoy=new JLabel("爱好：");

    public JLabel  lblGenders = new JLabel();//性别
    public JLabel	  lblUser = new JLabel();//账号
    public JLabel     lblNama = new JLabel();//昵称
    public JLabel     lblMobile =new JLabel();//手机
    public JLabel     lblEmail =new JLabel();//邮箱
    public JLabel     lblEnjoy = new JLabel();//爱好

    public JButton        add = new JButton();

    //____________________________________________________________________________________________




    public Front_user_profile(){
        this.setLayout(new FlowLayout());
        this.setLayout(null);
        this.setSize(300,450);
        this.getContentPane().setBackground(new Color(228,245,255));//颜色


        headPortrait.setBounds(100,5,100,100);//头像
        //头像暂时不传输
        txtName.setBounds(30,150,40,30);//昵称
        lblNama.setBounds(100,150,150,30);//输入框
        txtUser.setBounds(30,190,40,30);//账号
        lblUser.setBounds(100,190,150,30);

        txtGender.setBounds(30,230,40,30);//性别
        lblGenders.setBounds(100,230,150,30);//男或者女
        txtMobile.setBounds(30,270,40,30);//手机
        lblMobile.setBounds(100,270,150,30);
        txtEmail.setBounds(30,310,80,30);//邮箱
        lblEmail.setBounds(100,310,150,30);
        txtEnjoy.setBounds(30,350,40,30);//爱好
        lblEnjoy.setBounds(100,350,150,30);//
        add.setBounds(100,400,100,25);

        this.add(headPortrait);//头像
        this.add(txtName);//昵称
        this.add(lblNama);
        this.add(txtUser);//账号
        this.add(lblUser);
        this.add(txtMobile);//手机
        this.add(lblMobile);
        this.add(txtEmail);//邮箱
        this.add(lblEmail);
        this.add(txtEnjoy);//爱好

        this.add(txtGender);//性别
        this.add(lblGenders);
        this.add(lblEnjoy);//爱好
        this.add(add);//编辑

        add.setFocusPainted(false);

        this.setLocationRelativeTo(null);//设置窗口居中
        this.setVisible(true);//窗体可见性

    }

//    public static void main(String[] args){
//
//
//        new Front_user_profile();
//    }



}
