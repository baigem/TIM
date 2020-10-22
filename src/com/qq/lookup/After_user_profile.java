package com.qq.lookup;

import com.qq.lookup.gui.Front_user_profile;
import com.qq.qq;
import com.qq.tools.HeadPortrait;
import com.qq.tools.Picture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class After_user_profile extends Front_user_profile {
    public After_user_profile(qq mi, String data){

        StringTokenizer everyLineToken = new StringTokenizer(data," ");
        lblNama.setText(everyLineToken.nextToken());//昵称
        String id = everyLineToken.nextToken();
        lblUser.setText(id);//账号
        lblGenders.setText(everyLineToken.nextToken());//性别
        lblMobile.setText(everyLineToken.nextToken());//手机号
        lblEmail.setText(everyLineToken.nextToken());//邮箱
        lblEnjoy.setText(everyLineToken.nextToken());//爱好

        HeadPortrait.Download_Avatar(mi,mi.user,id);
        //裁剪图片
        Picture.garden(new File(System.getProperty("user.dir")+"/data/Account number/123/head_portrait/"+id+".jpg"),"temporary",this.getContentPane().getBackground());
        //镶嵌上去
        HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/123/head_portrait/temporary/"+id+".jpg",this.headPortrait,new ImageIcon(),100,100);

        if(mi.Friends_list.containsKey(id)){
            add.setText("已添加");
            add.setForeground(Color.lightGray);
        }
        else{
            add.setText("添加好友");
        }

        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!add.getText().equals("已添加")){
                    //用主线程还是父线程哪？
                    try {
                        mi.datahuaTong.writeUTF("8");
                        mi.datahuaTong.writeUTF(lblUser.getText());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    dispose();//关闭界面
                    JOptionPane.showMessageDialog(null,"申请发送成功");
                }
                //如果点击了添加好友则发送通知




            }
        });



    }





}
