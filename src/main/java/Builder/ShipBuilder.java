/**
 * @(#) ShipBuilder.java
 */

package Builder;

public class ShipBuilder extends Builder
{
    
    public void addHealth( )
    {
	object.setHealth(100);
    }
	

    @Override
    public Builder assemble() {
        addHealth();
        return this;
    }

    @Override
    public void addColor(String color) {
       object.setColor(color);
    }
	
	
}
