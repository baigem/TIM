package xiaowen.gui.chat03;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Server {
public static final String HOST="127.0.0.1";
public static final int PORT=6868;
public static final String DISCONNECT_TOKEN="disconnect";
public static final String TRAN_USER_FLAG="connect:";
public static final String CHAR_FLAG_START="to:";
public static final String CHAR_FLAG_END=":end";
private Map<String,ServerThread> cs;

public static void main(String[] args) {
	new Server().startup();
}

private void startup(){
	ServerSocket ss=null;
	try {
		ss=new ServerSocket(PORT);
		cs=new HashMap<String,ServerThread>();
		while(true){
			try {
				Socket s=ss.accept();//阻塞在这里
				new Thread(new ServerThread(s)).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} catch (IOException e) {
		e.printStackTrace();
	} finally{
		try {
			if(ss!=null) ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

private class ServerThread implements Runnable{
private Socket s;
private BufferedReader br;
private PrintWriter out;
private String name;
private boolean stop=false;

public ServerThread(Socket s) throws IOException {
this.s=s;
br=new BufferedReader(new InputStreamReader(s.getInputStream()));
out=new PrintWriter(s.getOutputStream(),true);
name=br.readLine();
name+="["+s.getInetAddress().getHostAddress()+"]";
cs.put(name,this);
send(name+"连接了");
sendUser();
}

private void sendUser(){
String us=TRAN_USER_FLAG;
Set<String> keys=cs.keySet();
for(String u:keys){
us+=u+",";
}
send(us);
}

private void close(){
stop=true;
out.println(DISCONNECT_TOKEN);
cs.remove(name);
send(name+"断开连接了");
sendUser();
}

private void send(String msg){
Set<String> keys=cs.keySet();
for(String key:keys){
cs.get(key).out.println(msg);
}
}

private String getUsersByMsg(String msg){
String str=msg.substring(CHAR_FLAG_START.length(), msg.indexOf(CHAR_FLAG_END));
return str;
}

private String formatMsg(String msg){
String str=msg.substring(msg.indexOf(CHAR_FLAG_END)+CHAR_FLAG_END.length());
return str;
}


//@Override
public void run() {
try {
while(true){
if(stop) break;
String str=br.readLine();
if(str.equals(DISCONNECT_TOKEN)){
close();
break;
}
String us=getUsersByMsg(str);
String msg=formatMsg(str);
if(us.equals("all")){
send(name+":"+msg+"[群]");
} else{
sendPrivateUsers(us,msg);
}
//System.out.println(us);
//System.out.println(name+str);
}
} catch(SocketException e){
System.out.println(name+"非正常离开了");
close();
} catch (IOException e) {
e.printStackTrace();
} finally{
try {
if(s!=null) s.close();
} catch (IOException e) {
e.printStackTrace();
}
}
}


private void sendPrivateUsers(String us, String msg) {
String[] uus=us.split(",");
for(String u:uus){
cs.get(u).out.println(name+":"+msg+"[私]");
}
}
}

}