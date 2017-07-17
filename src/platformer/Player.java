package platformer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {

	BufferedImage healImage = null;

	boolean shoot = false;

	public Player(double xstart, double ystart) {
		super(xstart, ystart);
		maxHealth = 20;
		health = 20;
		attack1Delay = 10;

		try {
			spriteImage = Main.resize(ImageIO.read(new File("walkCycle.png")), 576, 64);
		} catch (IOException e1) {
			//e1.printStackTrace();
		}
		try {
			healImage = Main.resize(ImageIO.read(new File("healOverlay.png")), 192, 64);
		} catch (IOException e1) {
			//e1.printStackTrace();
		}
	}

	// draw player

	@Override
	public void draw(Graphics g, Main game) {

		super.draw(g, game);

		if (down && xv == 0 && yv == 0) {
			if (healTime == 20) {
				game.flashDisplay.add(new Box((int) x - (imgWidth / 2), (int) y - (imgHeight / 2), (int) x + (imgWidth / 2),
						(int) y + (imgHeight / 2), healImage, 128, 0, facingLeft, 1));
			} else if (healTime >= 10) {
				game.flashDisplay.add(new Box((int) x - (imgWidth / 2), (int) y - (imgHeight / 2), (int) x + (imgWidth / 2),
						(int) y + (imgHeight / 2), healImage, 64, 0, facingLeft, 1));
			} else {
				game.flashDisplay.add(new Box((int) x - (imgWidth / 2), (int) y - (imgHeight / 2), (int) x + (imgWidth / 2),
						(int) y + (imgHeight / 2), healImage, 0, 0, facingLeft, 1));
			}
		}

	}

	// update player

	@Override
	public void update(Main game) {
		if (down && xv == 0 && yv == 0 && health < maxHealth - maimAmount) {
			healTime++;
			if (healTime > 20) {
				healTime--;
				health++;
			}
		} else {
			healTime = 0;
			down = false;
		}

		if (left) {
			facingLeft = true;
		}
		if (right) {
			facingLeft = false;
		}
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		double scrollXAmount = screenSize.width*0.4;
		
		while (x > game.scrollX + (screenSize.width - scrollXAmount)) {
			game.scrollX += 1;
		}

		while (x < game.scrollX + scrollXAmount) {
			game.scrollX += x - (game.scrollX + scrollXAmount);

		}
		if (attack2Delay > 0) {
			attack2Delay--;
		}
		if (shoot) {
			attack2Delay = 10;
			attack1 = -1;

		}
		super.update(game);
		if (shoot) {
			if (facingLeft) {
				game.newEntities.add(new Bullet(x - (width+Math.abs(xv)+8), y-10, -32, 0, attack2Power, maimDamage2));

			} else {
				game.newEntities.add(new Bullet(x + (width+Math.abs(xv)+8), y-10, 32, 0, attack2Power, maimDamage2));

			}
			shoot = false;
		}
		if (health <= 0||y>5000) {
			game.GameOver();
		}
		if (xv != 0) {

			step += 0.5;
			if (step >= 6) {
				step -= 5;
			}
		} else {
			step = 0;
		}

	}

	public void deathAnim(int i) {

	}

}
