package MainGame;

import Resource.Scene.myScenes;
import Resource.Scheme.Scheme;
import javafx.application.Application;
import javafx.stage.Stage;

public class gameStart extends Application{
	// 初始化一个默认设置的游戏
	public static Game thisGame;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		
		thisGame = new Game(Game.GAMEMODE.PRIMARY, Scheme.A);
		thisGame.stage = primaryStage;
		// 把初始界面加入Map
		thisGame.mapStages.put("primaryStage", primaryStage);
		// 显示登录窗口
		primaryStage.setTitle("Launcher");
		primaryStage.setResizable(false);
		primaryStage.setScene(myScenes.Launcher);
		primaryStage.show();
		
	}
}
