import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Sanitizer{
	private Image img;
	private AffineTransform tx;
	private double scale;
	private boolean hover;
	private int cost;
	public Sanitizer(int x, int y, double scale, boolean hover, int difficulty) {
		img = getImage("/imgs/handSanitizer.png");
		tx = AffineTransform.getTranslateInstance(x, y);
		this.scale = scale;
		this.hover = hover;
		if (difficulty == 0) {
			cost = 100;
		}
		else if (difficulty == 1) {
			cost = 125;
		}
		else {
			cost = 140;
		}
		init(x, y);
	}
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img,  tx, null);
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
	public void placeHover(int x, int y) {
		if (hover) {
			init(x, y);
		}
	}
	public void setHover(boolean h) {
		hover = h;
	}
	public int getCost() {
		return cost;
	}
}
