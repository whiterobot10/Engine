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
	int xMin = 0;
	int xMax = 0;
	int yMin = 0;
	int yMax = 0;
	int xStart = -1;
	int yStart = -1;
	boolean isLeft = false;
	BufferedImage texture = null;

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

	public Box(int x1, int y1, int x2, int y2, BufferedImage ImportTexture, int x3, int y3) {
		xMin = x1;
		xMax = x2;
		yMin = y1;
		yMax = y2;
		yStart = y3;
		xStart = x3;
		texture = ImportTexture;
	}

	public Box(int x1, int y1, int x2, int y2, BufferedImage ImportTexture, int x3, int y3, boolean left) {
		xMin = x1;
		xMax = x2;
		yMin = y1;
		yMax = y2;
		yStart = y3;
		xStart = x3;
		texture = ImportTexture;
		isLeft = left;
	}

	@Override
	public String toString() {
		return "" + xMin + " " + xMax + " " + yMin + " " + yMax;
	}

	private void Crop(Graphics2D g2d, BufferedImage textureInput, int cornerX, int cornerY, boolean isFlipped) {
		BufferedImage image = textureInput.getSubimage(cornerX, cornerY, xMax - xMin, yMax - yMin);
		if (isFlipped) {
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-image.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
		}
		g2d.drawImage(image, null, xMin, yMin);
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(xMin, yMin, xMax - xMin, yMax - yMin);
		Graphics2D g2d = (Graphics2D) g;
		if (!(texture == null)) {
			Crop(g2d, texture, xStart, yStart, isLeft);
		}

	}

	public boolean collides(Rectangle rect) {
		Rectangle hold = new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
		return hold.intersects(rect);

	}
	public boolean collides(Line2D line) {
		Rectangle hold = new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
		return hold.intersectsLine(line);

	}

}
