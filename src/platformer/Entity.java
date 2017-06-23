package platformer;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Entity {

	double x = 0;
	double y = 0;
	double xv = 0;
	double yv = 0;
	int width = 0;
	int height = 0;
	boolean facingLeft = false;
	int health = 0;
	int stamina = 0;
	int power = 0;
	int iframes = 0;

	boolean needsRemoval = false;

	public double gravity = 2;
	public double gravityFloat = 1.5;
	public double jumpStrenght = 18;
	public double walkspeed = 6;
	public double xvMax = 18;
	public double friction = 5;
	public double airFriction = 2;

	boolean left = false;
	boolean right = false;
	boolean up = false;
	boolean up2 = false;
	boolean down = false;

	int attack1 = 0;
	int attack1Duration = 5;
	int attackPower = 10;
	int attackKnockBack = 35;
	double knockBackResist = 0;
	int attack1Delay = 5;

	int fieldWidth = 16;
	int fieldHeight = 32;

	public Entity(double xstart, double ystart) {
		x = xstart;
		y = ystart;

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

				if (game.clsnCheck(this)) {
					y -= 1;
					yv = 0;
				}
			}
		}

		if (yv < 0) {
			for (double i = yv; i < 0; i++) {

				y -= 1;

				if (game.clsnCheck(this)) {
					y += 1;
					yv = 0;
				}
			}
		}

		if (xv < 0) {
			y -= 1;
			for (double i = xv; i < 0; i++) {
				x -= 1;
				game.clsnCheck(this);
				if (game.clsnCheck(this)) {
					x += 1;
				}
			}
			y += 1;

		}

		if (xv > 0) {
			y -= 1;
			for (double i = xv; i > 0; i--) {

				x += 1;

				if (game.clsnCheck(this)) {
					x -= 1;
				}

			}

			y += 1;
		}

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

	public Rectangle getRect() {
		return new Rectangle((int) x, (int) y, width, height);

	}

	public void draw(Graphics g) {
	}

	private void addVelocity(Main game) {

		if (left && xv == 0) {
			xv -= walkspeed;
			
		}
		if (right && xv == 0) {
			xv += walkspeed;
			
		}
		if (left) {
			if ((xv * -1) + walkspeed >= xvMax) {
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

		if (game.clsnCheck(this) && up2) {
			up2 = false;
			yv = jumpStrenght * -1;
		}
		y -= 2;

	}

	public void update(Main game) {
		if (iframes > 0) {
			iframes--;
		}

		addVelocity(game);
		Physics(game);

	}

	public void makeDamage(Main game) {
		if (attack1 > 0) {
			if (facingLeft) {

				game.damageFields.add(new DamageField((int) x - 21, (int) y, (int) x - 1, (int) y + fieldHeight,
						attackPower, attackKnockBack * -1, -7));
				game.flashDisplay.add(new Box((int) x - 21, (int) y, (int) x - 1, (int) y + fieldHeight, game.Image1,
						(attack1Duration - attack1) * fieldWidth, 0, true));
			} else {

				game.damageFields.add(new DamageField((int) x + 33, (int) y, (int) x + 53, (int) y + fieldHeight,
						attackPower, attackKnockBack, -7));
				game.flashDisplay
						.add(new Box((int) x + 33, (int) y, (int) x + 53, (int) y + fieldHeight, game.Image1, 0, 0));

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

	public void takeDamage(Main game) {

		for (DamageField x : game.damageFields) {
			if (x.collides(getRect()) && iframes == 0) {
				health -= x.damage;
				iframes = 15;
				xv = x.knockBack * (1 - knockBackResist);
				yv += x.knockBackUp * (1 - knockBackResist);
			}
			if (health <= 0) {
				needsRemoval = true;
			}
		}

	}

}
