package com.qq.file;

import java.io.*;

public class File_Control{

    private final String fileNama;//文件名称
    private final String filePath;//文件路径
    private boolean confirm = true;
    private File file;
    public File_Control(String filePath,String fileName){
        this.fileNama = fileName;
        this.filePath = filePath;
        file = new File(filePath);
    }





    public void setConfirm(boolean confirm){
        this.confirm = confirm;
    }
    public BufferedReader getRead() throws Exception {//返回输出流，读取

        while (!confirm){
            try {
                Thread.sleep(2000);//延迟1s执行
            } catch (Exception ignored) {}
        }

        try {
            return (new BufferedReader(new FileReader(filePath)));
        }catch (Exception e){
                throw new Exception(filePath+"此文件不存在");//直接报异常
        }
    }


    public File getFile(){return  file;}
    public String getFileNama(){//返回文件名
        return fileNama;
    }
    public String getFilePath(){return filePath;}

    //返回输入流，写入
    public PrintWriter setWrite() throws FileNotFoundException {
        while (!confirm){
            try {
                Thread.sleep(100);//延迟1s执行
            } catch (Exception ignored) {}
        }
        try{
        return (new PrintWriter(new FileOutputStream(filePath,true)));
        }catch (FileNotFoundException e){
            throw  new FileNotFoundException("此文件不存在");
        }
     }

//     修改文件信息
//    public boolean Modify(String user,String  data) throws IOException {
//        BufferedReader duqu = null;//读取流
//        File pash = new File(filePath+"t");//副本文件
//        PrintWriter xieru;//副本写入流
//        String everyLine;//数据临时承载
//
//        try {
//            duqu = this.getRead();
//            if(!pash.createNewFile()){
//                duqu.close();
//                System.out.println("创建副本失败");
//                return false;
//                //创建副本失败
//            }
//            xieru = new PrintWriter(pash);
//
//        } catch (Exception e) {
//            e.getStackTrace();
//            if(duqu != null)
//                duqu.close();
//            return false;
//        }
//
//        StringBuilder filedata = new StringBuilder();//数据保存
//        //进行正式的写入
//        while (true) {
//            assert duqu != null;
//            if ((everyLine = duqu.readLine()) == null) break;
//            StringTokenizer everyLineToken = new StringTokenizer(everyLine, " ");
//            String users = everyLineToken.nextToken();//读取账号
//            String datas = everyLineToken.nextToken();//读取后面信息
//
//
//            assert false;
//            if (user.equals(users)) {//寻找账号
//                filedata.append(users).append(" ").append(data).append("\n");
//            } else {
//                //如果不是需要修改的用户的信息则原样写入
//                filedata.append(users).append(" ").append(datas).append("\n");
//            }
//        }
//        assert false;
//        xieru.println(filedata.toString());//把修改好的数据写入到文件中
//        xieru.close();//关闭文件
//        duqu.close();//关闭文件
//        confirm = false;//锁住文件
//        File b = new File(filePath);
//        //删除文件并且更改文件名
//        return b.delete() && pash.renameTo(b);
//    }





}
