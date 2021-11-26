/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategy;

/**
 *
 * @author Domantukas
 */
public class MediumDamage extends DamageAlgorithm {
    
    private int damage = 2;
    @Override
	public int setDamage() {
		return damage;
	}
}
