package com.qq.server;

import com.qq.file.File_Control;
import com.qq.server.chat.Personal_data;
import com.qq.server.chat.Server_Extension;
import com.qq.tools.FileOperation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Server extends Thread{
	//总机
	ServerSocket srvSocket;
	public static HashMap<String ,Socket> allSocket = new HashMap<>();//定义集合
	public static HashMap<String ,DataOutputStream> allhuaTong = new HashMap<>();
	public static HashMap<String,HashMap<String,DataOutputStream>> allgroup = new HashMap<>();
	public static File_Control User;
	public static File_Control Name;
	public static File_Control Phone;
	public static File_Control Eailbox;
	public static File_Control Gender;
	public static File_Control Hobby;
	public static String PATH = System.getProperty("user.dir");//当前程序路径
	
	
	
	
	
	static {//检测文件



		System.out.println("开始检测各个文件夹是否存在");
		//记得添加群文件
		File dataPath = new File(PATH+"/Server_data");
		if(!dataPath.isDirectory()){
			Creation(dataPath);//不存在则创建
		}
		File userPath = new File(dataPath+"/User_information");
//		if(!userPath.isDirectory()){
//			Creation(userPath);
//		}
		try{
			File naMe = new File(userPath+"/Name.txt");//创建文件路径
			if(!naMe.exists()){//判断此文件是否存在
				Creation(naMe);
			}
			File usersFile = new File(userPath+"/User.txt");//创建文件路径
			if(!usersFile.exists()){//判断此文件是否存在
				Creation(usersFile);
			}
			File number = new File(userPath+"/Phone.txt");
			if(!number.exists()){
				Creation(number);
			}

			File Eailbox = new File(userPath+"/Eailbox.txt");
			if(!Eailbox.exists()){
				Creation(Eailbox);
			}

			File gender = new File(userPath+"/Gender.txt");
			if(!gender.exists()){
				Creation(gender);
			}

			File hobby = new File(userPath+"/Hobby.txt");
			if(!hobby.exists()){
				Creation(hobby);
			}

			File Group = new File(userPath+"/Group.txt");
			if(!Group.exists()){
				Creation(Group);
			}

			File User_Friends = new File(dataPath+"/User_Friends/Group/");
			if(!User_Friends.exists()){
				Creation(User_Friends);
			}

			File Groups = new File(dataPath+"/Group/");//群文件
			if(!Groups.exists()){
				Creation(Groups);
			}

			File Notice = new File(Groups+"Notice/");//公告文件
			if(!Notice.exists()){
				Creation(Notice);
			}

			File data = new File(Groups+"Data/");//群聊天记录文件
			if(!data.exists()){
				Creation(data);
			}


			//_____________________________________________________________
			//读取所有的群
			try {
				BufferedReader qun = new BufferedReader(new FileReader(PATH+"/Server_data/User_information/Group.txt"));
				String hang;
				StringTokenizer lin = null;
				while ((hang = qun.readLine()) != null) {
					lin = new StringTokenizer(hang, " ");
					allgroup.put(lin.nextToken(), new HashMap<>());
				}

			} catch (IOException e) {
				e.printStackTrace();
			}


			//______________________________________________________________


		}catch(Exception e){
			System.out.println("文件创建失败"+e);
		}

		User = new File_Control(userPath+"/User.txt","User");
		Name = new File_Control(userPath+"/Name.txt","Name");
		Phone = new File_Control(userPath+"/Phone.txt","Phone");
		Eailbox = new File_Control(userPath+"/Eailbox.txt","Eailbox");
		Gender = new File_Control(userPath+"/Gender.txt","Gender");
		Hobby = new File_Control(userPath+"/Hobby.txt","Hobby");
	}

	//创建文件或文件夹
	public static void Creation(File file){
			String name = file.getName();
			int res = name.indexOf(".");
		try {
			if (res == -1) {
				System.out.println(file.getName()+"是一个文件夹");
				//如果是目录的话,直接创建
				if (!file.mkdirs()) {
					//创建失败
					System.out.println(file + "创建失败");
				} else {
					System.out.println(file + "创建成功");
				}
			} else {
				//这是一个文件
				//获取父路径
				System.out.println(file.getName()+"是一个文件");
				File fu = new File(file.getParent());
				//判断夫路径是否纯债
				if(!fu.isDirectory()){
					//不纯债则创建
					if (!fu.mkdirs()) {
						System.out.println(fu + "创建失败");
					}
				}
				if (!file.createNewFile()) {
					System.out.println(file + "创建失败");
				} else {
					System.out.println(file + "创建成功");
				}

			}
		}catch (Exception e){
			e.printStackTrace();
		}


	}


	//创建文件异常
//	static class File_or_folderException extends Exception{
//		public File_or_folderException(){
//			super("这不是一个文件或者文件夹");
//		}
//		public File_or_folderException(String a){ super(a); }
//	}

	public static void main(String[] args){
		File recordFile = new File("record.txt");//创建文件路径
		try{
            if(!recordFile.exists())//判断此文件是否存在
              Creation(recordFile);//不存在则创建一个
        }catch(Exception e){System.out.println("16");}

        
		Scanner input = new Scanner(System.in);
		new Server();
		while(true){
			String exet = input.next();
			if(exet.equals("no"))
				System.exit(0);
		}
	}


	public Server(){//启动服务器监测客户端来电
		try{
		srvSocket = new ServerSocket(9191);	//实现总机
		start();//启动run线程
 		System.out.println("服务器已启动，等待客户连接........");
		}catch(IOException ex){
			System.out.println("端口被占用！");
		}
	}

	public void run(){//给来电的客户端根据申请分配不同的内部类

			while (true) {
				Socket client = null;//若没有ip地址加端口号打进来，则一直阻塞这里
				try {
					client = srvSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}
				assert client != null;
				new Transfer(client);
			}

	}
	
	//中转站
	public static class Transfer extends Thread{
		Socket client;
		DataInputStream tingTong;//创建听筒
		String qtYpe = null;


		

		public Transfer(Socket clients){//构造方法
		client = clients;
		try{
		tingTong = new DataInputStream(clients.getInputStream());
		start();
		}catch(IOException ex){
			System.out.println("客户端下线58");
		}
			
		}


		public void run(){
						//判断此申请的要求
			while(true){
			try{	
				qtYpe = tingTong.readUTF();//
				System.out.println("接到连接申请");
				System.out.println("申请要求："+qtYpe);
			}catch(IOException ex){
				System.out.println("客户端下线82");
				return;
			}


			switch (qtYpe) {
				case "聊天":
						(new Server_Extension()).Extension(client);//启动一个Extension方法，并把client当做形参传递
						return;
				case "个人信息":
						new Personal_data(client);
						return;
				case "注册":
						new Register(client);
						break;
				case "登录":
						new Sign_in(client);
						break;

				case "网络监测":
						new Network(client);
						return;
				default:
						System.out.println("申请错误，此申请不属于注册、登录或登录成功！");
						System.out.println(qtYpe);
						break;
				}
			}
		}
	}

	

	//注册功能内部类
	public static class Register extends Thread{
		//Socket socket;
		DataOutputStream huaTong;
		DataInputStream tingTong; 
		String user;
		PrintWriter toFile;
		public Register(Socket client){
			try{
			huaTong = new DataOutputStream(client.getOutputStream());
        	tingTong = new DataInputStream(client.getInputStream());
			
			huaTong.writeBoolean(true);
			user = tingTong.readUTF();//获取注册的账号
			}catch(IOException ex){
				System.out.println("客户端意外下线，无法获取账号信息，关闭注册进程...\n\n");
				return;
			}

			boolean state = false;//判断是否有此账号;
			try {
				state = FileOperation.lookup(User.getRead(),user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String registInfo = null;

			try {
				huaTong.writeBoolean(state);
				if(state) {
					System.out.println("存在账号");
					return;
				}
				System.out.println("经检测此账号没有注册，允许账号注册请求\n获取账号信息中...");
				registInfo = tingTong.readUTF();//获取注册信息
			}catch(IOException ex){
				ex.printStackTrace();
				System.out.println("客户端已下线，退出注册流程");
			}




			try {
          		System.out.println("信息获取成功\n写入中...");
				assert null != registInfo;
				StringTokenizer everyLineToken = new StringTokenizer(registInfo,"|");//以|进行分割
				//需要读字符串进行提取

          		toFile = User.setWrite();//获取写入权限
                toFile.println(everyLineToken.nextToken()+" "+everyLineToken.nextToken());//写入账号密码
                toFile.close();//关闭文件
                //打开电话号码文件
                
                toFile = Phone.setWrite();
                toFile.println(user+" "+everyLineToken.nextToken());//写入账号和手机号码
                toFile.close();//关闭文件

                
                toFile =Eailbox.setWrite();
                toFile.println(user+" "+everyLineToken.nextToken());//写入账号和邮箱
                toFile.close();//关闭文件

                
                toFile = Gender.setWrite();
                toFile.println(user+" "+everyLineToken.nextToken());//写入账号和性别
                toFile.close();//关闭文件

                
                toFile = Hobby.setWrite();
                toFile.println(user+" "+everyLineToken.nextToken());//写入账号和爱好
                toFile.close();//关闭文件

				//设置默认头像，复制一个头像到文件夹中
				//如果复制成功会返回true，否则返回false
				huaTong.writeBoolean(FileOperation.copy(PATH+"/Server_data/User_Avatar/默认头像.jpg",PATH+"/Server_data/User_Avatar/"+user+".jpg"));
				huaTong.flush();
				//创建一个好友文件夹
				File haoyou = new File(PATH+"/Server_data/User_Friends/"+user+".txt");
				if(!haoyou.createNewFile()){
					System.out.println("好友文件创建失败");
				}
				haoyou = new File(PATH+"/Server_data/User_Friends/Group/"+user+".txt");
				if(!haoyou.createNewFile()){
					System.out.println("群文件创建失败");
				}
				haoyou = new File(PATH+"/Server_data/Data/"+user+"/");
				if(!haoyou.mkdirs()){
					System.out.println("聊天记录文件夹创建失败");
				}
				System.out.println("写入成功！");
			}catch(IOException ex){
				try {
					huaTong.writeBoolean(false);
				} catch (IOException e) {
					System.out.println("客户端已下线，退出注册流程316server");
				}
				if(toFile != null)//如果因为异常导致文件没有关闭则在这里关闭
					toFile.close();
				System.out.println("客户端已下线，退出注册流程");
			}
				
		}	
	}

	//登录功能内部类
	public static class Sign_in extends Thread{
		DataOutputStream huaTong;
		DataInputStream tingTong; 
		public Sign_in(Socket client){
			try{
			huaTong = new DataOutputStream(client.getOutputStream());
        	tingTong = new DataInputStream(client.getInputStream());
			}catch(IOException ex){
				System.out.println("客户端意外下线\n\n");
			}

			BufferedReader formFile = null;
			String everyLine;//创建字符串并赋值

			try{
				try {
					formFile=User.getRead();//打开此文件
				} catch (Exception e) {
					e.printStackTrace();
				}
				String user = tingTong.readUTF();//获取注册的账号名字
          		System.out.println("获取账号成功");
          		String lineUser;
          		String linepassword;
          		StringTokenizer everyLineToken;
          		while(true){
					assert formFile != null;
					if ((everyLine = formFile.readLine()) == null) break;//判断获取的内容是否为空，为空则到达文件末尾
                	System.out.println("进行判断");
                	everyLineToken = new StringTokenizer(everyLine," ");//以|进行分割字符串
                	lineUser = everyLineToken.nextToken();//把获取到的账号赋予字符串
                	linepassword = everyLineToken.nextToken();//密码
                	if(user.equals(lineUser)){//判断文件中是否有这个账号
                		System.out.println("账号存在，正在验证密码...");
                  		huaTong.writeBoolean(true);//有则发送一个1
                  		huaTong.flush();
                  		String password = tingTong.readUTF();
                  		if(password.equals(linepassword)){
                  			huaTong.writeBoolean(true);//密码正确发送一个1
                  			huaTong.flush();
                  			formFile.close();//关闭文件
                  			//判断是否有昵称
							try {
								formFile=Name.getRead();//打开此文件
							} catch (Exception e) {
								e.printStackTrace();
							}
							while((everyLine = formFile.readLine())!=null){
								everyLineToken = new StringTokenizer(everyLine," ");
								String ID = everyLineToken.nextToken();//获取账号
								if(ID.equals(user)){//判断
									huaTong.writeBoolean(true);//有昵称
									formFile.close();//关闭文件
									return;
								}
              				}

              				formFile.close();//关闭文件
                  			huaTong.writeBoolean(false);//没有昵称
                  			System.out.println("等待昵称到来");
                  			String name = tingTong.readUTF();
                  			
                  			System.out.println("录入成功");
                  			PrintWriter toFile = Name.setWrite();
                  			toFile.println(user+" "+name);
                 			toFile.close();
        					System.out.println("密码正确，登录成功\n\n");
						}
              			else{
              				huaTong.writeBoolean(false);
                  			huaTong.flush();
              				System.out.println("密码错误\n\n");
              				formFile.close();//关闭文件
						}
						return;
					}
            	}

				huaTong.writeBoolean(false);
				huaTong.flush();
				System.out.println("账号未注册\n\n");
				formFile.close();//关闭文件

			}catch(IOException ex){
				System.out.println("客户端已下线，退出登录流程\n\n");
			}

		}
	}

	//网络监控内部类
	public static class Network extends Thread{
		DataInputStream tingTong;
    	DataOutputStream huaTong;
		public Network(Socket client){
				try{
			 	huaTong = new DataOutputStream(client.getOutputStream());
			 	tingTong = new DataInputStream(client.getInputStream());
				}catch(IOException ex){
					System.out.println("客户端网络异常");
				}
				start();
				
		}

		public void run(){
			try{
				huaTong.writeUTF("1");
				tingTong.readUTF();
			}catch(Exception ex){
					try{
					Thread.sleep(100);
					}catch(Exception e){System.out.println("server415"+e);}
					System.out.println("网络监控结束\n\n\n");

			}

		}
	}




			
}//这里是Server结尾


















