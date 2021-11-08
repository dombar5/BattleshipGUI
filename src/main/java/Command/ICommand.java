/**
 * @(#) ICommand.java
 */

package Command;

import GameObjects.Player;

public abstract class ICommand
{
	public Player player;
        
        public ICommand(Player player){
            super();
            this.player = player;
        }
	
	public abstract void execute();
}
