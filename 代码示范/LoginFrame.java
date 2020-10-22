package xiaowen.gui.chat03;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class LoginFrame extends JFrame {
private JLabel jl;//用户名
private JButton jbtn;//按钮
private JTextField jtf;//用户名输入框
//private LoginFrame lf;

public static void main(String[] args) {
new LoginFrame();
}

private void close(){
this.setVisible(false);
}

public LoginFrame(){
//lf=this;
this.setLocation(100,100);
this.setSize(400, 100);
this.setTitle("用户连接");//窗口标题
this.setResizable(false);
this.setLayout(new FlowLayout());
this.setDefaultCloseOperation(EXIT_ON_CLOSE);

jl=new JLabel("输入用户名：");
jtf=new JTextField(25);
jbtn=new JButton("连接");
jbtn.addActionListener(new ActionListener()
{//点击连接后的操作
//@Override
public void actionPerformed(ActionEvent arg0) {
String name=jtf.getText();
if(name==null||name.trim().equals("")){
JOptionPane.showMessageDialog(null, "输入你的名字");
return;
}
new ClientFrame(name);//执行主界面
//lf.setVisible(false);
close();
}

});

this.add(jl);
this.add(jtf);
this.add(jbtn);

this.setVisible(true);
}
}

