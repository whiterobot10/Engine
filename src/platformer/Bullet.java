package platformer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends Entity {
	int damage = 1;
	double xSpeed = 0;
	double ySpeed = 0;
	int type = 0;
	boolean hitWall=false;
	int explosionTime=0;

	public Bullet(double xstart, double ystart, double xSpeedStart, double ySpeedStart, int damageStart,
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
			spriteImage = Main.resize(ImageIO.read(new File("bullet.png")), 256, 64);
		} catch (IOException e1) {
			//e1.printStackTrace();
		}

	}

	public Bullet(double xstart, double ystart, double xSpeedStart, double ySpeedStart, int damageStart,
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
			spriteImage = Main.resize(ImageIO.read(new File("bullet.png")), 256, 64);
		} catch (IOException e1) {
			//e1.printStackTrace();
		}

	}

	@Override
	public void draw(Graphics g, Main game) {
		if (game.Show_Hit_Boxes) {
			super.showHitBox(g);
		}
		//game.flashDisplay.add(new Box((int) x - 8, (int) y - 8, (int) x + 24, (int) y + 24, spriteImage, type * 32, 0,
			//	Math.atan2(ySpeed, xSpeed), 2));
		if(explosionTime>0){super.DrawPiece((Graphics2D)g,0,0, spriteImage, (explosionTime-1)*64, 0, false, 64, 64, 0);}else{
		super.DrawPiece((Graphics2D) g, 0, 0, spriteImage, type * 64, 0, false, 64, 64, Math.atan2(ySpeed, xSpeed));}
		
	}

	private void bulletRemoveOnCollide(Main game) {
		if (game.clsnCheck(super.getRect(),game.boxes)) {
			hitWall = true;
		}
		if (game.clsnCheck(this, game.entities)) {
			hitWall = true;
		}
		if (game.clsnCheck(game.PC)) {
			hitWall = true;
		}
	}

	@Override
	public void update(Main game) {
		if (type == 1) {
			xv = xv *1.5;
			yv = yv *1.5;
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
		if (hitWall&&type==0) {
			//game.damageFields.add(new DamageField((int) x - 3 - (width / 2), (int) y - 3 - (height / 2),
			//		(int) x + (width / 2) + 3, (int) y + (height / 2) + 3, damage, 0, 0, maimDamage));
			needsRemoval=true;
			game.damageFields.add(new Explosion((int)x, (int)y, damage, (int)maimDamage,10));
		} else if (hitWall && type == 1) {
			try {
				spriteImage = Main.resize(ImageIO.read(new File("explosion.png")), 512, 64);
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			xSpeed=0;
			ySpeed=0;
			explosionTime++;
			
			

			game.damageFields.add(new DamageField((int) x - 16, (int) y - 16,
					(int) x +16, (int) y + 16, damage, 0, 0, maimDamage));
			game.damageFields.add(new DamageField((int) x - 32, (int) y - 32,
					(int) x +32, (int) y + 32, damage/2, 0, 0, maimDamage/2));}
		if(explosionTime>=8){needsRemoval=true;}
	}

}
