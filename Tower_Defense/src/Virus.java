import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Virus {
	public Image virus1;
	private int x, y, speed, type, damage;  //for now, max speed is 5
	private boolean gameStarted, hasDamaged;
	private AffineTransform tx;
	
	public Virus(int x, int y, int level) {
		this.x = x;
		this.y = y;
		virus1 = null;
		gameStarted = false;
		hasDamaged = false;
		int rnd = 0;
		if (level >= 1 && level <= 3) {
			rnd = 1;
		}
		else if (level >= 4 && level <= 6) {
			rnd = (int)(Math.random() * 2)+1;
			
		}
		else if (level >= 7 && level <= 14) {
			rnd = (int)(Math.random() * 3)+1;
		}
		else if (level >= 15 && level <= 19) {
			rnd = (int)(Math.random() * 3)+2;
		}
		else if (level >= 20 && level <= 24) {
			rnd = (int)(Math.random() * 3)+3;
		}
		else if (level >= 25 && level <= 30){
			rnd = (int)(Math.random() * 4)+3;
		}
		else {
			rnd = (int)(Math.random()* 2)+ 5;
		}
		spawn(rnd);
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				//initialize the location of the image
									//use your variables
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;		
		
		//call update to update the actualy picture location
		if(gameStarted) {
			update();
		}
		
		
		g2.drawImage(virus1, x, y, 40, 40, null);
	}
	
	private void update() {
		//code for attacker movement through the course
		if(y > 400 && x < 180) {
			x += speed;
		}else if(y > 275 && x < 190) {
			y -= speed;
		}else if(y > 270 && x > 30 && x < 200) {
			x -= speed;
		}else if(y > 155 && x > 20 && x < 50) {
			y -= speed;
		}else if(y > 145 && y < 195 && x < 285) {
			x += speed;
		}else if(y < 235 && x < 290) {
			y += speed;
		}else if(y < 240 && y < 260 && x < 450) {
			x += speed;
		}else if(y > 150 && y < 240 && x < 460) {
			y -= speed;
		}else if(y > 145 && y < 165 && x < 535) {
			x += speed;
		}else if(y < 330 && x < 545) {
			y += speed;
		}else if(y < 340 && x > 265) {
			x -= speed;
		}else if(y < 490 && x > 260 && x < 285) {
			y += speed;
		}else if(y < 495 && x > 380 && x < 425) {
			x += speed;
		}else if(y > 410 && x > 390 && x < 430) {
			y -= speed;
		}else if(y > 400 && x < 525) {
			x += speed;
		}else if(y < 600 && x < 535) {
			y += speed;
		}
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(5, 5);
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
	
	public void spawn(int num) {
		switch(num) {
			case 1:
				virus1 = getImage("/imgs/virus1.png");
				type = 1;
				speed = 2;
				damage = 1;
				break;
			case 2:
				virus1 = getImage("/imgs/virus2.png");
				type = 2;
				speed = 3;
				damage = 1;
				break;
			case 3:
				virus1 = getImage("/imgs/virus3.png");
				type = 3;
				speed = 4;
				damage = 2;
				break;
			case 4:
				virus1 = getImage("/imgs/virus4.png");
				type = 4;
				speed = 5;
				damage = 3;
				break;
			case 5:
				virus1 = getImage("/imgs/virus5.png");
				type = 5;
				speed = 6;
				damage = 4;
				break;
			case 6:
				virus1 = getImage("/imgs/virus6.png");
				type = 6;
				speed = 7;
				damage = 5;
				break;
		}
	}
	
	public void homescreenVirus() {
		virus1 = null;
	}
	
	public void setGameStarted() {
		gameStarted = true;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x1) {
		x = x1;
	}
	public void setY(int y1) {
		y = y1;
	}
	public int getType(){
		return type;
	}
	public int getDamage() {
		return damage;
	}
	public boolean getHasDamaged() {
		return hasDamaged;
	}
	public void setHasDamaged(boolean b) {
		hasDamaged = b;
	}
}
