/**
 * @(#) LifesOk.java
 */

package Visitor;

public class LifesOk implements VisitorDamage
{


	@Override
	public int calculateDamage(Rifle weapon) {
		return weapon.addDamage();
		
	}

	@Override
	public int calculateDamage(Cannon weapon) {
		return weapon.addDamage();
	}

	@Override
	public int calculateDamage(Rocket weapon) {
		return weapon.addDamage();
	}
	
}
