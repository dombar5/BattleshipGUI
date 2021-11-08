/**
 * @(#) SurrenderCommand.java
 */

package Command;

import GameObjects.Player;

public class SurrenderCommand extends ICommand
{
    public SurrenderCommand(Player player){
        super(player);
    }

    @Override
    public void execute() {
        player.Surrender();
    }

}
