package com.qq.ComponentControl;

import com.qq.qq;

import javax.swing.*;
import java.awt.*;

/**
 *
 * 通知组件，用来显示通知
 * 先创建一个按钮
 */
public class Notice {
    static int y = 20;


    public Notice(qq mi) {

        JLabel tongzhi = new JLabel("通知");
        //设置大小
        tongzhi.setBounds(0, Private_button.y,mi.Dynamic.getWidth(),65);
        Private_button.y+=50;
        //添加进集合
        mi.Sessionlw.put("通知",tongzhi);//把好友装进好友列表中
        //设置颜色
        tongzhi.setOpaque(true);//设置为不透明
        tongzhi.setBackground(Color.YELLOW);
        //添加进容器里面
        mi.Conversations.add(tongzhi);
        //刷新
        mi.Conversations.setPreferredSize(new Dimension(mi.My_friends.getWidth()-20,Friends.y+30));//添加一份长大一段
        mi.Conversations.repaint();//重绘Jpanel,大哥醒醒，我又往你身上加东西了，你还不起来显示出来
        mi.Dynamic.revalidate();//JScrollPane刷新，大哥醒醒jp又长大了，你还不跟着长大，它挤得慌

    }

//    public static int getY() {
//        return y;
//    }
//    public static void setY(int a){
//        y = a;
//    }
}
