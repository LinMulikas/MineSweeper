package MainGame;

import GameControl.myScenes;
import Resource.Scheme.Scheme;
import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;

public class gameStart extends Application{
	// 初始化一个默认设置的游戏
	public static Game thisGame;
	
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
		}
		catch(UnsupportedLookAndFeelException e){
			e.printStackTrace();
		}
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		
		thisGame = new Game(Game.GAMEMODE.PRIMARY, Scheme.B);
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
