package platformer;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Slasher extends Entity {

	public Slasher(double xstart, double ystart) {
		super(xstart, ystart);
		walkspeed = 3.5;
		friction = 3.5;
		maxHealth = 20;
		health = 20;
		canBeRemoved = true;
		height = 90;
		imgHeight=96;
		{



			try {
				spriteImage = Main.resize(ImageIO.read(new File("EnemySprites1.png")), 336*2, 96);
			} catch (IOException e1) {
				//e1.printStackTrace();
			}

		}

	}

	//draw slasher
	


//		Color fillColor = (Color.GREEN);
//		 if(game.lineOsight((int)x+(width/2), (int)y+(height/2),
//		 (int)game.PC.x+(game.PC.width/2),
//		 (int)game.PC.y+(game.PC.height/2))){
//			 fillColor = (Color.RED);
//		 }
//		g.setColor(fillColor);
//		g.fillRect((int) x, (int) y, width, height);
//		g.setColor(Color.WHITE);
//		g.fillOval((int) x, (int) y, width, height);
//		g.setColor(fillColor);
//		g.fillRect((int) x, (int) y + (height / 2) - 2, width, 4);
//		g.fillRect((int) x + (width / 2) - 2, (int) y, 4, height);

	

	//slasher specific AI
	
	@Override
	public void update(Main game) {
		// if(game.lineOsight((int)x+(width/2), (int)y+(height/2),
		// (int)game.PC.x+(game.PC.width/2),
		// (int)game.PC.y+(game.PC.height/2))){
		// System.out.println("LineOfSight");
		// }
		// System.out.println("test");

		if (game.lineOsight((int) x, (int) y, (int) game.PC.x, (int) game.PC.y)) {
			xvMax = 12;
			if (Math.abs(x - game.PC.x) < 40   && Math.abs(y - game.PC.y) <32 && attack1 == 0) {
				attack1 = attack1Duration;
			} else if(attack1<=0){
				if (game.PC.x < x) {
					left = true;
					right = false;
					facingLeft=true;
				}
				if (game.PC.x > x) {
					left = false;
					right = true;
					facingLeft=false;
				}
				step += 0.5;
				if (step >= 12) {
					step -= 11;
				}
			}

			
		} else {
			step += 0.25;
			if (step >= 12) {
				step -= 11;
			}
			xvMax = 5;
			y -= 1;
			if (facingLeft) {
				left = true;
				right = false;
				x = x - 2;
				if (game.clsnCheck(this)||game.clsnCheck(this, game.entities)) {
					
					facingLeft = false;
				}
				x = x + 2;
			} else {
				right = true;
				left = false;

				x = x + 2;
				if (game.clsnCheck(this)||game.clsnCheck(this, game.entities)) {
					
					facingLeft = true;
				}
				x = x - 2;
				y += 1;
			}
		}

		super.update(game);

	}

}
