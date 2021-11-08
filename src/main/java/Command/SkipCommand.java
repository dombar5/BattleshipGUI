/**
 * @(#) SkipCommand.java
 */

package Command;

import GameObjects.Player;

public class SkipCommand extends ICommand
{
    public SkipCommand(Player player){
        super(player);
    }
    
    @Override
    public void execute() {
        player.Skip();
    }
}
