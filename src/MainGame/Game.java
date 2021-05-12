package MainGame;

import Controller.ctrl_Launcher;
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
			// 加载 Launcher 界面的，动态读取界面
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resourse/Scene/Launcher.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene_launcher = new Scene(root, 600, 400);
			ctrl_Launcher iLauncher = (ctrl_Launcher) fxmlLoader.getController();
			iLauncher.setStage(primaryStage);
			iLauncher.setScene(scene_launcher);
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
