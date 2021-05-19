package Resource.Scene;

import GameControl.Square;
import MainGame.MainGame;
import Settings.GAMEMODE;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
	 * LauncherScene定位
	 */
	static{
		// Launcher 界面绘制
		// flowPane 的基础设置
		FlowPane flowPane = new FlowPane();
		flowPane.setOrientation(Orientation.VERTICAL);
		flowPane.setAlignment(Pos.CENTER);
		flowPane.setPrefSize(1200, 800);
		flowPane.setVgap(100);
		flowPane.setStyle(
				" -fx-background-image: url(" + "file:src/Resource/Image/Useful/Launcher.jpg" + "); " +
						" -fx-background-size: 120%;");
		
		// Launcher界面的基础功能
		
		// 按钮1：开始默认游戏
		Button btn1 = new Button("开始游戏");
		btn1.setPrefSize(200, 80);
		btn1.setOnAction(event -> {
			createGameScene(GAMEMODE.PRIMARY);
			myScenes.primaryStage.setScene(MainGame.thisGame.mapScenes.get("PrimaryGameScene"));
		});
		// 按钮2：进入设置界面
		Button btn2 = new Button("启动设置");
		btn2.setPrefSize(200, 80);
		// 绑定基础功能
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
	// 构建游戏场景
	public static Scene createGameScene(GAMEMODE iMode){
		Scene GameScene = null;
		
		// 根据模式创建棋盘
		switch(iMode){
			case PRIMARY:
				MainGame.thisGame.getSetting().setMode(GAMEMODE.PRIMARY);
				// 创建游戏面板
				/**
				 * 按照位置关系大致为：
				 * 信息模块()  功能模块
				 * 雷区
				 */
				// 1. 雷区面板绘制
				GridPane BoomsPane = myScenes.createBoomsPane(9, 9);
				// 2. 信息面板
				FlowPane infoArea = new FlowPane();
				infoArea.setOrientation(Orientation.HORIZONTAL);
				// 临时用文本代替
				TextArea GameInfo = new TextArea();
				GameInfo.setPrefSize(40*MainGame.thisGame.getSetting().getAreaWidth(), 200);
				GameInfo.setText("未来的信息面板");
				infoArea.getChildren().add(GameInfo);
				
				// B. 控制区域
				
				// 3. 游戏控制面板
				FlowPane controlArea = new FlowPane();
				
				Button btnTest = new Button();
				btnTest.setText("未来的控制器");
				
				controlArea.getChildren().add(btnTest);
				
				// C. 构建游戏面板
				FlowPane playArea = new FlowPane();
				playArea.setOrientation(Orientation.HORIZONTAL);
				playArea.getChildren().addAll(infoArea, BoomsPane);
				
				// 游戏区域和控制面板加入到游戏面板
				FlowPane GamePane = new FlowPane();
				GamePane.setOrientation(Orientation.VERTICAL);
				GamePane.getChildren().addAll(playArea, controlArea);
				
				// 设置游戏场景
				GameScene = new Scene(GamePane);
				MainGame.thisGame.mapScenes.put("PrimaryGameScene", GameScene);
				
				return MainGame.thisGame.mapScenes.get("PrimaryGameScene");
			case MIDDLE:
				MainGame.thisGame.getSetting().setMode(GAMEMODE.MIDDLE);
				
				break;
			case HARD:
				break;
			case SELF:
				break;
		}
		return MainGame.thisGame.mapScenes.get("PrimaryGameScene");
	}
	
	public static GridPane createBoomsPane(int width, int height){
		GridPane BoomsPane = new GridPane();
		// 1.1 生成block
		Square[] Blocks = null;
		Blocks = new Square[width*height];
		for(int i = 0; i < width*height; i++){
			Blocks[i] = new Square(i + 1);
			BoomsPane.add(Blocks[i], (i)%width, (i)/width);
		}
		// 1.2 初始化游戏内容
		MainGame.thisGame.setBlockArea(Blocks);
		MainGame.thisGame.setBoomsNumber(10);
		MainGame.thisGame.setMaxWidth(9);
		MainGame.thisGame.setMaxHeight(9);
		return BoomsPane;
	}
}
