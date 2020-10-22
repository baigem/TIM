//这是个人信息修改的后端代码
package com.qq;

import com.qq.gui.Front_data_Modification;
import com.qq.tools.HeadPortrait;
import com.qq.tools.FileOperation;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.util.StringTokenizer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class After_data_Modification extends Front_data_Modification {
	DataInputStream tingTong;
    DataOutputStream huaTong;
	String user;
	qq mi;

	public After_data_Modification(qq mi){
		this.mi =mi;
		this.user = mi.user;
		//首先弄出来头像
		try{
		tingTong = new DataInputStream(mi.data.getInputStream());
        huaTong = new DataOutputStream(mi.data.getOutputStream());


        //_________先切割一下____________________________-
//		 Picture.garden(new File(System.getProperty("user.dir")+"/data/Account number/"+user+"/head_portrait/"+user+".jpg"),"Select_Avatar",this.getContentPane().getBackground());
		//______________________________________________-



			//显示头像
		HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/"+user+"/head_portrait/Select_Avatar/"+user+".jpg",headPortrait,head,100,100);
		//接下来显示可以修改的内容，接收信息

        huaTong.writeUTF("1");//接收可以修改的信息

        StringTokenizer everyLineToken = new StringTokenizer(tingTong.readUTF()," ");
        lblName.setText(everyLineToken.nextToken());//填入昵称
        lblMobile.setText(everyLineToken.nextToken());//手机
        lblEmail.setText(everyLineToken.nextToken());//邮箱
        lblEnjoy.setText(everyLineToken.nextToken());//爱好

    	}catch(Exception e){System.out.println(System.getProperty("user.dir")+"/data44"+e);}

//		/原始信息备份
    	String name = lblName.getText().trim();//昵称
    	String mobile = lblMobile.getText().trim();//手机号
    	String email = lblEmail.getText().trim();//邮箱
    	String enjoy = lblEnjoy.getText().trim();//爱好

		//鼠标点击头像时
		headPortrait.addMouseListener(new Mta());

    	//修改按钮点击后
		butSign_out.addMouseListener(new MouseAdapter(){
      		public void mouseClicked(MouseEvent e){
      			//首先判断是否为空，然后判断修改了那些信息
      			//lblName,lblMobile,lblEmail,Enjoy
      			if(lblName.getText().trim().equals("")){
      				JOptionPane.showMessageDialog(null,"昵称不可为空");
      				return;
      			}
      			if(lblMobile.getText().trim().equals("")){
      				JOptionPane.showMessageDialog(null,"手机号不可为空");
      				return;
      			}
      			if(lblEmail.getText().trim().equals("")){
      				JOptionPane.showMessageDialog(null,"邮箱不可为空");
      				return;
      			}

      			//判断完成
      			//判断哪项完成了改动
      			try{
      				huaTong.writeUTF("2");//进行信息修改
					System.out.println("正在进行信息修改");

      			if(!lblName.getText().trim().equals(name)){
      				//如果改动过改动后的传回去
      				huaTong.writeUTF("Name.txt "+lblName.getText().trim());
      			}
      			if(!lblMobile.getText().trim().equals(mobile)){//手机号
      				huaTong.writeUTF("Phone.txt "+lblMobile.getText().trim());
      			}
      			if(!lblEmail.getText().trim().equals(email)){
      				huaTong.writeUTF("Eailbox.txt "+lblEmail.getText().trim());
      			}
      			if(!lblEnjoy.getText().trim().equals(enjoy)){
      				huaTong.writeUTF("Hobby.txt "+lblEnjoy.getText().trim());
      			}


          		huaTong.writeUTF("-1");//结束
					System.out.println("修改完成");


          		if(tingTong.readBoolean()){//接收到则代表信息修改完成
          			new After_personal_Data(mi);//打开个人信息页面
          			dispose();//关闭本页面
          		}

          		}catch(Exception ex){System.out.println(System.getProperty("user.dir")+"/data_modi97"+ex);}
      		}
    	});

	}



	//修改头像Modify the Avatar
	class Mta extends MouseAdapter{
		//实现抽象类的方法
		@Override
		public void mouseClicked(MouseEvent e) {
			// 处理鼠标点击
			//点击后会发生的事
			JFileChooser jfc=new JFileChooser();//文件选择框组件
			//设置当前路径为桌面路径,否则将我的文档作为默认路径
			FileSystemView fsv = FileSystemView .getFileSystemView();//获取桌面路径
			jfc.setCurrentDirectory(fsv.getHomeDirectory());//把获取到的路径给文件选择框
			//JFileChooser.FILES_AND_DIRECTORIES 选择路径和文件
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );//只可以选择文件
			//用户选择的路径或文件
			if (jfc.showOpenDialog(new Component(){})==JFileChooser.FILES_ONLY){
				File file=jfc.getSelectedFile();//把路径给file
				//把图片发给服务器
				//首先要发送信息提醒服务器我要发照片了
				//这个时候服务器是在等待我要修改的信息
				FileInputStream touxiang = null;
				try {
					huaTong.writeUTF("4");
					touxiang = new FileInputStream(file);
					//发送数据大小，服务器好做好接收
					huaTong.writeLong(file.length());
					//发送头像后缀fileName.substring(fileName.lastIndexOf("."));
					String suffix =file.getName().substring(file.getName().indexOf("."));
					huaTong.writeUTF(suffix);

					byte[] b = new byte[1024];
					int data;
					while ((data = touxiang.read(b)) != -1) {
						huaTong.write(b,0,data);
					}
					//数据发送完成，关闭文件
					touxiang.close();
					//复制头像到用户头像文件夹
					if(FileOperation.copy(file.toString(),System.getProperty("user.dir")+"/data/Account number/"+user+"/head_portrait/"+user+suffix)){
						HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/"+user+"/head_portrait/"+user+".jpg",headPortrait,head,100,100);
						//主界面刷新
						HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/"+user+"/head_portrait/"+user+".jpg",mi.inforMation,new ImageIcon(),30,30);

					}
				} catch (IOException ioException) {
					if(touxiang != null) {
						try {
							touxiang.close();
						} catch (IOException exception) {
							exception.printStackTrace();
						}
					}
					ioException.printStackTrace();
				}

			}
		}







	}

}












