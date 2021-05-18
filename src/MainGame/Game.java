package MainGame;

import GameControl.Position;
import Resource.Scene.myScenes;
import Settings.GameSetting;
import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application{
	
	public static Containers thisGame;
	public static GameSetting thisSetting = GameSetting.DefaultSetting;
	
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
		
		Containers iGame = new Containers("newGame");
		thisGame = iGame;
		
		// 把初始界面加入HashMap
		iGame.mapStages.put("primaryStage", primaryStage);
		
		// 加入初始设置GameSettings
		thisGame.setSetting(thisSetting);
		
		// 显示登录窗口
		Position p1 = new Position(2, 9);
		System.out.println(p1.toId());
		primaryStage.setTitle("Launcher");
		primaryStage.setScene(myScenes.Launcher);
		primaryStage.show();
		
	}
}
