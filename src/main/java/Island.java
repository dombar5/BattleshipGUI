/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author auris233
 */
public class Island {
    public int health = 0;
    public boolean isActive;
    public int[] coordX;
    public int[] coordY;
    public int counter = 0;

    public Island(){
        isActive = true;
        coordX = new int[4];
        coordY = new int[4];
        health = 3;
        counter = 0;
    }

    public void TakeHit(int x, int y){
        if(isActive){
            for (int i = 0; i <=health; i++) {
                if(coordX[i]==x && coordY[i]==y){
                    health--;
                }
            }
        }
        if(health<0)
            isActive = false;
               
    }
    
    public void Add(int x, int y){
        coordX[counter] = x;
        coordY[counter] = y;
        counter++;
    }
}
