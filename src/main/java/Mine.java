/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author auris233
 */
public class Mine {
    public int damage;
    public boolean isActive;
    public int coordX;
    public int coordY;

    public Mine(int dmg, int x, int y){
        damage = dmg;
        isActive = true;
        coordX = x;
        coordY = y;
    }
    
    public void Explode(){
        isActive = false;
    }
}
