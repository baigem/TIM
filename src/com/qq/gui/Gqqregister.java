package com.qq.gui;
import java.awt.*;
import javax.swing.*;


 public class Gqqregister extends JFrame{
      
      JLabel  txtUser =new JLabel("账号：");
      JLabel  txtPassword =new JLabel("密码：");
      JLabel  txtConfirmPassword=new JLabel("确认密码:");
      JLabel  txtMobile= new JLabel("手机：");
      JLabel  txtEmail=new JLabel("电子邮箱:");
      JLabel  txtGender=new JLabel("性别：");
      JLabel  txtEnjoy=new JLabel("爱好：");


      public JTextField     lblUser = new JTextField(10);//账号
      public JPasswordField lblPassword = new JPasswordField(10);//密码
      public JPasswordField lblConfirmPassword =new JPasswordField(10);
      public JTextField     lblMobile =new JTextField(10);//手机
      public JTextField     lblEmail =new JTextField(10);//邮箱
      public JRadioButton   lblMale = new JRadioButton("男");
      public JRadioButton   lblFemale =new JRadioButton("女");
      public JCheckBox      lblSports = new  JCheckBox("体育");
      public JCheckBox      lblRead = new  JCheckBox("阅读");
      public JCheckBox      lblTravel = new JCheckBox("旅游");
      public JButton        butSign_in = new JButton("登录");
      public JButton        butSign_out = new JButton("退出");
      public JButton        butRegister = new JButton("注册");
      
      ButtonGroup    genderGroup = new ButtonGroup(); 

      

      public Gqqregister(){
        
        this.setLayout(new FlowLayout());
        this.setLayout(null);
        this.setSize(250,360);
        this.getContentPane().setBackground(new Color(228,245,255));//颜色

        txtUser.setBounds(10,10,50,30);//账号
	      lblUser.setBounds(80,10,150,30);//输入框
        txtPassword.setBounds(10,50,50,30);//密码
        lblPassword.setBounds(80,50,150,30);
        txtConfirmPassword.setBounds(10,90,60,30);//确认密码
        lblConfirmPassword.setBounds(80,90,150,30);
   	    txtMobile.setBounds(10,130,40,30);//手机
     	  lblMobile.setBounds(80,130,150,30);
  	    txtEmail.setBounds(10,170,60,30);//邮箱
  	    lblEmail.setBounds(80,170,150,30);
     	  txtGender.setBounds(10,210,40,30);//性别
    	  lblMale.setBounds(80,210,50,30);//男
    	  lblFemale.setBounds(140,210,50,30);//女
  	    txtEnjoy.setBounds(10,250,50,30);//爱好
    	  lblSports.setBounds(80,250,60,30);//体育
    	  lblRead.setBounds(130,250,60,30);//阅读
    	  lblTravel.setBounds(180,250,60,30);//旅游
    	  butSign_in.setBounds(20,290,60,30);//登录
    	  butSign_out.setBounds(100,290,60,30);//退出
    	  butRegister.setBounds(170,290,60,30);//注册

        this.add(lblUser);//账号
        this.add(txtUser);
        this.add(txtPassword);
        this.add(lblPassword);
        this.add(txtConfirmPassword);
        this.add(lblConfirmPassword);
        this.add(txtMobile);//手机
        this.add(lblMobile);
        this.add(txtEmail);//邮箱
        this.add(lblEmail);
        this.add(txtGender);//性别
        this.add(lblMale);//男
        this.add(lblFemale);//女
        this.add(txtEnjoy); //爱好
        this.add(lblRead);//阅读
        this.add(lblSports);//体育
        this.add(lblTravel);//旅游
        this.add(butSign_in);//登录
        this.add(butRegister);//注册
        this.add(butSign_out);//退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出时杀死后台
        this.setVisible(true);//窗体可见性



        lblMale.setContentAreaFilled(false);
        lblFemale.setContentAreaFilled(false);
        lblRead.setContentAreaFilled(false);
        lblSports.setContentAreaFilled(false);
        lblTravel.setContentAreaFilled(false);

        //组件的细节设置
        txtUser.setFont(new Font("微软雅黑",Font.PLAIN,15)); //设置字体，大小，以及形式
        txtPassword.setForeground(Color.RED);//设置字体颜色为红色
        lblMale.setSelected(true);//默认选择为男性
        genderGroup.add(lblMale);//性别组二选一
        genderGroup.add(lblFemale);

      }//Gqqregister结尾


}





















