package platformer;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Blocade extends Entity {
	int phase = 0;

	public Blocade(double xstart, double ystart, int setHealth) {
		super(xstart, ystart);
		maxHealth = setHealth;
		health = setHealth;
		width = 50;
		height = 50;
		canBeRemoved = true;
		try {
			spriteImage = Main.resize(ImageIO.read(new File("wall.png")), 250, 50);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void update(Main game) {
		if (iframes >= 1) {
			iframes--;
		}
		if (health > (maxHealth / 5) * 4) {
			phase = 0;
		} else if (health > (maxHealth / 5) * 3) {
			phase = 1;
		} else if (health > (maxHealth / 5) * 2) {
			phase = 2;
		} else if (health > (maxHealth / 5) * 1) {
			phase = 3;
		} else {
			phase = 4;
		}
	}

	// make damage textures
	@Override
	public void draw(Graphics g, Main game) {
		game.flashDisplay.add(new Box((int) x-(width/2), (int) y-(height/2), (int) x + (width/2), (int) y + (height/2), spriteImage, phase *50, 0,4));
	}

}
