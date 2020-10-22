package com.qq.lookup.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * 建群的前端代码
 *
 */
public class Front_Group_building extends JFrame{
    public JTextField id = new JTextField();
    public JTextField name = new JTextField();
    public JLabel tishiid = new JLabel("请输入id");
    public JLabel tishiname = new JLabel("请输入群名");
    public  Front_Group_building(){
        this.setLayout(new FlowLayout());
        this.setLayout(null);
        this.setSize(380,160);
        setTitle("建群");	//窗体的名字

        id.setBounds(20,35,340,25);
        name.setBounds(20,80,340,25);
        tishiid.setBounds(22,58,340,25);
        tishiname.setBounds(20,110,340,25);
        tishiid.setForeground(Color.red);
        tishiname.setForeground(Color.red);

        id.setText("请输入群id");
        id.setForeground(Color.lightGray);
        name.setText("请输入群昵称");
        name.setForeground(Color.lightGray);


        this.add(id);
        this.add(name);
        this.setLocationRelativeTo(null);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.requestFocus();//获取焦点
        this.setVisible(true);






        id.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {//失去焦点bai时
            //失去了焦点
                if(!"请输入群id".equals(id.getText()) && "".equals(id.getText())){
                    id.setText("请输入群id");
                    id.setForeground(Color.lightGray);
                }
            }
            public void focusGained(FocusEvent e) {//获得焦du点时
            //获得了焦点
                if("请输入群id".equals(id.getText())){
                    id.setText("");
                    id.setForeground(Color.black);
                }

            }
        });

        name.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {//失去焦点bai时
                //失去了焦点
                if(!"请输入群昵称".equals(name.getText()) && "".equals(name.getText())){
                    name.setText("请输入群昵称");
                    name.setForeground(Color.lightGray);
                }
            }
            public void focusGained(FocusEvent e) {//获得焦du点时
                //获得了焦点
                if("请输入群昵称".equals(name.getText())){
                    name.setText("");
                    name.setForeground(Color.black);
                }

            }
        });



        id.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == '\n'){
                    name.grabFocus();//获取焦点
                    System.gc();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });





    }
//    public static void main(String[] args){
//
//        new Front_Group_building();
//
//    }


}
