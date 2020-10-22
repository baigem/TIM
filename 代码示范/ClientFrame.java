package xiaowen.gui.chat03;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


 //@SuppressWarnings("serial")
public class ClientFrame extends JFrame {
private JTextArea jta;
private JTextField jtf;
private JPanel jp;
private JButton jbtn;//发送按钮
private JScrollPane jsp1,jsp2;
private JList list;//动态按钮
private DefaultListModel model;
private String name;
private Socket s;
private BufferedReader br;
private PrintWriter out;
private boolean stop=false;



public ClientFrame(String name){
this.setSize(800, 600);
this.setLocation(100, 100);//窗口的位置
this.name=name;//用户名
this.setTitle("网络聊天！当前用户是："+this.name);
//this.setDefaultCloseOperation(EXIT_ON_CLOSE);

this.addWindowListener(new WindowAdapter(){//窗口的关闭按钮被点击时调用的方法
@Override
public void windowClosing(WindowEvent e) {
System.out.println(Server.DISCONNECT_TOKEN);//断开与服务器的连接
}
});


jta=new JTextArea();
jsp1=new JScrollPane(jta);//垂直框
jtf=new JTextField(35);
jbtn=new JButton("发送");
jbtn.addActionListener(new BtnClick());

model=new DefaultListModel();
list=new JList(model);
list.setFixedCellWidth(100);//设置宽度之后，如果宽度不够，会使用...
jsp2=new JScrollPane(list);
jp=new JPanel();
this.add(jsp1);
jp.add(jtf);
jp.add(jbtn);
this.add(jp,BorderLayout.SOUTH);
this.add(jsp2, BorderLayout.WEST);
jtf.addKeyListener(new KeyClick());

connect();

this.setVisible(true);
}

private void connect(){
try {
s=new Socket(Server.HOST,Server.PORT);
br=new BufferedReader(new InputStreamReader(s.getInputStream()));
out=new PrintWriter(s.getOutputStream(),true);
out.println(name);

new Thread(new ReceiveThread()).start();

} catch (UnknownHostException e) {
e.printStackTrace();
} catch (IOException e) {
e.printStackTrace();
}
}

private class KeyClick extends KeyAdapter{
@Override
public void keyPressed(KeyEvent e) {
if(e.getKeyCode()==KeyEvent.VK_ENTER){
send();
}
}
}






//发送信息
private void send(){
String word=jtf.getText();
if(word==null||word.trim().equals("")){//如果输入框内容为空则不发送
return;
}
Object[] vals=list.getSelectedValues();
boolean isAll=false;
String us=Server.CHAR_FLAG_START;
if(vals.length<=0) isAll=true;//没选人就是向所有人发布
for(Object v:vals){
	if(v.toString().equals("所有人")){
		isAll=true;
		break;
	}
	else{
		us+=v.toString()+",";
	}
}
if(isAll){
System.out.println(us+"all"+Server.CHAR_FLAG_END+word);
} else{
System.out.println(us+Server.CHAR_FLAG_END+word);
}
jtf.setText("");//清空输入框
}







private void close(){
stop=true;
}




private void handleList(String str){
String users=str.substring(Server.TRAN_USER_FLAG.length());
//System.out.println(users);
model.removeAllElements();//每次都清空
String[] us=users.split(",");
for(String u:us){
model.addElement(u);
}
model.addElement("所有人:");
}

private void receive() throws IOException{
String str=br.readLine();
if(str.equals(Server.DISCONNECT_TOKEN)){
close();
}
if(str.startsWith(Server.TRAN_USER_FLAG)){
handleList(str);
return;
}
jta.setText(jta.getText()+str+"\n");
System.out.println(str);
}

private class ReceiveThread implements Runnable{
@Override
public void run() {
try {
while(true){
if(stop) break;
receive();
}
} catch (IOException e) {
e.printStackTrace();
} finally{
try {
if(s!=null) s.close();
} catch (IOException e) {
e.printStackTrace();
}
System.exit(0);
}
}
}

private class BtnClick implements ActionListener{
@Override
public void actionPerformed(ActionEvent e) {
if(e.getSource()==jbtn){
send();
}
}

}
}