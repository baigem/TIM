package com.qq.file;



//此文件废弃不用



import com.qq.file.File_Control;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * 此类用于读写文件
 * 复制主文件为副本
 * 访问副本文件
 * 防止因为文件导致异常
 *
 * 更改思路，设置为文件读取的主权限
 * 其他想要调取读写权限需要排队
 * 防止拥堵或大量文件创建和删除
 *
 * 再次更改思路
 * 一个主体方法
 * 其余可以吧文件的控制权集成到这个方法中
 * 好处是无论多少个文件都可以添加即运行
 *
 */
public class File_reading_Writing{
//    private static boolean userBlock = false;//假就是文件没被调用，真为文件被调用中
//    private static boolean nameBlock = false;
//    private static boolean genderBlock = false;
//    private static boolean phoneBlock = false;
//    private static boolean eailboxBlock = false;
//    private static boolean hobbyBlock = false;


//    static File path = new File("Server_data/User_information/");
//    static File userFile = new File(path+"User.txt");
//    static File nameFile = new File(path+"Name.txt");
//    static File genderFile = new File(path+"Gender.txt");
//    static File phoneFile = new File(path+"Phone.txt");
//    static File eailboxFile = new File(path+"Eailbox.txt");
//    static File hobbyFile = new File(path+"Hobby.txt");
//    private BufferedReader userData;
//    private BufferedReader nameData;
//    private BufferedReader genderData;
//    private BufferedReader phoneData;
//    private BufferedReader eailboxData;
//    private BufferedReader hobbyData;

    //创建一个泛型用来储存文件的路径
    //储存一个内部类当做结构体

    static ArrayList<File_Control> fileAggregate = new ArrayList<>();


    public void add(String filePath,String fileName) throws Exception {
        fileAggregate.add(new File_Control(filePath,fileName));
    }

    //读取文件
    public BufferedReader reading(String fileName) throws Exception {

        for(File_Control file : fileAggregate){
            //通过文件名判断是否接管了这个文件
            if(fileName.equals(file.getFileNama())){
                    return file.getRead();
            }
       }
        throw new FileNotFoundException("未获得此文件的控制权");
    }






}
