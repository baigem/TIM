package com.qq;
import com.qq.ComponentControl.*;
import com.qq.gui.Gqq;
import com.qq.lookup.After_Group_building;
import com.qq.lookup.After_add_friends;
import com.qq.tools.Agreement;
import com.qq.tools.HeadPortrait;
import com.qq.tools.Picture;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;



/*
* 目前新发现bug
* 网络断开后又重连聊天记录框会出现信息重复现象
*
*
*
*
*
 */

// 聊天主界面

public class qq extends Gqq implements Runnable{
	Socket client;//聊天专用
	Socket net;     //网络监控专用
    Socket data;    //资料修改专用
	DataInputStream tingTong;
    public DataOutputStream huaTong;
    public DataInputStream datatingTong;
    public DataOutputStream datahuaTong;
	boolean inspectNetwork = true;
	String IP = "127.0.0.1";
    public String user;
    String passwok;
    public String name;
    public HashMap<String,String> Friends_list = new HashMap<>(); //好友列表的一个集合，主要用来储存好友
    public HashMap<String ,JLabel > Sessionlw = new HashMap<>(); //会话左侧窗口 Session left window
    public HashMap<String , Windows> Sessionrw  = new HashMap<>();   //好友会话右侧的窗口
    qq mi;

    //构造方法，程序开始之地
    public qq(String users,String passworks,Socket clients,Socket network){
        mi = this;
        setLocationRelativeTo(null);
        client = clients;
        net = network;
        user = users;
        passwok = passworks;
        new Thread(this).start();//网络监测




        //点击个人信息
        inforMation.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                //查看个人信息
                new After_personal_Data(mi);
            }
        });

       //两大金刚鼠标点击设置
        Conversation.addMouseListener(new mouse(new File(System.getProperty("user.dir")+"/data/Resources/会话"), Conversation));
        super.Friend.addMouseListener(new mouse(new File(System.getProperty("user.dir")+"/data/Resources/好友"),  Friend));

        //鼠标点击设置
        super.Friend.addMouseListener(new FriClick(this));
        Conversation.addMouseListener(new ConClick(this));

        //加好友按钮被点击
        Add_friends.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                new After_add_friends(mi);
            }
        });
        //创建群按钮
        Group_building.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new After_Group_building(mi);
                System.gc();
            }
        });

    }

    //会话
    static class ConClick extends MouseAdapter{
        qq mi;
        public ConClick(qq mi){
            this.mi = mi;
        }
        public void mouseClicked(MouseEvent e) {
            mi.remove(mi.JPfriend);//好友界面
            mi.add(mi.JPrsoc);//添加会话界面
            mi.repaint();
        }
    }

    //好友
    class FriClick extends MouseAdapter{
        qq mi;
        public FriClick(qq mi){
            this.mi = mi;
        }
        public void mouseClicked(MouseEvent e){
            mi.All_friends.removeAll();//删除所有好友组件
            mi.remove(mi.JPrsoc);//删除会话界面
            mi.add(mi.JPfriend);//添加好友界面
            try {
                new receive(mi);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            mi.repaint();//刷新
        }


    }






    //好友界面
     class receive{

        public receive(qq mi) throws IOException {

            //开始获取好友
            System.out.println("开始接收好友");
            //接收之前先判断好友的头像在不在
            //不在则下载头像，好友列表在集合中已经有了
            File tx;
            for(String ignored:Friends_list.keySet()){
                tx = new File(System.getProperty("user.dir")+"/data/Account number/"+user+"/head_portrait/"+ignored+".jpg");
                if(!tx.exists()){
                    //不存在的话下载
                    HeadPortrait.Download_Avatar(mi,mi.user,ignored);
                }
            }

            mi.datahuaTong.writeUTF("5");
            String line;
            Friends.setY(10);
            while(!(line = mi.datatingTong.readUTF()).equals("0")){
                System.out.println("接收到："+line);
                StringTokenizer eve =  new StringTokenizer(line," ");
                String user = eve.nextToken();
                String name = eve.nextToken();
                Friends.Friends_list(mi,user,"群："+name);
            }
            while(!(line = mi.datatingTong.readUTF()).equals("-1")){
                StringTokenizer eve =  new StringTokenizer(line," ");
                String user = eve.nextToken();
                String name = eve.nextToken();
                Friends.Friends_list(mi,user,name);
            }
            //接收完毕
            System.out.println("接收完毕");
        }


    }






    //两大金刚的点击事件
    static class mouse extends MouseAdapter{
        File a;
        JLabel c;
        static ArrayList<JLabel> pd = new ArrayList<>();


        public mouse(File a,JLabel c){
            this.a = a;
            this.c = c;
            pd.add(c);
        }
            public void mouseClicked(MouseEvent e){
                HeadPortrait.Cyh(a+"true.png",c,new ImageIcon(),27,27);
                for(JLabel s:pd) {
                    if (s.equals(c))
                        continue;
                    if(s.getText().equals("会话")) {
                        HeadPortrait.Cyh(a.getParent() + "/" + "会话false.png", s, new ImageIcon(), 27, 27);
                    }else {
                        HeadPortrait.Cyh(a.getParent() + "/" + "好友false.png", s, new ImageIcon(), 27, 27);
                    }
                }
            }
    }







	public void run(){
		int i=0;
		try{
			    tingTong = new DataInputStream(client.getInputStream());//聊天通道打开
        	huaTong = new DataOutputStream(client.getOutputStream());
            data = new Socket(IP,9191);
            head();//建立连接并且判断
            new initialization(mi);//只执行一次
            new Chat();//执行监听任务
            new Network(net);
    	}catch(Exception e){
		    e.printStackTrace();
    		System.out.println("网络异常");
    	}
        for(inspectNetwork=false;true;inspectNetwork=false){
          try{
            client = new Socket(IP,9191);//获取服务器地址
            data = new Socket(IP,9191);
            tingTong = new DataInputStream(client.getInputStream());
            huaTong = new DataOutputStream(client.getOutputStream());
            inspectNetwork = true;
            head();//建立连接并且判断
            new Chat();//执行信息监听
            Network net = new Network(IP);//进行实时网络监测
            new Network(net.network);
            i=0;
          }catch(Exception e){
            if(i<=0){
              JOptionPane.showMessageDialog(null,"网络连接失败请检查网络是否正常");
              i++;
            }
            try{
              Thread.sleep(1000);//延迟1s执行
            }catch(Exception ex){System.out.println("qq207");}
          }
        }
	}





    private void head(){
        head(data);//进入信息控制线程
        File[] tx = (new File("data/Account number/"+user+"/head_portrait/")).listFiles();
        //______________获取好友头像______________________
        for(String id:Friends_list.keySet()){
            HeadPortrait.Download_Avatar(mi,mi.user,id);
        }
        //______________________________________________

        boolean judge=true;
        assert tx != null;
//        if(tx.length ==0){//如果文件为0，直接获取头像
//            HeadPortrait.Download_Avatar(mi,this.user,this.user);
//        }
        for(File s:tx){
            if((user+".jpg").equals(s.getName())){
                judge = false;
            }
        }


        if(judge) {
            HeadPortrait.Download_Avatar(mi,this.user,this.user);
        }
        //________切割一下头像

        Picture.garden(new File("data/Account number/"+user+"/head_portrait/"+user+".jpg"),"Round_head",mi.JPtkk.getBackground());

        //_________________
        HeadPortrait.Cyh("data/Account number/"+user+"/head_portrait/Round_head/"+user+".jpg",inforMation,new ImageIcon(),30,30);
    }







	class Chat extends Thread{//每时每刻接收服务器发来的信息

	    public Chat(){

            System.out.println("Chat启动");
            start();
        }

		public void run(){
	        char a = 0;
			while(true){
				try{
					String news = tingTong.readUTF();
                    System.out.println("接收："+news);
                    String[] str = Agreement.analysis(news);//解析协议
                    switch (str[0]){

                        case "0":{
                            //0暂时不用
                            //群聊，在按钮库中查找
                            System.out.println("0进入");
                            String nameid = tingTong.readUTF();
                            String time = tingTong.readUTF();

                            File qun = new File(System.getProperty("user.dir")+"/data/Account number/" + mi.user+"/data/"+nameid+".txt");
                            if(!qun.exists()){
                                if(!qun.createNewFile()){
                                    System.out.println("群聊天记录文件创建失败");
                                }
                            }
                            PrintWriter datafile = new PrintWriter(new FileWriter(qun,true));
                            if(Sessionlw.containsKey(str[1])){
                                //已经创建了窗口，直接发送就行了
                                Sessionrw.get(str[1]).roll.append("    "+nameid+"：\n            "+str[2]+"\n");
                                //使得聊天记录永远在最下方
                                Sessionrw.get(str[1]).roll.setCaretPosition( Sessionrw.get(str[1]).roll.getText().length());
                                datafile.println(nameid+" "+str[2]+a+time);//保存聊天记录
                            }
                            else{
                                //不存在则直接创建一个窗口
                                //先创建按钮
                                new Group_button(mi,str[1],Friends_list.get(str[1]),false);//不强制获取窗口
                                //创建之后输入进去
                                if(Sessionlw.containsKey(str[1])){
                                    Sessionrw.get(str[1]).roll.append("    "+nameid + "：\n            " + str[2] + "\n");
                                    //使得聊天记录永远在最下方
                                    Sessionrw.get(str[1]).roll.setCaretPosition(Sessionrw.get(str[1]).roll.getText().length());
                                    datafile.println(nameid+" "+str[2]+a+time);//保存聊天记录
                                }else {
                                    System.out.println("有异常，这里不可能为假的");
                                }
                            }
                            datafile.close();
                            System.out.println("0退出");
                            break;
                        }
                        case "1":{
                            //私聊，在按钮库中查找
                            System.out.println("1进入");
                            String time = tingTong.readUTF();//接收时间
                            File qun = new File(System.getProperty("user.dir")+"/data/Account number/" + mi.user+"/data/"+str[1]+".txt");
                            if(!qun.exists()){
                                if(!qun.createNewFile()){
                                    System.out.println("群聊天记录文件创建失败");
                                }
                            }
                            PrintWriter datafile = new PrintWriter(new FileWriter(qun,true));

                            if(Sessionlw.containsKey(str[1])){
                                //已经创建了窗口，直接发送就行了
                                Sessionrw.get(str[1]).roll.append("    "+Friends_list.get(str[1])+"：\n            "+str[2]+"\n");
                                //使得聊天记录永远在最下方
                                Sessionrw.get(str[1]).roll.setCaretPosition( Sessionrw.get(str[1]).roll.getText().length());
                                datafile.println(str[1]+" "+str[2]+a+time);//保存聊天记录
                            }
                            else{
                                //不存在则直接创建一个窗口
                                //先创建按钮
                                new Private_button(mi,str[1],Friends_list.get(str[1]),false);//不强制获取窗口
                                //创建之后输入进去
                                if(Sessionlw.containsKey(str[1])){
                                    Sessionrw.get(str[1]).roll.append("    "+Friends_list.get(str[1]) + "：\n            " + str[2] + "\n");
                                    //使得聊天记录永远在最下方
                                    Sessionrw.get(str[1]).roll.setCaretPosition(Sessionrw.get(str[1]).roll.getText().length());
                                    datafile.println(str[1]+" "+str[2]+a+time);//保存聊天记录
                                }else {
                                    System.out.println("有异常，这里不可能为假的");
                                }
                            }
                            System.out.println("1退出");
                            break;
                        }
                        case "2":{
                            //2是好友申请
                            //首先创建按钮
                            System.out.println("接收到有用户添加我为好友");
                            File file = new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/data/notice.txt");
                            if(!file.exists()){
                                if(!file.createNewFile()){
                                    System.out.println("通知文件创建失败");
                                }
                            }
                            PrintWriter datafile = new PrintWriter(new FileWriter(file,true));
                            //写入信息
                            datafile.println(news+":null");
                            datafile.close();

                            if(!Sessionlw.containsKey("通知")){
                                new Notice(mi);
                            }
                             //有按钮直接执行
                             new Notification_Click(mi,str[1],Sessionlw.get("通知"),"用户");
                            break;
                        }
                        case "3":{
                            //对方同意或拒绝好友申请
                            //先把结果保存在本地
                            File file = new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/data/notice.txt");
                            if(!file.exists()){
                                if(!file.createNewFile()){
                                    System.out.println("通知文件创建失败");
                                }
                            }
                            PrintWriter datafile = new PrintWriter(new FileWriter(file,true));

                            //写入信息
                            datafile.println(news+":"+str[2]);
                            datafile.close();


                            if(!Sessionlw.containsKey("通知")){
                                new Notice(mi);
                            }
                            if("true".equals(str[2])){
                                //同意
                                    new Add_results(mi,Sessionlw.get("通知"),str[1],"同意","用户");
                                    //更新一下好友列表
                                try {
                                    datahuaTong.writeUTF("5");
                                    String haoyou;
                                    while(!(haoyou = datatingTong.readUTF()).equals("-1")) {
                                        if("0".equals(haoyou)){
                                            continue;
                                        }
                                        StringTokenizer lin = new StringTokenizer(haoyou, " ");
                                        Friends_list.put(lin.nextToken(), lin.nextToken());
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //然后聊天列表中添加一个按钮
                                new Private_button(mi,str[1],Friends_list.get(str[1]),false);//不强制获取窗口
                                //然后结束


                            }else if("false".equals(str[2])){
                                //拒绝
                                new Add_results(mi,Sessionlw.get("通知"),str[1],"拒绝");
                            }else{
                                System.out.println("指令错误");
                            }
                            break;
                        }
                        case "4":{
                            //加群申请，判断添不添加按钮

                            File file = new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/data/notice.txt");
                            if(!file.exists()){
                                if(!file.createNewFile()){
                                    System.out.println("通知文件创建失败");
                                }
                            }
                            PrintWriter datafile = new PrintWriter(new FileWriter(file,true));

                            //写入信息
                            datafile.println(news+":null");
                            datafile.close();


                            if(!Sessionlw.containsKey("通知")){
                                new Notice(mi);
                            }
                            //申请加群   str[1]对方的user，str[2]是群的id
                            new Notification_Click(mi,str[1],str[2],Sessionlw.get("通知"),"群");
                            break;
                        }
                        case "5":{
                            //对申请加群的回应，对方同意还是不同意
                            //接收到的协议，指令>群id>以及同意true，不同意false
                            //指令>群is>结果

                            File file = new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/data/notice.txt");
                            if(!file.exists()){
                                if(!file.createNewFile()){
                                    System.out.println("通知文件创建失败");
                                }
                            }
                            PrintWriter datafile = new PrintWriter(new FileWriter(file,true));

                            //写入信息
                            datafile.println(news+":"+str[2]);
                            datafile.close();

                            if(!Sessionlw.containsKey("通知")){
                                new Notice(mi);
                            }

                            if("true".equals(str[2])){
                                //同意了入群申请
                                new Add_results(mi,Sessionlw.get("通知"),str[1],"同意","群");
                                //更新一下好友列表
                                System.out.println("开始接收好友列表");
                                try {
                                    datahuaTong.writeUTF("5");
//                                    datahuaTong.writeUTF("5");
                                    String haoyou;
                                    while(!(haoyou = datatingTong.readUTF()).equals("-1")) {
                                        if("0".equals(haoyou)){
                                            continue;
                                        }
                                        StringTokenizer lin = new StringTokenizer(haoyou, " ");
                                        Friends_list.put(lin.nextToken(), lin.nextToken());
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("接收完毕");
                                //然后聊天列表中添加一个按
                                new Group_button(mi,str[1],Friends_list.get(str[1]),false);//不强制获取窗口
                                //然后结束
                            }else{
                                new Add_results(mi,Sessionlw.get("通知"),str[1],"拒绝","群");
                            }
                            break;
                        }




                    }

				}catch(Exception e){
					System.out.println("网络异常");
					return;
				}
			}
		}
  }




  //建立连接，其余事不做
  public void head(Socket data){//建立连接
      try{
      datatingTong = new DataInputStream(data.getInputStream());
      datahuaTong = new DataOutputStream(data.getOutputStream());
      datahuaTong.writeUTF("个人信息");
      datahuaTong.writeUTF(user);
      datahuaTong.writeUTF(passwok);
      if(!datatingTong.readBoolean()) {
          System.out.println("请不要使用破解手段");
      }
      else
          System.out.println("副线程进入状态");
          huaTong.writeUTF("聊天");
          huaTong.writeUTF(user);
          huaTong.writeUTF(passwok);

          if(!tingTong.readBoolean()){
              JOptionPane.showMessageDialog(null,"警告！请不要破解本客户端以达到登录目的");
          }
          else {
              name = tingTong.readUTF();
              System.out.println("主线程进入状态");
          }
      }catch(Exception w){
          JOptionPane.showMessageDialog(null,"网络异常324");
      }

      //接收一次好友信息
      try {
          datahuaTong.writeUTF("5");
          String haoyou;
          while(!(haoyou = datatingTong.readUTF()).equals("-1")) {
            if("0".equals(haoyou)){
                continue;
            }
            StringTokenizer lin = new StringTokenizer(haoyou, " ");
            Friends_list.put(lin.nextToken(), lin.nextToken());
          }

      } catch (IOException e) {
          e.printStackTrace();
      }




  }


//初始化initialization
  static class initialization{
        public initialization(qq mi){
            System.out.println("准备恢复聊天记录");
            //专门为了重现聊天记录用的
            //检测本地的聊天记录，如果有就加载，其他的就是服务器的事了，目前三中类型，群聊记录，私聊记录，通知记录
            File[] file = (new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/data/")).listFiles();

            if(file == null){
                return;
            }

            for(File fi:file){
                if(fi.isHidden() ){
                    System.out.println(fi.getName()+"是一个隐藏文件");
                    continue;
                }
                String id = (new StringTokenizer(fi.getName(),".").nextToken());
                System.out.printf("620"+id);
                //查询该账号是群聊还是私聊
                try {
                    BufferedReader filedata = new BufferedReader(new FileReader(fi));
                    mi.datahuaTong.writeUTF("19");
                    mi.datahuaTong.writeUTF(id);
                    String data = mi.datatingTong.readUTF();
                    System.out.println("类型："+data);
                    if("群".equals(data)){
                        //new个按钮
                        new Group_button(mi,id,mi.Friends_list.get(id),false);
                        char a = 0;
                        while((data = filedata.readLine()) != null){
                            StringTokenizer eve = new StringTokenizer(data,a+"");
                            eve = new StringTokenizer(eve.nextToken()," ");
                            String user = eve.nextToken();
                            String datas = eve.nextToken();
                            //获取用户昵称
                            mi.datahuaTong.writeUTF("7");
                            mi.datahuaTong.writeUTF(user);
                            String name = mi.datatingTong.readUTF();
                            mi.Sessionrw.get(id).roll.append("    "+name+"：\n            "+datas+"\n");
                            //使得聊天记录永远在最下方
                            mi.Sessionrw.get(id).roll.setCaretPosition(mi.Sessionrw.get(id).roll.getText().length());
                        }
                    }else if("用户".equals(data)){

                        //new个按钮
                        new Private_button(mi,id,mi.Friends_list.get(id),false);
                        char a = 0;
                        while((data = filedata.readLine()) != null){
                            StringTokenizer eve = new StringTokenizer(data,a+"");
                            eve = new StringTokenizer(eve.nextToken()," ");
                            String user = eve.nextToken();
                            String datas = eve.nextToken();
                            String name;
                            if(!user.equals(mi.user)){
                                name = mi.Friends_list.get(user);
                            }else
                                name = mi.name;
                            mi.Sessionrw.get(id).roll.append("    "+name+"：\n            "+datas+"\n");
                            //使得聊天记录永远在最下方
                            mi.Sessionrw.get(id).roll.setCaretPosition(mi.Sessionrw.get(id).roll.getText().length());
                        }

                    }else if("通知".equals(data)) {
                        //读取通知
                        while((data = filedata.readLine()) != null){
                            if(!mi.Sessionlw.containsKey("通知")){
                                new Notice(mi);
                            }
                            String[] agr = Agreement.analysis(data);
                            switch (agr[0]){
                                case "2":{
                                    //好友申请，需要重新解析
                                    StringTokenizer eve = new StringTokenizer(data,":");
                                    agr = Agreement.analysis(eve.nextToken());
                                    String pd = eve.nextToken();
                                    Notification_Click f = new Notification_Click(mi,agr[1],mi.Sessionlw.get("通知"),"用户");
                                    if(pd.equals("false")){
                                        f.z=false;
                                        f.jieguo.setText("已拒绝");

                                    }else if(pd.equals("true")) {
                                        f.z=false;
                                        f.jieguo.setText("已同意");
                                    }
                                    break;
                                }
                                case "3":{
                                    //对方同意或拒绝好友申请
                                    StringTokenizer eve = new StringTokenizer(data,":");
                                    agr = Agreement.analysis(eve.nextToken());

                                    if("true".equals(agr[2])){
                                        //同意
                                        new Add_results(mi,mi.Sessionlw.get("通知"),agr[1],"同意","用户");
                                    }else if("false".equals(agr[2])) {
                                        //拒绝
                                        new Add_results(mi, mi.Sessionlw.get("通知"), agr[1], "拒绝");

                                    }
                                    break;
                                }
                                case "4":{
                                    //加群申请，判断添不添加按钮
                                    StringTokenizer eve = new StringTokenizer(data,":");
                                    agr = Agreement.analysis(eve.nextToken());
                                    String pd = eve.nextToken();
                                    Notification_Click f =new Notification_Click(mi,agr[1],agr[2],mi.Sessionlw.get("通知"),"群");
                                    if(pd.equals("false")){
                                        f.z=false;
                                        f.jieguo.setText("已拒绝");

                                    }else if(pd.equals("true")) {
                                        f.z=false;
                                        f.jieguo.setText("已同意");
                                    }
                                    break;
                                }
                                case "5":{
                                    //对申请加群的回应，对方同意还是不同意
                                    StringTokenizer eve = new StringTokenizer(data,":");
                                    agr = Agreement.analysis(eve.nextToken());

                                    if("true".equals(agr[2])){
                                        //同意了入群申请
                                        new Add_results(mi,mi.Sessionlw.get("通知"),agr[1],"同意","群");
                                    }else{
                                        new Add_results(mi,mi.Sessionlw.get("通知"),agr[1],"拒绝","群");
                                    }
                                    break;
                                }
                            }




                        }








                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }





            }






        }



  }






}





















