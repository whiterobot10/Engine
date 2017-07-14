package platformer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

	// setup

	private static final long serialVersionUID = -4720310464752288420L;

	//
	//
	//
	//debug
	//
	//
	//
	
	
	public boolean Show_Hit_Boxes = false;
	public boolean Show_Damage_Fields = false;

	//
	//
	//
	//
	//
	
	
	
	
	public ArrayList<Box> boxes;
	public ArrayList<Box> instaKill;
	public ArrayList<Entity> entities;
	public ArrayList<Entity> newEntities;
	public ArrayList<DamageField> damageFields;
	public ArrayList<Box> flashDisplay;
	public Player PC;

	public int fps = 100;
	public int pcStartX = 100;
	public int pcStartY = 185;
	public double movementSpeed = 1;
	public double movementFrame = 1;
	public double scrollX = 0;
	public double scrollY = -175;
	public double scrollXPoint = 200;

	public boolean door1 = false;
	public boolean door2 = false;

	public boolean objectDefine = false;
	public boolean running = true;

	public Thread game;

	public Main(Display f) {
		setBackground(Color.LIGHT_GRAY);

		defineObjects();

		game = new Thread(this);
		game.start();

		// Key input

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
				if (e.getKeyCode() == KeyEvent.VK_Z && PC.attack1 == 0 && !(PC.space)) {
					PC.attack1 = PC.attack1Duration;
				}
				if (e.getKeyCode() == KeyEvent.VK_X && PC.attack2Delay == 0) {
					PC.shoot = true;
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
					PC.down = false;
				}
			}

		});

	}

	// defining objects

	void defineObjects() {
		door1 = false;
		door2 = false;
		PC = new Player(pcStartX + 50, pcStartY);
		scrollX = pcStartX;
		scrollY = -175;
		boxes = new ArrayList<Box>();
		instaKill = new ArrayList<Box>();
		boxes.add(new Box(0, 350, 2500, 400));
		boxes.add(new Box(0, -175, 10000, 0));
		boxes.add(new Box(0, 200, 350, 250));
		boxes.add(new Box(-150, -175, 50, 400));
		boxes.add(new Box(850, 300, 900, 400));
		boxes.add(new Box(850, 0, 900, 200));
		boxes.add(new Box(1350, 200, 1400, 400));
		boxes.add(new Box(1050, 250, 1200, 300));
		boxes.add(new Box(1650, 0, 1700, 300));
		boxes.add(new Box(2250, 0, 2300, 300));
		instaKill.add(new Box(2500, 375, 2700, 400));
		boxes.add(new Box(2700, 350, 2950, 400));
		boxes.add(new Box(3000, 200, 3025, 400));
		boxes.add(new Box(2800, 250, 2850, 400));
		instaKill.add(new Box(3000, 200, 3025, 400));
		instaKill.add(new Box(2950, 375, 3675, 400));
		boxes.add(new Box(2950, 150, 3075, 200));
		boxes.add(new Box(3650, 0, 3700, 100));
		boxes.add(new Box(3675, 150, 3800, 200));
		boxes.add(new Box(3050, 0, 3100, 100));

		entities = new ArrayList<Entity>();
		newEntities = new ArrayList<Entity>();
		flashDisplay = new ArrayList<Box>();
		damageFields = new ArrayList<DamageField>();
		entities.add(new CheckPoint(100, 185));
		entities.add(new CheckPoint(2400, 335));
		entities.add(new Slasher(100, 300));
		entities.add(new Gunner(1000, 300));
		entities.add(new WallTurret(1750, 50));
		entities.add(new WallTurret(2175, 50));
		entities.add(new WallTurret(3325, 300, true));
		entities.add(new Blocade(3100, 175, 30));
		entities.add(new Blocade(3150, 175, 30));
		entities.add(new Blocade(3200, 175, 30));
		entities.add(new Blocade(3250, 175, 30));
		entities.add(new Blocade(3300, 175, 30));
		entities.add(new Blocade(3350, 175, 30));
		entities.add(new Blocade(3400, 175, 30));
		entities.add(new Blocade(3450, 175, 30));
		entities.add(new Blocade(3500, 175, 30));
		entities.add(new Blocade(3550, 175, 30));
		entities.add(new Blocade(3600, 175, 30));
		entities.add(new Blocade(3650, 175, 30));

		objectDefine = true;
		repaint();
	}

	public void events() {
		if (door1 == false && PC.x >= 1750 && PC.x <= 1950) {
			boxes.add(new Box(1650, 300, 1700, 400));
			entities.add(new Blocade(2275, 325, 100));
			door1 = true;
		}
		if (door2 == false && PC.x >= 3150 && PC.x <= 3350) {
			boxes.add(new Box(3050, 100, 3100, 150));
			entities.add(new Blocade(3675, 125, 100));
			door2 = true;
		}

	}

	// drawing

	public void paint(Graphics g) {

		super.paint(g);
		AffineTransform Trans = new AffineTransform();
		Trans.translate(scrollX * -1, scrollY * -1);
		Graphics2D g2d = (Graphics2D) g;
		g2d.transform(Trans);
		if (objectDefine) {

			PC.draw(g, this);
			if (PC.iframes % 6 < 3) {

			}

			for (Entity e : entities) {
				e.draw(g, this);
				if (e.iframes % 6 < 3) {

				}
			}
			if (Show_Damage_Fields) {
				for (Box e : damageFields) {
					e.draw(g, Color.RED);
				}
			}
			for (Box e : flashDisplay) {
				e.draw(g, 5);
			}
			for (Box e : flashDisplay) {
				e.draw(g, 4);
			}
			for (Box e : flashDisplay) {
				e.draw(g, 3);
			}
			for (Box e : flashDisplay) {
				e.draw(g, 2);
			}
			for (Box e : flashDisplay) {
				e.draw(g, 1);
			}
			for (Box e : boxes) {

				e.draw(g);

			}
			for (Box e : instaKill) {
				e.draw(g, Color.BLACK);
			}

			flashDisplay.clear();

		}

	}
	// Exception in thread "AWT-EventQueue-0"
	// java.util.ConcurrentModificationException
	// at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
	// at java.util.ArrayList$Itr.next(Unknown Source)
	// at platformer.Main.paint(Main.java:201)
	// at javax.swing.JComponent.paintToOffscreen(Unknown Source)
	// at javax.swing.RepaintManager$PaintManager.paintDoubleBuffered(Unknown
	// Source)
	// at javax.swing.RepaintManager$PaintManager.paint(Unknown Source)
	// at javax.swing.RepaintManager.paint(Unknown Source)
	// at javax.swing.JComponent._paintImmediately(Unknown Source)
	// at javax.swing.JComponent.paintImmediately(Unknown Source)
	// at javax.swing.RepaintManager$4.run(Unknown Source)
	// at javax.swing.RepaintManager$4.run(Unknown Source)
	// at java.security.AccessController.doPrivileged(Native Method)
	// at
	// java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(Unknown
	// Source)
	// at javax.swing.RepaintManager.paintDirtyRegions(Unknown Source)
	// at javax.swing.RepaintManager.paintDirtyRegions(Unknown Source)
	// at javax.swing.RepaintManager.prePaintDirtyRegions(Unknown Source)
	// at javax.swing.RepaintManager.access$1200(Unknown Source)
	// at javax.swing.RepaintManager$ProcessingRunnable.run(Unknown Source)
	// at java.awt.event.InvocationEvent.dispatch(Unknown Source)
	// at java.awt.EventQueue.dispatchEventImpl(Unknown Source)
	// at java.awt.EventQueue.access$500(Unknown Source)
	// at java.awt.EventQueue$3.run(Unknown Source)
	// at java.awt.EventQueue$3.run(Unknown Source)
	// at java.security.AccessController.doPrivileged(Native Method)
	// at
	// java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(Unknown
	// Source)
	// at java.awt.EventQueue.dispatchEvent(Unknown Source)
	// at java.awt.EventDispatchThread.pumpOneEventForFilters(Unknown Source)
	// at java.awt.EventDispatchThread.pumpEventsForFilter(Unknown Source)
	// at java.awt.EventDispatchThread.pumpEventsForHierarchy(Unknown Source)
	// at java.awt.EventDispatchThread.pumpEvents(Unknown Source)
	// at java.awt.EventDispatchThread.pumpEvents(Unknown Source)
	// at java.awt.EventDispatchThread.run(Unknown Source)

	public void GameOver() {
		boxes.clear();
		instaKill.clear();
		entities.clear();
		newEntities.clear();
		defineObjects();

	}

	// check collision

	public boolean clsnCheck(Rectangle rect) {

		for (Box e : boxes) {

			if (e.collides(rect)) {
				return true;
			}

		}

		return false;
	}

	public boolean clsnCheck(Rectangle rect, ArrayList<Box> list) {

		for (Box e : list) {

			if (e.collides(rect)) {
				return true;
			}

		}

		return false;
	}

	public boolean clsnCheck(Entity entity, ArrayList<Entity> list) {

		for (Entity e : list) {

			if (e.collides(entity.getRect()) && e.mOB && !(e == entity)) {
				return true;
			}
			if (entity.collides(PC.getRect()) && !(entity == PC) && list == entities) {
				return true;
			}

		}
		return false;
	}

	public boolean clsnCheck(Rectangle rect, Entity e) {
		if (e.collides(rect) && e.mOB) {
			return true;
		}
		return false;
	}

	public boolean lineOsight(int x1, int y1, int x2, int y2) {
		Line2D line = new Line2D.Double(x1, y1, x2, y2);

		for (Box e : boxes) {

			if (e.collides(line)) {
				return false;
			}

		}

		return true;
	}

	public boolean clsnCheck(Entity entity) {

		for (Box e : boxes) {

			if (e.collides(entity.getRect())) {
				return true;
			}

		}
		return false;
	}

	// processing

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

				events();

				for (int i = 0; i < entities.size(); i++) {
					if (entities.get(i).needsRemoval) {
						entities.remove(i);
						i--;
					}
				}

				for (Entity e : newEntities) {
					entities.add(e);
				}
				newEntities.clear();

				repaint();
				movementFrame = -1;

			} else {

				movementFrame += 1;
			}

			fpsSettler();

		}

	}

	// fpssettler

	public void fpsSettler() {

		long Currant = System.currentTimeMillis();
		long TimeCheck = System.currentTimeMillis() + 20;
		while (TimeCheck - Currant >= 10) {
			Currant = System.currentTimeMillis();
		}

	}
}
