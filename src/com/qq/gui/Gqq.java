package com.qq.gui;

import com.qq.tools.HeadPortrait;

import java.awt.*;
import javax.swing.*;


public class Gqq extends JFrame{
	//______________________会话界面基本组件__________________________________________________


	//______________________________________________________________________________________


	//________________________会话界面容器______________________________________________________
	public JPanel JPconfu = new JPanel();	//整个会话右侧界面
	public JPanel JPrsoc = new JPanel();  //整个会话界面 Right side of conversation

	public JPanel Conversations = new JPanel();//好友会话列表动态容器
	public JScrollPane Dynamic = new JScrollPane(Conversations);//好友滑动框
	//_________________________________________________________________________________

	

	//______________________好友界面容器_________________________________________________________
	public JButton Add_friends = new JButton("加好友/群");//加好友那个按钮
	public JButton Group_building = new JButton("建群");

	public JPanel All_friends = new JPanel();//所有的好友显示在这个区域
	public JScrollPane My_friends = new JScrollPane(All_friends);//为All_friends添加上滑动框
	public JPanel Friend_information = new JPanel();  //用来显示好友信息
	public JPanel JPfriend = new JPanel();//整个好友界面
	//________________________________________________________________________________________

	//_______________________三大铁金刚__________________________________________________________
	public JLabel Conversation = new JLabel("会话");
	public JLabel Friend = new JLabel("好友");
	public JLabel inforMation = new JLabel();//头像按钮

	//创建容器容纳三大金刚Three King Kong
	public JPanel JPtkk = new JPanel();
	//___________________________________________________________________________________________
	




	//____________好友资料________________________________________________________________________________
	public JLabel  txtName =new JLabel("昵称：");
	public JLabel  txtUser = new JLabel("账号：");
	public JLabel  txtMobile= new JLabel("手机：");
	public JLabel  txtEmail=new JLabel("邮箱:");
	public JLabel  txtGender=new JLabel("性别：");
	public JLabel  txtEnjoy=new JLabel("爱好：");

	public JLabel  lblGenders = new JLabel();//性别
	public JLabel	  lblUser = new JLabel();//账号
	public JLabel     lblNama = new JLabel();//昵称
	public JLabel     lblMobile =new JLabel();//手机
	public JLabel     lblEmail =new JLabel();//邮箱
	public JLabel     lblEnjoy = new JLabel();//爱好

	//____________________________________________________________________________________________







	public Gqq(){
		
		this.setLayout(new FlowLayout());
		this.setLayout(null);
		this.setSize(1000,800);

			//三大金刚永远不需要改动，最终静态
		//————————————————————————————————————————————————————————————————————————————————————
		JPtkk.setBounds(0,0,1000,50);
		Conversation.setBounds(400,10,60,30);//会话
		Friend.setBounds(500,10,60,30);//好友
		inforMation.setBounds(850,10,30,30);//个人信息

		JPtkk.add(Conversation);
		JPtkk.add(Friend);
		JPtkk.add(inforMation);
		//————————————————————————————————————————————————————————————————————————————————————-


			/*会话,此处动态创建 */
		//____________________________________________________________________-
		JPrsoc.setBounds(0,50,1000,750);//整个会话的页面大小，其他组件全部依赖这里
		Dynamic.setBounds(0,0,280,750);//动态聊天列表的父类容器，主要负责滚动条
		JPconfu.setBounds(280,0,720,750);//会话右侧容器

		//_______________________________________________________________________



			/*好友界面各个组件的详细位置  ，此处静态       */
		//_______________________________________________________________________
		JPfriend.setBounds(0,50,1000,750);//整个好友界面
		Add_friends.setBounds(10,10,270,35);//添加好友按钮
		Group_building.setBounds(10,45,270,35);//建群
		My_friends.setBounds(0,85,280,715);//好友列表
		Friend_information.setBounds(280,0,720,750);//好友界面右置位容器
		//________________________________________________________________________


		//_____________好友资料_________________________________________________________
		txtName.setBounds(50,50,40,30);//150
		lblNama.setBounds(90,50,150,30);

		txtUser.setBounds(50,90,40,30);
		lblUser.setBounds(90,90,150,30);

		txtGender.setBounds(50,130,40,30);
		lblGenders.setBounds(90,130,150,30);

		txtEmail.setBounds(50,170,40,30);
		lblEmail.setBounds(90,170,40,30);

		txtMobile.setBounds(50,210,40,30);
		lblMobile.setBounds(90,210,150,30);

		txtEnjoy.setBounds(50,250,40,30);
		lblEnjoy.setBounds(90,250,150,30);


		//_________________________________________________________________________




		this.getContentPane().setBackground(new Color(253,253,253));//整个界面的底色
		JPtkk.setBackground(new Color(226,226,227));//顶层三大金刚的底色
		Conversations.setBackground(new Color(253,253,253));//会话左侧好友列表容器的颜色
		JPfriend.setBackground(new Color(253,253,253));//整个好友界面的颜色
		All_friends.setBackground(new Color(253, 253, 253));//好友列表的颜色
		//Friend_information.setBackground(Color.BLACK);//好友资料的颜色
		//JPconfu.setBackground(Color.BLACK);

		this.add(JPtkk);//添加三大铁金刚

		this.add(JPrsoc);//添加会话部分

		//会话部分
		JPrsoc.setLayout(null);//整个会话容器
		Conversations.setLayout(null);//会话左侧好友列表容器，所有好友都会出现在这个容器中
		JPconfu.setLayout(null);//会话右侧界面

		//好友部分
		All_friends.setLayout(null);//好友列表界面
		Friend_information.setLayout(null);
		JPfriend.setLayout(null);	//整个好友界面的容器

		//三大铁金刚
		JPtkk.setLayout(null);


		//++++++++++++会话默认状态

		HeadPortrait.Cyh("/Users/baige/IT/java/QQ/data/Resources/会话true.png",Conversation,new ImageIcon(),27,27);
		HeadPortrait.Cyh("/Users/baige/IT/java/QQ/data/Resources/好友false.png", Friend,new ImageIcon(),27,27);


		//__________________________
		Dynamic.setBorder(null);//无边框


		//chatRecord.setBorder(new LineBorder(new java.awt.Color(226,226,227), 1, false));
		//__________________________


		//整个会话界面都在这里了
//		JPrsoc.setBorder(null);//无边框
		JPrsoc.add(JPconfu);
		JPrsoc.add(Dynamic);



		//整个好友界面都在这里了
		JPfriend.add(Add_friends);//把加好友按钮加上去
		JPfriend.add(Group_building);//把建群添加上去
		JPfriend.add(My_friends);//好友列表界面
		JPfriend.add(Friend_information);//好友资料

		//设置各个大小








		Dynamic.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Dynamic.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);




		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

			
	}
}//Gqq结