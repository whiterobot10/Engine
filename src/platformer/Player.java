package platformer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {



	BufferedImage healImage = null;

	boolean shoot = false;

	public Player(double xstart, double ystart) {
		super(xstart, ystart);
		width = 32;
		height = 32;
		health = 32;
		attack1Delay = 10;
		maimDamage = 5;
		maimDamage2 = 1;

		try {
			spriteImage = Main.resize(ImageIO.read(new File("walkCycle.png")), 288, 32);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			healImage = Main.resize(ImageIO.read(new File("healOverlay.png")), 96, 32);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// draw player

	@Override
	public void draw(Graphics g, Main game) {
		super.draw(g, game);
		if (down && xv == 0 && yv == 0) {
			if (healTime == 20) {
				game.flashDisplay.add(
						new Box((int) x, (int) y, (int) x + width, (int) y + height, healImage, 64, 0, facingLeft, 1));
			} else if (healTime >= 10) {
				game.flashDisplay.add(
						new Box((int) x, (int) y, (int) x + width, (int) y + height, healImage, 32, 0, facingLeft, 1));
			} else {
				game.flashDisplay.add(
						new Box((int) x, (int) y, (int) x + width, (int) y + height, healImage, 0, 0, facingLeft, 1));
			}
		}

		g.setColor(Color.CYAN);
		// g.fillRect((int) x, (int) y, width, height);
		// g.setColor(Color.WHITE);
		// g.fillOval((int) x, (int) y, width, height);
		// g.setColor(Color.CYAN);
		// g.fillRect((int) x, (int) y + (height / 2) - 2, width, 4);
		// g.fillRect((int) x + (width / 2) - 2, (int) y, 4, height);
		g.fillRect((int) x, (int) y - 10, health, 5);

	}

	// update player

	@Override
	public void update(Main game) {

		if (down && xv == 0 && yv == 0 && health < 32 - maimAmount) {
			healTime++;
			if (healTime > 20) {
				healTime--;
				health++;
			}
		} else {
			healTime = 0;
			down = false;
		}
		if (game.door1 == false && x >= 1700&&x<=1900) {
			game.boxes.add(new Box(1650, 300, 1700, 400));
			game.entities.add(new Blocade(2250, 300, 100));
			game.door1 = true;
		}
		if (game.door2 == false && x >= 3100&&x<=3300) {
			game.boxes.add(new Box(3050,100,3100,150));
			game.entities.add(new Blocade(3650, 100, 100));
			game.door2 = true;
		}
		if (left) {
			facingLeft = true;
		}
		if (right) {
			facingLeft = false;
		}

		while (x > game.scrollX + (Just_Jump.width - game.scrollXPoint)) {
			game.scrollX += 1;
		}

		while (x < game.scrollX + game.scrollXPoint) {
			game.scrollX += x - (game.scrollX + game.scrollXPoint);

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
				game.newEntities.add(new bullet(x - 32, y, -32, 0, attack2Power, maimDamage2));

			} else {
				game.newEntities.add(new bullet(x + 32+width, y, 32, 0, attack2Power, maimDamage2));

			}
			shoot = false;
		}
		if (health <= 0) {
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
