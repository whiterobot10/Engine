package platformer;

import java.awt.geom.Line2D;

import javafx.scene.shape.Line;

public class Explosion extends DamageField {

	// public Explosion(int x1, int y1, int x2, int y2, int Damage, int
	// KnockBack, int KnockBackUp, double MaimDamage) {
	// super(x1, y1, x2, y2, Damage, KnockBack, KnockBackUp, MaimDamage);
	// }
	//
	// public Explosion(int x1, int y1, int x2, int y2, int Damage, int
	// KnockBack, int KnockBackUp) {
	// super(x1, y1, x2, y2, Damage, KnockBack, KnockBackUp);
	// }
	
	int range;

	public Explosion(int x, int y, int Damage, int MaimDamage, int Range) {
		super(x, y, x, y, Damage, 0, 0, MaimDamage);
		range = Range;
	}

	public void takeDamage(Entity e) {
		double dist;
		double newDist;
		Line2D pie;

		pie = new Line2D.Double(e.getRect().x, e.getRect().y, e.getRect().x + e.getRect().getWidth(), e.getRect().y);
		dist = pie.ptLineDist(xMin, yMin);
		pie = new Line2D.Double(e.getRect().x, e.getRect().y, e.getRect().x, e.getRect().y + e.getRect().height);
		newDist = pie.ptLineDist(xMin, yMin);
		if (newDist < dist) {
			dist = newDist;
		}
		pie = new Line2D.Double(e.getRect().x, e.getRect().y + e.getRect().height,
				e.getRect().x + e.getRect().getWidth(), e.getRect().y);
		newDist = pie.ptLineDist(xMin, yMin);
		if (newDist < dist) {
			dist = newDist;
		}
		pie = new Line2D.Double(e.getRect().x + e.getRect().width, e.getRect().y, e.getRect().x,
				e.getRect().y + e.getRect().height);
		newDist = pie.ptLineDist(xMin, yMin);
		if (newDist < dist) {
			dist = newDist;
		}
		
		double dealDamage = 0;
		double dealMaimDamage = 0;
		
		
		if (dist<=range){
			dealDamage=damage/4;
			dealMaimDamage= (maimDamage/4);
			if (dist<=range*3/4){
				dealDamage=damage/2;
				dealMaimDamage= (maimDamage/2);
				if (dist<=range/2){
					dealDamage=damage*3/4;
					dealMaimDamage= (maimDamage*3/4);
					if (dist<=range/4){
						dealDamage=damage;
						dealMaimDamage=maimDamage;
					}
				}

			}

		}	


		if (dealDamage > e.armor) {
			e.health -= dealDamage - e.armor;
			e.iframes = 8;
			if (dealMaimDamage > e.armor && e.canMaim) {
				e.maimAmount += dealMaimDamage - e.armor;
			}
			e.healTime = 0;
		}

	}

}
