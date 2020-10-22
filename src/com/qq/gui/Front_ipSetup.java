package com.qq.gui;


import com.qq.file.File_Control;

import javax.swing.*;
import java.awt.*;

public class Front_ipSetup extends JFrame {
	public JButton butip = new JButton("确定");
	public JTextField txtip = new JTextField();
	JLabel lblip = new JLabel("IP:");


	public Front_ipSetup(){

		this.setLayout(new FlowLayout());
		this.setLayout(null);
		this.setSize(200,100);

		lblip.setBounds(10,25,20,30);
		txtip.setBounds(40,25,110,30);
		butip.setBounds(150,25,50,30);

		this.add(lblip);
		this.add(txtip);
		this.add(butip);

		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

}