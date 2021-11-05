/**
 * @(#) MineBuilder.java
 */

package Builder;

public  class MineBuilder extends Builder
{
    public void addDamage( )
    {
        object.setDamage(1);
    }
	

    @Override
    public Builder assemble() {
        addDamage();
        return this;
    }

    @Override
    public void addColor(String color) {
        object.setColor(color);
    }
	
	
}
