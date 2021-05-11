package MainGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Game extends Application{
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		try{
			// 加载Launcher.fxml中绘制的界面
			Parent root = FXMLLoader.load(getClass().getResource("Launcher.fxml"));
			Scene scene_launcher = new Scene(root);
			primaryStage.setTitle("Launcher");
			primaryStage.setScene(scene_launcher);
			primaryStage.show();
		}
		catch(IOException e){
			System.out.println("未成功建立scene_launcher");
			e.printStackTrace();
		}
		
	}
}
