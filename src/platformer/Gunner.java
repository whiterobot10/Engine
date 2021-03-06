package platformer;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Gunner extends Entity {

	public Gunner(double xstart, double ystart) {
		super(xstart, ystart);
		attack1Power = 1;
		walkspeed = 3.5;
		friction = 3.5;
		maxHealth = 15;
		health = 15;
		attackKnockBack = 20;
		maimDamage = 0;
		maimDamage2 = 1;
		canBeRemoved = true;
		height = 72;
		imgHeight=96;
		{



			try {
				spriteImage = Main.resize(ImageIO.read(new File("EnemySprites1.png")), 336*2, 96);
			} catch (IOException e1) {
				//e1.printStackTrace();
			}

		}
	}

	// draw Gunner

	// Color fillColor = (Color.GREEN);
	// if(game.lineOsight((int)x+(width/2), (int)y+(height/2),
	// (int)game.PC.x+(game.PC.width/2),
	// (int)game.PC.y+(game.PC.height/2))){
	// fillColor = (Color.RED);
	// }
	// g.setColor(fillColor);
	// g.fillRect((int) x, (int) y, width, height);
	// g.setColor(Color.WHITE);
	// g.fillOval((int) x, (int) y, width, height);
	// g.setColor(fillColor);
	// g.fillRect((int) x, (int) y + (height / 2) - 2, width, 4);
	// g.fillRect((int) x + (width / 2) - 2, (int) y, 4, height);

	// Gunner specific AI

	@Override
	public void update(Main game) {
		// if(game.lineOsight((int)x+(width/2), (int)y+(height/2),
		// (int)game.PC.x+(game.PC.width/2),
		// (int)game.PC.y+(game.PC.height/2))){
		// System.out.println("LineOfSight");
		// }
		// System.out.println("test");

		if (game.lineOsight((int) x, (int) y, (int) game.PC.x, (int) game.PC.y)) {
			xvMax = 12;
			if (Math.abs(x - game.PC.x) < 48 && Math.abs(y - game.PC.y) < 16 && attack1 == 0) {
				attack1 = attack1Duration;
			}
			if (attack2 > 0) {
				attack2--;
			}
			if (attack2 == 0) {
				attack2 = 10;

				if (facingLeft) {
					game.newEntities.add(new Bullet(x - (width+xvMax), y-10, -32, 0, attack2Power, maimDamage2));

				} else {
					game.newEntities.add(new Bullet(x + (width+xvMax), y-10, 32, 0, attack2Power, maimDamage2));

				}
			}

			if (game.PC.x < x) {
				left = false;
				right = true;
				facingLeft = true;
			}
			if (game.PC.x > x) {
				left = true;
				right = false;
				facingLeft = false;
			}
			step -= 0.5;
			if (step >= 12) {
				step -= 11;
			}
			if (step <= 0) {
				step += 11;
			}

		} else {
			if (attack2 > 0) {
				attack2--;
			}
			step += 0.25;
			if (step >= 12) {
				step -= 11;
			}
			xvMax = 5;
			y -= 1;
			if (facingLeft) {
				left = true;
				right = false;
				x = x - 2;
				if (game.clsnCheck(this)||game.clsnCheck(this, game.entities)) {
					
					facingLeft = false;
				}
				x = x + 2;
			} else {
				right = true;
				left = false;

				x = x + 2;
				if (game.clsnCheck(this)||game.clsnCheck(this, game.entities)) {
					
					facingLeft = true;
				}
				x = x - 2;
				
			}
			y += 1;
		}

		super.update(game);

	}

}
