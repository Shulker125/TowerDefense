import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Virus {
	public Image virus1, virus2, virus3, virus4, virus5, virus6;
	private int x, y, speed;  //for now, max speed is 5
	private boolean gameStarted;
	private AffineTransform tx;
	
	public Virus(int x, int y, int speed1) {
		this.x = x;
		this.y = y;
		speed = speed1;
		virus1 = null;
		virus2 = null;
		virus3 = null;
		virus4 = null;
		virus5 = null;
		virus6 = null;
		gameStarted = false;
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
		g2.drawImage(virus2, 200, 200, 100, 100, null);
	}
	
	private void update() {
		if(y > 400 && x < 190) {
			x += speed;
		}else if(y > 275 && x < 195) {
			y -= speed;
		}else if(y > 270 && x > 30 && x < 200) {
			x -= speed;
		}else if(y > 150 && x > 25 && x < 50) {
			y -= speed;
		}else if(y > 145 && y < 195 && x < 290) {
			x += speed;
		}else if(y < 235 && x < 295) {
			y += speed;
		}else if(y < 240 && y < 260 && x < 455) {
			x += speed;
		}else if(y > 150 && y < 240 && x < 460) {
			y -= speed;
		}else if(y > 145 && y < 165 && x < 545) {
			x += speed;
		}else if(y < 335 && x < 550) {
			y += speed;
		}else if(y < 340 && x > 270) {
			x -= speed;
		}else if(y < 490 && x > 265 && x < 285) {
			y += speed;
		}else if(y < 495 && x < 435) {
			x += speed;
		}else if(y > 410 && x < 440) {
			y -= speed;
		}else if(y > 405 && x < 535) {
			x += speed;
		}else if(y < 600 && x < 540) {
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
		virus1 = getImage("/imgs/virus1.png");
		virus2 = getImage("/imgs/virus2.png");
		virus3 = getImage("/imgs/virus3.png");
		virus4 = getImage("/imgs/virus4.png");
		virus5 = getImage("/imgs/virus5.png");
		virus6 = getImage("/imgs/virus6.png");
	}
	
	public void spawn1() {
		virus1 = getImage("/imgs/virus1.png");
	}
	public void spawn2() {
		virus2 = getImage("/imgs/virus2.png");
	}
	public void spawn3() {
		virus3 = getImage("/imgs/virus3.png");
	}
	public void spawn4() {
		virus4 = getImage("/imgs/virus4.png");
	}
	public void spawn5() {
		virus5 = getImage("/imgs/virus5.png");
	}
	public void spawn6() {
		virus6 = getImage("/imgs/virus6.png");
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
	
}
