package com.qq.gui;
import javax.swing.*;
import java.awt.*;


public class Front_Nickname extends JFrame{
	JLabel lblnicknames = new JLabel("请设置聊天昵称");
	JLabel lblnickname = new JLabel("昵称:");
	public JTextField txtnickname = new JTextField(20);
	public JButton enter = new JButton("确定");

	public Front_Nickname(){
		this.setLayout(new FlowLayout());
		this.setLayout(null);
		this.setSize(300,200);

		lblnicknames.setBounds(70,40,160,30);
		lblnickname.setBounds(25,100,50,30);
		txtnickname.setBounds(65,100,160,30);
		enter.setBounds(235,100,50,30);


		this.add(lblnicknames);
		this.add(lblnickname);
		this.add(txtnickname);
		this.add(enter);


		lblnicknames.setFont(new Font("微软雅黑",Font.PLAIN,22));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		System.out.println("界面理应创建成功");
	}
}