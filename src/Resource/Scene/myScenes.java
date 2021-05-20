package Resource.Scene;

import GameControl.Square;
import MainGame.Game;
import MainGame.gameStart;
import Resource.Scheme.Scheme;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
		 * 难度选择面板
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
		// 组合成游戏选择面板
		FlowPane gameModePane = new FlowPane();
		gameModePane.getChildren().addAll(labelGameMode, hBoxGameMode);
		
		/**
		 * Scheme 选择面板
		 */
		Label schemeChoose = new Label("Choose your scheme");
		ToggleGroup groupSchemeChoose = new ToggleGroup();
		RadioButton btnSchemeA = new RadioButton("Scheme A");
		btnSchemeA.setOnAction(event -> {
			if(btnSchemeA.isSelected()){
				Square[] blocks = gameStart.thisGame.getBlocks();
				for(int i = 0; i < blocks.length; i++){
					blocks[i].setView(Scheme.A);
				}
			}
		});
		RadioButton btnSchemeB = new RadioButton("Scheme B");
		btnSchemeB.setOnAction(event -> {
			if(btnSchemeB.isSelected()){
				Square[] blocks = gameStart.thisGame.getBlocks();
				for(int i = 0; i < blocks.length; i++){
					blocks[i].setView(Scheme.B);
				}
			}
		});
		RadioButton btnSchemeC = new RadioButton("Scheme C");
		btnSchemeC.setOnAction(event -> {
			if(btnSchemeC.isSelected()){
				Square[] blocks = gameStart.thisGame.getBlocks();
				for(int i = 0; i < blocks.length; i++){
					blocks[i].setView(Scheme.C);
				}
			}
		});
		
		vBoxSetting.getChildren().addAll(gameModePane);
		SettingScene = new Scene(vBoxSetting);
	}
	
	/**
	 * LauncherScene 细节
	 * LauncherScene 定位器
	 */
	static{
		// Launcher 界面绘制
		// flowPane 的基础设置
		// 菜单栏
		
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
		Button btn1 = new Button("游戏测试");
		btn1.setPrefSize(200, 80);
		btn1.setOnAction(event -> {
			createGameScene();
			primaryStage.setTitle("游戏测试");
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
	// 构建总体游戏场景
	public static Scene createGameScene(){
		int width = gameStart.thisGame.getWidth();
		int height = gameStart.thisGame.getHeight();
		
		// 菜单栏
		MenuBar menuBarGame = new MenuBar();
		menuBarGame.setPrefSize(gameStart.thisGame.getWidth(), 20);
		// 1. "Game" 菜单
		Menu menuGame = new Menu("Game");
		// 1.1 NewGame item
		MenuItem itemNewGame = new MenuItem("New Game");
		// 绑定功能
		
		// 绑定到 Game
		menuGame.getItems().add(itemNewGame);
		
		// 1.2 ReStart 选项
		MenuItem itemRestart = new MenuItem("Restart");
		
		// 绑定到 Game
		menuGame.getItems().add(itemRestart);
		
		// 2. "Scheme" 菜单
		Menu menuScheme = new Menu("Scheme");
		
		ToggleGroup schemeChoose = new ToggleGroup();
		RadioMenuItem itemA = new RadioMenuItem("Scheme A");
		itemA.setOnAction(event -> {
			if(itemA.isSelected()){
				gameStart.thisGame.setBlocksScheme(Scheme.A);
			}
		});
		RadioMenuItem itemB = new RadioMenuItem("Scheme B");
		itemB.setOnAction(event -> {
			if(itemB.isSelected()){
				gameStart.thisGame.setBlocksScheme(Scheme.B);
			}
		});
		RadioMenuItem itemC = new RadioMenuItem("Scheme C");
		itemC.setOnAction(event -> {
			if(itemC.isSelected()){
				gameStart.thisGame.setBlocksScheme(Scheme.C);
			}
		});
		schemeChoose.getToggles().addAll(itemA, itemB, itemC);
		
		menuScheme.getItems().addAll(itemA, itemB, itemC);
		
		// 3. "GUI" 菜单
		Menu menuGUI = new Menu("GUI");
		
		menuBarGame.getMenus().addAll(menuGame, menuScheme, menuGUI);
		
		Scene GameScene = null;
		// 创建默认初级游戏
		// 创建游戏面板
		/**
		 * 按照位置关系大致为：
		 * playArea controlArea
		 *  信息面板
		 *          功能区域
		 *  游戏面板
		 */
		
		// 游戏区域
		VBox vbInfoAndBooms = new VBox(10);
		
		// 游戏区域 - 信息面板
		FlowPane infoArea = new FlowPane();
		infoArea.setOrientation(Orientation.HORIZONTAL);
		
		// 游戏区域 - 信息面板
		// 临时用文本代替
		TextArea txtAreaInfo = new TextArea();
		txtAreaInfo.setPrefSize(40*gameStart.thisGame.getWidth(), 200);
		txtAreaInfo.setText("未来的信息面板");
		
		infoArea.getChildren().add(txtAreaInfo);
		
		// 游戏区域 - 雷区面板
		GridPane BoomsPane = myScenes.createBoomsPane(width, height);
		// 雷区视觉调控
		
		// 游戏区域 - 闲话面板
		Pane mulikas = new Pane();
		mulikas.setPrefSize(40*gameStart.thisGame.getWidth(), 100);
		Label labMulikas = new Label("Mulikas:感谢游玩");
		labMulikas.setStyle("-fx-border-style:solid;"
		);
		labMulikas.setPrefSize(40*gameStart.thisGame.getWidth(), 50);
		labMulikas.setAlignment(Pos.CENTER);
		
		mulikas.getChildren().add(labMulikas);
		
		// 加入信息面板、雷区面板
		vbInfoAndBooms.setAlignment(Pos.CENTER);
		vbInfoAndBooms.getChildren().addAll(infoArea, BoomsPane, mulikas);
		// 游戏区域 - END
		
		// 控制区域
		// 控制区域 - 控制面板
		FlowPane controlArea = new FlowPane();
		
		Button btnTest = new Button();
		btnTest.setText("未来的控制器");
		controlArea.getChildren().add(btnTest);
		
		// 菜单栏、游戏区域、控制区域加入到游戏面板
		
		FlowPane flowGamePane = new FlowPane();
		flowGamePane.setOrientation(Orientation.VERTICAL);
		flowGamePane.getChildren().addAll(vbInfoAndBooms, controlArea);
		
		// 设置游戏场景
		
		VBox gamePane = new VBox();
		gamePane.getChildren().addAll(menuBarGame, flowGamePane);
		
		GameScene = new Scene(gamePane);
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
		
		
		Square[] Blocks = null;
		Blocks = new Square[width*height];
		for(int i = 0; i < width*height; i++){
			Blocks[i] = new Square(i + 1);
//			System.out.printf("以%s创建了block\n", gameStart.thisGame.getScheme().toString());
			boomsPane.add(Blocks[i], (i)%width, (i)/width);
		}
		
		gameStart.thisGame.setBlocks(Blocks);
		gameStart.thisGame.setBlocksScheme(gameStart.thisGame.getScheme());
//		gameStart.thisGame.setBlocks();
//		gameStart.thisGame.setBoomsNumber(10);
		return boomsPane;
	}
}
