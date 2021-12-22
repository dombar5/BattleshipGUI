/**
 * @(#) Cannon.java
 */

package Visitor;

public class Cannon implements WeaponElement
{
    int damage = 2;
    @Override
    public int addDamage() {
        System.out.println("Cannon damage: " + damage);
        return damage;
    }
	
	
}
