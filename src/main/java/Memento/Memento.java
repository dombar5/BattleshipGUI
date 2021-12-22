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
public class Memento {
    
    ArrayList<Integer> list;
    Integer mine;
    String id;

    public boolean getState(Originator org) {
        if(id.equalsIgnoreCase(org.getId())){
                org.setState(this.list, this.mine);
                return true;
        }
        return false;
    }


    public Memento(ArrayList<Integer> newList, Integer mineDmg, String newId) {
        list = newList;
        mine = mineDmg;
        id = newId;
    }

    private ArrayList<Integer> getStateShip(){
        return list;
    }
    
    private Integer getStateMine(){
        return mine;
    }
    
}
