package platformer;

public class DamageField extends Box {
	int damage = 0;
	int knockBack = 0;
	int knockBackUp = 0;
	double maimDamage;
	

	public DamageField(int x1, int y1, int x2, int y2, int Damage, int KnockBack, int KnockBackUp, double MaimDamage) {
		super(x1, y1, x2, y2);
		damage = Damage;
		knockBack = KnockBack;
		knockBackUp = KnockBackUp;
		maimDamage = MaimDamage;
		

	}
	
	public DamageField(int x1, int y1, int x2, int y2, int Damage, int KnockBack, int KnockBackUp) {
		super(x1, y1, x2, y2);
		damage = Damage;
		knockBack = KnockBack;
		knockBackUp = KnockBackUp;

		

	}
	
	public void takeDamage(Entity e) {
		if (collides(e.getRect()) && e.iframes == 0 && this.damage > e.armor) {
		e.health -= damage - e.armor;
		e.iframes = 8;
		e.xv = knockBack * (1 - e.knockBackResist);
		e.yv += knockBackUp * (1 - e.knockBackResist);
		if (maimDamage > e.armor && e.canMaim) {
			e.maimAmount += maimDamage - e.armor;
		}
		e.healTime = 0;
	}	
		
	}


}
