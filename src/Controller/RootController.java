package Controller;

import MainGame.Containers;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RootController{
	private Scene scene;
	private Stage stage;
	
	public RootController(){
		Containers.Map_Controllers.put(this.getClass().getSimpleName(), this);
	}
	
	public void setStage(Stage stage){
		this.stage = stage;
	}
	
	public void setScene(Scene scene){
		this.scene = scene;
	}
}
