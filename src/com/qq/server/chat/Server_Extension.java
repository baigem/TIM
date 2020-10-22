package com.qq.server.chat;

import com.qq.server.Server;
import com.qq.tools.Agreement;
import com.qq.tools.FileOperation;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static com.qq.server.Server.PATH;


/*
 *前两个为前缀用来解析服务器应该提供的服务
 * 定义协议：
 *  	  前缀一				前缀二，对方id的位数
 *		   		1：私聊
 *		   		2：群聊
 *
 *
 *
 *
 */
	public class Server_Extension extends Thread{//分机
		Socket socket;//创建客户端主机
		DataOutputStream huaTong;//创建话筒
		DataInputStream tingTong;//创建听筒
		DataOutputStream huaTongs;
		String name = null;
		String user = null;
		SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式



	public void Extension(Socket client){//有参方法



			socket = client;//把接收到的ip地址和端口赋予此方法里面的socket，并作为一个独立线程持续运转
			try{
				tingTong = new DataInputStream(client.getInputStream());
                huaTong = new DataOutputStream(client.getOutputStream());
                huaTongs = huaTong;

				String user = tingTong.readUTF();
				String password = tingTong.readUTF();
				this.user = user;





				if(!FileOperation.retrieval(Server.User.getRead(),user,password)){
					//密码不正确
					huaTong.writeBoolean(false);
					return;
				}
				else {
					Server.allSocket.put(user,client);//保存客户端
					Server.allhuaTong.put(user,huaTong);

					huaTong.writeBoolean(true);
					//获取昵称
					name = FileOperation.extract(Server.Name.getRead(),user);
					assert name != null;
					huaTong.writeUTF(name);

					//读取用户所用的群
					BufferedReader qun = new BufferedReader(new FileReader(PATH+"/Server_data/User_Friends/Group/"+user+".txt"));
					StringTokenizer lin;
					String hang;
					while ((hang = qun.readLine()) != null) {
						lin = new StringTokenizer(hang, " ");
						Server.allgroup.get(lin.nextToken()).put(user,huaTong);

					}

					//把用户装进群了
				}




//				//读取聊天记录
//				System.out.println("读取聊天记录");
//          		File_Control record = new File_Control("record.txt","record");
//				BufferedReader formFile= null;//获取权限
//				try {
//					formFile = record.getRead();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//				String lineve;//创建字符串并赋值为空
//				//恢复聊天记录
//				while(true){
//					assert formFile != null;
//					if ((lineve = formFile.readLine()) == null) break;
//					huaTong.writeUTF(lineve);
//            	}
//				formFile.close();
//				System.out.println("恢复完成");



				start();//进入聊天监听

			}catch(IOException ex){
				System.out.println("网络异常se106"+ex);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run(){
			PrintWriter datafile;
			String time;
			char a = 0;
			//听客户提问
			//回答客户问题
			while(true){
				
				String question;
				try{
    			question = tingTong.readUTF();//接收客户端发来的信息

						String[] resu = Agreement.analysis(question);//解析协议
						switch (resu[0]) {
							case "0": {
								time = tingTong.readUTF();   //接收时间
								//执行群聊
								//把信息发给所有的用户
//								System.out.println("目前有"+Server.allgroup.get(resu[1]).size()+"个群友在线");
								HashMap<String, DataOutputStream> all = Server.allgroup.get(resu[1]);
								File file = new File(PATH + "/Server_data/Group/Data/" + resu[1] + ".txt");
								if(!file.exists()){
									if(!file.createNewFile()){
										System.out.println("聊天记录文件创建失败");
									}
								}
								datafile = new PrintWriter(new FileWriter(file, true));

								for (Map.Entry<String, DataOutputStream> yonghu : all.entrySet()) {
									System.out.println(yonghu.getKey());
									if (user.equals(yonghu.getKey())) {
										datafile.println(name + " " + resu[2]+"\t\t\t"+a+time);
										continue;
									}
									//向所有用户转发,1>群id>内容
									yonghu.getValue().writeUTF("0" + Integer.toHexString(resu[1].length()) + resu[1] + resu[2]);
									System.out.println("发送："+"0" + Integer.toHexString(resu[1].length()) + resu[1] + resu[2]);
									yonghu.getValue().writeUTF(name);//补发一个发送人
									yonghu.getValue().writeUTF(time);//发送时间
									//把聊天记录保存到文件夹中，格式：发送人-内容-时间
									datafile.println(name + " " + resu[2]+"\t\t\t"+a+time);
								}
								datafile.close();
								break;
							}
							case "1": {
								//执行私聊，在该账号的好友列表中查找，参数对方账号，发送内容
								//暂时先不查验此账号是否是对方的好友，先发送再说后面改
								//先使用全部人的集合，后面会改成好友集合
								//每个好友都会有一个集合
//								System.out.println("目前有"+Server.allhuaTong.size()+"个客户端");
								time = tingTong.readUTF();   //接收时间
								File file = new File(PATH + "/Server_data/Data/"+this.user+"/"+ resu[1] + ".txt");
								if(!file.exists()){
									if(!file.createNewFile()){
										System.out.println("聊天记录文件创建失败");
									}
								}
								datafile = new PrintWriter(new FileWriter(file, true));


								if (Server.allhuaTong.containsKey(resu[1])) {
									//查得到说明在线，暂时不处理不在线的情况，1>发送人>内容
									System.out.println("发送："+"1" + Integer.toHexString(user.length()) + user + resu[2]);
									Server.allhuaTong.get(resu[1]).writeUTF("1" + Integer.toHexString(user.length()) + user + resu[2]);
									Server.allhuaTong.get(resu[1]).writeUTF(time);
									datafile.println(name + " " + resu[2]+"\t\t\t"+a+time);
								} else {
									System.out.println(resu[1] + "不在线");
									datafile.println(name + " " + resu[2]+"\t\t\t"+a+time);
								}
								break;
							}
							default:
								//返回一个false，数据出现错误
								System.out.println("出错");
								//continue;
								break;
						}

					} catch (Exception w) {
						w.printStackTrace();
						Server.allhuaTong.remove(user);//全部用户集合中离线则删除
						//群文件中的也要删除
						return;
					}
//				try{
////					PrintWriter toFile = new PrintWriter(new FileOutputStream("record.txt",true),true);
////					 toFile.println(question);
////					 toFile.close();
//
////				for(DataOutputStream huaTong:Server.allhuaTong){
////					huaTong.writeUTF(name+"："+question);//转发给所有客户端
////					huaTong.flush();//清空缓存区
////					System.out.println("目前有"+Server.allhuaTong.size());
////				}
//
//
//
//
//				}catch(IOException e){
//					System.out.println("发现一个失效的连接，端口号："+socket.getLocalAddress());
//					System.out.println("目前有"+Server.allhuaTong.size());
//				}
			}
		}

	}























