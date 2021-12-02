/**
 * @(#) Water2.java
 */

package FlyWeight;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Water2 implements Texture
{
        Image image1;
        ImageView view;
	
	public Water2(String fileName){
            image1 = new Image("file:water2.jpg");          	
	}
	
	public void draw(Button button)
	{  
            view  = new ImageView(image1);
            button.setGraphic(view);
	}

}
