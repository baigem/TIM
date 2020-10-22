package com.qq.tools;

import com.qq.file.File_Control;

import java.io.*;
import java.security.PublicKey;
import java.util.StringTokenizer;

public class FileOperation {
    public static boolean copy(String rout, String routs){
        if(!new File(rout).exists()){
            System.out.println("默认头像并不纯债"+rout);
            return false;
        }

        //进行复制
        try {
            FileInputStream read = new FileInputStream(rout);
            FileOutputStream write = new FileOutputStream(routs);
            byte[] b = new byte[1024*1024];
            int data;

            while((data = read.read(b)) != -1){
                write.write(b,0,data);
            }
            //复制完成，关闭文件
            read.close();
            write.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }//读取错误


    }

    //检索文件数据是否正确，如账号密码，正确返回true，错误或不存在返回false
    public static boolean retrieval(BufferedReader file,String user,String password){
        String everyLine;   //保存单行数据
        String lineUser;     //保存读取的用户
        String linepassword;        //保存读取的密码
        try {
            while ((everyLine = file.readLine()) != null) {//判断获取的内容是否为空，为空则到达文件末尾
                StringTokenizer everyLineToken = new StringTokenizer(everyLine, " ");//以 进行分割字符串
                lineUser = everyLineToken.nextToken();//把获取到的账号赋予字符串
                linepassword = everyLineToken.nextToken();//把获取到的密码赋予字符串
                if (user.equals(lineUser)) {//判断文件中是否有这个账号
                    //有这个账号，接下来判断密码是否正确
                    //密码正确返回true
                    //密码错误返回false
                    file.close();//关闭文件
                    return password.equals(linepassword);
                }
            }
            //如果循环结束还没有结束程序，那一定是没找到账号，返回false
            file.close();
            return false;

        }catch (IOException e){
            try {
                file.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return false;
        }



    }

    //提取数据
    public static String extract(BufferedReader file,String user){
        String everyLine;   //保存单行数据
        String lineUser;     //保存读取的用户
        try {
            while ((everyLine = file.readLine()) != null) {//判断获取的内容是否为空，为空则到达文件末尾
                StringTokenizer everyLineToken = new StringTokenizer(everyLine, " ");//以 进行分割字符串
                lineUser = everyLineToken.nextToken();//把获取到的账号赋予字符串
                if (user.equals(lineUser)) {//判断文件中是否有这个账号
                    file.close();//关闭文件
                    return everyLineToken.nextToken();//返回数据
                }
            }
            //如果循环结束还没有结束程序，那一定是没找到账号，返回false
            file.close();
            return "null";

        }catch (IOException e){
            try {
                file.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return "null";
        }




    }

    //更改数据，篡改数据Tampering
    public static boolean tampering(File_Control username, String user, String data){
        try {
            BufferedReader file = username.getRead();
            File filepath = new File(username.getFilePath()+"t");
            if(!filepath.createNewFile()){
                System.out.println("创建失败");
                //创建失败就删除再创建
                if(filepath.delete()){
                    if(!filepath.createNewFile()){
                        return false;
                    }
                }else
                    return false;
            }
            PrintWriter files  = new PrintWriter(username.getFilePath()+"t");//副本

            String rou;
            String users;
            while((rou = file.readLine())!=null){
                StringTokenizer everyLineToken = new StringTokenizer(rou, " ");//以 进行分割字符串
                System.out.println(rou);
                if(rou.equals("\n") || rou.equals("") ||rou.equals(" ")){
                    break;
                }
                users = everyLineToken.nextToken();
                if(user.equals(users)){
                    System.out.println("写入："+user+" "+data);
                    files.println(users+" "+data);
                }
                else
                    System.out.println("写入："+users);
                    files.println(users+" "+everyLineToken.nextToken());
            }
            file.close();
            files.close();
            //把文件上锁
            username.setConfirm(false);

            if(username.getFile().delete()){
                if(filepath.renameTo(username.getFile())){
                    username.setConfirm(true);//解开锁
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
    //删除某一行数据
    public static boolean Delete_row(File file, String id){

        if(!Delete_row(file,id,"null"," ")){
            System.out.println("出现错误，无法删除文件数据");
            return false;
        }


        return true;
    }

    //为什么没有写注释？？？谁告诉我这个方法是干什么用的？？？？？？？？？？？？？？？？？？我傻了
    //修改id后面的内容
    public static boolean Delete_row(File file, String id,String modify,String jiange){
        try {
            BufferedReader data = new BufferedReader(new FileReader(file));//主文件
            File files = new File(file+"t");
            PrintWriter datas = new PrintWriter(new FileWriter(files));//副本
            String lin;
            while((lin = data.readLine()) != null){
                StringTokenizer eve = new StringTokenizer(lin,jiange);
                if(!id.equals(eve.nextToken())){
                    datas.println(lin+jiange+eve.nextToken());
                }else if(!modify.equals("null")){
                    datas.println(modify);
                }
            }
            data.close();
            datas.close();

            int x=0;
            while(!file.delete()){
                if(x==10000){
                    break;
                }
                x++;
            }

            //替换文件名
            if(files.renameTo(file)){
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;

    }






    //信息查找
    public static boolean lookup(BufferedReader chazhao, String user){
        //存在账号返回true
        //不存在账号返回false
        String everyLine = null;
        while(true){
            assert false;
            try {
                if ((everyLine = chazhao.readLine()) == null) break;//判断获取的内容是否为空，为空则到达文件末尾
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringTokenizer everyLineToken = new StringTokenizer(everyLine," ");//以空格进行分割字符串
            if(user.equals(everyLineToken.nextToken())){//判断文件中是否有这个账号
                try {
                    chazhao.close();//关闭文件
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

        }
        return false;
    }


    //读取整个文件的数据，并非单独一行
    public static String allextract(BufferedReader file,String interval,Boolean around){
        String everyLine = "";   //保存单行数据
        StringBuilder aggregate = new StringBuilder();

        if(interval.equals("null")){
            while(true){
                try {
                    if ((everyLine = file.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                aggregate.append(everyLine).append("\n");
            }

            return aggregate.toString();

        }

        try {
            if (around) {

                while ((everyLine = file.readLine()) != null) {//判断获取的内容是否为空，为空则到达文件末尾
                    StringTokenizer everyLineToken = new StringTokenizer(everyLine, interval);//以 进行分割字符串
                    aggregate.append(everyLineToken.nextToken()).append(" ");
                }
                //如果循环结束还没有结束程序，那一定是没找到账号，返回false
                file.close();
                return String.valueOf(aggregate);


            } else {

                while ((everyLine = file.readLine()) != null) {//判断获取的内容是否为空，为空则到达文件末尾
                    StringTokenizer everyLineToken = new StringTokenizer(everyLine, interval);//以 进行分割字符串
                    everyLineToken.nextToken();
                    aggregate.append(everyLineToken.nextToken()).append(" ");
                }
                //如果循环结束还没有结束程序，那一定是没找到账号，返回false
                file.close();
                return String.valueOf(aggregate);

            }
        }catch (IOException e){
            e.printStackTrace();
            try {
                file.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return "null";

        }
    }








}
