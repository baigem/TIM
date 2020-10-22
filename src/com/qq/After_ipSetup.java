package com.qq;

import com.qq.gui.Front_ipSetup;
import java.awt.event.KeyListener;


public class After_ipSetup extends Front_ipSetup{
//	Front_ipSetup setip = new Front_ipSetup();

	public After_ipSetup(After_Login login){

		butip.addActionListener(e -> {
			login.IP = txtip.getText();
			dispose();
	});


		txtip.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(java.awt.event.KeyEvent e) {

			}

			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				int key = e.getKeyCode();
				if(key == '\n'){
					login.IP = txtip.getText();
					dispose();
				}
			}

			@Override
			public void keyReleased(java.awt.event.KeyEvent e) {

			}
  		});



	}


}