package com.qq.lookup;

import com.qq.lookup.gui.Front_add_friends;
import com.qq.lookup.gui.Front_user_profile;
import com.qq.qq;
import com.qq.tools.HeadPortrait;
import com.qq.tools.Picture;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * 加好友操作后端
 *
 */
public class After_add_friends extends Front_add_friends {
        public After_add_friends(qq mi){
            id.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if(key == '\n'){

                        send(mi);
                        System.gc();
                    }

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });





        }


        public void send(qq mi){
            if(!id.getText().equals("") && !id.getText().equals("\n")){

                try {
                    mi.datahuaTong.writeUTF("6");
                    mi.datahuaTong.writeUTF(id.getText().trim());//发送账号
                    String data = mi.datatingTong.readUTF();//接收类型
                    if("群".equals(data)){
                        data = mi.datatingTong.readUTF();
                        if("此账号不存在".equals(data)){
                            id.setText("");
                            Tips.setText("此账号不存在");
                            this.add(Tips);
                            this.repaint();
                            return;
                        }

                        dispose();
                        Front_user_profile f = new Front_user_profile();
                        f.lblUser.setText(id.getText());
                        f.lblNama.setText(data);
                        //获取头像，关闭页面后需要删除文件
                        HeadPortrait.Download_Avatar(mi,mi.user,id.getText());
                        //裁剪图片
                        Picture.garden(new File(System.getProperty("user.dir")+"/data/Account number/123/head_portrait/"+id.getText()+".jpg"),"temporary",f.getContentPane().getBackground());
                        //镶嵌上去
                        HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/123/head_portrait/temporary/"+id.getText()+".jpg",f.headPortrait,new ImageIcon(),100,100);

                        if(!mi.Friends_list.containsKey(id.getText())){
                            //不存在的话
                            f.add.setText("申请入群");
                            new add(mi,f);
                            return;
                        }
                        else {
                            f.add.setText("已加入");
                        }
                        return;
                    }


                    System.out.println("申请入群的话到不了这里");
                    data = mi.datatingTong.readUTF();
                    StringTokenizer lin = new StringTokenizer(data," ");
                    String xingxi = lin.nextToken();
                    if(xingxi.equals("此账号不存在")){
                        id.setText("");
                        Tips.setText("此账号不存在");
                        this.add(Tips);
                        this.repaint();
                    }else{
                        //有用户直接显示
                        new After_user_profile(mi,data);
                        dispose();//关闭界面
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


        }




        //点击申请入群处理
    class add{

            public add(qq mi,Front_user_profile a){
                a.add.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //点击申请入群
                        //指令为11
                        //格式，指令>群id>
                        try {
                            mi.datahuaTong.writeUTF("11");
                            mi.datahuaTong.writeUTF(id.getText());
                            a.dispose();
                            JOptionPane.showMessageDialog(null,"申请发送成功");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
            }
    }





}
