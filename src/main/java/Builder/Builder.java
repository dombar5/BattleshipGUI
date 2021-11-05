/**
 * @(#) Builder.java
 */

package Builder;

public abstract class Builder
{
	protected Object object;
	
        public abstract Builder assemble( );
        public abstract void addColor(String color);
        
	public Object getBuildable( )
	{
		return this.object;
	}
	
	public Builder startNew(Object obj )
	{
            object = obj;
            return this;
	}
        

	
}
