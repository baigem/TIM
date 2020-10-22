package com.qq.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

/*
*	修改资料界面前端
* 主要修改昵称，手机，邮箱，爱好
* 目前不支持修改，性别，账号，密码
* 2020.7.30定
*/


public class Front_data_Modification extends JFrame{
	    JLabel  txtName =new JLabel("昵称：");
      JLabel  txtMobile= new JLabel("手机：");
      JLabel  txtEmail=new JLabel("邮箱:");
      // JLabel  txtGender=new JLabel("性别：");
      JLabel  txtEnjoy=new JLabel("爱好：");

      public ImageIcon head = new ImageIcon(); //头像
      public JLabel  headPortrait = new JLabel();  //承载头像的相框
      public JTextField     lblName = new JTextField(10);//昵称
      public JTextField     lblMobile =new JTextField(10);//手机
      public JTextField     lblEmail =new JTextField(10);//邮箱
      // public JRadioButton   lblMale = new JRadioButton("男");
      // public JRadioButton   lblFemale =new JRadioButton("女");
      public JTextField     lblEnjoy = new JTextField();//爱好


      public JButton        butSign_out = new JButton("修改");
      
      //ButtonGroup    genderGroup = new ButtonGroup();

	public Front_data_Modification(){
		this.setLayout(new FlowLayout());
        this.setLayout(null);
        this.setSize(300,450);
        this.getContentPane().setBackground(new Color(228,245,255));//颜色


        headPortrait.setBounds(100,5,100,100);//头像
        txtName.setBounds(30,150,40,25);//昵称
	      lblName.setBounds(100,150,150,25);//输入框
   	    txtMobile.setBounds(30,190,40,25);//手机
     	  lblMobile.setBounds(100,190,150,25);
  	    txtEmail.setBounds(30,230,40,25);//邮箱
  	    lblEmail.setBounds(100,230,150,25);
     	  txtEnjoy.setBounds(30,270,40,25);//爱好
     	  lblEnjoy.setBounds(100,270,150,25);//
  	    // txtGender.setBounds(10,160,40,30);//性别
    	  // lblMale.setBounds(80,160,50,30);//男
    	  // lblFemale.setBounds(140,160,50,30);//女

        lblName.setBorder(new EmptyBorder(0,0,0,0));//无边框
    	  lblMobile.setBorder(new EmptyBorder(0,0,0,0));
        lblEmail.setBorder(new EmptyBorder(0,0,0,0));
        lblEnjoy.setBorder(new EmptyBorder(0,0,0,0));

       //  lblName.setContentAreaFilled(false);//无填充
       //  lblMobile.setContentAreaFilled(false);
    	  // lblEmail.setContentAreaFilled(false);
       //  lblEnjoy.setContentAreaFilled(false);




    	  butSign_out.setBounds(125,350,50,30);//修改
        this.add(headPortrait);//头像
        this.add(txtName);//昵称
        this.add(lblName);
        this.add(txtMobile);//手机
        this.add(lblMobile);
        this.add(txtEmail);//邮箱
        this.add(lblEmail);
        this.add(txtEnjoy);//爱好
        this.add(lblEnjoy);
        // this.add(txtGender);//性别
        // this.add(lblMale);//男
        // this.add(lblFemale);//女
     
        
        this.setLocationRelativeTo(null);//设置窗口居中
        this.add(butSign_out);//修改
        this.setVisible(true);//窗体可见性



        // lblMale.setContentAreaFilled(false);
        // lblFemale.setContentAreaFilled(false);//无填充

      //组件的细节设置
        //txtName.setFont(new Font("微软雅黑",Font.PLAIN,15)); //设置字体，大小，以及形式
        // lblMale.setSelected(true);//默认选择为男性
        // genderGroup.add(lblMale);//性别组二选一
        // genderGroup.add(lblFemale);
        
        

	}


	// public static void main(String[] args){
	// 	new Front_data_Modification("2844087515");
	// }
}







