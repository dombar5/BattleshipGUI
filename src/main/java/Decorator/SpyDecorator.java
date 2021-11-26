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
public abstract class SpyDecorator extends Decorator {
    
    public SpyDecorator(Player component) {
		super(component);
	}

	public void Bombard( )
	{
		
	}
	
	public void Hit( )
	{
		Bombard();
	}
}
