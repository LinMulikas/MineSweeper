package resourse.Scene;

import MainGame.Containers;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Paint;

public class iScene extends Scene{
	
	public iScene(Parent root){
		super(root);
		Containers.Map_Scenes.put(this.getClass().getSimpleName(), this);
	}
	
	public iScene(Parent root, double width, double height){
		super(root, width, height);
		Containers.Map_Scenes.put(this.getClass().getSimpleName(), this);
	}
	
	public iScene(Parent root, Paint fill){
		super(root, fill);
		Containers.Map_Scenes.put(this.getClass().getSimpleName(), this);
	}
	
	public iScene(Parent root, double width, double height, Paint fill){
		super(root, width, height, fill);
		Containers.Map_Scenes.put(this.getClass().getSimpleName(), this);
	}
	
	public iScene(Parent root, double width, double height, boolean depthBuffer){
		super(root, width, height, depthBuffer);
		Containers.Map_Scenes.put(this.getClass().getSimpleName(), this);
	}
	
	public iScene(Parent root, double width, double height, boolean depthBuffer, SceneAntialiasing antiAliasing){
		super(root, width, height, depthBuffer, antiAliasing);
		Containers.Map_Scenes.put(this.getClass().getSimpleName(), this);
	}
}
