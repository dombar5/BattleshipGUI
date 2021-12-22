/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Memento;
import java.util.*;

/**
 *
 * @author Domantukas
 */
public class Caretaker {
    
    ArrayList<Memento> statesList;
	
    public Caretaker(){
        statesList = new ArrayList<Memento>();
    }

    public void add(Memento state){
        statesList.add(state);
    }

    public Memento get(int index){
        Memento restoreState = statesList.get(index);
        statesList.remove(index);
        return restoreState;
    }	

    public int size(){
        return statesList.size();
    }
}
