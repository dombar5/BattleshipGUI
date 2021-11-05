package GameObjects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author auris233
 */
public class Ship extends Object {
    public char type;
    public int health;
    public boolean isAlive;
    public int[] coordX;
    public int[] coordY;
    public int counter = 0;

    public Ship(char Type, int size){
        type = Type;
        coordX = new int[size];
        coordY = new int[size];
        health = size;
        isAlive = true;
    }
  
    public void TakeHit(int damage){
        health-= damage;
        if(health<=0)
            isAlive = false;
    }

    public void Add(int x, int y){
        coordX[counter] = x;
        coordY[counter] = y;
        counter++;
    }
    
 
  


}
