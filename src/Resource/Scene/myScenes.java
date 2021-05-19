package Resource.Scene;

import GameControl.Square;
import MainGame.Game;
import MainGame.gameStart;
import Resource.Scheme.Scheme;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class myScenes{
	// 静态成员
	public static Stage primaryStage = gameStart.thisGame.mapStages.get("primaryStage");
	/**
	 * Scenes
	 */
	// Setting
	public static Scene SettingScene;
	// Launcher
	public static Scene Launcher;
	
	/**
	 * SettingScene 细节
	 */
	static{
		VBox vBoxSetting = new VBox();
		/**
		 * 绘制难度选择
		 */
		// 创建一组单选框
		Label labelGameMode = new Label("请选择游戏难度：");
		// 创建对应游戏标签的容器
		HBox hBoxGameMode = new HBox();
		// 创建一个组容纳游戏难度调整的按钮
		ToggleGroup groupGameMode = new ToggleGroup();
		// 初级按钮的功能
		RadioButton btnPrimary = new RadioButton("Primary");
		btnPrimary.setOnAction(event -> {
			if(btnPrimary.isSelected()){
				gameStart.thisGame.setGameMode(Game.GAMEMODE.PRIMARY);
			}
		});
		
		btnPrimary.setToggleGroup(groupGameMode);
		RadioButton btnMiddle = new RadioButton("Middle");
		btnMiddle.setOnAction(event -> {
			if(btnPrimary.isSelected()){
				gameStart.thisGame.setGameMode(Game.GAMEMODE.PRIMARY);
			}
		});
		
		btnMiddle.setToggleGroup(groupGameMode);
		RadioButton btnHard = new RadioButton("Hard");
		btnHard.setOnAction(event -> {
			if(btnPrimary.isSelected()){
				gameStart.thisGame.setGameMode(Game.GAMEMODE.PRIMARY);
			}
		});
		
		btnHard.setToggleGroup(groupGameMode);
		
		RadioButton btnSelf = new RadioButton("Self-Design");
		btnSelf.setOnAction(event -> {
			if(btnSelf.isSelected()){
				System.out.println("显示定制模式");
			}
		});
		btnSelf.setToggleGroup(groupGameMode);
		hBoxGameMode.getChildren().addAll(btnPrimary, btnMiddle, btnHard, btnSelf);
		
		vBoxSetting.getChildren().addAll(labelGameMode, hBoxGameMode);
		
		SettingScene = new Scene(vBoxSetting);
	}
	
	/**
	 * LauncherScene 细节
	 * LauncherScene 定位器
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
		Button btn1 = new Button("新建游戏");
		btn1.setPrefSize(200, 80);
		btn1.setOnAction(event -> {
			createGameScene();
			primaryStage.setTitle("游戏设置");
//			primaryStage.setScene(SettingScene);
			primaryStage.setScene(gameStart.thisGame.mapScenes.get("GameScene"));
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
		gameStart.thisGame.mapScenes.put("Launcher", Launcher);
	}
	
	/**
	 * 包含初始绘制的一些方法
	 */
	
	// 游戏界面构造方法
	// 构建游戏场景
	public static Scene createGameScene(){
		int width = gameStart.thisGame.getWidth();
		int height = gameStart.thisGame.getHeight();
		
		Scene GameScene = null;
		// 创建默认初级游戏
		// 创建游戏面板
		/**
		 * 按照位置关系大致为：
		 * 信息模块()  功能模块
		 * 雷区
		 */
		
		// 游戏面板 = 雷区面板+信息面板
		//====================雷区面板=======================
		GridPane BoomsPane = myScenes.createBoomsPane(width, height);
		
		//====================信息面板=======================
		FlowPane infoArea = new FlowPane();
		infoArea.setOrientation(Orientation.HORIZONTAL);
		// 临时用文本代替
		TextArea GameInfo = new TextArea();
		GameInfo.setPrefSize(40*gameStart.thisGame.getWidth(), 200);
		GameInfo.setText("未来的信息面板");
		infoArea.getChildren().add(GameInfo);
		
		// B. 控制区域
		
		//=====================控制面板=========================
		FlowPane controlArea = new FlowPane();
		
		Button btnTest = new Button();
		btnTest.setText("未来的控制器");
		
		controlArea.getChildren().add(btnTest);
		
		//======================游戏面板=======================
		FlowPane playArea = new FlowPane();
		playArea.setOrientation(Orientation.HORIZONTAL);
		playArea.getChildren().addAll(infoArea, BoomsPane);
		
		// 游戏区域和控制面板加入到游戏面板
		FlowPane GamePane = new FlowPane();
		GamePane.setOrientation(Orientation.VERTICAL);
		GamePane.getChildren().addAll(playArea, controlArea);
		
		// 设置游戏场景
		GameScene = new Scene(GamePane);
		gameStart.thisGame.mapScenes.put("GameScene", GameScene);
		
		return gameStart.thisGame.mapScenes.get("GameScene");
	}
	
	/**
	 * 搭建游戏雷区场景，并把场景按钮数组关联到 thisGame.BlockArea
	 *
	 * @param width
	 * @param height
	 * @return
	 */
	public static GridPane createBoomsPane(int width, int height){
		GridPane boomsPane = new GridPane();
		
		// 雷区主题修正
		if(gameStart.thisGame.getScheme().equals(Scheme.B)){
			boomsPane.setVgap(0);
			boomsPane.setHgap(0);
		}
		
		Square[] Blocks = null;
		Blocks = new Square[width*height];
		for(int i = 0; i < width*height; i++){
			Blocks[i] = new Square(i + 1);
			boomsPane.add(Blocks[i], (i)%width, (i)/width);
		}
		gameStart.thisGame.setBlocks(Blocks);
		gameStart.thisGame.setBoomsNumber(10);
		return boomsPane;
	}
}
