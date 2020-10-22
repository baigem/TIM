package com.qq.cs;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;


@SuppressWarnings("serial")
public class bbbbbbbbbb extends JFrame implements ActionListener{
    JButton open=null;//按钮
    public static void main(String[] args) {
        new bbbbbbbbbb();
    }
    public bbbbbbbbbb(){
        open=new JButton("open");//按钮初始化
        this.add(open);//添加
        this.setBounds(400, 200, 100, 100);//精准布控
        this.setVisible(true);//界面可见
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击x时的动作，退出程序
        open.addActionListener(this);//点击按钮时的动作，因为自身实现了act接口所以传递自身
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //文件选择器获取文件或者文件夹
        //========================================
        JFileChooser jfc=new JFileChooser();//文件选择框组件
        //设置当前路径为桌面路径,否则将我的文档作为默认路径
        FileSystemView fsv = FileSystemView .getFileSystemView();//获取桌面路径
        jfc.setCurrentDirectory(fsv.getHomeDirectory());//把获取到的路径给文件选择框
        //JFileChooser.FILES_AND_DIRECTORIES 选择路径和文件
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );//可以选择文件和目录
        //用户选择的路径或文件
        if (jfc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            File file=jfc.getSelectedFile();//把路径给file
            if(file.isDirectory()){//进行判断
                System.out.println("文件夹:"+file.getAbsolutePath());
            }else if(file.isFile()){
                System.out.println("文件:"+file.getAbsolutePath());
            }
        }
        //=======================================================
        //文件选择器获取文件,这里只能获取文件，不能获取文件夹
       /* JFileChooser jfc=new JFileChooser("C:\\");//可以直接在这设置默认路径
        if(jfc.showOpenDialog(bbbbbbbbbb.this)==JFileChooser.APPROVE_OPTION){
        File file=jfc.getSelectedFile();
              System.out.println("文件:"+file.getAbsolutePath());
        }*/
        //====================================================
    }
}