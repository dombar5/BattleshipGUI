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
public class HealthList {
    
    private ArrayList<Integer> list = new ArrayList<Integer>();
    
    public HealthList(){
			
    }


    public void add(Integer s){
            list.add(s);
    }

    public void remove(Integer s){
            list.remove(s);
    }

    public ArrayList<Integer> getList(){
            return list;
    }

    public Iterator<Integer> getIterator(){
            return list.iterator();
    }
}
