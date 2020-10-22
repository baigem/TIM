package com.qq.lookup.gui;

import javax.swing.*;
import java.awt.*;

/**
 *
 *
 * 加好友前端
 *
 * 创建一个简单的gui用来输入userid
 *
 */
public class Front_add_friends extends JFrame {
    public JTextField id = new JTextField();
    public JLabel Tips = new JLabel();


    public Front_add_friends(){

        this.setLayout(new FlowLayout());
        this.setLayout(null);
        this.setSize(380,125);
        setTitle("   添加联系人群聊");	//窗体的名字

        id.setBounds(20,35,340,25);
        Tips.setBounds(50,70,200,25);
        Tips.setFont(new Font("宋体",Font.PLAIN,12));
        Tips.setForeground(Color.red);

        this.add(id);

        this.setLocationRelativeTo(null);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

}
