/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author auris233
 */
public class Map {
    public Island island;
    public Ship[] ships;
    public Mine[] mines;
    public int mineMap = 0;

    public Map(Island isl, Ship[] sh, Mine[] mn){
        island = isl;
        ships = sh;
        mines = mn;
        mineMap = 3;
    }

    public void DamageShip(int shipNum, int damage){
        if(ships[shipNum].isAlive) ships[shipNum].TakeHit(damage);
    }
    
    public int CheckShip(int shipNum){
        return ships[shipNum].health;
    }

    public void DamageIsland(int x, int y){
        if(island.isActive) island.TakeHit(x, y);
    }

    public void MineHit(int x, int y){
        for (int i = 0; i < mines.length; i++) {
            if(mines[i].coordX==x && mines[i].coordY==y){
                mines[i].Explode();
            }
        }

    }
  
}
