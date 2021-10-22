/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author auris233
 */
public class Ship {
    public char type;
    public int health;
    public boolean isAlive;
    public int[] coordX;
    public int[] coordY;
    public int counter = 0;

    public Ship(char Type){
        type = Type;
        isAlive = true;
        if(type=='C'){
            coordX = new int[5];
            coordY = new int[5];
        }
        if(type=='B'){
            coordX = new int[4];
            coordY = new int[4];
        }
        if(type=='S' || type=='D'){
            coordX = new int[3];
            coordY = new int[3];
        }
        if(type=='P'){
            coordX = new int[2];
            coordY = new int[2];
        }
            
    }
  
    public void TakeHit(int damage){
        health-= damage;
        if(health<=0)
            isAlive = false;
    }

    public void Add(int x, int y){
        coordX[counter] = x;
        coordY[counter] = y;
        health++;
        counter++;
    }
    
 
  


}
