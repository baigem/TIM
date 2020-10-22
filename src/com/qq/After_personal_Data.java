//个人信息显示后端代码
//此文件用来显示个人信息，也就是点击头像出现的那个页面
//
package com.qq;

import com.qq.gui.Front_personal_Data;
import com.qq.tools.HeadPortrait;
import com.qq.tools.Picture;

import java.awt.event.*;
import java.io.File;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.StringTokenizer;
import java.awt.Color;


public class After_personal_Data extends Front_personal_Data{//

	 DataInputStream tingTong;	
     DataOutputStream huaTong;

	/**
    //显示个人资料后端代码
    //头像，昵称，账号，性别，手机号，邮箱，爱好，编辑.touxiang,lblUser,lblNama,lblMobile,lblEmail,lblEnjoy


	 */

	



	public After_personal_Data (qq mi){

	try{
		//
		//告诉服务器我要看个人资料
		//
		huaTong = new DataOutputStream(mi.data.getOutputStream());
		tingTong  = new DataInputStream(mi.data.getInputStream());
		huaTong.writeUTF("0");
		File touxiangpath = new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/"+mi.user+".jpg");



		//_________先切割一下____________________________-

		Picture.garden(touxiangpath,"Select_Avatar",this.getContentPane().getBackground());

		//______________________________________________-

		//显示本地的头像
		HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/Select_Avatar/"+mi.user+".jpg",headPortrait,touxiang,100,100);//添加头像
		//获取昵称，账号，性别，手机号，邮箱，爱好
		String xingxi = tingTong.readUTF();//获取注册信息
		StringTokenizer everyLineToken = new StringTokenizer(xingxi," ");
		 lblNama.setText(everyLineToken.nextToken());//昵称
		 lblUser.setText(everyLineToken.nextToken());//账号
		 lblGenders.setText(everyLineToken.nextToken());//性别
		 lblMobile.setText(everyLineToken.nextToken());//手机号
		 lblEmail.setText(everyLineToken.nextToken());//邮箱
		 lblEnjoy.setText(everyLineToken.nextToken());//爱好
		this.requestFocus();//获取焦点

		//编辑鼠标移动事件
		butSign_out.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
               // 鼠标进入字体变为蓝色
  				butSign_out.setForeground(new Color(40,88,96));
            }
            public void mouseExited(MouseEvent e) {
                // 鼠标退出字体变为灰色
           		butSign_out.setForeground(Color.GRAY);//颜色
            }
		});

		//编辑
		butSign_out.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				//查看个人信息
				new After_data_Modification(mi);
				dispose();
				System.gc();//启动垃圾回收
			}
		});

		//在界面上按回车键关闭页面
		this.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == '\n'){
					dispose();
					System.gc();
				}
			}
		});



	}catch(Exception e){
		System.out.println("54"+e);
		e.printStackTrace();

	}

	}
}
