import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Virus {
	public Image virus1, virus2, virus3, virus4, virus5, virus6;
	private int x, y, speed, type;  //for now, max speed is 5
	private boolean gameStarted;
	private AffineTransform tx;
	
	public Virus(int x, int y, int level) {
		this.x = x;
		this.y = y;
		virus1 = null;
		virus2 = null;
		virus3 = null;
		virus4 = null;
		virus5 = null;
		virus6 = null;
		gameStarted = false;
		int rnd = 0;
		if (level >= 1 && level <= 3) {
			rnd = 1;
		}
		else if (level >= 4 && level <= 5) {
			rnd = (int)(Math.random() * 2)+1;
			
		}
		else if (level >= 6 && level <= 8) {
			rnd = (int)(Math.random() * 3)+1;
		}
		else if (level >= 9 && level <= 10) {
			rnd = (int)(Math.random() * 4)+1;
		}
		else if (level >= 11 && level <= 12) {
			rnd = (int)(Math.random() * 5)+1;
		}
		else {
			rnd = (int)(Math.random() * 6)+1;
		}
		switch (rnd) {
			case 1: 
				spawn1();
				break;
			case 2:
				spawn2();
				break;
			case 3:
				spawn3();
				break;
			case 4:
				spawn4();
				break;
			case 5:
				spawn5();
				break;
			case 6:
				spawn6();
				break;
		}
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
		g2.drawImage(virus2, x, y, 40, 40, null);
		g2.drawImage(virus3, x, y, 40, 40, null);
		g2.drawImage(virus4, x, y, 40, 40, null);
		g2.drawImage(virus5, x, y, 40, 40, null);
		g2.drawImage(virus6, x, y, 40, 40, null);
	}
	
	private void update() {
		//code for attacker movement through the course
		if(y > 400 && x < 185) {
			x += speed;
		}else if(y > 275 && x < 190) {
			y -= speed;
		}else if(y > 270 && x > 25 && x < 200) {
			x -= speed;
		}else if(y > 150 && x > 20 && x < 50) {
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
		}else if(y < 335 && x < 545) {
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
		}else if(y < 600 && x < 530) {
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
	
	public void spawn() {
		
	}
	
	public void spawn1() {
		virus1 = getImage("/imgs/virus1.png");
		type = 1;
		speed = 2;
	}
	public void spawn2() {
		virus1 = getImage("/imgs/virus2.png");
		type = 2;
		speed = 3;
	}
	public void spawn3() {
		virus1 = getImage("/imgs/virus3.png");
		type = 3;
		speed = 4;
	}
	public void spawn4() {
		virus1 = getImage("/imgs/virus4.png");
		type = 4;
		speed = 5;
	}
	public void spawn5() {
		virus1 = getImage("/imgs/virus5.png");
		type = 5;
		speed = 6;
	}
	public void spawn6() {
		virus1 = getImage("/imgs/virus6.png");
		type = 6;
		speed = 7;
	}
	
	public void homescreenVirus() {
		virus1 = null;
		virus2 = null;
		virus3 = null;
		virus4 = null;
		virus5 = null;
		virus6 = null;
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
	
}
