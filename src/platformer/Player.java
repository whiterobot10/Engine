package platformer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
		height = 72;
		imgHeight=96;

		try {
			spriteImage = Main.resize(ImageIO.read(new File("PlayerSprites.png")), 336*2, 96);
		} catch (IOException e1) {
			// e1.printStackTrace();
		}
	}

	// draw player

	@Override
	public void draw(Graphics g, Main game) {

super.draw(g, game);

//		DrawPiece((Graphics2D) g, -8, -60, spriteImage, ((int) step) * 48, 60, facingLeft, 48, 36, 0);
//		if (!(attack2 == 0)) {
//			DrawPiece((Graphics2D) g, -8, -15, spriteImage, 96, 0, facingLeft, 48, 60, 0);
//		} else if (!(attack1 == 0)) {
//			DrawPiece((Graphics2D) g, -8, -15, spriteImage, 144, 0, facingLeft, 48, 60, 0);
//		} else if(xv==0){
//			DrawPiece((Graphics2D) g, -8, -15, spriteImage, 0, 0, facingLeft, 48, 60, 0);
//		} else {
//			DrawPiece((Graphics2D) g, -8, -15, spriteImage, 192, 0, facingLeft, 48, 60, 0);
//		}
//		DrawPiece((Graphics2D) g, -8, -60, spriteImage, ((int) step) * 48, 60, facingLeft, 48, 36, 0);
//		if (attack1 > 0) {
//			if (facingLeft) {
//				DrawPiece((Graphics2D) g, 64 + (attack1 * -6), -15, spriteImage, 240, 0, facingLeft, 48, 60, 0);
//			} else {
//				DrawPiece((Graphics2D) g, -72 + (attack1 * 6), -15, spriteImage, 240, 0, facingLeft, 48, 60, 0);
//			}
//		}

//		if (down && xv == 0 && yv == 0) {
//			if (healTime == 20) {
//				super.DrawPiece((Graphics2D) g, 0, 0, healImage, 128, 0, facingLeft, imgWidth, imgHeight, 0);
//			} else if (healTime >= 10) {
//				super.DrawPiece((Graphics2D) g, 0, 0, healImage, 64, 0, facingLeft, imgWidth, imgHeight, 0);
//			} else {
//				super.DrawPiece((Graphics2D) g, 0, 0, healImage, 0, 0, facingLeft, imgWidth, imgHeight, 0);
//			}
//
//		}
		
		if (health > 0) {
			g.setColor(Color.gray);
			g.fillRect((int) x - (maxHealth / 2), (int) y - (imgHeight / 2) - 10, maxHealth, 5);
			g.setColor(Color.RED);
			if (this == game.PC) {
				g.setColor(Color.CYAN);
			}
			g.fillRect((int) x - (maxHealth / 2), (int) y - (imgHeight / 2) - 10, health, 5);

			g.setColor(Color.black);
			g.fillRect((int) (x - (maxHealth / 2)) + (maxHealth - maimAmount), (int) y - (imgHeight / 2) - 10,
					maimAmount, 5);
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

		double scrollXAmount = screenSize.width * 0.4;

		while (x > game.scrollX + (screenSize.width - scrollXAmount)) {
			game.scrollX += 1;
		}

		while (x < game.scrollX + scrollXAmount) {
			game.scrollX += x - (game.scrollX + scrollXAmount);

		}
		if (attack2 > 0) {
			attack2--;
		}
		if (shoot) {
			attack2 = 10;
			attack1 = -1;

		}
		super.update(game);
		if (shoot) {
			if (facingLeft) {
				game.newEntities
						.add(new Bullet(x - (width + Math.abs(xv) + 8), y - 10, -32, 0, attack2Power, maimDamage2));

			} else {
				game.newEntities
						.add(new Bullet(x + (width + Math.abs(xv) + 8), y - 10, 32, 0, attack2Power, maimDamage2));

			}
			//shoot = false;
		}
		if (health <= 0 || y > 5000) {
			game.GameOver();
		}
		if (xv != 0) {

			step += 0.5;
			if (step >= 12) {
				step -= 11;
			}
			y+=4;
			if(game.clsnCheck(super.getRect())==false){step=1;}
			y-=4;
		} else {
			step = 0;
		}

	}

	public void deathAnim(int i) {

	}

}
