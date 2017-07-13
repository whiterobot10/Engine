package platformer;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class bullet extends Entity {
	int damage = 1;
	double xSpeed = 0;
	double ySpeed = 0;
	int type = 0;

	public bullet(double xstart, double ystart, double xSpeedStart, double ySpeedStart, int damageStart,
			double MaimDamage) {
		super(xstart, ystart);
		xSpeed = xSpeedStart;
		ySpeed = ySpeedStart;
		width = 16;
		height = 16;
		x = xstart - (width / 2);
		y = ystart - (height / 2);
		needsRemoval = false;
		canTakeDamage = false;
		damage = damageStart;
		mOB = false;
		maimDamage = MaimDamage;
		friction = 0;
		airFriction = 0;
		try {
			spriteImage = Main.resize(ImageIO.read(new File("bullet.png")), 128, 32);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public bullet(double xstart, double ystart, double xSpeedStart, double ySpeedStart, int damageStart,
			double MaimDamage, int setType) {
		super(xstart, ystart);
		xSpeed = xSpeedStart;
		ySpeed = ySpeedStart;
		width = 16;
		height = 16;
		x = xstart - (width / 2);
		y = ystart - (height / 2);
		needsRemoval = false;
		canTakeDamage = false;
		damage = damageStart;
		mOB = false;
		maimDamage = MaimDamage;
		friction = 0;
		airFriction = 0;
		type = setType;
		try {
			spriteImage = Main.resize(ImageIO.read(new File("bullet.png")), 128, 32);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void draw(Graphics g, Main game) {
		game.flashDisplay.add(new Box((int) x - 8, (int) y - 8, (int) x + 24, (int) y + 24, spriteImage, type * 32, 0,
				Math.atan2(ySpeed, xSpeed), 2));
	}

	private void bulletRemoveOnCollide(Main game) {
		if (game.clsnCheck(this)) {
			needsRemoval = true;
		}
		if (game.clsnCheck(this, game.entities)) {
			needsRemoval = true;
		}
		if (game.clsnCheck(game.PC)) {
			needsRemoval = true;
		}
	}

	@Override
	public void update(Main game) {
		if (type == 1) {
			xv = xv * 2;
			yv = yv * 2;
		}
		// System.out.println(xSpeed);
		if (ySpeed > 0) {
			for (double i = ySpeed; i > 0; i--) {

				y += 1;

				bulletRemoveOnCollide(game);
			}
		}

		if (ySpeed < 0) {
			for (double i = ySpeed; i < 0; i++) {

				y -= 1;

				bulletRemoveOnCollide(game);
			}
		}

		if (xSpeed > 0) {
			for (double i = xSpeed; i > 0; i--) {

				x += 1;

				bulletRemoveOnCollide(game);
			}
		}

		if (xSpeed < 0) {

			for (double i = xSpeed; i < 0; i++) {
				x -= 1;

				bulletRemoveOnCollide(game);
			}
		}

	}

	@Override
	public void makeDamage(Main game) {
		if (needsRemoval) {
			game.damageFields.add(new DamageField((int) x - 3 - (width / 2), (int) y - 3 - (height / 2),
					(int) x + (width / 2) + 3, (int) y + (height / 2) + 3, damage, 0, 0, maimDamage));
		}
	}

}
