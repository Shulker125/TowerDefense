import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ArrayList;

public class Soap{
	private Image img;
	private ArrayList<Projectile> projectile;
	private AffineTransform tx;
	private double scale;
	private boolean hover, upgrade;
	private int cost, x, y, upgradeCost;
	private int fireRate;
	private long startDefend, timeDefend;
	public Soap(int x, int y, double scale, boolean hover, int difficulty){
		img = getImage("/imgs/soap.png");
		projectile = new ArrayList<Projectile>();
		tx = AffineTransform.getTranslateInstance(x, y);
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.hover = hover;
		upgrade = false;
		if (difficulty == 0) {
			cost = 50;
			upgradeCost = 100;
			fireRate = 1500;
		}
		else if (difficulty == 1) {
			cost = 75;
			upgradeCost = 125;
			fireRate = 2000;
		}
		else {
			cost = 80;
			upgradeCost = 150;
			fireRate = 2500;
		}
		startDefend = 0;
		init(x, y);
	}
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img,  tx, null);
		for (Projectile p : projectile) {
			p.paint(g);
		}
		timeDefend = System.currentTimeMillis() - startDefend;
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
			this.x = x;
			this.y = y;
		}
	}
	public void setHover(boolean h) {
		hover = h;
	}
	public int getCost() {
		return cost;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean getHover() {
		return hover;
	}
	public int getUpgradeCost() {
		return upgradeCost;
	}
	public void upgrade() {
		upgrade = true;
		fireRate -= 500;
	}
	public boolean getUpgrade() {
		return upgrade;
	}
	public int getFireRate() {
		return fireRate;
	}
	public long getTimeDefend() {
		return timeDefend;
	}
	public void setStartDefend() {
		startDefend = System.currentTimeMillis();
	}
	public ArrayList<Projectile> getProjectile() {
		return projectile;
	}
	public void removeProjectile(Projectile p) {
		projectile.remove(p);
	}
	public boolean isInHitbox(int pointerX, int pointerY) {
		if (pointerX-10 >= x && pointerX-10 <= x+63 && pointerY-40 >= y && pointerY-40 <= y+63) {
			return true;
		}
		return false;
	}
	public void fire(int x, int y) {
		int displacementX = this.x - x;
		int displacementY = this.y - y;
		if(Math.abs(displacementX) < 100 && Math.abs(displacementY) < 100) {
			projectile.add(new Projectile(this.x, this.y, "/imgs/soap-projectile.png", 0.6));
			projectile.get(projectile.size() - 1).setVelocity(this.x, this.y, x, y);
		}
		
	}
	public void projectileMove(int x, int y) {
		for (Projectile p : projectile) {
			p.move();
		}
	}
}
