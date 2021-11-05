package GameObjects;

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
public class Island extends Object {
    public int health = 0;
    public boolean isActive;
    public List<Integer> coordX;
    public List<Integer> coordY;
    public int counter = 0;

    public Island(){
        isActive = true;
        coordX = new ArrayList<>();
        coordY = new ArrayList<>();
        health = 3;
        counter = 0;
    }

    public void TakeHit(int x, int y){
        if(isActive){
            for (int i = 0; i <=health; i++) {
                if(coordX.get(i)==x && coordY.get(i)==y){
                    health--;
                }
            }
        }
        if(health<0)
            isActive = false;
               
    }
    
    public void Add(int x, int y){
        coordX.add(x);
        coordY.add(y);
    }
}
