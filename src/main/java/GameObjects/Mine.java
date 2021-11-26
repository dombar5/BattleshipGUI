package GameObjects;
import Strategy.*;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author auris233
 */
public class Mine extends Object {
    public int damage;
    public boolean isActive;
    public int coordX;
    public int coordY;
    
    private DamageAlgorithm damageAlgo;

    public Mine(int x, int y){
        coordX = x;
        coordY = y;
    }
    
    public int Explode(){
        isActive = false;
        return this.getDamageAlgo().setDamage();
    }
   
    public DamageAlgorithm getDamageAlgo( ) {
        return damageAlgo;
    }
    
    public void setDamageAlgo( DamageAlgorithm damageAlgo ) {
        this.damageAlgo = damageAlgo;
    }
}
