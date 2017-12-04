package SFDesignManager;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.*;
import java.lang.Thread;

import WindowManager.MainWindow;

public class Intro extends JPanel implements Runnable {
	MainWindow window;
	Sound type = new Sound("Typing.wav");
	ImageIcon logo;
	ImageIcon typing = new ImageIcon("testimage\\SF_typing.png");
	ImageIcon clickTonext = new ImageIcon("testimage\\click anywhere.png");
	
	int location_x = 30, location_y=200, count = 0, i =40, sleeptime; // count : 페이드인, 타이핑 효과 조절1, i : 타이핑 효과 조절2 sleeptime : 페이드인과 슬라이드 효과 간격 조절
	Thread th;

	public Intro(MainWindow window){
		this.window = window;
		setLayout(null);
		setBackground(Color.BLACK);
		setVisible(true);
		// setLocationRelativeTo(window.contentPane);
		start();
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(){
				th.interrupt();
				window.changePanel(window.gamePanel);
			}
		});

	}
	// private void setLocationRelativeTo(Container contentPane) {}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Image img;
		// 21개의 페이드인 효과를 주는 이미지 출력. 
		
		  if(count<22){
		 
			sleeptime = 40;
			logo = new ImageIcon("testimage\\SF_"+count+".png"); 
			img = logo.getImage();
			g.drawImage(img, location_x, location_y , this);
			count++;
		}
		
		// 0.5초동안 로고 출력
		else if(count==22){
			sleeptime = 500;
			img = logo.getImage();
			g.drawImage(img, location_x, location_y , this);
			count++; // 차후 타이핑 효과를 위한 제어 변수 셋팅
		}
		// 이후 슬라이드 효과-로고 내림
		else if(location_y<270){
			sleeptime = 10;
			img = logo.getImage();
			g.drawImage(img, location_x, location_y , this);
			location_y++;
		}
		// 로고가 내려가 멈춰있는 상태, 키 깜박임 출력.
		else if(location_y==270) {
			img = logo.getImage();
			g.drawImage(img, location_x, location_y , this);
			// 세번 깜빡깜빡
			if(count%2 == 1 && count < 28){
				img = typing.getImage();
				g.drawImage(img, location_x, location_y-180 , this);
				sleeptime = 400;
				if(count==27) type.play();// 한 번만 실행
			}
			else if(28 <= count && count < 70){
				
				sleeptime = 100;
				typing = new ImageIcon("testimage\\SF_typing"+(count-27)+".png");
				img = typing.getImage();
				g.drawImage(img, location_x, location_y-200 , this);
			}
			else if( 69 < count ) {
				sleeptime = 400;
				i = (i==41)?40:41;
				typing = new ImageIcon("testimage\\SF_typing"+i+".png");
				img = typing.getImage();
				g.drawImage(img, location_x, location_y-200 , this);
				img = clickTonext.getImage();
				g.drawImage(img, location_x+270, location_y+150 , this);
				
			}
			count++;
		}
	}

	/**쓰레드 메소드run*/
	public void run(){    
		try{ 
			while(true){
				repaint();
				Thread.sleep(sleeptime); //쓰레드 속도
			}
		}catch (Exception e){}
	}
	public void start(){



		th = new Thread(this); 
		th.start(); 
	}
}