import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame; 
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener{
	JFrame f = new JFrame("Tower Defense");
	Background back = new Background(0, 0);
	Point p = MouseInfo.getPointerInfo().getLocation();
	Virus v1 = new Virus(0, 435, 5);
	Virus temp = new Virus(0, 435, 5);
	private boolean yesSpawn = false;
	public int difficulty; // 0 = easy, 1 = medium, 2 = hard
	public boolean isOnHomescreen = true;
	private int rNum;
	
	public void paint(Graphics g) {
		int winX = f.getX(), winY = f.getY();
		p.x -= winX;
		p.y -= winY;
		checkHover();
		super.paint(g);
		back.paint(g);
		v1.paint(g);
		p = MouseInfo.getPointerInfo().getLocation();
		
		if(!isOnHomescreen) {
			v1.setGameStarted();
			v1.spawn1();
		}
		
		rNum = (int)(Math.random() * 6 + 1);
		if(v1.getX() == 150 && v1.getY() == 435) {
			yesSpawn = true;
		}else if(v1.getX() > 500 && v1.getY() > 550) {
			yesSpawn = true;
		}
		if(yesSpawn) {
			temp.paint(g);
			if(rNum == 1) {
				temp.spawn1();
			}else if(rNum == 2) {
				temp.spawn2();
			}else if(rNum == 3) {
				temp.spawn3();
			}else if(rNum == 4) {
				temp.spawn4();
			}else if(rNum == 5) {
				temp.spawn5();
			}else if(rNum == 6) {
				temp.spawn5();
			}

			temp.setGameStarted();
		}

	}
	public static void main(String[] arg) {
		Frame f = new Frame();
		
;	}
	public Frame() {
		Timer t = new Timer(16, this);
		f.setSize(new Dimension(600, 600));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
//		if(e.getKeyCode() == 37) {
//			if(!isOnHomescreen) {
//				v1.spawn1();
//			}
//		}

		
		
	} 
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() == 1) {
			System.out.println(p.x + "," + p.y);
			int winX = f.getX(), winY = f.getY();
			p.x -= winX;
			p.y -= winY;
			if (isOnHomescreen) {
				back.menu = null;
				if (p.x >= 105 && p.x <= 210 && p.y >= 340 && p.y <= 385) {
					difficulty = 0;
					back.setBackground("/imgs/Background.png");
					back.returnMenu();
					isOnHomescreen = false;
				}
				if (p.x >= 250 && p.x <= 365 && p.y >= 340 && p.y <= 385) {
					difficulty = 1;
					back.setBackground("/imgs/Background.png");
					back.returnMenu();
					isOnHomescreen = false;
				}
				if (p.x >= 410 && p.x <= 505 && p.y >= 340 && p.y <= 385) {
					difficulty = 2;
					back.setBackground("/imgs/Background.png");
					back.returnMenu();
					isOnHomescreen = false;
				}
			}
			else {
				
				if (p.x >= 20 && p.x <= 115 && p.y >= 50 && p.y <= 95) {
					back.returnToMenu();
					isOnHomescreen = true;
					v1.homescreenVirus();
					temp.homescreenVirus();
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
		
	}
	public void checkHover() {
		if (isOnHomescreen) {
			if (p.x >= 105 && p.x <= 210 && p.y >= 340 && p.y <= 385) {
				back.switchEasy();
			}
			else {
				back.returnEasy();
			}
			if (p.x >= 250 && p.x <= 365 && p.y >= 340 && p.y <= 385) {
				back.switchMed();
			}
			else {
				back.returnMed();
			}
			if (p.x >= 410 && p.x <= 505 && p.y >= 340 && p.y <= 385) {
				back.switchHard();
			}
			else {
				back.returnHard();
			}
		}
		else {
			if (p.x >= 20 && p.x <= 115 && p.y >= 50 && p.y <= 95) {
				back.switchMenu();
			}
			else {
				back.returnMenu();
			}
		}
		
	}
	
}