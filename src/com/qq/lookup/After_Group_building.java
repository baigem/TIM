package com.qq.lookup;


import com.qq.ComponentControl.Friends;
import com.qq.lookup.gui.Front_Group_building;
import com.qq.qq;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * 建群的后端代码
 *
 */
public class After_Group_building extends Front_Group_building {
    public After_Group_building(qq mi){
            name.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if(key == '\n'){
                        Send(mi);
                        System.gc();
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {}
            });
    }


    public void Send(qq mi){
        //发送信息给服务器
        //id为空的话
        if("".equals(id.getText())  || "请输入群id".equals(id.getText())){
            //提示用户，输入id
            tishiid.setText("请输入id");
            this.add(tishiid);
            this.repaint();//刷新
            return;
        }
        if("".equals(name.getText()) || "请输入群昵称".equals(name.getText())){
            this.add(tishiname);
            this.repaint();//刷新
            return;
        }

        try {
            mi.datahuaTong.writeUTF("10");

        mi.datahuaTong.writeUTF(id.getText());
        if(mi.datatingTong.readBoolean()){
            //群不存在可以创建，发送昵称
            mi.datahuaTong.writeUTF(name.getText());
            if(mi.datatingTong.readBoolean()){
                //创建成功
                dispose();//关闭窗口
                //重洗接收一遍好友名单，先接群再接好友
                mi.datahuaTong.writeUTF("5");
                String line;
                Friends.setY(10);
                while(!(line = mi.datatingTong.readUTF()).equals("0")){

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
                mi.All_friends.repaint();

                this.repaint();//刷新

                JOptionPane.showMessageDialog(null,"\""+name.getText() +"\"创建成功");
            }
            else {
                JOptionPane.showMessageDialog(null,"\""+name.getText() +"\"创建失败");

            }

        }else{
            //群存在，则提示重新注册
            this.remove(tishiname);
            tishiid.setText("此ID已存在，请选择其他ID创建");
            this.add(tishiid);
            id.setText("");
            this.repaint();//刷新
        }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
