package platformer;

import java.awt.Color;
import java.awt.Graphics;

public class Slasher extends Entity {

	public Slasher(double xstart, double ystart) {
		super(xstart, ystart);
		width = 32;
		height = 32;
		walkspeed = 3.5;
		friction = 3.5;
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
		// if(game.lineOsight((int)x+(width/2), (int)y+(height/2),
		// (int)game.PC.x+(game.PC.width/2),
		// (int)game.PC.y+(game.PC.height/2))){
		// System.out.println("LineOfSight");
		// }
		// System.out.println("test");

		if (game.lineOsight((int) x + (width / 2), (int) y + (height / 2), (int) game.PC.x + (game.PC.width / 2),
				(int) game.PC.y + (game.PC.height / 2))) {
			xvMax = 18;
			if (Math.abs(x - game.PC.x)+Math.abs(y - game.PC.y) < 48 && attack1 == 0) {
				attack1 = attack1Duration;
			} else if(attack1<=0){
				if (game.PC.x < x) {
					left = true;
					right = false;
				}
				if (game.PC.x > x) {
					left = false;
					right = true;
				}
			}

			
		} else {
			
			xvMax = 5;
			y -= 1;
			if (facingLeft) {
				left = true;
				right = false;
				x = x - 2;
				if (game.clsnCheck(this)) {
					System.out.println("hi2");
					facingLeft = false;
				}
				x = x + 2;
			} else {
				right = true;
				left = false;

				x = x + 2;
				if (game.clsnCheck(this)) {
					System.out.println("hi");
					facingLeft = true;
				}
				x = x - 2;
				y += 1;
			}
		}

		super.update(game);

	}

}
