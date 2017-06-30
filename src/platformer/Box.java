package platformer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Box {

	// define variables

	int xMin = 0;
	int xMax = 0;
	int yMin = 0;
	int yMax = 0;
	int xStart = -1;
	int yStart = -1;
	boolean isLeft = false;
	double rotationRads = 0;
	int layer=0;
	BufferedImage texture = null;

	// set variables on create

	public Box(int x1, int y1, int x2, int y2) {
		xMin = x1;
		xMax = x2;
		yMin = y1;
		yMax = y2;
	}

	public Box(int x1, int y1, int x2, int y2, BufferedImage ImportTexture) {
		xMin = x1;
		xMax = x2;
		yMin = y1;
		yMax = y2;
		texture = ImportTexture;
	}

	public Box(int x1, int y1, int x2, int y2, BufferedImage ImportTexture, int x3, int y3,int Layer) {
		xMin = x1;
		xMax = x2;
		yMin = y1;
		yMax = y2;
		yStart = y3;
		xStart = x3;
		texture = ImportTexture;
		layer=Layer;
	}

	public Box(int x1, int y1, int x2, int y2, BufferedImage ImportTexture, int x3, int y3, boolean left,int Layer) {
		xMin = x1;
		xMax = x2;
		yMin = y1;
		yMax = y2;
		yStart = y3;
		xStart = x3;
		texture = ImportTexture;
		isLeft = left;
		layer=Layer;
	}

	public Box(int x1, int y1, int x2, int y2, BufferedImage spriteImage, int x3, int y3, double atan2,int Layer) {
		xMin = x1;
		xMax = x2;
		yMin = y1;
		yMax = y2;
		yStart = y3;
		xStart = x3;
		texture = spriteImage;
		rotationRads=atan2;
		layer=Layer;
		
	}

	// for testing
	@Override
	public String toString() {
		return "" + xMin + " " + xMax + " " + yMin + " " + yMax;
	}

	// flip image

	public void Crop(Graphics2D g2d, BufferedImage textureInput, int cornerX, int cornerY, boolean isFlipped) {
		BufferedImage image = textureInput.getSubimage(cornerX, cornerY, xMax - xMin, yMax - yMin);
		if (isFlipped) {
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-image.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
		}
		g2d.rotate(rotationRads,(xMin+xMax)/2,(yMin+yMax)/2);
		g2d.drawImage(image, null, xMin, yMin);
		g2d.rotate(rotationRads*-1,(xMin+xMax)/2,(yMin+yMax)/2);
	}

	// draw the box

	public void draw(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		if (!(texture == null)) {
			Crop(g2d, texture, xStart, yStart, isLeft);
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(xMin, yMin, xMax - xMin, yMax - yMin);
		}

	}
	
	public void draw(Graphics g,int layerInput) {

		Graphics2D g2d = (Graphics2D) g;
		if (!(texture == null)&&layer==layerInput) {
			Crop(g2d, texture, xStart, yStart, isLeft);
		}
	}
	
	public void draw(Graphics g,Color setColor) {

		Graphics2D g2d = (Graphics2D) g;
		if (!(texture == null)) {
			Crop(g2d, texture, xStart, yStart, isLeft);
		} else {
			g.setColor(setColor);
			g.fillRect(xMin, yMin, xMax - xMin, yMax - yMin);
		}

	}

	// collision detection

	public boolean collides(Rectangle rect) {
		Rectangle hold = new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
		return hold.intersects(rect);

	}

	public boolean collides(Line2D line) {
		Rectangle hold = new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
		return hold.intersectsLine(line);

	}

}
