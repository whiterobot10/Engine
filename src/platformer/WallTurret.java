package platformer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WallTurret extends Entity {

	double direction = Math.PI / 2;
	boolean rocket = false;

	public WallTurret(double xstart, double ystart) {
		super(xstart, ystart);
		maxHealth = 10;
		health = 10;
		width = 64;
		height = 64;
		imgWidth = 64;
		imgHeight = 64;
		attack2Power = 5;
		maimDamage = 2;
		canBeRemoved = true;
		try {
			spriteImage = Main.resize(ImageIO.read(new File("turret.png")), 256, 64);
		} catch (IOException e1) {
			// e1.printStackTrace();
		}
	}

	public WallTurret(double xstart, double ystart, boolean isRocket) {
		super(xstart, ystart);
		maxHealth = 10;
		health = 10;
		width = 64;
		height = 64;
		imgWidth = 64;
		imgHeight = 64;
		attack2Power = 10;
		maimDamage = 5;
		canBeRemoved = true;
		try {
			spriteImage = Main.resize(ImageIO.read(new File("turret.png")), 256, 64);
		} catch (IOException e1) {
			// e1.printStackTrace();
		}
		rocket = isRocket;
	}

	@Override
	public void update(Main game) {
		if (iframes > 0) {
			iframes--;
		}
		if (game.lineOsight((int) x, (int) y, (int) game.PC.x, (int) game.PC.y)) {
			direction = Math.atan2(game.PC.y - y, game.PC.x - x);
			if (attack2Delay == 0) {
				if(rocket){attack2Delay=20;}else
				{attack2Delay = 10;}
				if (rocket) {
					game.newEntities.add(new Bullet(x + Math.cos(direction) * 64, y + Math.sin(direction) * 64,
							Math.cos(direction) * 20, Math.sin(direction) * 20, attack2Power, maimDamage, 1));
				} else {
					game.newEntities.add(new Bullet(x + Math.cos(direction) * 64, y + Math.sin(direction) * 64,
							Math.cos(direction) * 30, Math.sin(direction) * 30, attack2Power, maimDamage));
				}

			} else {
				attack2Delay--; 
			}

		} else if (attack2Delay > 0) {
			attack2Delay--;
		}

	}

	@Override
	public void draw(Graphics g, Main game) {
		if (game.Show_Hit_Boxes) {
			super.showHitBox(g);
		}
		super.DrawPiece((Graphics2D) g, 0, 0, spriteImage, 0, 0, false, 64, 64, 0);
		if (!(rocket)) {
			super.DrawPiece((Graphics2D) g, 0, 0, spriteImage, 64, 0, false, 64, 64, direction);
		} else if (attack2Delay <= 3) {
			super.DrawPiece((Graphics2D) g, 0, 0, spriteImage, 192, 0, false, 64, 64, direction);
		} else {
			super.DrawPiece((Graphics2D) g, 0, 0, spriteImage, 128, 0, false, 64, 64, direction);
		}
		if (health > 0) {
			g.setColor(Color.gray);
			g.fillRect((int) x - (maxHealth / 2), (int) y - (imgHeight / 2) - 10, maxHealth, 5);
			g.setColor(Color.RED);
			g.fillRect((int) x - (maxHealth / 2), (int) y - (imgHeight / 2) - 10, health, 5);

		}
		// g.setColor(Color.DARK_GRAY);
		// g.fillRect((int) x, (int) y, width, height);
		// g.setColor(Color.GRAY);
		// g.fillOval((int) x, (int) y, width, height);
		// g.setColor(Color.RED);
		// g2d.rotate(direction,x+(width/2),y+(height/2));
		// g.fillRect((int) x-5, (int) y+(height/2)-8, width+20, 16);
		// g.fillOval((int)x-(width/2), (int)y, width, height);
		// g2d.rotate(direction*-1,x+(width/2),y+(height/2));

	}

}
