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
		health = 64;
		width = 32;
		height = 32;
		attack2Power = 15;
		maimDamage = 7;
		try {
			spriteImage = Main.resize(ImageIO.read(new File("turret.png")), 128, 32);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public WallTurret(double xstart, double ystart, boolean isRocket) {
		super(xstart, ystart);
		health = 64;
		width = 32;
		height = 32;
		attack2Power = 15;
		maimDamage = 7;
		try {
			spriteImage = Main.resize(ImageIO.read(new File("turret.png")), 128, 32);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		rocket = isRocket;
	}

	@Override
	public void update(Main game) {
		if (game.lineOsight((int) x + (width / 2), (int) y + (height / 2), (int) game.PC.x + (game.PC.width / 2),
				(int) game.PC.y + (game.PC.height / 2))) {
			direction = Math.atan2(game.PC.y - y, game.PC.x - x);
			if (attack2Delay == 0) {
				attack2Delay = 20;
				if (rocket) {
					game.newEntities.add(new bullet(x + (width / 2) + Math.cos(direction) * 50,
							y + (height / 2) + Math.sin(direction) * 50, Math.cos(direction) * 20, Math.sin(direction) * 20,
							attack2Power, maimDamage,1));
				} else {
					game.newEntities.add(new bullet(x + (width / 2) + Math.cos(direction) * 50,
							y + (height / 2) + Math.sin(direction) * 50, Math.cos(direction) * 20, Math.sin(direction) * 20,
							attack2Power, maimDamage));
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
		Graphics2D g2d = (Graphics2D) g;
		game.flashDisplay.add(new Box((int) x, (int) y, (int) x + 32, (int) y + 32, spriteImage, 0, 0, 3));
		if (!(rocket)) {
			game.flashDisplay
					.add(new Box((int) x, (int) y, (int) x + 32, (int) y + 32, spriteImage, 32, 0, direction, 3));
		} else if (attack2Delay <= 3) {
			game.flashDisplay
					.add(new Box((int) x, (int) y, (int) x + 32, (int) y + 32, spriteImage, 96, 0, direction, 3));
		} else {
			game.flashDisplay
					.add(new Box((int) x, (int) y, (int) x + 32, (int) y + 32, spriteImage, 64, 0, direction, 3));
		}
		g.setColor(Color.gray);
		g.fillRect((int) x, (int) y - 10, 32, 5);
		g.setColor(Color.RED);
		g.fillRect((int) x, (int) y - 10, health / 2, 5);
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
