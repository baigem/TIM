package com.qq.ComponentControl;

import com.qq.qq;
import com.qq.tools.HeadPortrait;
import com.qq.tools.Picture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


/**
 *
 * 群聊天的按钮
 * 简称群聊
 *
 */



public class Group_button extends Button{

    public Group_button(qq mi, String user, String name , boolean pd){
        mi.remove(mi.JPfriend);//移除之前的界面,现在肯定在好友界面
        mi.add(mi.JPrsoc);     //删除之后需要添加


        JLabel a = new JLabel(name);
        HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/Round_head/"+user+".jpg",a,new ImageIcon(),40,40);

        mi.Sessionlw.put(user,a);
        a.setOpaque(true);//设置为不透明的
        a.setBackground(new Color(253, 253, 253));//按钮颜色
        //System.out.println(user+"创建成功");
        a.setBounds(0,y,mi.Dynamic.getWidth(),65);//设置位置
        a.setFont(new Font("宋体",Font.PLAIN,13)); //设置字体，大小，以及形式
//        Picture.garden(new File(System.getProperty("user.dir")+"/Server_data/User Avatar/321.jpg"),a.getBackground());
//        HeadPortrait.Cyh(System.getProperty("user.dir")+"/Server_data/User Avatar/Tailoring/321.jpg",a,new ImageIcon(),40,40);
        //添加组件
        mi.Conversations.add(a);



        i++;
        y+=70;
        mi.Conversations.setPreferredSize(new Dimension(mi.Dynamic.getWidth()-20,y+30));//添加一份长大一段
        new Group_chat(mi,user,name,pd);//创建右侧输入框之类的

        Picture.garden(new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/"+user+".jpg"),"Select_Avatar",new Color(176, 216, 216));
        Picture.garden(new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/"+user+".jpg"),"Round_head",new Color(253, 253, 253));




        //___________获取焦点和失去焦点事件_________________________
        mi.Sessionrw.get(user).inPut.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {//失去焦点bai时
            }
            public void focusGained(FocusEvent e) {//获得焦du点时
                if(!Sess.equals("null")){
                    if(mi.Sessionlw.containsKey(Sess)){
                        mi.Sessionlw.get(Sess).setBackground(new Color(253, 253, 253));//好友列表的颜色
                        HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/Round_head/"+Sess+".jpg",mi.Sessionlw.get(Sess),new ImageIcon(),40,40);
                    }
                }
                //获得了焦点
                if(mi.Sessionlw.containsKey(user)){
                    Sess = user;
                    mi.Sessionlw.get(user).setBackground(new Color(176, 216, 216));//好友列表的颜色
                    HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/Select_Avatar/"+user+".jpg",a,new ImageIcon(),40,40);
                }
            }
        });



        //_____________________________________



        //按钮被点击了的事件
        a.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    //通过点击位置找到点击为表格中的行,菜单
                    new Menu(mi,user,a.getX()+a.getWidth(),a.getY()+a.getHeight());
                    return;
                }


                //把界面删除
                mi.JPconfu.removeAll();//把会话右侧的容器全部删除
                //然后重新加载新的组件
                mi.JPconfu.add(mi.Sessionrw.get(user).all);//向下转型，后期再改

                //____________________刷新部分______________________________
                mi.repaint();
                mi.Sessionrw.get(user).rolls.revalidate();//刷新
                mi.Sessionrw.get(user).inPut.revalidate();

                mi.Conversations.repaint();
                mi.Sessionrw.get(user).inPut.grabFocus();//获取焦点
                //____________________刷新部分______________________________

            }
        });


//        mi.Conversations.repaint();//重绘Jpanel,大哥醒醒，我又往你身上加东西了，你还不起来显示出来
//        mi.Dynamic.revalidate();//JScrollPane刷新，大哥醒醒jp又长大了，你还不跟着长大，它挤得慌

        //mi.repaint();//整个界面进行刷新












    }








}
