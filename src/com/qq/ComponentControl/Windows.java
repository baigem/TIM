package com.qq.ComponentControl;

import javax.swing.*;
import java.text.SimpleDateFormat;

public class Windows {
    public JTextArea roll = new JTextArea();//聊天记录，历史
    public JTextArea inPut = new JTextArea();//输入文本框
    public JScrollPane rolls = new JScrollPane(roll);//聊天记录的下拉框
    public JScrollPane inPuts = new JScrollPane(inPut);//输入框
    //创建一个容器，用来显示当前名字
    public JPanel JPname = new JPanel();
    public JLabel txtname = new JLabel();//名字显示的地方

    //_________________群专属______________________________
    public JTextArea noTice = new JTextArea();//公告
    public JTextArea memBer = new JTextArea();//群成员

    public JScrollPane noTices = new JScrollPane(noTice);//公告滑动
    public JScrollPane memBers = new JScrollPane(memBer);//群成员
    //_________________群专属______________________________

    public JPanel all = new JPanel();

    SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式



}
