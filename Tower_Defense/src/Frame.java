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
	ArrayList<Soap> soap = new ArrayList<Soap>();
	ArrayList<Bleach> bleach = new ArrayList<Bleach>();
	ArrayList<Flamethrower> flame = new ArrayList<Flamethrower>();
	ArrayList<Sanitizer> sanitizer = new ArrayList<Sanitizer>();
	Point p = MouseInfo.getPointerInfo().getLocation();
	Picture game = new Picture("bigBackground.png");
	Pixel[][] pixel = game.getPixels2D();
	Pixel[][] cursor = new Pixel[50][50];
	public int difficulty; // 0 = easy, 1 = medium, 2 = hard
	public int cursorX, cursorY;
	public boolean isOnHomescreen = true;
	public boolean isPointerActive = false;
	public void paint(Graphics g) {
		pointerSet();
		setCursor();
		checkHover();
		System.out.println(isInNoZone());
		//System.out.println(cursorX+","+cursorY);
		super.paint(g);
		back.paint(g);
		if (!isOnHomescreen) {
			for (Soap s : soap) {
				s.paint(g);
				s.placeHover(cursorX-40, cursorY-40);
			}
			for (Bleach b : bleach) {
				b.paint(g);
				b.placeHover(cursorX-40, cursorY-40);
			}
			for (Flamethrower fl : flame) {
				fl.paint(g);
				fl.placeHover(cursorX-40, cursorY-40);
			}
			for (Sanitizer st : sanitizer) {
				st.paint(g);
				st.placeHover(cursorX-40, cursorY-40);
			}
		}
		g.drawRect(cursorX-40, cursorY-40, 40, 40);
		p = MouseInfo.getPointerInfo().getLocation();
	}
	public static void main(String[] arg) {
		Frame f = new Frame();
		
;	}
	public Frame() {
		soap.add(new Soap(150, 10, 2.5, false));
		bleach.add(new Bleach(350, 10, 2.7, false));
		flame.add(new Flamethrower(450, 10, 3, false));
		sanitizer.add(new Sanitizer(250, 10, 2.8, false));
		Timer t = new Timer(1, this);
		f.setSize(new Dimension(600, 600));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(true);
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
		if (e.getButton() == 3) {
			System.out.println(cursorX + "," + cursorY);
		}
		if(e.getButton() == 1) {
			if (isOnHomescreen) {
				back.menu = null;
				if (cursorX >= 105 && cursorX <= 210 && cursorY >= 340 && cursorY <= 385) {
					difficulty = 0;
					back.setBackground("/imgs/Background.png");
					back.returnMenu();
					isOnHomescreen = false;
				}
				if (cursorX >= 250 && cursorX <= 365 && cursorY >= 340 && cursorY <= 385) {
					difficulty = 1;
					back.setBackground("/imgs/Background.png");
					back.returnMenu();
					isOnHomescreen = false;
				}
				if (cursorX >= 410 && cursorX <= 505 && cursorY >= 340 && cursorY <= 385) {
					difficulty = 2;
					back.setBackground("imgs/Background.png");
					back.returnMenu();
					isOnHomescreen = false;
				}
			}
			else {
				
				if (cursorX >= 20 && cursorX <= 115 && cursorY >= 50 && cursorY <= 95) {
					back.returnToMenu();
					isOnHomescreen = true;
				}
				
				if (!isPointerActive) {
					if (cursorX >= 155 && cursorX <= 215 && cursorY >= 45 && cursorY <= 100) {
						soap.add(new Soap(cursorX-40, cursorY-40, 2.5, true));
						isPointerActive = true;
					}
					if (cursorX >= 270 && cursorX <= 295 && cursorY >= 45 && cursorY <= 105) {
						sanitizer.add(new Sanitizer(cursorX-40, cursorY-40, 2.5, true));
						isPointerActive = true;
					}
					if (cursorX >= 365 && cursorX <= 410 && cursorY >= 45 && cursorY <= 105) {
						bleach.add(new Bleach(cursorX-40, cursorY-40, 2.5, true));
						isPointerActive = true;
					}
					if (cursorX >= 450 && cursorX <= 525 && cursorY >= 65 && cursorY <= 95) {
						flame.add(new Flamethrower(cursorX-40, cursorY-40, 2.5, true));
						isPointerActive = true;
					}
				}
				else {
					if (cursorY > 130 && !isInNoZone()) {
						for (Soap s : soap) {
							s.setHover(false);
							isPointerActive = false;
						}
						for (Bleach b : bleach) {
							b.setHover(false);
							isPointerActive = false;
						}
						for (Flamethrower fl : flame) {
							fl.setHover(false);
							isPointerActive = false;
						}
						for (Sanitizer st : sanitizer) {
							st.setHover(false);
							isPointerActive = false;
						}
					}
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
			if (cursorX >= 105 && cursorX <= 210 && cursorY >= 340 && cursorY <= 385) {
				back.switchEasy();
			}
			else {
				back.returnEasy();
			}
			if (cursorX >= 250 && cursorX <= 365 && cursorY >= 340 && cursorY <= 385) {
				back.switchMed();
			}
			else {
				back.returnMed();
			}
			if (cursorX >= 410 && cursorX <= 505 && cursorY >= 340 && cursorY <= 385) {
				back.switchHard();
			}
			else {
				back.returnHard();
			}
		}
		else {
			if (cursorX >= 20 && cursorX <= 115 && cursorY >= 50 && cursorY <= 95) {
				back.switchMenu();
			}
			else {
				back.returnMenu();
			}
		}
		
	}
	public boolean isInNoZone() {
		try {
			for (Pixel[] r : cursor) {
				for (Pixel p : r) {
					if (p.getBlue() == 241 && p.getRed() == 236 && p.getGreen() == 239) {
						return true;
					}
				}
			}
		}
		catch(NullPointerException e) {}
		return false;
	}
	public void setCursor() {
		if (cursorX >= 40 && cursorX <= 570 && cursorY >= 40 && cursorY <= 570) {
			for (int r = cursorY-40, i = 0; r < cursorY; r++, i++) {
				for (int c = cursorX-40, j = 0; c < cursorX; c++, j++) {
					cursor[i][j] = pixel[r][c];
				}
			}
		}
	}
	public void pointerSet() {
		int winX = f.getX(), winY = f.getY();
		p.x -= winX;
		p.y -= winY;
		if (p.x <= 600 && p.x >= 0 && p.y <= 600 && p.y >= 0) {
			cursorX = p.x;
			cursorY = p.y;
		}
		else {
			cursorX = 0;
			cursorY = 0;
		}
	}
}