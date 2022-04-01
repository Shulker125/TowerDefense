import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Background {
	public Image img, easy, medium, hard; 	
	private AffineTransform tx;
	private String[] stage = {"/imgs/bg.png", "/imgs/bg2.png", "/imgs/bg3.png", "/imgs/bg4.png"};
	private int stageNum = 0;
	public Background(int x, int y) {
		img = getImage("/imgs/homescreen.png"); //load the image for Tree
		easy = getImage("/imgs/easy.png");
		medium = getImage("/imgs/medium.png");
		hard = getImage("imgs/hard.png");
		tx = AffineTransform.getTranslateInstance(x, y);
		init(x, y); 				//initialize the location of the image
									//use your variables
	}

	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		
		//call update to update the actualy picture location
		update();
		
		
		
		g2.drawImage(img, tx, null);
		g2.drawImage(easy, 100, 300, 100, 50, null);
		g2.drawImage(medium, 240, 300, 120, 50, null);
		g2.drawImage(hard, 400, 300, 100, 50, null);
		

	}
	/* update the picture variable location */
	private void update() {

		
		
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(5.95, 5.65);
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

}
