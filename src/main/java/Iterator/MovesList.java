/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iterator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Domantukas
 */
public class MovesList {
    
    private ArrayList<String> list = new ArrayList<String>();
    
    public MovesList(){
			
    }


    public void add(String s){
            list.add(s);
    }

    public void remove(String s){
            list.remove(s);
    }

    public ArrayList<String> getList(){
            return list;
    }

    public Iterator<String> getIterator(){
            return list.iterator();
    }
}
