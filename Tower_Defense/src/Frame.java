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
	public int difficulty; // 0 = easy, 1 = medium, 2 = hard
	public boolean isOnHomescreen = true;
	public void paint(Graphics g) {
		int winX = f.getX(), winY = f.getY();
		p.x -= winX;
		p.y -= winY;
		if (isOnHomescreen) {
			checkHover();
		}
		super.paint(g);
		back.paint(g);
		p = MouseInfo.getPointerInfo().getLocation();
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
			System.out.println(p.getX() + "," + p.getY());
			if (isOnHomescreen) {
				if (p.getX() >= 105 && p.getX() <= 210 && p.getY() >= 340 && p.getY() <= 385) {
					difficulty = 0;
					back.setBackground("/imgs/Background.png");
					isOnHomescreen = false;
				}
				if (p.getX() >= 250 && p.getX() <= 365 && p.getY() >= 340 && p.getY() <= 385) {
					difficulty = 1;
					back.setBackground("/imgs/Background.png");
					isOnHomescreen = false;
				}
				if (p.getX() >= 410 && p.getX() <= 505 && p.getY() >= 340 && p.getY() <= 385) {
					difficulty = 2;
					back.setBackground("/imgs/Background.png");
					isOnHomescreen = false;
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
		if (p.getX() >= 105 && p.getX() <= 210 && p.getY() >= 340 && p.getY() <= 385) {
			back.switchEasy();
		}
		else {
			back.returnEasy();
		}
		if (p.getX() >= 250 && p.getX() <= 365 && p.getY() >= 340 && p.getY() <= 385) {
			back.switchMed();
		}
		else {
			back.returnMed();
		}
		if (p.getX() >= 410 && p.getX() <= 505 && p.getY() >= 340 && p.getY() <= 385) {
			back.switchHard();
		}
		else {
			back.returnhard();
		}
	}
}