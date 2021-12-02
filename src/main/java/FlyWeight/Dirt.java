/**
 * @(#) Dirt.java
 */

package FlyWeight;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Dirt implements Texture
{
        Image image1;
        ImageView view;
	
	public Dirt(String fileName){
            image1 = new Image("file:dirt.jpg");	
	}
        

	public void draw(Button button)
	{  
            view  = new ImageView(image1);
            button.setGraphic(view);
	}


	
	
}
