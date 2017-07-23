package platformer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Baracade extends Entity {
	int phase = 0;

	public Baracade(double xstart, double ystart, int setHealth) {
		super(xstart, ystart);
		maxHealth = setHealth;
		health = setHealth;
		width = 48;
		height = 96;
		imgWidth =48;
		imgHeight=96;
		canBeRemoved = true;

		try {
			spriteImage = Main.resize(ImageIO.read(new File("Doors.png")), 96, 96);
		} catch (IOException e1) {
			// e1.printStackTrace();
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
	public void takeDamage(Main game) {
		super.takeDamage(game);
		if(iframes==8){
		game.playSound("doorhit.wav");}
	}

	@Override
	public void draw(Graphics g, Main game) {
		
		DrawPiece((Graphics2D) g, 0, 0, spriteImage, 0, 0, facingLeft, 48, 96, 0);
	}

}
