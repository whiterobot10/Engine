package platformer;

import java.awt.Color;
import java.awt.Graphics;

public class Slasher extends Entity {
	int direction = 0;

	public Slasher(double xstart, double ystart) {
		super(xstart, ystart);
		width = 32;
		height = 32;
		walkspeed = 2;
		xvMax = 10;
		friction = 0.1;
		health = 32;

	}

	@Override
	public void draw(Graphics g) {

		g.setColor(Color.RED);
		g.fillRect((int) x, (int) y, width, height);
		g.setColor(Color.WHITE);
		g.fillOval((int) x, (int) y, width, height);
		g.setColor(Color.RED);
		g.fillRect((int) x, (int) y + (height / 2) - 2, width, 4);
		g.fillRect((int) x + (width / 2) - 2, (int) y, 4, height);		
		g.fillRect((int) x, (int) y - 10, health, 5);
	}

	@Override
	public void update(Main game) {
		if(game.lineOsight((int)x+(width/2), (int)y+(height/2), (int)game.PC.x+(game.PC.width/2), (int)game.PC.y+(game.PC.height/2))){
			System.out.println("LineOfSight");
			}
		System.out.println("test");
		if (game.PC.x > x) {
			if (direction > 0) {
				direction = 30;
			} else {
				direction += 1;
			}
		}

		if (game.PC.x < x) {
			if (direction < 0) {
				direction = -30;
			} else {
				direction -= 1;
			}
		}

		if (game.PC.y+20 < y) {
			up2 = true;
		} else {
			up2 = false;
		}

		if (direction < 0) {
			left = true;
		} else {
			left = false;
		}
		if (direction > 0) {
			right = true;
		} else {
			right = false;
		}

		super.update(game);
		
	}

}
