import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Projectile {
	public Image img;
	private double x, y, vx, vy;
	private double baseSpeed;
	private double scale;
	private AffineTransform tx;
	private double displacementX, displacementY;
	private int flameCount;
	
	public Projectile(double x, double y, String fileName, double scale) {
		img = getImage(fileName);
		this.scale = scale;
		vy = 0;
		vx = 0;
		baseSpeed = 0.15;
		flameCount = 0;
		this.x = x;
		this.y = y;
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				//initialize the location of the image
									//use your variables
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;		
		
		//call update to update the actualy picture location
		g2.drawImage(img, tx, null);
	}
	
	private void update() {
		//code for attacker movement through the course
		
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scale, scale);
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
	
	
	
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setX(int x1) {
		x = x1;
	}
	public void setY(int y1) {
		y = y1;
	}
	public double getVX() {
		return vx;
	}
	public double getVY() {
		return vy;
	}
	public void setVelocity(int x1, int y1, int x2, int y2) {
		displacementX = x2 - x1;
		displacementY = y2 - y1;
		
		
		vx = (displacementX * baseSpeed);
		vy = (displacementY * baseSpeed);
	
	}
	public void move() {

		if(Math.abs(displacementX) < 100 && Math.abs(displacementY) < 100) {
			this.x += vx;
			this.y += vy;
		}
		tx.setToTranslation(this.x, this.y);
		tx.scale(scale, scale);
	}
	public int getFlameCount() {
		return flameCount;
	}
	public void addFlameCount() {
		flameCount++;
	}
	public void setFlameCount(int num) {
		flameCount = num;
	}
	
}
