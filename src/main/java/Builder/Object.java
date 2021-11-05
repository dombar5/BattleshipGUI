/**
 * @(#) Object.java
 */

package Builder;

public abstract class Object
{
    private String color;
    private int size;
    private int damage;
    private int health;
    
    public Object(){
        
    }
    
    public void getConfiguration( )
    {
	String config = color +  " " + size + " " + damage + " "+ health;
        System.out.println(config);
    }
    
    public void setColor(String color){
        this.color = color;
    }
    
    public void setSize(int size){
        this.size = size;
    }
        
    public void setDamage(int damage){
        this.damage = damage;
    }
    
    public void setHealth(int health){
        this.health = health;
    }
	
}
