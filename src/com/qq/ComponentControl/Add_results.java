package com.qq.ComponentControl;

import com.qq.qq;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 *
 * 返回添加好友的结果
 * 同意或者拒绝
 *
 */

public class Add_results {
    JLabel jieguo = new JLabel();


    public Add_results(qq mi,JLabel tongzhi, String user , String z){
        this(mi,tongzhi,user,z,"用户");
    }

    public Add_results(qq mi,JLabel tongzhi, String user , String z,String leixing){
        jieguo.setBounds(20,Notice.y,400,50);
        Notice.y+=55;
        System.out.println("创建了一条通知");
        tongzhi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mi.JPconfu.removeAll();
                add(mi,user,z,leixing);
            }
        });
    }

    public void add(qq mi, String user , String z,String leixing){
        try {
            mi.datahuaTong.writeUTF("7");
            mi.datahuaTong.writeUTF(user);
            String id = mi.datatingTong.readUTF();
            if("群".equals(leixing)){
                jieguo.setText(id + " 已"+z+"您的入群申请");
            }else {
                jieguo.setText(id + " 已" + z + "您的好友申请");
            }
            mi.JPconfu.add(jieguo);
            //刷新
            mi.JPconfu.repaint();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
