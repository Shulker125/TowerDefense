import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame; 
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener{
	JFrame f = new JFrame("Bathroom Battle");
	Background back = new Background(0, 0);
	Music soundtrack = new Music("game-soundtrack.wav", true);
	Music hit = new Music("enemyHit.wav", false);
	Music gameFailed = new Music("gameOver.wav", false);
	ArrayList<Soap> soap = new ArrayList<Soap>();
	ArrayList<Bleach> bleach = new ArrayList<Bleach>();
	ArrayList<Flamethrower> flame = new ArrayList<Flamethrower>();
	ArrayList<Sanitizer> sanitizer = new ArrayList<Sanitizer>();
	ArrayList<Virus> virus = new ArrayList<Virus>(); 
	Point p = MouseInfo.getPointerInfo().getLocation();
	Picture game = new Picture("bigBackground.png");
	Pixel[][] pixel = game.getPixels2D();
	Pixel[][] cursor = new Pixel[40][40];
	Image helpScreen1 = null;
	public int difficulty; // 0 = easy, 1 = medium, 2 = hard
	public int cursorX, cursorY;
	public int money = 20000;
	public int index = 0;
	public int level = 1;
	public int maxLevel;
	public int numVirus = 1;
	public int virusSpawned = 0;
	public int hp = 100;
	public boolean isOnHomescreen = true, isOnHelpscreen = false, isPointerActive = false, placementError = false, fundError = false, upgradeError = false, openSoapGUI = false, openSanGUI = false, openBleachGUI = false, openFlameGUI = false, gameStarted = false, levelSwitch = false, gameOver = false;
	public long start = System.currentTimeMillis();
	public long startAttack;
	public long timeAttack;
	private double attackStagger;
	
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		pointerSet();
		setCursor();
		checkHover();
		setGameFailed();
		if (gameStarted && isGameEnded()) {
			nextLevel();
		}
		super.paint(g);
		back.paint(g);
		if (isOnHelpscreen) {
			g.setColor(new Color(220, 220, 220));
			g.fillRect(10, 70, 560, 400);
			paintHelp(g);
			g2.drawImage(helpScreen1, 20, 120, 300, 300, null);
		}
		//run game
		if (!isOnHomescreen) {
			if (gameStarted) {
				back.start = null;
				timeAttack = System.currentTimeMillis() - startAttack;
				if(virusSpawned < numVirus &&  (timeAttack/100) / attackStagger == 1 || virus.size() == 0) {
					spawnAttack();
					timeAttack = 0;
					startAttack = System.currentTimeMillis();
				}
				//firing for defenders
				if(soap.size() > 1) {
					for(Soap s: soap) {
						Virus v = calculateClosestToSoap(s);
						if(s.getTimeDefend() > s.getFireRate()) {
							if(!s.getHover()) {
								s.fire(v.getX(), v.getY());
							}
							s.setStartDefend();
						}
					}
				}
				if(sanitizer.size() > 1) {
					for(Sanitizer s: sanitizer) {
						Virus v = calculateClosestToSanitizer(s);
						if(s.getTimeDefend() > s.getFireRate()) {
							if(!s.getHover()) {
								s.fire(v.getX(), v.getY());
							}
							s.setStartDefend();
						}
					}
				}
				if(bleach.size() > 1) {
					for(Bleach s: bleach) {
						Virus v = calculateClosestToBleach(s);
						if(s.getTimeDefend() > s.getFireRate()) {
							if(!s.getHover()) {
								s.fire(v.getX(), v.getY());
							}
							s.setStartDefend();
						}
					}
				}
				if(flame.size() > 1) {
					for(Flamethrower s: flame) {
						Virus v = calculateClosestToFlamethrower(s);
						if(s.getTimeDefend() > s.getFireRate()) {
							if(!s.getHover()) {
								s.fire(v.getX(), v.getY());
							}
							s.setStartDefend();
						}
					}
				}
		}
			if (soap.size() > 1) {
				//System.out.println(soap.get(1).getX() + "," + soap.get(1).getY());
			}
			//painting viruses and defenders
			for (Virus v : virus) {
				v.paint(g);
				v.setGameStarted();
				//damage hp if virus gets to the end
				if (v.getY() > 580 && !v.getHasDamaged()) {
					hp -= v.getDamage();
					v.setHasDamaged(true);
				}
			}
			for (Soap s : soap) {
				s.paint(g);
				s.placeHover(cursorX-40, cursorY-40);
				s.projectileMove(100, 100);
			}
			for (Bleach b : bleach) {
				b.paint(g);
				b.placeHover(cursorX-40, cursorY-40);
				b.projectileMove(100, 100);
			}
			for (Flamethrower fl : flame) {
				fl.paint(g);
				fl.placeHover(cursorX-40, cursorY-40);
				fl.projectileMove(100, 100);
			}
			for (Sanitizer st : sanitizer) {
				st.paint(g);
				st.placeHover(cursorX-40, cursorY-40);
				st.projectileMove(100, 100);
			}
			
			//creating hitboxes and collisions for viruses and projectiles
			try {
				for(Virus v: virus) {
					Rectangle r = new Rectangle(v.getX(), v.getY(), 40, 40);
					//g.drawRect(v.getX(), v.getY(), 40, 40);
				
					for(Soap s: soap) {
						for(Projectile p: s.getProjectile()) {
							Rectangle sRect = new Rectangle((int)p.getX(), (int)p.getY(), 40, 50);
							//g.drawRect((int)p.getX(), (int)p.getY(), 40, 50);
							if(r.intersects(sRect)) {
								if (v.getType() > 1) {
									v.spawn(v.getType() - s.getDamage());
								}
								else {
									hit.play();
									earnMoney(v);
									virus.remove(v);
								}
								s.removeProjectile(p);
							}
						}
					}
					for(Sanitizer s: sanitizer) {
						for(Projectile p: s.getProjectile()) {
							Rectangle sRect = new Rectangle((int)p.getX(), (int)p.getY(), 20, 30);
							//g.drawRect((int)p.getX(), (int)p.getY(), 0, 30);
							if(r.intersects(sRect)) {
								if (v.getType() > 1 && s.getDamage() == 1) {
									v.spawn(v.getType() - s.getDamage());
								}else if(v.getType() > 2 && s.getDamage() == 2) {
									v.spawn(v.getType() - s.getDamage());
								}
								else {
									hit.play();
									earnMoney(v);
									virus.remove(v);
								}
								s.removeProjectile(p);
							}
						}
					}
					for(Bleach s: bleach) {
						for(Projectile p: s.getProjectile()) {
							Rectangle sRect = new Rectangle((int)p.getX(), (int)p.getY(), 40, 35);
							//g.drawRect((int)p.getX(), (int)p.getY(), 40, 35);
							if(r.intersects(sRect)) {
								if (v.getType() > 1 && s.getDamage() == 1) {
									v.spawn(v.getType() - s.getDamage());
								}else if(v.getType() > 2 && s.getDamage() == 2) {
									v.spawn(v.getType() - s.getDamage());
								}
								else {
									hit.play();
									earnMoney(v);
									virus.remove(v);
								}
								s.removeProjectile(p);
							}
						}
					}
					for(Flamethrower s: flame) {
						for(Projectile p: s.getProjectile()) {
							Rectangle sRect = new Rectangle((int)p.getX(), (int)p.getY(), 27, 34);
							//g.drawRect((int)p.getX(), (int)p.getY(), 27, 34);
							if(r.intersects(sRect)) {
								if (v.getType() > 1 && s.getDamage() == 1) {
									v.spawn(v.getType() - s.getDamage());
								}else if(v.getType() > 2 && s.getDamage() == 2) {
									v.spawn(v.getType() - s.getDamage());
								}
								else {
									hit.play();
									earnMoney(v);
									virus.remove(v);
								}
								p.addFlameCount();
								if(p.getFlameCount() == 3) {
									s.removeProjectile(p);
								}
								
							}
						}
					}
				
				//whoo colorful
//				for(Rectangle r: virusRectangles) {
//					int red = (int)(Math.random() * 256);
//					int green = (int)(Math.random() * 256);
//					int blue = (int)(Math.random() * 256);
//					Color c = new Color(red, green, blue);
//					g.setColor(c);
//					g.drawRect((int)r.getX(), (int)r.getY(), r.width, r.height);
//				}
				}
			} catch(ConcurrentModificationException e) {
				
			}

			//text for shop
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Balance", 10, 78);
			g.drawString("$"+money, 10, 95);
			g.drawString("HP", 95, 78);
			g.drawString(hp + "%", 85, 95);
			g.drawString("$" + soap.get(0).getCost(), 160, 95);
			g.drawString("$" + sanitizer.get(0).getCost(), 255, 95);
			g.drawString("$" + bleach.get(0).getCost(), 360, 95);
			g.drawString("$" + flame.get(0).getCost(), 460, 95);
		paintErrors(g);
		paintDefenderGUI(g);
		
		}
		else if (!isOnHelpscreen){
			
			g.setColor(new Color(235, 236, 239));
			g.fillRect(210, 440, 180, 50);
			g.setFont(new Font("Arial", Font.PLAIN, 25));
			g.setColor(Color.black);
			g.drawRect(210, 440, 180, 50);
			g.drawString("Highscore: " + maxLevel, 220, 475);
		}
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
		f.setResizable(true);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		setMaxLevel();
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		start = System.currentTimeMillis();
		int key = e.getKeyCode();
		switch(key) {
			case 10: //enter key - moves to next level, for dev purposes
				//nextLevel();
				break;	
			case 83: //key: s - selling defenders
				if (openSoapGUI) {
					soap.remove(index);
					money += soap.get(0).getCost()*3/4;
					openSoapGUI = false;
				}
				if (openBleachGUI) {
					bleach.remove(index);
					money += bleach.get(0).getCost()*3/4;
					openBleachGUI = false;
				}
				if (openSanGUI) {
					sanitizer.remove(index);
					money += sanitizer.get(0).getCost()*3/4;
					openSanGUI = false;
				}
				if (openFlameGUI) {
					flame.remove(index);
					money += flame.get(0).getCost()*3/4;
					openFlameGUI = false;
				}
				break;
			case 89: //key: y - upgrade defenders
				if (openSoapGUI) {
					if (soap.get(index).getUpgrade()) {
						upgradeError = true;
					}
					if (money >= soap.get(index).getUpgradeCost() && !soap.get(index).getUpgrade()) {
						money -= soap.get(index).getUpgradeCost();
						soap.get(index).upgrade();
						openSoapGUI = false;
					}
					else if (money < soap.get(index).getUpgradeCost()){
						fundError = true;
					}
				}
				else if (openBleachGUI) {
					if (bleach.get(index).getUpgrade()) {
						upgradeError = true;
					}
					if (money >= bleach.get(index).getUpgradeCost() && !bleach.get(index).getUpgrade()) {
						money -= bleach.get(index).getUpgradeCost();
						bleach.get(index).upgrade();
						openBleachGUI = false;
					}
					else if (money < bleach.get(index).getUpgradeCost()){
						fundError = true;
					}
				}
				else if (openSanGUI) {
					if (sanitizer.get(index).getUpgrade()) {
						upgradeError = true;
					}
					if (money >= sanitizer.get(index).getUpgradeCost() && !sanitizer.get(index).getUpgrade()) {
						money -= sanitizer.get(index).getUpgradeCost();
						sanitizer.get(index).upgrade();
						openSanGUI = false;
					}
					else if (money < sanitizer.get(index).getUpgradeCost()){
						fundError = true;
					}
				}
				else if (openFlameGUI) {
					if (flame.get(index).getUpgrade()) {
						upgradeError = true;
					}
					if (money >= flame.get(index).getUpgradeCost() && !flame.get(index).getUpgrade()) {
						money -= flame.get(index).getUpgradeCost();
						flame.get(index).upgrade();
						openFlameGUI = false;
					}
					else if (money < flame.get(index).getUpgradeCost()){
						fundError = true;
					}
				}
				break;
			case 78: //key n: rejecting offer to upgrade
				openSoapGUI = false;
				openBleachGUI = false;
				openSanGUI = false;
				openFlameGUI = false;
				break;
		}
		
		
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
		start = System.currentTimeMillis();
		int key = e.getButton();
		switch(key){
			case 3: //right click
				System.out.println(cursorX + "," + cursorY);
				break;
			case 1: //left click
				openSoapGUI = false;
				openBleachGUI = false;
				openSanGUI = false;
				openFlameGUI = false;
				if (isOnHomescreen && !isOnHelpscreen) {
					back.menu = null;
					//easy mode (clicking easy button)
					if (cursorX >= 105 && cursorX <= 210 && cursorY >= 340 && cursorY <= 385) {
						setDifficulty(0);
					}//medium mode
					if (cursorX >= 250 && cursorX <= 365 && cursorY >= 340 && cursorY <= 385) {
						setDifficulty(1);
					}//hard mode
					if (cursorX >= 410 && cursorX <= 505 && cursorY >= 340 && cursorY <= 385) {
						setDifficulty(2);
					}//help screen
					if (cursorX >= 260 && cursorX <= 355 && cursorY >= 410 && cursorY <= 455) {
						back.enterHelp();
						helpScreen1 = getImage("/imgs/helpWalkthrough1.gif");
						isOnHelpscreen = true;
					}
				}
				else { 
					//back to menu
					if (cursorX >= 20 && cursorX <= 115 && cursorY >= 50 && cursorY <= 95) {
						reset();
					}//starting game (clicking start button)
					if (cursorX >= 20 && cursorX <= 135 && cursorY >= 470 && cursorY <= 510 && !gameStarted && !isOnHomescreen && !gameOver) {
						setGameStarted();
					}//checking to see if mouse is hovering over certain defender
					if (!isPointerActive) {
						for (int i = 1; i < soap.size(); i++) {
							if(soap.get(i).isInHitbox(cursorX, cursorY)) {
								openSoapGUI = true;
								index = i;
							}
						}
						for (int i = 1; i < bleach.size(); i++) {
							if(bleach.get(i).isInHitbox(cursorX, cursorY)) {
								openBleachGUI = true;
								index = i;
							}
						}
						for (int i = 1; i < sanitizer.size(); i++) {
							if(sanitizer.get(i).isInHitbox(cursorX, cursorY)) {
								openSanGUI = true;
								index = i;
							}
						}
						for (int i = 1; i < flame.size(); i++) {
							if(flame.get(i).isInHitbox(cursorX, cursorY)) {
								openFlameGUI = true;
								index = i;
							}
						}
						//buying defenders from shop
						if (cursorX >= 155 && cursorX <= 215 && cursorY >= 45 && cursorY <= 100) {
							buyDefender(1);
						}
						if (cursorX >= 270 && cursorX <= 295 && cursorY >= 45 && cursorY <= 105) {
							buyDefender(2);
						}
						if (cursorX >= 365 && cursorX <= 410 && cursorY >= 45 && cursorY <= 105) {
							buyDefender(3);
						}
						if (cursorX >= 450 && cursorX <= 525 && cursorY >= 65 && cursorY <= 95) {
							buyDefender(4);
						}
					}
					else {
						//placing defenders - imperfect
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
						else {
							placementError = true;
						}
					}
				}
				break;
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
		//changing image of buttons if mouse is hovering over it
		//image gets darker if mouse is hovering on button
		//image returns to normal if mouse stops hovering over button
		if (isOnHomescreen && !isOnHelpscreen) {
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
			if (cursorX >= 260 && cursorX <= 355 && cursorY >= 410 && cursorY <= 455) {
				back.switchHelp();
			}
			else {
				back.returnHelp();
			}
		}
		else {
			if (cursorX >= 20 && cursorX <= 115 && cursorY >= 50 && cursorY <= 95) {
				back.switchMenu();
			}
			else {
				back.returnMenu();
			}
			if (cursorX >= 20 && cursorX <= 135 && cursorY >= 470 && cursorY <= 510 && !gameStarted && !isOnHomescreen && !gameOver) {
				back.switchStart();
			}
			else if (!gameStarted && !gameOver){
				back.returnStart();
			}
			
		}
		
	}
	public boolean isInNoZone() {
		//method for seeing if defender is on track
		//used to determine if defender can be placed there
		//imperfect
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
	public void buyDefender(int num) {
		//buying defenders
		if (num == 1 && money >= soap.get(0).getCost()) {
			soap.add(new Soap(cursorX-40, cursorY-40, 2.5, true, difficulty));
			isPointerActive = true;
			money -= soap.get(0).getCost();
		}
		else if (num == 2 && money >= sanitizer.get(0).getCost()) {
			sanitizer.add(new Sanitizer(cursorX-40, cursorY-40, 2.5, true, difficulty));
			isPointerActive = true;
			money -= sanitizer.get(0).getCost();
		}
		else if (num == 3 && money >= bleach.get(0).getCost()) {
			bleach.add(new Bleach(cursorX-40, cursorY-40, 2.5, true, difficulty));
			isPointerActive = true;
			money -= bleach.get(0).getCost();
		}
		else if (num == 4 && money >= flame.get(0).getCost()) {
			flame.add(new Flamethrower(cursorX-40, cursorY-40, 2.5, true, difficulty));
			isPointerActive = true;
			money -= flame.get(0).getCost();
		}
		else {
			fundError = true;
		}
	}
	//spawning viruses
	public void spawnAttack() {
		virus.add(new Virus(0, 435, level));
		virus.get(virus.size()-1).setGameStarted();
		virusSpawned++;
	}
	
	//calculating closest virus to defender
	public Virus calculateClosestToSoap(Soap s) {
		int x1 = s.getX();
		int y1 = s.getY();
		
		Virus closest = null;
		double smallest = 1000;
		
		for(Virus v: virus) {
			int x2 = v.getX();
			int y2 = v.getY();
			double displacementX = x2 - x1;
			double displacementY = y2 - y1;
			displacementX *= displacementX;
			displacementY *= displacementY;
			double displacement = Math.sqrt(displacementX + displacementY);
			if(displacement < smallest) {
				smallest = displacement;
				closest = v;
			}
		}
		return closest;
	}
	public Virus calculateClosestToSanitizer(Sanitizer s) {
		int x1 = s.getX();
		int y1 = s.getY();
		
		Virus closest = null;
		double smallest = 1000;
		
		for(Virus v: virus) {
			int x2 = v.getX();
			int y2 = v.getY();
			double displacementX = x2 - x1;
			double displacementY = y2 - y1;
			displacementX *= displacementX;
			displacementY *= displacementY;
			double displacement = Math.sqrt(displacementX + displacementY);
			if(displacement < smallest) {
				smallest = displacement;
				closest = v;
			}
		}
		return closest;
	}
	public Virus calculateClosestToBleach(Bleach s) {
		int x1 = s.getX();
		int y1 = s.getY();
		
		Virus closest = null;
		double smallest = 1000;
		
		for(Virus v: virus) {
			int x2 = v.getX();
			int y2 = v.getY();
			double displacementX = x2 - x1;
			double displacementY = y2 - y1;
			displacementX *= displacementX;
			displacementY *= displacementY;
			double displacement = Math.sqrt(displacementX + displacementY);
			if(displacement < smallest) {
				smallest = displacement;
				closest = v;
			}
		}
		return closest;
	}
	public Virus calculateClosestToFlamethrower(Flamethrower s) {
		int x1 = s.getX();
		int y1 = s.getY();
		
		Virus closest = null;
		double smallest = 1000;
		
		for(Virus v: virus) {
			int x2 = v.getX();
			int y2 = v.getY();
			double displacementX = x2 - x1;
			double displacementY = y2 - y1;
			displacementX *= displacementX;
			displacementY *= displacementY;
			double displacement = Math.sqrt(displacementX + displacementY);
			if(displacement < smallest) {
				smallest = displacement;
				closest = v;
			}
		}
		return closest;
	}
	
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Background.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}
	//help screen text
	public void paintHelp(Graphics c) {
		c.setColor(Color.black);
		c.drawString("1. Use the top bar to select the", 350, 120);
		c.drawString(" defender you want if you have sufficient ", 350, 140);
		c.drawString("funds", 350, 160);
		c.drawString("2. Place defender on anywhere on the", 350, 200);
		c.drawString("screen except for the white path", 350, 220);
		c.drawString("3. To upgrade click on defender and", 350, 260);
		c.drawString("click y or n on keyboard", 350, 280);
		c.drawString("4. To sell, click on defender and press", 350, 340);
		c.drawString("s on the keyboard", 350, 360);
		c.drawString("5. Click start to begin wave", 350, 420);
	}
	public void paintDefenderGUI(Graphics g) {
		if (openSoapGUI) {
			g.setColor(new Color(235, 236, 239, 220));
			g.fillRect(180, 300, 250, 35);
			g.setColor(Color.black);
			g.drawRect(180, 300, 250, 35);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString("Upgrade Costs: $" + soap.get(0).getUpgradeCost() + " Upgrade? y/n" , 192, 325);
			g.setColor(new Color(235, 236, 239, 220));
			g.fillRect(255, 340, 100, 35);
			g.setColor(Color.black);
			g.drawRect(255, 340, 100, 35);
			g.drawString("Click s to sell" , 263, 365);
		}
		if (openBleachGUI) {
			g.setColor(new Color(235, 236, 239, 220));
			g.fillRect(180, 300, 250, 35);
			g.setColor(Color.black);
			g.drawRect(180, 300, 250, 35);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString("Upgrade Costs: $" + bleach.get(0).getUpgradeCost() + " Upgrade? y/n", 192, 325);
			g.setColor(new Color(235, 236, 239, 220));
			g.fillRect(255, 340, 100, 35);
			g.setColor(Color.black);
			g.drawRect(255, 340, 100, 35);
			g.drawString("Click s to sell" , 263, 365);
		}
		if (openSanGUI) {
			g.setColor(new Color(235, 236, 239, 220));
			g.fillRect(180, 300, 250, 35);
			g.setColor(Color.black);
			g.drawRect(180, 300, 250, 35);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString("Upgrade Costs: $" + sanitizer.get(0).getUpgradeCost() + " Upgrade? y/n", 192, 325);
			g.setColor(new Color(235, 236, 239, 220));
			g.fillRect(255, 340, 100, 35);
			g.setColor(Color.black);
			g.drawRect(255, 340, 100, 35);
			g.drawString("Click s to sell" , 263, 365);
		}
		if (openFlameGUI) {
			g.setColor(new Color(235, 236, 239, 220));
			g.fillRect(180, 300, 250, 35);
			g.setColor(Color.black);
			g.drawRect(180, 300, 250, 35);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString("Upgrade Costs: $" + flame.get(0).getUpgradeCost() + " Upgrade? y/n", 192, 325);
			g.setColor(new Color(235, 236, 239, 220));
			g.fillRect(255, 340, 100, 35);
			g.setColor(Color.black);
			g.drawRect(255, 340, 100, 35);
			g.drawString("Click s to sell" , 263, 365);
		}
	}
	//invalid message if defender cannot be placed
	public void paintErrors(Graphics g) {
		if (placementError) {
			long time = System.currentTimeMillis() - start;
			if (time <= 500) {
				g.setColor(new Color(235, 236, 239));
				g.fillRect(250, 300, 100, 35);
				g.setColor(Color.black);
				g.drawRect(250, 300, 100, 35);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Invalid", 273, 325);
			}
			else {
				placementError = false;
			}
		}
		//not enough money message if not enough money to buy defender
		if (fundError) {
			long time2 = System.currentTimeMillis() - start;
			if (time2 <= 500) {
				g.setColor(new Color(235, 236, 239));
				g.fillRect(200, 380, 200, 35);
				g.setColor(Color.black);
				g.drawRect(200, 380, 200, 35);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Not Enough Money!", 215, 405);
			}
			else {
				fundError = false;
			}
		}
		//message if defender is already upgraded
		if (upgradeError) {
			long time3 = System.currentTimeMillis() - start;
			if (time3 <= 500) {
				g.setColor(new Color(235, 236, 239));
				g.fillRect(200, 380, 200, 35);
				g.setColor(Color.black);
				g.drawRect(200, 380, 200, 35);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Already Upgraded!", 217, 405);
			}
			else {
				upgradeError = false;
			}
		}
		//message for new level
		if (levelSwitch) {
			long time4 = System.currentTimeMillis() - start;
			if (time4 <= 1000) {
				g.setColor(new Color(235, 236, 239));
				g.fillRect(240, 330, 105, 35);
				g.setColor(Color.black);
				g.drawRect(240, 330, 105, 35);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString("Level " + level, 257, 355);
			}
			else {
				levelSwitch = false;
			}
		}
		//message for game over
		if (gameOver) {
			g.setColor(new Color(235, 236, 239));
			g.fillRect(230, 330, 140, 35);
			g.setColor(Color.black);
			g.drawRect(230, 330, 140, 35);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Game Over!", 247, 355);
		}
		
	}
	//starting game
	public void setGameStarted() {
		levelSwitch = true;
		gameStarted = true;
		virusSpawned = 0;
		numVirus = level * 2;
		spawnAttack();
		startAttack = System.currentTimeMillis();

	}
	//checking if level has ended
	public boolean isGameEnded() {
		if (virusSpawned < numVirus) {
			return false;
		}
		else if (gameStarted && virus.size() == 0) {
			return true;
		}
		for (Virus v : virus) {
			if (v.getY() < 600) {
				return false;
			}
			
		}
		return true;
		
	}
	public void setGameFailed() {
		if (hp <= 0) {
			if (virus.size() > 0) {
				soundtrack.stop();
				gameFailed.play();
			}
			gameOver = true;
			gameStarted = false;
			virus.clear();
			if (level > maxLevel) {
				maxLevel = level;
				try {
					FileWriter fileWriter = new FileWriter("highscore.txt");
					fileWriter.write(maxLevel + "");
					fileWriter.close();
				} 
				catch (IOException l) {
				      System.out.println("An error occurred.");
				      l.printStackTrace();
				}
			}
		}
	}
	//moving to next level
	public void nextLevel() {
		gameStarted = false;
		virus.clear();
		level++;
		if (difficulty == 0 && level % 3 == 0 && attackStagger > 1) {
			attackStagger--;
		}
		else if (difficulty == 1 && level % 3 == 0 && attackStagger > 1) {
			attackStagger--;
		}
		else if (difficulty == 2 && level % 5 == 0 && attackStagger > 1) {
			attackStagger--;
		}
	}
	//resetting game when going back to menu
	public void reset() {
		back.returnToMenu();
		soap.clear();
		bleach.clear();
		sanitizer.clear();
		flame.clear();
		money = 200;
		isOnHomescreen = true;
		isOnHelpscreen = false;
		gameOver = false;
		gameStarted = false;
		isPointerActive = false;
		helpScreen1 = null;
		for(Virus v: virus) {
			v.homescreenVirus();
		}
		virus.clear();
		level = 1;
		hp = 100;
	}
	//changing game based on difficulty chosen
	public void setDifficulty(int num) {
		soundtrack.loop();
		back.setBackground("/imgs/Background.png");
		back.returnMenu();
		isOnHomescreen = false;
		switch(num) {
			case 0:
				difficulty = 0;
				soap.add(new Soap(150, 10, 2.5, false, difficulty));
				bleach.add(new Bleach(350, 10, 2.7, false, difficulty));
				flame.add(new Flamethrower(450, 10, 3, false, difficulty));
				sanitizer.add(new Sanitizer(250, 10, 2.8, false, difficulty));
				attackStagger = 9;
				money = 125;
				break;
			case 1:
				difficulty = 1;
				soap.add(new Soap(150, 10, 2.5, false, difficulty));
				bleach.add(new Bleach(350, 10, 2.7, false, difficulty));
				flame.add(new Flamethrower(450, 10, 3, false, difficulty));
				sanitizer.add(new Sanitizer(250, 10, 2.8, false, difficulty));
				attackStagger = 7;
				money = 150;
				break;
			case 2:
				difficulty = 2;
				soap.add(new Soap(150, 10, 2.5, false, difficulty));
				bleach.add(new Bleach(350, 10, 2.7, false, difficulty));
				flame.add(new Flamethrower(450, 10, 3, false, difficulty));
				sanitizer.add(new Sanitizer(250, 10, 2.8, false, difficulty));
				attackStagger = 5;
				money = 175; //175
				break;
		}
		
	}
	public void setMaxLevel() {
		try {
		      File myObj = new File("highscore.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        maxLevel = Integer.parseInt(myReader.nextLine());
		        
		      }
		      myReader.close();
		    } catch (FileNotFoundException l) {
		      System.out.println("An error occurred.");
		      l.printStackTrace();
		    }
	}
	//earning money when virus is killed
	public void earnMoney(Virus v) {
		if(v.getType() == 1 || v.getType() == 2 || v.getType() == 3) {
			money += 5;
		}else if(v.getType() == 4 || v.getType() == 5 || v.getType() == 6) {
			money += 10;
		}
	}
}
