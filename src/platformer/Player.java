package platformer;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Entity {

	public Player(double xstart, double ystart) {
		super(xstart, ystart);
		width = 32;
		height = 32;
		health = 32;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect((int) x, (int) y, width, height);
		g.setColor(Color.WHITE);
		g.fillOval((int) x, (int) y, width, height);
		g.setColor(Color.CYAN);
		g.fillRect((int) x, (int) y + (height / 2) - 2, width, 4);
		g.fillRect((int) x + (width / 2) - 2, (int) y, 4, height);

	}

	
	

}
