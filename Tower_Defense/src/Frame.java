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
	Picture game = new Picture("/imgs/Background.png");
	Pixel[][] pixel = game.getPixels2D();
	public int difficulty; // 0 = easy, 1 = medium, 2 = hard
	public boolean isOnHomescreen = true;
	public boolean isPointerActive = false;
	public void paint(Graphics g) {
		int winX = f.getX(), winY = f.getY();
		p.x -= winX;
		p.y -= winY;
		checkHover();
		super.paint(g);
		back.paint(g);
		if (!isOnHomescreen) {
			for (Soap s : soap) {
				s.paint(g);
				s.placeHover(p.x-40, p.y-40);
			}
			for (Bleach b : bleach) {
				b.paint(g);
				b.placeHover(p.x-40, p.y-40);
			}
			for (Flamethrower fl : flame) {
				fl.paint(g);
				fl.placeHover(p.x-40, p.y-40);
			}
			for (Sanitizer st : sanitizer) {
				st.paint(g);
				st.placeHover(p.x-40, p.y-40);
			}
		}
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
			int winX = f.getX(), winY = f.getY();
			p.x -= winX;
			p.y -= winY;
			System.out.println(p.x + "," + p.y);
		}
		if(e.getButton() == 1) {
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
				}
				
				if (!isPointerActive) {
					if (p.x >= 155 && p.x <= 215 && p.y >= 45 && p.y <= 100) {
						soap.add(new Soap(p.x-40, p.y-40, 2.5, true));
						isPointerActive = true;
					}
					if (p.x >= 270 && p.x <= 295 && p.y >= 45 && p.y <= 105) {
						sanitizer.add(new Sanitizer(p.x-40, p.y-40, 2.5, true));
						isPointerActive = true;
					}
					if (p.x >= 365 && p.x <= 410 && p.y >= 45 && p.y <= 105) {
						bleach.add(new Bleach(p.x-40, p.y-40, 2.5, true));
						isPointerActive = true;
					}
					if (p.x >= 450 && p.x <= 525 && p.y >= 65 && p.y <= 95) {
						flame.add(new Flamethrower(p.x-40, p.y-40, 2.5, true));
						isPointerActive = true;
					}
				}
				else {
					if (p.y > 130 && !isInNoZone()) {
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
	public boolean isInNoZone() {
		if (pixel[p.x][p.y].getBlue() == 241 && pixel[p.x][p.y].getRed() == 236 && pixel[p.x][p.y].getGreen() == 239) {
			return true;
		}
		return false;
	}
}