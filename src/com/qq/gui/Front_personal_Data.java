package com.qq.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
//
//资料显示页面，需要头像，昵称，账号，性别，手机号，邮箱，爱好，编辑
//
//  个人信息显示前端代码
//  2020.7.30
//
//
//
public class Front_personal_Data extends JFrame{


	public ImageIcon touxiang = new ImageIcon(); //头像
	public JLabel  headPortrait = new JLabel();  //承载头像的相框
	JLabel  txtName =new JLabel("昵称：");
	JLabel  txtUser = new JLabel("账号：");
    JLabel  txtMobile= new JLabel("手机：");
    JLabel  txtEmail=new JLabel("邮箱:");
    JLabel  txtGender=new JLabel("性别：");
    JLabel  txtEnjoy=new JLabel("爱好：");
    
    public JLabel  lblGenders = new JLabel();//性别
    public JLabel	  lblUser = new JLabel();//账号
    public JLabel     lblNama = new JLabel();//昵称
    public JLabel     lblMobile =new JLabel();//手机
    public JLabel     lblEmail =new JLabel();//邮箱
    
    public JLabel     lblEnjoy = new JLabel();//爱好
      
    public JButton        butSign_out = new JButton("编辑");
    
    
    




	public Front_personal_Data(){
		this.setLayout(new FlowLayout());
        this.setLayout(null);
        this.setSize(300,450);
        this.getContentPane().setBackground(new Color(228,245,255));//颜色




        //touxiang = tx;
        




        butSign_out.setBorderPainted(false);//无边框 
        // lblUser.setBorderPainted(false);//无边框
        // lblMobile.setBorderPainted(false);//无边框
        // lblEmail.setBorderPainted(false);//无边框
        // lblEnjoy.setBorderPainted(false);//无边框


 		butSign_out.setContentAreaFilled(false);//无填充
        // lblUser.setContentAreaFilled(false);//无填充
        // lblMobile.setContentAreaFilled(false);//无填充
        // lblEmail.setContentAreaFilled(false);//无填充
        // lblEnjoy.setContentAreaFilled(false);//无填充


        // lblUser.setEditable(false);//不可编辑状态
        // lblMobile.setEditable(false);
        // lblEmail.setEditable(false);
        // lblEnjoy.setEditable(false);



        headPortrait.setBounds(100,5,100,100);//头像
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
    	  
    	  
    	  butSign_out.setBounds(230,0,70,20);//编辑



    	this.add(headPortrait);
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
        
        
        this.add(butSign_out);//编辑



        this.setLocationRelativeTo(null);//设置窗口居中
        this.setVisible(true);//窗体可见性



        

      //组件的细节设置
        //txtUser.setFont(new Font("微软雅黑",Font.PLAIN,15)); //设置字体，大小，以及形式
        
        
	}




//	public static void main(String[] args){
//		new Front_personal_Data();
//	}
}































