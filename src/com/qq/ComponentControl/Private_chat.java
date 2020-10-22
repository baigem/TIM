package com.qq.ComponentControl;

import com.qq.qq;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


/**
 *
 *
 * 创建会话界面右窗口
 * 有历史记录
 * 有输入框这两个组件
 *
 *
 * 私聊
 *
 */




//会话右窗口Session right window
public class Private_chat extends Windows {


    public Private_chat(qq mi, String user, String name, boolean pd) {
        JPname.add(txtname);//把好友名字显示在历史记录左上角


        mi.Sessionrw.put(user,this);//把自己添加进去
        JPname.setLayout(null);
        JPname.setBounds(0,0,720,30);//昵称
        JPname.setBackground(new Color(253, 253, 253));
        txtname.setBounds(0,0,200,30);
        txtname.setText("\t\t\t\t"+name);

        rolls.setBounds(0, 30, 720, 550);//聊天记录
        inPuts.setBounds(0, 580, 720, 170);//输入框

        all.setBounds(0,0,720,750);


        all.add(JPname);//昵称
        all.add(rolls);//历史记录
        all.add(inPuts);//输入框



        rolls.setBorder(null);//无边框

        rolls.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);//设置水平滚动条策略以使水平滚动条只在需要时显示。
        rolls.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);//设置垂直滚动条策略以使垂直滚动条只在需要时显示。
        inPuts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inPuts.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        roll.setLineWrap(true);//自动换行
        roll.setEditable(false);//聊天记录框不可写入
        inPut.setEnabled(true);


        //东西创建好以后，需要添加进容器里面
        //第一次创建直接显示，但是需要先关闭之前的界面
        if(pd){
            //如果为false则是别人发送过来的信息我们不需要强制转到哪个界面
            //为true则是我们自己创建的，需要强制转移到界面

            //转移之前容器里面的东西需要清除

            mi.JPconfu.removeAll();
            //清除之后添加
            mi.JPconfu.add(all);

            //需要刷新
            mi.Conversations.repaint();//重绘Jpanel,大哥醒醒，我又往你身上加东西了，你还不起来显示出来
            mi.Dynamic.revalidate();//JScrollPane刷新，大哥醒醒jp又长大了，你还不跟着长大，它挤得慌

            inPuts.revalidate();//给输入框的父亲醒醒酒，不然输入框不能输入

            mi.repaint();//整个界面进行刷新
            inPut.grabFocus();//获取焦点


        }
        else{
            //否则别人发的东西我们可以选择不看，就不用删除容器以及添加新的界面了
            //只需要刷新显示出来按钮就可以了
            mi.repaint();//整个界面进行刷新
        }

        //创建好友的聊天记录文件
        File qun = new File(System.getProperty("user.dir")+"/data/Account number/"+ mi.user+"/data/"+user+".txt");
        try {
            if (!qun.exists()) {
                if (!qun.createNewFile()) {
                    System.out.println("群聊天记录文件创建失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //在输入框中回车键被触发的事件，明天写
        //按回车发送信息
        inPut.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == '\n'){
                    new Send(mi,user);
                    System.gc();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });



    }

    //发送信息给服务器
	class Send extends  Thread{
        qq mi;
		String user;
        char a=0;
		public Send(qq mi,String user){
			this.mi = mi;
		    this.user = user;
		    start();
		}

		public void run(){
			if(!inPut.getText().equals("") && !inPut.getText().equals("\n")){
                try{

                    File qun = new File(System.getProperty("user.dir")+"/data/Account number/"+ mi.user+"/data/"+user+".txt");
                    //获取写入权限
                    PrintWriter datafile = new PrintWriter(new FileWriter(qun,true));

                    //发送的前面需要加协议
                    System.out.println("私聊：发送了："+"0" + Integer.toHexString(user.length()) + user + inPut.getText().trim());
                    mi.huaTong.writeUTF("1" + Integer.toHexString(user.length()) + user + inPut.getText().trim());//把信息发给服务器
                    mi.huaTong.flush();//清空缓存区
                    String time = DF.format(new Date());//发送时间
                    mi.huaTong.writeUTF(time);
                    mi.huaTong.flush();//清空缓存区
                    //然后自己的屏幕显示
                    roll.append("    "+mi.name+"：\n            "+ inPut.getText().trim() + "\n");
                    datafile.println(mi.user+" "+inPut.getText().trim()+a+time);//保存聊天记录
                    datafile.close();//关闭保存文件
                    inPut.setText("");//清空输入框
                }catch(Exception e){
                    e.printStackTrace();
                    roll.append(mi.name+"："+ inPut.getText().trim()+"  ：发送失败\n");
                	JOptionPane.showMessageDialog(null,"网络异常");
          		}
             }
            else
                inPut.setText("");//清空输入框
		}//发送信息//负责信息发送//发送信息
    }


}
