package com.qq.server.chat;

import com.qq.server.Server;
import com.qq.tools.FileOperation;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

import static com.qq.server.Server.PATH;


//服务器端->聊天->用户信息修改

public class Personal_data extends Thread {
	Socket client;
	DataOutputStream huaTong;
	DataInputStream tingTong;
	String user;
	String password;


	public Personal_data(Socket net) {
		System.out.println("进入用户资料操作地区");
		client = net;
		try {
			huaTong = new DataOutputStream(client.getOutputStream());
			tingTong = new DataInputStream(client.getInputStream());


			user = tingTong.readUTF();
			password = tingTong.readUTF();

			if (!FileOperation.retrieval(Server.User.getRead(), user, password)) {
				//如果返回false，一定是对方破解了客户端，不然这里一定返回true
				huaTong.writeBoolean(false);
				return;
			}
			huaTong.writeBoolean(true);
			//账号正确进入工作模式
			System.out.println("密码正确，进入工作状态");
			start();//进入循环

		} catch (Exception w) {
			System.out.println("25异常" + w);
		}

	}


	public void run() {
		while (true) {
			try {
				String data = tingTong.readUTF();
				switch (data) {
					case "0": {
						System.out.println(user + "指令0已进入");
						//发送昵称，账号，性别，手机号，邮箱，爱好

						huaTong.writeUTF(Data_extraction(user, new String[]{"name", "user", "gender", "phone", "eailbox", "hobby"}));//转化成字符串发送//发送昵称，账号，性别，手机号，邮箱，爱好

						System.out.println(user + "指令0已退出");
						break;
					}
					case "1": {
						System.out.println(user + "指令1已进入");
						//把注册信息传回去
						//需要传送昵称，手机，邮箱，爱好
						huaTong.writeUTF(Data_extraction(user, new String[]{"name", "phone", "eailbox", "hobby"}));

						System.out.println(user + "指令1已退出");
						break;
					}
					case "2": //把客户端传回来的信息修改服务器数据
						System.out.println(user + "指令2已进入");
						//完成注册信息信息修改
						String xingxi;
						while (!(xingxi = tingTong.readUTF()).equals("-1")) {
							//进行解析
							StringTokenizer everyLineToken = new StringTokenizer(xingxi, " ");
							Modify(everyLineToken.nextToken(), everyLineToken.nextToken());//修改信息

						}
						huaTong.writeBoolean(true);
						System.out.println(user + "指令2已退出");
						break;
					case "3": {
						System.out.println(user + "指令3已进入");
						//返回头像
						String id = tingTong.readUTF();//传回谁的头像

						File touxiangpath = new File("Server_data/User_Avatar/" + id + ".jpg");//头像路径

						if (!Send_Avatar(touxiangpath)) {
							System.out.println("出现错误了");
						}


						System.out.println(user + "指令3已退出");
						break;
					}
					case "4": {
						System.out.println(user + "指令4已进入");
						//接收头像,数据大小
						long leng = tingTong.readLong();
						//接收后缀
						String suffix = tingTong.readUTF();
//
						byte[] b = new byte[1024];
						int packet, mun = 0;
						FileOutputStream touxiangpash = new FileOutputStream("Server_data/User_Avatar/" + user + suffix);
						do {
							packet = tingTong.read(b);
							touxiangpash.write(b, 0, packet);
							mun = mun + packet;
						} while ((int) leng != mun);

						touxiangpash.close();//关闭文件
						System.out.println(user + "指令4已退出");
						break;
					}
					case "5": {
						//System.out.println("开始传回好友信息");
						//返回用户的好友
						System.out.println(user + "指令5已进入");

						//用户的群文件
						BufferedReader Friends = new BufferedReader(new FileReader(PATH+"/Server_data/User_Friends/Group/" + user + ".txt"));
						File_data(Friends,"群");
						huaTong.writeUTF("0");
						huaTong.flush();

						//用户的好友文件
						Friends = new BufferedReader(new FileReader(PATH+"/Server_data/User_Friends/" + user + ".txt"));
						File_data(Friends,"用户");
						huaTong.writeUTF("-1");
						huaTong.flush();
						//发送完毕
						System.out.println(user + "指令5已退出");
						break;

					}
					case "6": {
						System.out.println(user + "指令6已进入");
						//返回好友信息
						Friend_profile();
						System.out.println(user + "指令6已退出");
						break;
					}
					case "7": {
						System.out.println(user + "指令7已进入");
						//返回用户name
						String user = tingTong.readUTF();
						String name = Extract(Server.Name.getRead(), user);
						if ("此账号不存在".equals(name)) {
							name = Extract(new BufferedReader(new FileReader(PATH+"/Server_data/User_information/Group.txt")), user);
						}
						huaTong.writeUTF(name);
						huaTong.flush();
//
						System.out.println(this.user + "指令7已退出");
						break;
					}
					case "8": {
						System.out.println(user + "指令8已进入");
						//申请加好友
						//___________这是在线的情况_______________________________________________________________
						String id = tingTong.readUTF();
						if (!Server.allhuaTong.containsKey(id)) {
							System.out.println("好友不在线");
						}
						//转发给那个用户
						Server.allhuaTong.get(id).writeUTF("2" + Integer.toHexString(user.length()) + user);
						//______________________________________________________________________________________
						System.out.println(user + "指令8已退出");
						break;
					}
					case "9": {
						System.out.println(user + "指令9已进入");
						String id = tingTong.readUTF();
						//判断用户是否同意好友申请
						//为双方都添加上好友
						if (tingTong.readBoolean()) {
//						System.out.println("已为"+user+"添加好友");
							PrintWriter add = new PrintWriter(new FileWriter(PATH+"/Server_data/User_Friends/" + user + ".txt"), true);
							add.println(id + " " + "无");
							add.close();
//						System.out.println("已为"+id+"添加好友");
							add = new PrintWriter(new FileWriter(PATH+"/Server_data/User_Friends/" + id + ".txt"), true);
							add.println(user + " " + "无");
							add.close();
							//在发送给另一个人,user同意了你的申请
//						System.out.println(user+"已同意"+id+"的好友申请");
							System.out.println("3" + Integer.toHexString(user.length()) + user + "true");
							Server.allhuaTong.get(id).writeUTF("3" + Integer.toHexString(user.length()) + user + "true");
							//以上都是在线的情况，不在线后面再说
						} else {
							//拒绝
							Server.allhuaTong.get(id).writeUTF("3" + Integer.toHexString(user.length()) + user + "false");
						}
						huaTong.writeBoolean(true);
						System.out.println(user + "指令9已退出");
						break;
					}
					case "10": {
						System.out.println(user + "指令10已进入");
						//建群
						String id = tingTong.readUTF();//群id
						//在群文件夹中遍历这个群，看看这个id有没有被注册
						File idpash = new File(PATH+"/Server_data/Group/" + id + ".txt");//群文件
						File gonggao = new File(PATH+"/Server_data/Group/Notice/" + id + ".txt");//公告文件
						File dataffile = new File(PATH+"/Server_data/Group/Data/" + id + ".txt");//聊天记录
						//判断有没有这个群
						if (idpash.exists() || FileOperation.lookup(Server.User.getRead(), id)) {
							//群存在返回false，这个群不能重复创建
							huaTong.writeBoolean(false);
						} else {
							//群不存在，返回true
							huaTong.writeBoolean(true);
							//接收群昵称
							String name = tingTong.readUTF();
							//创建群
							if (!idpash.createNewFile() || !gonggao.createNewFile() || !dataffile.createNewFile()) {
								//创建失败，返回false
								huaTong.writeBoolean(false);
							} else {
								//否则，返回true
								huaTong.writeBoolean(true);
								File yonghu = new File(PATH+"/Server_data/User_Friends/Group/" + user + ".txt");
								PrintWriter y = new PrintWriter(new FileWriter(yonghu, true));
								y.println(id + " " + "无");
								y.close();



								//把用户的账号写入群，备注为无，使用用户name
								y = new PrintWriter(new FileWriter(idpash, true));
								y.println(user + " " + "无");
								y.close();
								//创建群集合，保存群id以及名字
								y = new PrintWriter(new FileWriter(PATH+"/Server_data/User_information/Group.txt", true));
								//写入
								y.println(id + " " + name);
								y.close();
							}
							//创建完成
						}
						System.out.println(user + "指令10已退出");
						break;
					}
					case "11": {
						System.out.println(user + "指令11已进入");
						String id = tingTong.readUTF();
						//申请入群
						//获取群主id
						BufferedReader a = new BufferedReader(new FileReader(PATH+"/Server_data/Group/" + id + ".txt"));
						StringTokenizer lin = new StringTokenizer(a.readLine(), " ");
						//获取群主的联系方式，发送格式，指令>十六进制，申请人id，群id
						Server.allhuaTong.get(lin.nextToken()).writeUTF("4" + Integer.toHexString(user.length()) + user + id);
						System.out.println(user + "指令11已退出");
						break;
					}
					case "12": {
						System.out.println(user + "指令12已进入");
						//申请加群的结果
						String user = tingTong.readUTF();//申请人id
						String id = tingTong.readUTF();//群id
						if (tingTong.readBoolean()) {
							//群主同意了、在群文件以及申请人自己的文件中添加上各自的信息
							//先在群文件中添加
							PrintWriter file = new PrintWriter(new FileOutputStream(PATH+"/Server_data/Group/" + id + ".txt", true));
							file.println(user + " " + "无");
							file.close();
							//再在用户文件中添加
							file = new PrintWriter(new FileOutputStream(PATH+"/Server_data/User_Friends/Group/" + user + ".txt"));
							file.println(id + " " + "无");//无都是备注的意思，以后如果要完善备注功能的话可以从这里入手
							file.close();
							//都改完了
							//返回申请人结果指令>群is>结果
							Server.allhuaTong.get(user).writeUTF("5" + Integer.toHexString(id.length()) + id + "true");


							//_____________________________
							//读取用户所用的群
							BufferedReader qun = new BufferedReader(new FileReader(PATH+"/Server_data/User_Friends/Group/" + user + ".txt"));
							StringTokenizer lin;
							String hang;
							while ((hang = qun.readLine()) != null) {
								lin = new StringTokenizer(hang, " ");
								Server.allgroup.get(lin.nextToken()).put(user, Server.allhuaTong.get(user));
							}
							//_____________________________

						} else {
							//群主没同意
							Server.allhuaTong.get(user).writeUTF("5" + Integer.toHexString(id.length()) + id + "false");
						}
						huaTong.writeBoolean(true);
						System.out.println(user + "指令12已退出");
						break;
					}
					case "13":{
						//传回群成员id或者昵称
						System.out.println(this.user+"指令13已进入");
						String id = tingTong.readUTF();//接收群的id
						boolean qh = tingTong.readBoolean();//读取前边的内容还是后边的内容
						System.out.printf("查询"+id);
						String filedata = FileOperation.allextract(new BufferedReader(new FileReader(PATH+"/Server_data/Group/"+id+".txt"))," ",qh);
						huaTong.writeUTF(filedata);
						System.out.println(this.user+"指令13已退出");
						break;
					}
					case "14":{
						//整个文件都传送出来，读取公告
						System.out.println(this.user+"指令14已进入");
						String id = tingTong.readUTF();
						String filedata = FileOperation.allextract(new BufferedReader(new FileReader(PATH+"/Server_data/Group/Notice/"+id+".txt")),"null",true);
						huaTong.writeUTF(filedata);
						System.out.println(this.user+"指令14已退出");
						break;
					}
					case "15":{
						//重写公告
						System.out.println(this.user+"指令15已进入");
						String id = tingTong.readUTF();

//						先判断用户是不是群主
						BufferedReader lin = new BufferedReader(new FileReader(PATH+"/Server_data/Group/"+id+".txt"));
						StringTokenizer ids = new StringTokenizer(lin.readLine()," ");
						if(!this.user.equals(ids.nextToken())){
							huaTong.writeBoolean(false);
							lin.close();
							System.out.println(this.user+"指令15已退出");
							break;
						}
						huaTong.writeBoolean(true);

						PrintWriter file = new PrintWriter(new FileOutputStream(PATH+"/Server_data/Group/Notice/"+id+".txt"));
						file.println(tingTong.readUTF());
						file.close();
						System.out.println(this.user+"指令15已退出");
						break;
					}
					case "16":{
						//判断是否是群主
						System.out.println(this.user+"指令16已进入");
						String id = tingTong.readUTF();//群id
						BufferedReader lin = new BufferedReader(new FileReader(PATH+"/Server_data/Group/"+id+".txt"));
						StringTokenizer ids = new StringTokenizer(lin.readLine()," ");
						if(!this.user.equals(ids.nextToken())){
							huaTong.writeBoolean(false);
							lin.close();
							System.out.println(this.user+"指令16已退出");
							break;
						}
						huaTong.writeBoolean(true);
						System.out.println(this.user+"指令16已退出");
						break;
					}
					case "17":{
						//删除好友
						System.out.println(this.user+"指令17已进入");
						String id = tingTong.readUTF();
						FileOperation.Delete_row(new File(PATH+"/Server_data/User_Friends/"+id+".txt"),this.user);
						FileOperation.Delete_row(new File(PATH+"/Server_data/User_Friends/"+this.user+".txt"),id);
						System.out.println(this.user+"指令17已退出");
						break;
					}
					case "18":{
						//退群
						System.out.println(this.user+"指令18已进入");
						String id = tingTong.readUTF();
						FileOperation.Delete_row(new File(PATH+"/Server_data/Group"+id+".txt"),this.user);
						FileOperation.Delete_row(new File(PATH+"/Server_data/User_Friends/Group/"+this.user+".txt"),id);
						System.out.println(this.user+"指令18已退出");
						break;
					}
					case "19":{
						//查询是群还是用户
						System.out.println(this.user+"指令19已进入");
						String id = tingTong.readUTF();
						//判断是否是用户
						if ("此账号不存在".equals(Extract(Server.User.getRead(), id))) {
							//不是用户
							if ("此账号不存在".equals(Extract(new BufferedReader(new FileReader(PATH + "/Server_data/User_information/Group.txt")), id))) {
								if (id.equals("notice")) {
									huaTong.writeUTF("通知");
									break;
								} else {
									huaTong.writeUTF("错误账号");
								}
								//不是群
							} else{
								huaTong.writeUTF("群");
								break;
							}
						}else{
							huaTong.writeUTF("用户");
						}

						System.out.println(this.user+"指令19已退出");
						break;
					}
					default:
						System.out.println(user + "指令错误：" + data);


				}

			} catch (Exception w) {
				//报异常就返回false
				try {
					huaTong.writeBoolean(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
				w.printStackTrace();
				return;
			}
		}

	}


	String Extract(BufferedReader formFile, String user) {//用来提取各个文件里面的信息
		try {
			StringTokenizer everyLineToken;
			String everyLine;

			while ((everyLine = formFile.readLine()) != null) {
				everyLineToken = new StringTokenizer(everyLine, " ");
				if (user.equals(everyLineToken.nextToken())) {//判断
					formFile.close();//关闭文件
					return (everyLineToken.nextToken());//返回需要提取的信息
				}
			}


		} catch (Exception e) {
			System.out.println("Personal_data 194" + e);
		}
		return "此账号不存在";
	}


	void Modify(String file, String data) {//修改用户信息

		System.out.println("接收到：" + file + "\t" + data + "准备修改");

		switch (file) {
			case "Name.txt":
				if (!FileOperation.tampering(Server.Name, user, data)) {
					System.out.println("信息修改失败");
				}
				break;
			case "Phone.txt":
				FileOperation.tampering(Server.Phone, user, data);
				break;
			case "Gender.txt":
				FileOperation.tampering(Server.Gender, user, data);
				break;
			case "Eailbox.txt":
				FileOperation.tampering(Server.Eailbox, user, data);
				break;
			case "Hobby.txt":
				FileOperation.tampering(Server.Hobby, user, data);
				break;
		}
	}

	void Friend_profile() throws Exception {
		//System.out.println("开始发回好友资料");
		String user = tingTong.readUTF();//查询的id
		if ("此账号不存在".equals(Extract(Server.User.getRead(), user))) {
			//不是用户就是群,获取群的信息
			huaTong.writeUTF("群");
			String name = Extract(new BufferedReader(new FileReader(PATH+"/Server_data/User_information/Group.txt")), user);
			huaTong.writeUTF(name);
			return;
		}

		huaTong.writeUTF("用户");
		//发送昵称，账号，性别，手机号，邮箱，爱好
		String filedata = Extract(Server.Name.getRead(), user) + " " + user + " " +//提取账号和昵称
				Extract(Server.Gender.getRead(), user) + " " +//提取性别
				Extract(Server.Phone.getRead(), user) + " " +//提取手机号
				Extract(Server.Eailbox.getRead(), user) + " " +//提取邮箱
				Extract(Server.Hobby.getRead(), user) + " ";//提取爱好
		huaTong.writeUTF(filedata);//转化成字符串发送//发送昵称，账号，性别，手机号，邮箱，爱好
		//System.out.println(filedata);
		huaTong.flush();//清缓冲
	}


	String Data_extraction(String user, String[] args) {
		StringBuilder filedata = new StringBuilder();
		for (String file : args) {
			try {
				switch (file) {

					case "name": {
						filedata.append(Extract(Server.Name.getRead(), user)).append(" ");
						break;
					}
					case "user": {
						filedata.append(user).append(" ");
						break;
					}
					case "gender": {
						filedata.append(Extract(Server.Gender.getRead(), user)).append(" ");
						break;
					}
					case "phone": {
						filedata.append(Extract(Server.Phone.getRead(), user)).append(" ");
						break;
					}
					case "eailbox": {
						filedata.append(Extract(Server.Eailbox.getRead(), user)).append(" ");
						break;
					}
					case "hobby": {
						filedata.append(Extract(Server.Hobby.getRead(), user)).append(" ");
						break;
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
				return "错误";
			}
		}


		return String.valueOf(filedata);


	}
	
	boolean Send_Avatar(File touxiangpath){

		FileInputStream touxiang = null;//头像的读取对象
		//传输头像


		try {
			touxiang = new FileInputStream(touxiangpath);
			System.out.println();
			huaTong.writeLong(touxiangpath.length());
		} catch (IOException e) {
//			e.printStackTrace();
			//这里一般执行不到，因为注册的时候就会分配一个头像
//			System.out.println(user + "头像不存在");
			try {
				touxiang = new FileInputStream("Server_data/User_Avatar/默认头像.jpg");
				huaTong.writeLong((new File("Server_data/User_Avatar/默认头像.jpg")).length());
			} catch (IOException fileNotFoundException) {
				fileNotFoundException.printStackTrace();
			}
		}

		try {
			byte[] byteArray = new byte[2048];
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			int len;

			if(touxiang == null){
				return false;
			}

			while ((len = touxiang.read(byteArray)) != -1) {
				huaTong.write(byteArray, 0, len);
				huaTong.flush();//清理缓存
//							System.out.println("发送：" + len + "当前时间：" + df.format(new Date()));
			}
			touxiang.close();//关闭文件
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}//传回头像
		
		return true;
		
	}

//	boolean File_data(BufferedReader Friends){
//		File_data(Friends,"null");
//		return true;
//	}
	
	void File_data(BufferedReader Friends, String leixing){
		try {
			String line;
			while ((line = Friends.readLine()) != null) {
				StringTokenizer eve = new StringTokenizer(line, " ");
				String id = eve.nextToken();//群id
				if(leixing.equals("null") || leixing.equals("群")){
					//提取出来群name
					String name = Extract(new BufferedReader(new FileReader(PATH+"/Server_data/User_information/Group.txt")), id);
					System.out.println("发送："+id+" "+name);
					huaTong.writeUTF(id + " " + name);
				}else{
					//提取出来用户name
					String name = Extract(new BufferedReader(new FileReader(PATH+"/Server_data/User_information/Name.txt")), id);
					System.out.println("发送："+id+" "+name);
					huaTong.writeUTF(id + " " + name);
				}
				huaTong.flush();
			}
		}catch (IOException e){

			e.printStackTrace();
			return;
		}
		try {
			Friends.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	


}







