package GameObjects;
import GameObjects.Ship;
import GameObjects.Island;
import java.util.ArrayList;
import java.util.List;

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
    public List<Mine> mines;
    public int mineMap = 0;
    public int mineDamage = 0;

    public Map(){
        ships = new Ship[5];
        ships[0] = new Ship('C', 5);
        ships[1] = new Ship('B', 4);
        ships[2] = new Ship('S', 3);
        ships[3] = new Ship('D', 3);
        ships[4] = new Ship('P', 2);
        mines = new ArrayList<>();
        island = new Island();
        mineMap = 3;
    }

    public void DamageShip(int shipNum, int damage){
        if(ships[shipNum].isAlive) ships[shipNum].TakeHit(damage);
    }
    
    public int CheckShip(int shipNum){
        return ships[shipNum].health;
    }
    
    public void setShipHealth(int shipNum, int newHealth){
        ships[shipNum].health = newHealth;
    }

    public void DamageIsland(int x, int y){
        if(island.isActive) island.TakeHit(x, y);
    }

    public int MineHitEnemy(int x, int y){
        for (int i = 0; i < mines.size(); i++) {
            if(mines.get(i).coordX==x && mines.get(i).coordY==y){
                mineDamage += mines.get(i).Explode();
            }
        }
        return mineDamage;
    }
    
    public void AddShipPart(char type, int x, int y){
        if(type=='C')
            ships[0].Add(x, y);
        if(type=='B')
            ships[1].Add(x, y);
        if(type=='S')
            ships[2].Add(x, y);
        if(type=='D')
            ships[3].Add(x, y);
        if(type=='P')
            ships[4].Add(x, y);
    }
  
}
