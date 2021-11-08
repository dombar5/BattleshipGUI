/**
 * @(#) ShootCommand.java
 */

package Command;

import GameObjects.Player;

public class ShootCommand extends ICommand
{
    
    public ShootCommand(Player player){
        super(player);   
    }

    @Override
    public void execute() {
        player.Shoot("shoot");
    }
}
