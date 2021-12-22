package GameObjects;

import Observer.IObserver;
import Observer.Server;
import Iterator.*;
import Memento.*;
import java.util.ArrayList;


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
    private ArrayList<Integer> healthList;
    private ArrayList<Integer> mineHealthList;
    public Originator org = new Originator(healthList, mineDamage, "111");
    public Caretaker ct = new Caretaker();

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
    
    public void Undo(){
        Memento restoreState = ct.get( ct.size() - 1 );
        org.restoreState(restoreState);
        int i = 0;
        for (Integer e : org.getList()) {
            map.setShipHealth(i, e);
            i++;
        }
        for (int a = 0; a < 5; a++) {
         System.out.println(map.CheckShip(a) + " ");
        }
    }
    
    public void saveHealth(){
        healthList = new ArrayList<Integer>(); 
        for (int i = 0; i < 5; i++) {
          healthList.add(map.CheckShip(i));
        }
        org.setState(healthList, this.mineDamage);
        Memento state = org.saveState();
        ct.add(state);
    }

    @Override
    public void update( String msg ) {
		System.out.println(this.name + " got message: " + msg);
	}
    

}
