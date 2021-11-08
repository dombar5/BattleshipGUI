package GameObjects;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author auris233
 */
public class Player  {
    public boolean isFirst;
    public Map map;
    public String name;
    public int hits;
    public int misses;
    public String status;

    public Player(){
        map = new Map();
        hits = 0;
        misses = 0;
    }

    public void Hit(boolean shot){
        if(shot) hits++;
        else misses++;
    }
    
    public int GetHealth(){
        int health = 0;
        for (int i = 0; i < 5; i++) {
          health += map.CheckShip(i);
        }
        return health;
    }
    

    
    
    public void Shoot(String data){
       status = data; 
    }
    
    public void Skip(){
        status = "skip";
    }
    
    public void Surrender(){
        status = "surrender";
    }
    

}
