package MainGame;

import Resource.Scene.SceneCreater;
import Settings.GAMEMODE;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application{
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		Containers.mapStages.put("primaryStage", primaryStage);
		
		// 1. 加载Scene
		SceneCreater.createBasicScene();
		Scene LauncherScene = Containers.mapScenes.get("LauncherScene");
		SceneCreater.createGameScene(GAMEMODE.PRIMARY);
		Scene Game = Containers.mapScenes.get("Game");
//		Scene SettingScene = Containers.mapScenes.get("Setting");
		
		// 显示登录窗口
		primaryStage.setTitle("Game");
		primaryStage.setScene(Game);
		primaryStage.show();
		
	}
}
