package com.qq.ComponentControl;

import com.qq.qq;
import com.qq.tools.FileOperation;
import com.qq.tools.HeadPortrait;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * 通知点击事件
 *
 *
 */
public class Notification_Click {
    public JLabel jiahaoyou = new JLabel();
    public JButton tongyi = new JButton("同意");
    public JButton jujue = new JButton("拒绝");
    public JLabel jieguo = new JLabel();
    public boolean z = true;


    public Notification_Click(qq mi,String user,JLabel tongzhi,String leixing) {
    this(mi,user,"null",tongzhi,leixing);
    }


    public Notification_Click(qq mi,String user,String qid,JLabel tongzhi,String leixing){
        jiahaoyou.setBounds(20,Notice.y,300,50);
        tongyi.setBounds(320,Notice.y,50,50);
        jujue.setBounds(370,Notice.y,50,50);
        jieguo.setBounds(320,Notice.y,50,50);
        jieguo.setForeground(Color.lightGray);
        Notice.y+=55;

        jiahaoyou.setBackground(Color.BLACK);


        //        按钮点击事件
        tongzhi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //首先删除会话右边容器中的所有组件
                mi.JPconfu.removeAll();
                add(mi,user,qid,leixing);
            }
        });



    }

    public void add(qq mi, String user,String qid,String leixing){
        System.out.println("创建了一个申请通知");
        //创建显示通知的组件(280,0,720,750)
        //获取昵称
        try {


            mi.datahuaTong.writeUTF("7");
            mi.datahuaTong.writeUTF(user);
            String name = mi.datatingTong.readUTF();
            //判断是用户还是群
            if("用户".equals(leixing)){

                jiahaoyou.setText("\""+name +"\"希望添加您为好友");

            }else {

                jiahaoyou.setText("\""+name +"\""+"申请加入"+mi.Friends_list.get(qid));
            }

            //把组件添加进容器
            mi.JPconfu.add(jiahaoyou);
            if(z){
                mi.JPconfu.add(tongyi);
                mi.JPconfu.add(jujue);

            }else{
                mi.JPconfu.add(jieguo);
            }

            //需要刷新
            mi.JPconfu.repaint();


            //同意点击组件
            tongyi.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        if("null".equals(qid)) {
                            mi.datahuaTong.writeUTF("9");
                            mi.datahuaTong.writeUTF(user);
                            mi.datahuaTong.writeBoolean(true);//同意添加好友，false为拒绝添加好友
                            //本地的通知文件
                            File file = new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/data/notice.txt");
                            //更改本地的通知数据
                            FileOperation.Delete_row(file,"2"+Integer.toHexString(user.length())+user,"2"+Integer.toHexString(user.length())+user+":true",":");
                        }else {
                            mi.datahuaTong.writeUTF("12");
                            mi.datahuaTong.writeUTF(user);//传递申请人id
                            mi.datahuaTong.writeUTF(qid);//传递其申请的群昵称
                            mi.datahuaTong.writeBoolean(true);//这表示同意进群
                            File file = new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/data/notice.txt");
                            FileOperation.Delete_row(file,"4"+Integer.toHexString(user.length())+user+qid,"4"+Integer.toHexString(user.length())+user+qid+":true",":");
                        }


                        //选择了之后，等待服务器的通知
                        if(mi.datatingTong.readBoolean()){
                         //true表示服务器端已完成所有操作
//                            客户端这边需要显示已同意或者已拒绝
                            //同意的话同时也需要开辟一个新的窗口
                            mi.JPconfu.remove(tongyi);//做出决定后删除同意
                            mi.JPconfu.remove(jujue);//删除拒绝
                            mi.JPconfu.add(jieguo);//显示最终结果
                            jieguo.setText("已同意");
                            mi.JPconfu.repaint();//刷新
                            z = false;   //表示已有最终结果，后面再查看不需要进入此流程，直接显示结果
                            if("null".equals(qid)) {
                                //_______________________________________________________________________
                                //还需要刷新一遍好友集合
                                mi.datahuaTong.writeUTF("5");
                                String haoyou;
                                while (!(haoyou = mi.datatingTong.readUTF()).equals("-1")) {
                                    if (haoyou.equals("0")) {
                                        continue;
                                    }
                                    StringTokenizer lin = new StringTokenizer(haoyou, " ");
                                    mi.Friends_list.put(lin.nextToken(), lin.nextToken());
                                }
                                //__________________________________________________________________________
                                //创建出界面

                            }//否则就结束了
                        }

                        //判断是群还是好友
                        if("null".equals(qid)){
                            //下载最新的头像
                            HeadPortrait.Download_Avatar(mi,mi.user,user);
                            //好友则创建私聊界面
                            new Private_button(mi, user, mi.Friends_list.get(user), false);//不强制获取窗口
                        }else{
                            //群则创建群聊界面
                            new Group_button(mi, qid, mi.Friends_list.get(qid), false);//不强制获取窗口
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }


                }
            });

            //拒绝
            jujue.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {


                    try {
                        if("null".equals(qid)) {
                            mi.datahuaTong.writeUTF("9");
                            mi.datahuaTong.writeUTF(user);
                            mi.datahuaTong.writeBoolean(false);
                            File file = new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/data/notice.txt");
                            FileOperation.Delete_row(file,"2"+Integer.toHexString(user.length())+user,"2"+Integer.toHexString(user.length())+user+":false",":");


                        }else {
                            mi.datahuaTong.writeUTF("12");
                            mi.datahuaTong.writeUTF(user);//传递申请人id
                            mi.datahuaTong.writeUTF(qid);//传递其申请的群昵称
                            mi.datahuaTong.writeBoolean(false);//这表示同意了
                            File file = new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/data/notice.txt");
                            FileOperation.Delete_row(file,"4"+Integer.toHexString(user.length())+user+qid,"4"+Integer.toHexString(user.length())+user+qid+":false",":");
                        }

                        if(mi.datatingTong.readBoolean()){

                            mi.JPconfu.remove(tongyi);
                            mi.JPconfu.remove(jujue);
                            mi.JPconfu.add(jieguo);
                            jieguo.setText("已拒绝");
                            z = false;
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
            });









        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
