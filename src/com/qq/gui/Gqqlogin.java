package com.qq.gui;
import java.awt.*;
import javax.swing.*;

public class Gqqlogin extends JFrame{
	String[] strState = {"在线","离线","隐身","在忙","空闲"};
	//背景图片
	BackgroundPanel backGroudqq = new BackgroundPanel((new ImageIcon("images/qqbj.png")).getImage());
	
	JLabel lblUser = new JLabel("账号");
	JLabel lblPassword = new JLabel("密码");
	JLabel lblState = new JLabel("状态");

	public JComboBox<String> txtUser = new JComboBox<>();//下拉框
	public JPasswordField txtPassword = new JPasswordField(10);//密码输入框
	

	public JButton butregisterAccount = new JButton("注册新账号");
	public JButton butretrievePassword = new JButton("找回密码");
	public JButton butSet = new JButton("设置");
	public JButton butSign_in = new JButton("登录");
	
	
	public JCheckBox jbrememberPassword = new JCheckBox("记住密码");
	public JCheckBox jbautomaticLogon = new JCheckBox("自动登录");

	JComboBox<String> jcbState = new JComboBox<>(strState);//下拉选择框








	//构造方法
	public Gqqlogin(){
		System.out.println("实现了构造方法");
		this.setLayout(new FlowLayout());
		this.setLayout(null);
		this.setSize(340,250);
		this.getContentPane().setBackground(new Color(228,245,255));
		ImageIcon icon = new ImageIcon("images/qq.jpg");//窗体的图标
		setIconImage(icon.getImage());
		setTitle("javaQQ");	//窗体的名字
		
		backGroudqq.setBounds(0,0,340,60);//背景图
		lblUser.setBounds(30,70,40,25);	//账号
		txtUser.setBounds(60,70,190,25);//账号下拉框
		lblPassword.setBounds(30,105,40,25);//密码
		txtPassword.setBounds(60,105,190,25);
		butregisterAccount.setBounds(230,70,112,25);//注册新账号
		butretrievePassword.setBounds(230,105,100,25);//找回密码
		lblState.setBounds(30,145,30,25);//状态
		jcbState.setBounds(60,145,80,20);
		jbrememberPassword.setBounds(140,145,80,20);//记住密码
		jbautomaticLogon.setBounds(210,145,80,20);//自动登录
		butSet.setBounds(10,185,70,20);//设置
		butSign_in.setBounds(245,185,70,20);//登录



		butregisterAccount.setBorderPainted(false);//注册账号无边框
		butretrievePassword.setBorderPainted(false);//找回密码无边框
		jbrememberPassword.setBorderPainted(false);//记住密码无边框
		jbautomaticLogon.setBorderPainted(false);//自动登录无边框

		butregisterAccount.setContentAreaFilled(false);//注册账号无填充
		butretrievePassword.setContentAreaFilled(false);//找回密码无填充
		jbrememberPassword.setContentAreaFilled(false);//记住密码无填充
		jbautomaticLogon.setContentAreaFilled(false);//自动登录无填充

		butregisterAccount.setForeground(Color.GRAY);//注册账号蓝色
		butretrievePassword.setForeground(Color.GRAY);	//找回密码设置为蓝色
		
		jbrememberPassword.setFont(new Font("微软雅黑",Font.PLAIN,12));//记住密码
		jbautomaticLogon.setFont(new Font("微软雅黑",Font.PLAIN,12));//自动登录

		this.add(backGroudqq);
		this.add(lblUser);	//账号
		this.add(txtUser);
		this.add(butregisterAccount);//注册账号
		this.add(lblPassword);	//密码
		this.add(txtPassword);
		this.add(butretrievePassword);//找回密码
		this.add(lblState);//状态
		this.add(jcbState);//下拉框
		this.add(jbrememberPassword);//记住密码
		this.add(jbautomaticLogon);//自动登录
		this.add(butSet);		//设置
		this.add(butSign_in);	//登录
    	this.setLocationRelativeTo(null);
		txtUser.setEditable(true);//下拉框可写入
		//退出时结束后台
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}


		static class BackgroundPanel extends JPanel{//背景图片
		Image im;
		public BackgroundPanel(Image im){
		this.im=im;
		this.setOpaque(true);
		}
		public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);	
		}
	}


}