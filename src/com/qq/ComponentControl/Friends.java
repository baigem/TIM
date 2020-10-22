package com.qq.ComponentControl;

import com.qq.qq;
import com.qq.tools.HeadPortrait;
import com.qq.tools.Picture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 *  好友 界面，用来显示好友的资料
 *
 */






public class Friends {
    public static int y=100;
    static String Sess = "null";
    static HashMap<String,JLabel> Button = new HashMap<>();

    public static void Friends_list(qq mi, String user, String name){
            mi.Friends_list.put(user,name);//把好友装进好友列表中
            JLabel a = new JLabel(name);
            Button.put(user,a);
            a.setBounds(0,y,280,50);
            a.setOpaque(true);//设置为不透明的
            a.setBackground(new Color(253, 253, 253));//按钮颜色
            //添加上头像,头像需要剪切好的
            //判断一下有没有剪切好的头像，没有就剪切

            //先切一下头像
            //_______________________________________________________________
            Picture.garden(new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/"+user+".jpg"),"Select_Avatar",new Color(176, 216, 216));
            Picture.garden(new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/"+user+".jpg"),"Round_head",new Color(253, 253, 253));
            //_________________________________________________________________



            File txfile = new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/Round_head/"+user+".jpg");
            HeadPortrait.Cyh(String.valueOf(txfile),a,new ImageIcon(),40,40);//把头像镶上去
            //把创建好的按钮加进去
            mi.All_friends.add(a);
            //刷新
            mi.All_friends.setPreferredSize(new Dimension(mi.My_friends.getWidth()-20,y+30));//添加一份长大一段
            mi.All_friends.repaint();//重绘Jpanel,大哥醒醒，我又往你身上加东西了，你还不起来显示出来
            mi.My_friends.revalidate();//JScrollPane刷新，大哥醒醒jp又长大了，你还不跟着长大，它挤得慌
            new clicka(a,user,name,mi);//点击好友触发事件
            y+=50;










    }

    public static void setY(int a){
        y = a;
    }

    //点击触发事件,点击 好友名字 按钮时,需要传递两样东西，一个是JL，一个是好友id
    static class clicka{
            public JButton JBchat = new JButton("会话");	  //用来显示会话某位好友
            public JLabel touxiang = new JLabel();


            public clicka(JLabel a,String user,String name,qq mi){
                JBchat.setBounds(100,300,50,30);
                touxiang.setBounds(300,80,200,200);
                //切割一下
                Picture.garden(new File(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/"+user+".jpg"),"temporary",mi.Friend_information.getBackground());
                HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/temporary/"+user+".jpg",touxiang,new ImageIcon(),200,200);




                a.addMouseListener(new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){

                        if(!Sess.equals("null")){
                            Button.get(Sess).setBackground(new Color(253, 253, 253));//好友列表的颜色
                            HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/Round_head/"+Sess+".jpg",Button.get(Sess),new ImageIcon(),40,40);
                        }
                        Sess = user;
                        Button.get(user).setBackground(new Color(176, 216, 216));//好友列表的颜色
                        HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Account number/"+mi.user+"/head_portrait/Select_Avatar/"+user+".jpg",a,new ImageIcon(),40,40);


                        try {
                             if("群：".equals(name.substring(0,2))){
                                mi.Friend_information.removeAll();//删除右侧所有组件
                                JBchat.setBounds(250,300,200,100);
                                touxiang.setBounds(250,80,200,200);
                                mi.Friend_information.add(JBchat);
                                mi.Friend_information.add(touxiang);
                                mi.Friend_information.repaint();
                                return;
                             }
                            display(mi,user,name);//点击之后显示好友信息
                        } catch (IOException ioException) {
                        ioException.printStackTrace();
                        }
                    }
                });

                //点击会话的话
                JBchat.addMouseListener(new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        JBchat(mi,user,name);
                    }
                });


            }

            public void display(qq mi,String user,String name) throws IOException {

                mi.Friend_information.removeAll();//删除右侧所有组件
                System.out.println("开始接收好友资料");
                //720,750);
                mi.Friend_information.add(mi.txtName);//昵称
                mi.Friend_information.add(mi.txtUser);//账号
                mi.Friend_information.add(mi.txtMobile);//手机
                mi.Friend_information.add(mi.txtEmail);//邮箱
                mi.Friend_information.add(mi.txtGender);//性别
                mi.Friend_information.add(mi.txtEnjoy);//爱好

                mi.Friend_information.add(mi.lblGenders);
                mi.Friend_information.add(mi.lblUser);
                mi.Friend_information.add(mi.lblNama);
                mi.Friend_information.add(mi.lblMobile);
                mi.Friend_information.add(mi.lblEmail);
                mi.Friend_information.add(mi.lblEnjoy);
                mi.Friend_information.add(touxiang);
                mi.Friend_information.add(JBchat);



                //发送信号
                //System.out.println("发送申请");
                mi.datahuaTong.writeUTF("6");
                //发送需要查询的好友id
                mi.datahuaTong.writeUTF(user);
                String leixing = mi.datatingTong.readUTF();
                if("用户".equals(leixing)) {
                    //接收信息
                    String xingxi = mi.datatingTong.readUTF();//获取注册信息
                    //System.out.println(xingxi);
                    StringTokenizer everyLineToken = new StringTokenizer(xingxi, " ");
                    mi.lblNama.setText(everyLineToken.nextToken());//昵称
                    mi.lblUser.setText(everyLineToken.nextToken());//账号
                    mi.lblGenders.setText(everyLineToken.nextToken());//性别
                    mi.lblMobile.setText(everyLineToken.nextToken());//手机号
                    mi.lblEmail.setText(everyLineToken.nextToken());//邮箱
                    mi.lblEnjoy.setText(everyLineToken.nextToken());//爱好
                    //接收完毕
                    mi.Friend_information.repaint();//刷新
                }else{
                    //群的信息就暂时无视
                    leixing = mi.datatingTong.readUTF();//虽然无视但还是需要接收一下
                }




            }

            void JBchat(qq mi,String user,String name){
                //检索会话界面有没有这个好友的聊天界面，有的话回去，没有则创建
                if(mi.Sessionlw.containsKey(user)){
                    //存在直接跳转到会话界面的那个窗口
                    //因为现在是在好友界面所以需要先移除好友界面
                    mi.remove(mi.JPfriend);
                    //然后添加会话界面
                    mi.add(mi.JPrsoc);
                    //然后移除其他的组件
                    mi.JPconfu.removeAll();
                    //然后添加组件
                    mi.JPconfu.add(mi.Sessionrw.get(user).inPuts);
                    mi.JPconfu.add(mi.Sessionrw.get(user).rolls);
                    mi.JPconfu.add(mi.Sessionrw.get(user).txtname);
                    mi.Conversations.repaint();//重绘Jpanel,大哥醒醒，我又往你身上加东西了，你还不起来显示出来
                    mi.Dynamic.revalidate();//JScrollPane刷新，大哥醒醒jp又长大了，你还不跟着长大，它挤得慌

                    mi.repaint();//整个界面进行刷新
                    mi.Sessionrw.get(user).inPut.grabFocus();//获取焦点


                }
                else{
                    //不存在则创建，头疼这里怎么写哪？？？
                    //首先输入框聊天记录，成员，公告都具有唯一性
                    //还需要判断这是群还是，不这不是群，群的话不会进入这里
                    //创建一个方法专门创建新的窗口
                    if("群：".equals(name.substring(0,2))){
                        new Group_button(mi,user,name,true);//先创建按钮,因为是强制创建，所以最后的true代表强制创建窗口且获取焦点
                        HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Resources/会话true.png",mi.Conversation,new ImageIcon(),27,27);
                        HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Resources/好友false.png",mi.Friend,new ImageIcon(),27,27);
                    }else
                        new Private_button(mi,user,name,true);//先创建按钮,因为是强制创建，所以最后的true代表强制创建窗口且获取焦点
                        HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Resources/会话true.png",mi.Conversation,new ImageIcon(),27,27);
                        HeadPortrait.Cyh(System.getProperty("user.dir")+"/data/Resources/好友false.png",mi.Friend,new ImageIcon(),27,27);
                }



            }




        }
}


