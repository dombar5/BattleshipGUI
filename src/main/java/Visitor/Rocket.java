/**
 * @(#) Rocket.java
 */

package Visitor;

public class Rocket implements WeaponElement
{
    
        
    int damage = 3;
    @Override
    public int addDamage() {
        System.out.println("Cannon damage: " + damage);
        return damage;
    }
	
	
}
