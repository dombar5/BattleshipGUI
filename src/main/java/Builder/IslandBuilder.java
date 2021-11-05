/**
 * @(#) IslandBuilder.java
 */

package Builder;

public class IslandBuilder extends Builder
{
 
    public void addSize() {
     object.setSize(4);
    }


    @Override
    public void addColor(String color) {
       object.setColor(color);
    }

    @Override
    public Builder assemble() {
        addSize();
        return this;
    }
	
	
}
