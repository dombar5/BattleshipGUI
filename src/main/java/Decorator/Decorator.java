/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Decorator;
import GameObjects.*;

/**
 *
 * @author Domantukas
 */
public abstract class Decorator extends Player {
    
    protected Player wrapee;
	
	public Decorator( Player component )
	{
		wrapee = component;
	}
	
	public void shoot( )
	{
		wrapee.Hit(true);
	}
}
