package com.qq;
import com.qq.gui.*;
import java.io.*;
import java.awt.event.*;
import java.net.Socket;
import javax.swing.*;

//设置昵称的后端按钮enter，输入框txtAfter_Nickname
public class After_Nickname extends Front_Nickname{
	boolean confirm = true;
	public After_Nickname(DataOutputStream huaTong, String user, String  password, Socket client,Socket networks){

		//确定点击事件
		enter.addActionListener(e -> {
			//如果点击按钮就把内容发送给服务器
			try{
				if(enter(huaTong)){
					new qq(user, password, client, networks);
					dispose();//关闭本窗口
				}
			}catch(Exception w){System.out.println("an33"+w);}
		});

		txtnickname.addKeyListener(new KeyListener(){
           	public void keyTyped(KeyEvent a){}
           	public void keyReleased(KeyEvent a){}//弹回执行
           	public void keyPressed(KeyEvent a){//按下执行
           	int key = a.getKeyCode();
                if(key == '\n'){
                	try{
                	if(enter(huaTong)){
                		new qq(user, password, client, networks);
            			dispose();//关闭本窗口
            		}
            		}catch(Exception w){System.out.println("an47"+w);}
          		}	
            }  //按下回车事件
  		});
		
	}

	
	//获取输入框中的name，并发送给服务器
	Boolean enter(DataOutputStream huaTong){
		String name = txtnickname.getText();//获取输入框中的内容
		if(!name.equals("")){
			try{
				huaTong.writeUTF(name);//把昵称发送给服务器
				confirm = false;
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"网络异常，发送失败");
			}	
			return true;
		}
		else
		JOptionPane.showMessageDialog(null,"昵称不能为空");
		return false;//发送功能
	}


	

}