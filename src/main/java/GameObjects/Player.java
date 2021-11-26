package GameObjects;

import Observer.IObserver;
import Observer.Server;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author auris233
 */
public abstract class Player implements IObserver  {
    public boolean isFirst;
    public Map map;
    public String name;
    public int hits;
    public int misses;
    public String status;
    public int mineDamage;

    public Player(){
        map = new Map();
        hits = 0;
        misses = 0;
        mineDamage = 0;
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
        health -= getMineDamage();
        return health;
    }
    
    public void setMineDamage(int dmg){
        this.mineDamage = dmg;
    }
    
    public int getMineDamage(){
        return this.mineDamage;
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

    @Override
    public void update( String msg ) {
		System.out.println(this.name + " got message: " + msg);
	}
    

}
