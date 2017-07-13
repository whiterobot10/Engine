package platformer;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CheckPoint extends Entity {

	public CheckPoint(double xstart, double ystart) {
		super(xstart, ystart);
		canTakeDamage = false;
		mOB = false;
		width=32;
		height=32;
		try {
			spriteImage = Main.resize(ImageIO.read(new File("checkPoint.png")), 64, 32);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics g, Main game) {
		if (x==game.pcStartX&&y==game.pcStartY) {
			game.flashDisplay
					.add(new Box((int) x-(width/2), (int) y-(height/2), (int) x + (width/2), (int) y + (height/2), spriteImage, 32, 0, 5));
		} else {
			game.flashDisplay
					.add(new Box((int) x-(width/2), (int) y-(height/2), (int) x + (width/2), (int) y + (height/2), spriteImage, 0, 0, 5));
		}
	}

	@Override
	public void update(Main game) {
		if (Math.abs(x - game.PC.x) < 16 && Math.abs(y - game.PC.y) < 16) {
			game.PC.health = 32;
			game.PC.maimAmount = 0;
			game.pcStartX=(int) x;
			game.pcStartY=(int) y;
		}
	}
}
