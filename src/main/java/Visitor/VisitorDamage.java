/**
 * @(#) VisitorDamage.java
 */

package Visitor;

public interface VisitorDamage
{
        public int calculateDamage(Rifle weapon);
	public int calculateDamage(Cannon weapon);
	public int calculateDamage(Rocket weapon);
	
	
}
