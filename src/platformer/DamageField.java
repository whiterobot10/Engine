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


}
