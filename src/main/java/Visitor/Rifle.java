/**
 * @(#) Rifle.java
 */

package Visitor;

public class Rifle implements WeaponElement
{
    int damage = 1;
    @Override
    public int addDamage() {
        System.out.println("Rifle damage: " + damage);
        return damage;
    }

	
	
}
