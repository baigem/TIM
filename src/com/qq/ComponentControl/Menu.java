package com.qq.ComponentControl;

import com.qq.lookup.After_user_profile;
import com.qq.lookup.gui.Front_user_profile;
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
import java.io.IOException;

/**
 *
 *
 * 好友按钮上右击菜单
 *
 *
 */
public class Menu extends JFrame {
    public String leix;
    public JLabel data = new JLabel("查看资料");
    public JLabel remove = new JLabel("移除该会话");
    public JLabel delete = new JLabel("删除该好友");

    public Menu(qq mi , String id,int x,int y){
        this.setLayout(new FlowLayout());
        this.setLayout(null);
        this.setSize(100,100);
        this.setLocation(x,y);
        this.requestFocus();//获取焦点


        this.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {//失去焦点bai时
                //失去了焦点
                dispose();
            }
            public void focusGained(FocusEvent e) {//获得焦du点时
                //获得了焦点
            }
        });



        data.setBounds(0,0,100,25);
        remove.setBounds(0,25,100,25);
        delete.setBounds(0,50,100,25);



        //____________________添加__________________________
            this.add(data);//查看资料
            this.add(remove);//移除会话
            this.add(delete);//删除好友|退群
        //____________________添加__________________________

        //判断是群还是用户
        try {
            mi.datahuaTong.writeUTF("19");
            mi.datahuaTong.writeUTF(id);
            leix = mi.datatingTong.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if("群".equals(leix)){
            delete.setText("退出该群");
        }

        data.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                //查看资料
                try {
                    mi.datahuaTong.writeUTF("6");
                    mi.datahuaTong.writeUTF(id);
                    if(!"群".equals(mi.datatingTong.readUTF())){
                        String data = mi.datatingTong.readUTF();
                        new After_user_profile(mi,data);
                        dispose();
                    }else{
                        String data = mi.datatingTong.readUTF();
                        Front_user_profile f = new Front_user_profile();
                        f.lblUser.setText(id);
                        f.lblNama.setText(data);
                        //获取头像，关闭页面后需要删除文件
                        HeadPortrait.Download_Avatar(mi,mi.user,id);
                        //裁剪图片
                        Picture.garden(new File(System.getProperty("user.dir")+"/data/Account number/123/head_portrait/"+id+".jpg"),"temporary",f.getContentPane().getBackground());
                        //镶嵌上去
                        HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/123/head_portrait/temporary/"+id+".jpg",f.headPortrait,new ImageIcon(),100,100);

                        if(!mi.Friends_list.containsKey(id)){
                            //不存在的话
                           f.remove(f.add);
                        }
                        else {
                            f.remove(f.add);
                        }
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                dispose();//关闭本窗口
            }
        });

        remove.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                //移除会话
                int y = mi.Sessionlw.get(id).getY();
                mi.Conversations.remove(mi.Sessionlw.get(id));//列表里面删除
                mi.Sessionlw.remove(id);//集合里面删除
                mi.JPconfu.removeAll();
                System.gc();
                for(JLabel a:mi.Sessionlw.values()){
                    int yy = a.getY();
                    if(y<yy){
                        a.setBounds(0,yy-70,mi.Dynamic.getWidth(),65);
                    }
                }
                Button.y-=70;
                mi.JPconfu.removeAll();
                //刷新
                mi.Conversations.repaint();//刷新
                mi.JPconfu.repaint();
                dispose();//关闭本窗口
            }
        });


        delete.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                //删除好友/退群
                if("群".equals(leix)){
                    //是群的话
                    try {
                        mi.datahuaTong.writeUTF("18");
                        mi.datahuaTong.writeUTF(id);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    //退完群再移除会话
                    //移除会话
                    int y = mi.Sessionlw.get(id).getY();
                    mi.Sessionlw.remove(id);
                    for(JLabel a:mi.Sessionlw.values()){
                        int yy = a.getY();
                        if(y<yy){
                            a.setBounds(0,yy-50,mi.Dynamic.getWidth(),65);
                        }
                    }
                    Button.y-=70;
                    mi.JPconfu.removeAll();
                    //刷新
                    mi.Conversations.repaint();//刷新
                }else{
                    //删除好友
                    //移除会话
                    int y = mi.Sessionlw.get(id).getY();
                    mi.Sessionlw.remove(id);
                    for(JLabel a:mi.Sessionlw.values()){
                        int yy = a.getY();
                        if(y<yy){
                            a.setBounds(0,yy-50,mi.Dynamic.getWidth(),65);
                        }
                    }
                    Button.y-=70;
                    //移除完会话只是把按钮移除了右侧的界面还在
                    //需要进行删除
                    mi.JPconfu.removeAll();
                    //刷新
                    mi.Conversations.repaint();//刷新

                    try {
                        //在系统中进行删除
                        mi.datahuaTong.writeUTF("17");
                        mi.datahuaTong.writeUTF(id);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }

                //最后需要删除本身
                dispose();//关闭本窗口
            }
        });



        this.setVisible(true);
    }

}
