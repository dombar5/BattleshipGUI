/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FlyWeight;

import java.util.Hashtable;

/**
 *
 * @author auris233
 */
public class TextureFactory {
    

	private final static Hashtable<String, Texture> hash = new Hashtable<String, Texture>();

	

	
	public static Texture getTexture(String TextureType){
		
		Texture p = hash.get(TextureType);
		
		if(p == null){
			if(TextureType.equals("file:water1.jpg")){
				p = new Water1(TextureType);
			}else
			if(TextureType.equals("file:water2.jpg")){
				p = new Water2(TextureType);
			}else
			if(TextureType.equals("file:dirt.jpg")){
				p = new Dirt(TextureType);	
			}
			
			hash.put(TextureType, p);
		}
		
		return p;
		
	}
	

}
