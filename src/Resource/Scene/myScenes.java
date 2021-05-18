package Resource.Scene;

import GameControl.Square;
import MainGame.MainGame;
import Settings.GAMEMODE;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class myScenes{
	// 静态成员
	public static Stage primaryStage = MainGame.thisGame.mapStages.get("primaryStage");
	/**
	 * Scenes
	 */
	// Setting
	public static Scene SettingScene;
	/**
	 * SettingScene 细节
	 */
	// Launcher
	public static Scene Launcher;
	public static Scene PrimaryGameScene = createGameScene(GAMEMODE.PRIMARY);
	
	/**
	 * LauncherScene细节
	 */
	static{
		FlowPane flowPane = new FlowPane();
		flowPane.setOrientation(Orientation.VERTICAL);
		flowPane.setPrefSize(1200, 800);
		flowPane.setVgap(100);
		Button btn1 = new Button("开始游戏");
		
		// Launcher界面的基础功能
		
		// 按钮1：开始默认游戏
		btn1.setOnAction(event -> {
			createGameScene(GAMEMODE.PRIMARY);
			myScenes.primaryStage.setScene(MainGame.thisGame.mapScenes.get("PrimaryGameScene"));
		});
		// 按钮2：进入设置界面
		Button btn2 = new Button("启动设置");
		
		btn2.setOnAction(event -> {
			myScenes.primaryStage.setScene(myScenes.SettingScene);
		});
		
		flowPane.getChildren().addAll(btn1, btn2);
		myScenes.Launcher = new Scene(flowPane);
		MainGame.thisGame.mapScenes.put("Launcher", Launcher);
	}
	
	/**
	 * 包含初始绘制的一些方法
	 */
	
	// 游戏界面构造方法
	public static Scene createGameScene(GAMEMODE iMode){
		Scene GameScene = null;
		switch(iMode){
			case PRIMARY:
				GridPane gridPane1 = new GridPane();
//				Button[] buttons = new Button[81];
				Square[] Blocks = new Square[81];
				for(int i = 0; i < 81; i++){
					Blocks[i] = new Square(i + 1);
					gridPane1.add(Blocks[i], (i)%9, (i)/9);
				}
				// 初始化游戏内容
				MainGame.thisGame.setBlockArea(Blocks);
				MainGame.thisGame.setBoomsNumber(10);
				MainGame.thisGame.setMaxWidth(9);
				MainGame.thisGame.setMaxHeight(9);
				// 构建游戏场景
				GameScene = new Scene(gridPane1);
				MainGame.thisGame.mapScenes.put("PrimaryGameScene", GameScene);
		}
		return GameScene;
	}
	
}
