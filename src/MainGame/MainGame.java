package MainGame;

import GameControl.Position;
import Resource.Scene.myScenes;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainGame extends Application{
	// 初始化一个默认设置的游戏
	public static GameContainers thisGame;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		// 输入本次游戏的名字，记录本次游戏关键信息（临时）
//		Scanner input = new Scanner(System.in);
//		System.out.println("请输入本次游戏的名字:");
//		String gameName = input.nextLine();
//		Containers iGame = new Containers(gameName);
		
		GameContainers iGame = new GameContainers("newGame");
		thisGame = iGame;
		
		// 把初始界面加入HashMap
		iGame.mapStages.put("primaryStage", primaryStage);
		
		// 显示登录窗口
		primaryStage.setTitle("Launcher");
		primaryStage.setScene(myScenes.Launcher);
		primaryStage.show();
		
	}
}
