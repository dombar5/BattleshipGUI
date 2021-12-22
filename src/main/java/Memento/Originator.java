/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Memento;

import java.util.ArrayList;

/**
 *
 * @author Domantukas
 */
public class Originator {
    
    ArrayList<Integer> shipHealthList = new ArrayList<Integer>();
    Integer mineDamage;
    String id;
    
    public Originator(ArrayList<Integer> newListShip, Integer mineDmg, String newId){

        shipHealthList = newListShip;
        mineDamage = mineDmg;
        id = newId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void restoreState(Memento memento){

        if(memento.getState(this)){
                System.out.println("Restore state successful");
        }else{
                System.out.println("Unable to restore state");
        }
    }

    public ArrayList<Integer> getList(){
        return shipHealthList;
    }

    public void setState(ArrayList<Integer> newListShip, Integer mineDmg){
        shipHealthList = newListShip;
        mineDamage = mineDmg;
    }

    public Memento saveState(){
        return new Memento(shipHealthList, mineDamage, id);
    }
}
