package platformer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Main extends JPanel implements Runnable {

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	private static final long serialVersionUID = -4720310464752288420L;
	public Box[] boxes;
	public ArrayList<Entity> entities;
	public ArrayList<DamageField> damageFields;
	public ArrayList<Box> flashDisplay;
	public Player PC;
	BufferedImage Image1 = null;

	public int fps = 100;
	public double movementSpeed = 1;
	public double movementFrame = 1;

	public boolean objectDefine = false;
	public boolean running = true;

	public Thread game;

	public Main(Display f) {
		setBackground(Color.blue);
		try {
			Image1 = ImageIO.read(new File("glory.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		defineObjects();

		game = new Thread(this);
		game.start();
		f.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					PC.left = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					PC.right = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					PC.up = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					PC.up2 = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					PC.down = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE&&PC.attack1==0) {
					PC.attack1 = PC.attack1Duration;
				}
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					PC.left = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					PC.right = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					PC.up = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					PC.down = true;
				}
			}

		});

	}

	void defineObjects() {
		PC = new Player(300, 0);
		boxes = new Box[30];
		boxes[0] = new Box(0, 350, 600, 400);
		boxes[1] = new Box(250, 250, 350, 280);
		boxes[2] = new Box(0, 0, 50, 400);
		boxes[3] = new Box(550, 0, 600, 400);

		entities = new ArrayList<Entity>();
		flashDisplay = new ArrayList<Box>();
		damageFields = new ArrayList<DamageField>();
		entities.add(new Slasher(200, 200));
		//entities.add(new Slasher(400, 200));

		objectDefine = true;
		repaint();
	}

	public void makeEntity(int xstart, int ystart) {
		entities.add(new Entity(xstart, ystart));

	}

	public void paint(Graphics g) {
		super.paint(g);
		if (objectDefine) {
			if (PC.iframes % 6 < 3) {
				PC.draw(g);
			}

			for (Entity e : entities) {
				if (e.iframes % 6 < 3) {
					e.draw(g);
				}
			}
			for (Box e : flashDisplay) {
				e.draw(g);
			}
			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i] == null) {
					continue;
				} else {
					boxes[i].draw(g);
				}
			}
			flashDisplay.clear();
		}
	}

	public boolean clsnCheck(Rectangle rect) {

		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i] == null) {
				continue;
			} else {
				if (boxes[i].collides(rect)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean lineOsight(int x1, int y1, int x2, int y2) {
		Line2D line = new Line2D.Double(x1, y1, x2, y2);

		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i] == null) {
				continue;
			} else {
				if (boxes[i].collides(line)) {
					return false;
				}
			}

		}

		return true;
	}

	public boolean clsnCheck(Entity entity) {

		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i] == null) {
				continue;
			} else {
				if (boxes[i].collides(entity.getRect())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run() {
		while (running) {
			if (movementFrame >= movementSpeed) {

				PC.update(this);
				for (Entity e : entities) {
					e.update(this);
				}
				damageFields.clear();
				PC.makeDamage(this);
				for (Entity e : entities) {
					e.makeDamage(this);
				}
				PC.takeDamage(this);
				for (Entity e : entities) {
					e.takeDamage(this);
				}
				for (int i = 0; i < entities.size(); i++) {
					if (entities.get(i).needsRemoval) {
						entities.remove(i);
						i--;
					}
				}
				repaint();
				movementFrame = -1;

			} else {

				movementFrame += 1;
			}

			fpsSettler();

		}

	}

	public void fpsSettler() {

		long Currant = System.currentTimeMillis();
		long TimeCheck = System.currentTimeMillis() + 20;
		while (TimeCheck - Currant >= 10) {
			Currant = System.currentTimeMillis();
		}

	}
}
