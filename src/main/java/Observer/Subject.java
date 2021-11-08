/**
 * @(#) Subject.java
 */

package Observer;

import java.util.ArrayList;

public abstract class Subject
{
	private ArrayList<IObserver> playerList = new ArrayList<IObserver>();
	
	public void attach( IObserver player )
	{
		playerList.add(player);
	}
	
	public void detach( IObserver player )
	{
            for (IObserver Player : playerList) {
                if(Player.equals(player))
                    playerList.remove(player);
            }
	}
	
        
	public void notifyAll(String message)
	{
            for (IObserver Player : playerList) {
                    Player.update(message);
            }
	}
	
	
}
