package platformer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Entity {

	// define variables

	double x = 0;
	double y = 0;
	double xv = 0;
	double yv = 0;
	int width = 0;
	int height = 0;
	int imgWidth = 0;
	int imgHeight = 0;
	boolean facingLeft = false;
	
	int health = 0;
	int maxHealth = 0;
	int healTime = 0;
	int maimAmount = 0;
	int stamina = 0;
	int power = 0;
	int armor = 0;
	
	
	int iframes = 0;
	int attackOffset = 16;

	boolean needsRemoval = false;
	boolean canTakeDamage = true;
	boolean mOB = true;
	boolean canBeRemoved = false;

	public double gravity = 3;
	public double gravityFloat = 1.5;
	public double jumpStrenght = 21;
	public double walkspeed = 3;
	public double xvMax = 18;
	public double friction = 10;
	public double airFriction = 3;

	boolean left = false;
	boolean right = false;
	boolean up = false;
	boolean up2 = false;
	boolean down = false;
	boolean space = false;

	int attack1 = 0;
	int attack1Duration = 5;
	int attack1Power = 5;
	int attack2Power = 2;
	int attackKnockBack = 35;
	double knockBackResist = 0;
	int attack1Delay = 0;
	int attack2 = 0;
	double maimDamage = 3;
	double maimDamage2 = 1;

	double step = 0;

	BufferedImage spriteImage = null;
	BufferedImage attack1Image = null;
	BufferedImage attack2Image = null;
	BufferedImage heartImage = null;

	{
		try {
			heartImage = Main.resize(ImageIO.read(new File("Hearts.png")), 48, 24);
		} catch (IOException e1) {
			// e1.printStackTrace();
		}
	}

	public void DrawPiece(Graphics2D g2d, int xOffset, int yOffset, BufferedImage textureInput, int cornerX,
			int cornerY, boolean isFlipped, int inputWidth, int inputHeigh, double rotationRads) {
		BufferedImage image = textureInput.getSubimage(cornerX, cornerY, inputWidth, inputHeigh);
		if (isFlipped) {
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-image.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
		}
		g2d.rotate(rotationRads, x, y);
		g2d.drawImage(image, null, (int) x - (imgWidth / 2) - xOffset, (int) y - (imgHeight / 2) - yOffset);
		g2d.rotate(rotationRads * -1, x, y);
	}

	int fieldWidth = 32;
	int fieldHeight = 64;

	// making entity

	public Entity(double xstart, double ystart) {
		x = xstart;
		y = ystart;
		width = 32;
		height = 64;
		imgWidth = 64;
		imgHeight = 64;
	}

	// preforming physics calculations
	public boolean collides(Rectangle rect) {
		Rectangle hold = new Rectangle(getRect());
		return hold.intersects(rect);

	}

	private void Physics(Main game) {
		if (up) {
			yv += gravityFloat;
		} else {
			yv += gravity;
		}
		if (yv > 0) {
			for (double i = yv; i > 0; i--) {

				y += 1;

				if (game.clsnCheck(this) || game.clsnCheck(this, game.entities)) {
					y -= 1;
					yv = 0;
				}
			}
		}

		if (yv < 0) {
			for (double i = yv; i < 0; i++) {

				y -= 1;

				if (game.clsnCheck(this) || game.clsnCheck(this, game.entities)) {
					y += 1;
					yv = 0;
				}
			}
		}

		if (xv < 0) {
			y -= 1;
			for (double i = xv; i < 0; i++) {
				x -= 1;

				if (game.clsnCheck(this) || game.clsnCheck(this, game.entities)) {
					x += 1;
				}
			}
			y += 1;

		}

		if (xv > 0) {
			y -= 1;
			for (double i = xv; i > 0; i--) {

				x += 1;

				if (game.clsnCheck(this) || game.clsnCheck(this, game.entities)) {
					x -= 1;
				}

			}

			y += 1;
		}
		y++;
		if (game.clsnCheck(getRect(), game.instaKill)) {
			health = 0;
		}
		y--;

		if (!(left) && !(right)) {

			if (xv > friction) {
				xv -= friction;
			}
			if (xv <= friction && xv > 0) {
				xv = 0;
			}
			if (xv < friction * -1) {
				xv += friction;
			}
			if (xv >= friction * -1 && xv < 0) {
				xv = 0;
			}
		}
	}

	// obtain rectangular hitbox of entity

	public Rectangle getRect() {
		return new Rectangle((int) x - (width / 2), (int) y - (height / 2), width, height);

	}
	
	// draw entity
	public void showHitBox(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int) x - (imgWidth / 2), (int) y - (imgHeight / 2), imgWidth, imgHeight);
		g.setColor(Color.ORANGE);
		g.fillRect((int) x - (width / 2), (int) y - (height / 2), width, height);
		g.setColor(Color.BLUE);
		g.fillOval((int) x - 5, (int) y - 5, 10, 10);
	}

	
	public void showhealth(Graphics g){
		
		for (int i = 0;i<maxHealth-maimAmount;i++){
			int shift = 0;
			
			if (health<=i){
				shift = 24;
			}
			

			if(i%4==0){
				DrawPiece((Graphics2D) g, (maxHealth/4)+(i/4*-24)+27, 23, heartImage, 0+shift, 0, false, 12, 12, 0);	
			}
			if(i%4==3){
				DrawPiece((Graphics2D) g, (maxHealth/4)+(i/4*-24)+15, 23, heartImage, 12+shift, 0, false, 12, 12, 0);	
			}
			if(i%4==2){
				DrawPiece((Graphics2D) g, (maxHealth/4)+(i/4*-24)+15, 11, heartImage, 12+shift, 12, false, 12, 12, 0);	
			}
			if(i%4==1){
				DrawPiece((Graphics2D) g, (maxHealth/4)+(i/4*-24)+27, 11, heartImage, 0+shift, 12, false, 12, 12, 0);	
			}
			
			
		}
		
	}
	
	
	public void draw(Graphics g, Main game) {
		
		if (game.Show_Hit_Boxes) {
			showHitBox(g);
		}
		

		showhealth(g);


		//DrawPiece((Graphics2D) g, -11, 23, heartImage, 0, 0, false, 12, 12, 0);
		//DrawPiece((Graphics2D) g, -20, 23, heartImage, 12, 0, false, 12, 12, 0);
		//DrawPiece((Graphics2D) g, -11, 11, heartImage, 0, 12, false, 12, 12, 0);
		//DrawPiece((Graphics2D) g, -20, 11, heartImage, 12, 12, false, 12, 12, 0);
		
		
		
		y += 2;

		if (attack2 != 0) {
			DrawPiece((Graphics2D) g, -8, -5, spriteImage, 96, 0, facingLeft, 48, 60, 0);
		} else if (attack1 != 0 && attack1 > -5) {
			if (attack1 > 0) {
				DrawPiece((Graphics2D) g, -8, -5, spriteImage, 144, 0, facingLeft, 48, 60, 0);
				if (facingLeft) {
					DrawPiece((Graphics2D) g, 16, 14, spriteImage, 9 * 48, 60, facingLeft, 48, 36, 0);
				} else {
					DrawPiece((Graphics2D) g, -32, 14, spriteImage, 9 * 48, 60, facingLeft, 48, 36, 0);
				}
			} else {
				DrawPiece((Graphics2D) g, -8, -5, spriteImage, 144 + 48, 0, facingLeft, 48, 60, 0);
				if (facingLeft) {
					DrawPiece((Graphics2D) g, 16, -42, spriteImage, 10 * 48, 60, facingLeft, 48, 36, 0);
				} else {
					DrawPiece((Graphics2D) g, -32, -42, spriteImage, 10 * 48, 60, facingLeft, 48, 36, 0);
				}
			}

		} else if (xv != 0 && !(game.clsnCheck(this))) {
			DrawPiece((Graphics2D) g, -8, -5, spriteImage, 192 + 48, 0, facingLeft, 48, 60, 0);
		} else if (xv != 0) {

			DrawPiece((Graphics2D) g, -8, -5, spriteImage, 336 + 48 + ((int) (step / 3) * 48), 0, facingLeft, 48, 60,
					0);
		} else {
			DrawPiece((Graphics2D) g, -8, -5, spriteImage, 0, 0, facingLeft, 48, 60, 0);
		}
		DrawPiece((Graphics2D) g, -8, -50, spriteImage, ((int) step) * 48, 60, facingLeft, 48, 36, 0);
		if (attack1 > 0) {
			if (facingLeft) {
				DrawPiece((Graphics2D) g, 64 + (attack1 * -6), -5, spriteImage, 240 + 48, 0, facingLeft, 48, 60, 0);
			} else {
				DrawPiece((Graphics2D) g, -72 + (attack1 * 6), -5, spriteImage, 240 + 48, 0, facingLeft, 48, 60, 0);
			}
		}
		y -= 2;

//		if (mOB) {
//			if (health > 0) {
//				g.setColor(Color.gray);
//				g.fillRect((int) x - (maxHealth / 2), (int) y - (imgHeight / 2) - 10, maxHealth, 5);
//				g.setColor(Color.RED);
//				if (this == game.PC) {
//					g.setColor(Color.CYAN);
//				}
//				g.fillRect((int) x - (maxHealth / 2), (int) y - (imgHeight / 2) - 10, health, 5);
//
//				g.setColor(Color.black);
//				g.fillRect((int) (x - (maxHealth / 2)) + (maxHealth - maimAmount), (int) y - (imgHeight / 2) - 10,
//						maimAmount, 5);
//			}
//
//		}

	}

	// defining movement

	private void addVelocity(Main game) {

		if (left && xv == 0) {
			xv -= walkspeed;

		}
		if (right && xv == 0) {
			xv += walkspeed;

		}
		if (left) {
			if ((xv * -1) - walkspeed >= xvMax) {
				xv = xvMax * -1;
			} else {
				xv -= walkspeed;
			}
		}
		if (right) {
			if (xv + walkspeed >= xvMax) {
				xv = xvMax;
			} else {
				xv += walkspeed;
			}
		}

		y += 2;

		if ((game.clsnCheck(this) || game.clsnCheck(this, game.entities)) && up2) {
			up2 = false;
			yv = jumpStrenght * -1;
		}
		y -= 2;

	}

	// update entity

	public void update(Main game) {
		if (iframes > 0) {
			iframes--;
		}

		addVelocity(game);
		Physics(game);

	}

	// make damagefield

	public void makeDamage(Main game) {
		if (attack1 > 0) {
			if (facingLeft) {

				game.damageFields.add(new DamageField((int) x - (fieldWidth + attackOffset),
						(int) y - (fieldHeight / 2), (int) x - attackOffset, (int) y + (fieldHeight / 2), attack1Power,
						attackKnockBack * -1, -7, maimDamage));
				// game.flashDisplay.add(new Box((int) x - (fieldWidth +
				// attackOffset), (int) y - (fieldHeight / 2),
				// (int) x - attackOffset, (int) y + (fieldHeight / 2),
				// attack1Image,
				// (attack1Duration - attack1) * fieldWidth, 0, true, 1));
			} else {

				game.damageFields.add(new DamageField((int) x + attackOffset, (int) y - (fieldHeight / 2),
						(int) x + (fieldWidth + attackOffset), (int) y + (fieldHeight / 2), attack1Power,
						attackKnockBack, -7, maimDamage));
				// game.flashDisplay.add(new Box((int) x + attackOffset, (int) y
				// - (fieldHeight / 2),
				// (int) x + (fieldWidth + attackOffset), (int) y + (fieldHeight
				// / 2), attack1Image,
				// (attack1Duration - attack1) * fieldWidth, 0, false, 1));

			}
			if (attack1 == 1) {
				attack1 = -1;
			} else {
				attack1--;
			}
		}

		else if (attack1 > attack1Delay * -1 && !(attack1 == 0)) {
			attack1--;
		} else {
			attack1 = 0;
		}

	}

	// detecting and taking damage

	public void takeDamage(Main game) {

		for (DamageField x : game.damageFields) {
			if (x.collides(getRect()) && iframes == 0&&x.damage>armor) {
				health -= x.damage-armor;
				iframes = 8;
				xv = x.knockBack * (1 - knockBackResist);
				yv += x.knockBackUp * (1 - knockBackResist);
				if(x.maimDamage>armor){
				maimAmount += x.maimDamage-armor;}
				healTime = 0;
			}
			if ((health <= 0 && canTakeDamage) || (y > 1000) && canBeRemoved) {
				needsRemoval = true;
			}
		}

	}

	public void makeEntities(Main game) {
		// TODO Auto-generated method stub

	}

}
