package GameControl;

import MainGame.Game;
import MainGame.gameStart;
import Resource.Scheme.Scheme;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public class myScenes{
	// 静态成员
	public static Stage primaryStage = gameStart.thisGame.mapStages.get("primaryStage");
	public static Map<String, Node> myNodes = new HashMap<String, Node>();

	/**
	 * Scenes
	 */
	// Setting
	public static Scene SettingScene;
	// Launcher
	public static Scene Launcher;
	// Winner
	public static Scene Winner;
	// GameLoader
	public static Scene LoadGame;
	public static Scene LoadRecord;
	// Rank
	public static Scene Rank;

	/**
	 * Rank 定位
	 */

	static{
		createRecordLoadScene();
		LoadRecord = gameStart.thisGame.mapScenes.get("LoadRecord");
	}

	// 初始化Rank Scene
	static{
		createRankScene();
		Rank = gameStart.thisGame.mapScenes.get("Rank");
	}

	// GameLoader Scene
	static{
		createGameLoadScene();
		LoadGame = gameStart.thisGame.mapScenes.get("LoadGame");
	}

	// Winner 定位
	static{
		createWinnerScene();
		Winner = gameStart.thisGame.mapScenes.get("Winner");
	}

	static{
		createSettingScene();
		SettingScene = gameStart.thisGame.mapScenes.get("SettingScene");
	}

	/**
	 * LoadGameScene
	 */
	static{
		createGameLoadScene();
		LoadGame = gameStart.thisGame.mapScenes.get("LoadGame");
	}

	/**
	 * LauncherScene 细节
	 * LauncherScene 定位器
	 */
	static{
		createLauncherScene();
		Launcher = gameStart.thisGame.mapScenes.get("Launcher");
	}

	public static void createWinnerScene(){

		FlowPane flWinner = new FlowPane();
		flWinner.setPrefSize(1200, 800);
		Text txtWinner = new Text("Test");
		gameStart.thisGame.setTxtWinner(txtWinner);
		txtWinner.setFont(Font.font(24));

		Button btnSave = new Button("保存分数");
		btnSave.setOnAction(event -> {
			// 保存分数
			gameStart.thisGame.saveScore();
		});
		btnSave.setPrefSize(120, 40);

		Button btnMain = new Button("返回菜单");
		btnMain.setOnAction(event -> {
			primaryStage.setTitle("主菜单");
			primaryStage.setScene(Launcher);
		});
		btnMain.setPrefSize(120, 40);

		flWinner.setOrientation(Orientation.VERTICAL);
		flWinner.setAlignment(Pos.CENTER);
		flWinner.setVgap(120);
		flWinner.getChildren().addAll(txtWinner, btnSave, btnMain);
		Winner = new Scene(flWinner);
		gameStart.thisGame.mapScenes.put("Winner", Winner);

	}

	/**
	 * SettingScene 细节
	 * SettingScene 定位
	 */

	public static void createSettingScene(){
		Scene settingScene;
		/**
		 * 游戏名称
		 */
		Label labGameName = new Label("请输入游戏名称：");
		TextField txtGameName = new TextField();
		HBox hboxGameName = new HBox();
		txtGameName.setMaxWidth(200);
		Button btnSetName = new Button("确定名称");
		btnSetName.setOnAction(event -> {
			if(txtGameName.getText().equals("")){
				Stage gameNameWarning = new Stage();
				gameNameWarning.setTitle("警告");
				FlowPane flWarning = new FlowPane();
				flWarning.setPrefSize(300, 200);
				flWarning.setAlignment(Pos.CENTER);
				Text txtWarning = new Text("游戏名称为空，不合法!");
				flWarning.getChildren().addAll(txtWarning);
				gameNameWarning.setScene(new Scene(flWarning));
				gameNameWarning.setResizable(false);
				gameNameWarning.show();
			} else{
				gameStart.thisGame.setName(txtGameName.getText());
			}
		});

		hboxGameName.getChildren().addAll(txtGameName, btnSetName);
		VBox vboxGameName = new VBox();

		vboxGameName.getChildren().addAll(labGameName, hboxGameName);

		/**
		 * 难度选择面板
		 */
		// 创建一组单选框
		Label labelGameMode = new Label("请选择游戏难度：");
		// 创建对应游戏标签的容器
		HBox hboxGameMode = new HBox();
		hboxGameMode.setSpacing(60);

//		hBoxGameMode.setAlignment(Pos.TOP_CENTER);
		GridPane gridSelf = new GridPane();
		Label labelWidth = new Label("Width:");
		Label labelHeight = new Label("Height:");
		Label labelBooms = new Label("BoomsNumber:");

		TextField txtWidth = new TextField();
		TextField txtHeight = new TextField();
		TextField txtBooms = new TextField();

		txtWidth.setTextFormatter(new TextFormatter<String>(new UnaryOperator<TextFormatter.Change>(){
			@Override
			public TextFormatter.Change apply(TextFormatter.Change change){
				String value = change.getText();
				if(value.matches("[0-9]*")){
					return change;
				}
				return null;
			}
		}));

		txtHeight.setTextFormatter(new TextFormatter<String>(new UnaryOperator<TextFormatter.Change>(){
			@Override
			public TextFormatter.Change apply(TextFormatter.Change change){
				String value = change.getText();
				if(value.matches("[0-9]*")){
					return change;
				}
				return null;
			}
		}));

		txtBooms.setTextFormatter(new TextFormatter<String>(new UnaryOperator<TextFormatter.Change>(){
			@Override
			public TextFormatter.Change apply(TextFormatter.Change change){
				String value = change.getText();
				if(value.matches("[0-9]*")){
					return change;
				}
				return null;
			}
		}));

		Button checkSelf = new Button("采用设置");
		checkSelf.setOnAction(event -> {
			gameStart.thisGame.setGameMode(Game.GAMEMODE.SELF);
			int width = 0, height = 0, booms = 0;
			try{
				width = Integer.parseInt(txtWidth.getText());
				height = Integer.parseInt(txtHeight.getText());
				booms = Integer.parseInt(txtBooms.getText());
				if((width >= 1) && (width <= 30)){
					if((height >= 1) && (height <= 24)){
						if((booms >= 1) && (booms <= (int) (width*height*0.5))){
							gameStart.thisGame.setWidth(width);
							gameStart.thisGame.setHeight(height);
							gameStart.thisGame.setBoomsNumber(booms);
							gameStart.thisGame.setGameMode(Game.GAMEMODE.SELF);
//							System.out.println(width);
//							System.out.println(height);
//							System.out.println(booms);
						} else{
							Stage gameSelfWarning = new Stage();
							gameSelfWarning.setTitle("警告");
							FlowPane flWarning = new FlowPane();
							flWarning.setPrefSize(300, 200);
							flWarning.setAlignment(Pos.CENTER);
							Text txtWarning = new Text("游戏设置不合法：\n" +
									"请输入整数高度介于1~24之间.\n" +
									"请输入整数宽度介于1~30之间.\n" +
									"输入的雷数介于1~高度×宽度×0.5之间!\n");
							flWarning.getChildren().addAll(txtWarning);
							gameSelfWarning.setScene(new Scene(flWarning));
							gameSelfWarning.setResizable(false);
							gameSelfWarning.show();
						}
					} else{
						Stage gameSelfWarning = new Stage();
						gameSelfWarning.setTitle("警告");
						FlowPane flWarning = new FlowPane();
						flWarning.setPrefSize(300, 200);
						flWarning.setAlignment(Pos.CENTER);
						Text txtWarning = new Text("游戏设置不合法：\n" +
								"请输入整数高度介于1~24之间.\n" +
								"请输入整数宽度介于1~30之间.\n" +
								"输入的雷数介于1~高度×宽度×0.5之间!\n");
						flWarning.getChildren().addAll(txtWarning);
						gameSelfWarning.setScene(new Scene(flWarning));
						gameSelfWarning.setResizable(false);
						gameSelfWarning.show();
					}
				} else{
					Stage gameSelfWarning = new Stage();
					gameSelfWarning.setTitle("警告");
					FlowPane flWarning = new FlowPane();
					flWarning.setPrefSize(300, 200);
					flWarning.setAlignment(Pos.CENTER);
					Text txtWarning = new Text("游戏设置不合法：\n" +
							"请输入整数高度介于1~24之间.\n" +
							"请输入整数宽度介于1~30之间.\n" +
							"输入的雷数介于1~高度×宽度×0.5之间!\n");
					flWarning.getChildren().addAll(txtWarning);
					gameSelfWarning.setScene(new Scene(flWarning));
					gameSelfWarning.setResizable(false);
					gameSelfWarning.show();
				}

			}
			catch(Exception e){
				Stage gameNameWarning = new Stage();
				gameNameWarning.setTitle("警告");
				FlowPane flWarning = new FlowPane();
				flWarning.setPrefSize(300, 200);
				flWarning.setAlignment(Pos.CENTER);
				Text txtWarning = new Text("当前设置为空！");
				flWarning.getChildren().addAll(txtWarning);
				gameNameWarning.setScene(new Scene(flWarning));
				gameNameWarning.setResizable(false);
				gameNameWarning.show();
			}

		});
		gridSelf.setVgap(10);
		gridSelf.setHgap(10);

		gridSelf.add(labelWidth, 1, 1);
		gridSelf.add(txtWidth, 2, 1);

		gridSelf.add(labelHeight, 1, 2);
		gridSelf.add(txtHeight, 2, 2);

		gridSelf.add(labelBooms, 1, 3);
		gridSelf.add(txtBooms, 2, 3);
		gridSelf.add(checkSelf, 1, 4);

		gridSelf.setVisible(false);

		// 创建一个组容纳游戏难度调整的按钮
		ToggleGroup groupGameMode = new ToggleGroup();
		// 初级按钮的功能

		RadioButton btnPrimary = new RadioButton("Primary");
		btnPrimary.setSelected(true);
		btnPrimary.setPrefWidth(160);
		btnPrimary.setOnAction(event -> {
			gridSelf.setVisible(false);
			gameStart.thisGame.setGameMode(Game.GAMEMODE.PRIMARY);
		});
		btnPrimary.setToggleGroup(groupGameMode);

		RadioButton btnMiddle = new RadioButton("Middle");
		btnMiddle.setOnAction(event -> {
			gridSelf.setVisible(false);
			gameStart.thisGame.setGameMode(Game.GAMEMODE.MIDDLE);
		});
		btnMiddle.setToggleGroup(groupGameMode);
		btnMiddle.setPrefWidth(160);

		RadioButton btnHard = new RadioButton("Hard");
		btnHard.setPrefWidth(160);
		btnHard.setOnAction(event -> {
			gridSelf.setVisible(false);
			gameStart.thisGame.setGameMode(Game.GAMEMODE.HARD);
		});

		btnHard.setToggleGroup(groupGameMode);

		RadioButton btnSelf = new RadioButton("Self-Design");
		btnSelf.setPrefWidth(160);
		btnSelf.setOnAction(event -> {
			if(btnSelf.isSelected()){
				gridSelf.setVisible(true);
				gridSelf.setManaged(true);
			}
//			System.out.println("tt");
		});
		btnSelf.setToggleGroup(groupGameMode);
		hboxGameMode.getChildren().addAll(btnPrimary, btnMiddle, btnHard, btnSelf);

		// 组合成游戏选择面板
		FlowPane FLGameMode = new FlowPane();
		FLGameMode.setVgap(20);
		FLGameMode.getChildren().addAll(labelGameMode, hboxGameMode, gridSelf);

		/**
		 * Scheme 选择面板
		 */
		Label labelScheme = new Label("请选择游戏主题：");
		HBox hboxSchemeChoose = new HBox();

		ToggleGroup groupSchemeChoose = new ToggleGroup();
		RadioButton btnSchemeA = new RadioButton("Scheme A");
		btnSchemeA.setPrefWidth(160);
		btnSchemeA.setOnAction(event -> {
			gameStart.thisGame.setScheme(Scheme.A);
		});
		RadioButton btnSchemeB = new RadioButton("Scheme B");
		btnSchemeB.setSelected(true);

		btnSchemeB.setSelected(true);
		btnSchemeB.setPrefWidth(160);
		btnSchemeB.setOnAction(event -> {
			gameStart.thisGame.setScheme(Scheme.B);
		});
		RadioButton btnSchemeC = new RadioButton("Scheme C");
		btnSchemeC.setPrefWidth(160);
		btnSchemeC.setOnAction(event -> {
			gameStart.thisGame.setScheme(Scheme.C);
		});
		btnSchemeA.setToggleGroup(groupSchemeChoose);
		btnSchemeB.setToggleGroup(groupSchemeChoose);
		btnSchemeC.setToggleGroup(groupSchemeChoose);

		// 组合成Scheme选择
		VBox vboxScheme = new VBox();
		hboxSchemeChoose.setSpacing(60);
		hboxSchemeChoose.getChildren().addAll(btnSchemeA, btnSchemeB, btnSchemeC);

		vboxScheme.getChildren().addAll(labelScheme, hboxSchemeChoose);

		/**
		 * 玩家人数
		 */

		HBox hboxSlider = new HBox();
		Text txtSlider = new Text("请选择步数：");

		Slider sliderSteps = new Slider(1, 5, 3);
		sliderSteps.setValue(1);
		gameStart.thisGame.getRecorder().setStepsChance(1);
		sliderSteps.setShowTickLabels(true);
		sliderSteps.setShowTickMarks(true);
		sliderSteps.setMajorTickUnit(1);
		sliderSteps.setMinorTickCount(0);
		sliderSteps.setSnapToTicks(true);
		sliderSteps.setPrefWidth(200);

		sliderSteps.setOnMouseDragged(event -> {
			gameStart.thisGame.getRecorder().setStepsChance((int) sliderSteps.getValue());
		});

		hboxSlider.getChildren().addAll(txtSlider, sliderSteps);

		hboxSlider.setVisible(false);
		Label labPlayers = new Label("请选择玩家人数：");

		Label labPlayerAName = new Label("玩家1的名字：");
		TextField txtfNameA = new TextField();

		Label labPlayerBName = new Label("玩家2的名字：");
		TextField txtfNameB = new TextField();

		Button btnEnterName = new Button("确定名称");

		labPlayerBName.setVisible(false);
		txtfNameB.setVisible(false);

		ToggleGroup groupPlayers = new ToggleGroup();

		RadioButton btnOnePlayer = new RadioButton("One");
		btnOnePlayer.setPrefWidth(160);
		btnOnePlayer.setSelected(true);

		gameStart.thisGame.getRecorder().setPlayerNumber(1);

		btnOnePlayer.setOnAction(event -> {
			if(btnOnePlayer.isSelected()){
				gameStart.thisGame.getRecorder().setPlayerNumber(1);
				labPlayerBName.setVisible(false);
				txtfNameB.setVisible(false);
				hboxSlider.setVisible(false);
			}
		});

		RadioButton btnTwoPlayer = new RadioButton("Two");
		btnTwoPlayer.setPrefWidth(160);

		btnTwoPlayer.setOnAction(event -> {
			gameStart.thisGame.getRecorder().setPlayerNumber(2);
			if(btnTwoPlayer.isSelected()){
				labPlayerBName.setVisible(true);
				txtfNameB.setVisible(true);
				hboxSlider.setVisible(true);
			}

		});

		btnEnterName.setOnAction(event -> {
//			System.out.println(txtfNameA.getText().length());
			if(btnTwoPlayer.isSelected()){
				if((txtfNameA.getText().length() != 0) && (txtfNameB.getText().length() != 0)){
					gameStart.thisGame.getRecorder().getPlayers()[0].playerName = txtfNameA.getText();
					gameStart.thisGame.getRecorder().getPlayers()[1].playerName = txtfNameB.getText();
//					System.out.println(gameStart.thisGame.getRecorder().getPlayers()[0].playerName);
				} else{
//					System.out.println("SSSS");
					Stage playerNameWarning = new Stage();
					playerNameWarning.setTitle("警告");
					FlowPane flNameWarning = new FlowPane();
					flNameWarning.setPrefSize(300, 200);
					flNameWarning.setAlignment(Pos.CENTER);
					Text txtNameWarning = new Text("你的玩家姓名有空白\n系统将会采取默认名称！\n");
					txtNameWarning.setFont(new Font(24));
					HBox hboxNameChoose = new HBox();
					Button btnOK = new Button("确定");
					btnOK.setOnAction(event1 -> {
						playerNameWarning.close();

					});
					Button btnNO = new Button("取消");
					btnNO.setOnAction(event1 -> {
						playerNameWarning.close();
					});
					hboxNameChoose.setSpacing(100);
					hboxNameChoose.getChildren().addAll(btnOK, btnNO);

					flNameWarning.getChildren().addAll(txtNameWarning, hboxNameChoose);

					playerNameWarning.setScene(new Scene(flNameWarning));
					playerNameWarning.setResizable(false);
					playerNameWarning.show();
				}
			}
			if(btnOnePlayer.isSelected()){
				if((txtfNameA.getText().length() != 0)){
					gameStart.thisGame.getRecorder().getPlayers()[0].playerName = txtfNameA.getText();
				} else{
//					System.out.println("SSSS");
					Stage playerNameWarning = new Stage();
					playerNameWarning.setTitle("警告");
					FlowPane flNameWarning = new FlowPane();
					flNameWarning.setPrefSize(300, 200);
					flNameWarning.setAlignment(Pos.CENTER);
					Text txtNameWarning = new Text("你的玩家姓名有空白\n系统将会采取默认名称！\n");
					txtNameWarning.setFont(new Font(24));
					HBox hboxNameChoose = new HBox();
					Button btnOK = new Button("确定");
					btnOK.setOnAction(event1 -> {
						playerNameWarning.close();

					});
					Button btnNO = new Button("取消");
					btnNO.setOnAction(event1 -> {
						playerNameWarning.close();
					});
					hboxNameChoose.setSpacing(100);
					hboxNameChoose.getChildren().addAll(btnOK, btnNO);

					flNameWarning.getChildren().addAll(txtNameWarning, hboxNameChoose);

					playerNameWarning.setScene(new Scene(flNameWarning));
					playerNameWarning.setResizable(false);
					playerNameWarning.show();
				}
			}
		});

		HBox hboxPlayers = new HBox();
		hboxPlayers.setSpacing(60);
		hboxPlayers.getChildren().addAll(btnOnePlayer, btnTwoPlayer);

		VBox vboxPlayer = new VBox();
		vboxPlayer.setSpacing(10);
		vboxPlayer.getChildren().addAll(labPlayers, hboxPlayers, hboxSlider);

		HBox hboxPlayer = new HBox();

		GridPane gridPlayerName = new GridPane();
		gridPlayerName.add(labPlayerAName, 1, 1);
		gridPlayerName.add(labPlayerBName, 1, 2);
		gridPlayerName.add(txtfNameA, 2, 1);
		gridPlayerName.add(txtfNameB, 2, 2);
		gridPlayerName.add(btnEnterName, 1, 3);

		hboxPlayer.getChildren().addAll(vboxPlayer, gridPlayerName);

		btnOnePlayer.setToggleGroup(groupPlayers);
		btnTwoPlayer.setToggleGroup(groupPlayers);

		// 开始游戏按钮和返回菜单按钮
		Button btnGameStart = new Button("开始游戏");
		btnGameStart.setOnAction(event -> {

			gameStart.thisGame.getRecorder().setGamemode(gameStart.thisGame.getGameMode());

			if(btnSelf.isSelected()){
				if(!gameStart.thisGame.getGameMode().equals(Game.GAMEMODE.SELF)){
					Stage gameStartWarning = new Stage();
					gameStartWarning.setTitle("警告");
					FlowPane flSelfWarning = new FlowPane();
					flSelfWarning.setPrefSize(300, 200);
					flSelfWarning.setAlignment(Pos.CENTER);
					Text txtWarning = new Text("你的自定义设置没有点击'采用设置'\n是否要开始默认游戏!\n");

					HBox hboxMyChoose = new HBox();
					Button btnOK = new Button("确定");
					btnOK.setOnAction(event1 -> {
						gameStartWarning.close();
						createGameScene();
						gameStart.thisGame.clearGame();
						primaryStage.setTitle(gameStart.thisGame.getName());
						primaryStage.setScene(gameStart.thisGame.mapScenes.get("GameScene"));
					});

					Button btnNO = new Button("取消");
					btnNO.setOnAction(event1 -> {
						gameStartWarning.close();
					});

					hboxMyChoose.setSpacing(100);
					hboxMyChoose.getChildren().addAll(btnOK, btnNO);

					flSelfWarning.setOrientation(Orientation.VERTICAL);
					flSelfWarning.getChildren().addAll(txtWarning, hboxMyChoose);

					gameStartWarning.setScene(new Scene(flSelfWarning));
					gameStartWarning.setResizable(false);
					gameStartWarning.show();
				}
				// 正常开始游戏
				else{
					createGameScene();
					gameStart.thisGame.clearGame();

					primaryStage.setTitle(gameStart.thisGame.getName());
					primaryStage.setScene(gameStart.thisGame.mapScenes.get("GameScene"));
				}
			}

			if(btnTwoPlayer.isSelected()){
				gameStart.thisGame.getRecorder().setStepsChance((int) sliderSteps.getValue());
			}
			createGameScene();
			gameStart.thisGame.clearGame();

			gameStart.thisGame.getRecorder().setWidth(gameStart.thisGame.getWidth());
			gameStart.thisGame.getRecorder().setHeight(gameStart.thisGame.getHeight());

			primaryStage.setTitle(gameStart.thisGame.getName());
			primaryStage.setScene(gameStart.thisGame.mapScenes.get("GameScene"));
		});

		Button btnBackMain = new Button("返回菜单");
		btnBackMain.setOnAction(event -> {
			gameStart.thisGame.stage.setScene(Launcher);
		});

		HBox hboxSceneControl = new HBox();
		hboxSceneControl.setSpacing(40);
		hboxSceneControl.getChildren().addAll(btnGameStart, btnBackMain);

		// 设置的总面板
		VBox vboxTotalSetting = new VBox();

		VBox vboxSetting = new VBox();
		vboxSetting.setSpacing(40);
		vboxSetting.getChildren().addAll(vboxGameName, vboxScheme, hboxPlayer, FLGameMode);

		vboxTotalSetting.getChildren().addAll(vboxSetting, hboxSceneControl);

//		FLSetting.setPrefSize(1200, 800);

		settingScene = new Scene(vboxTotalSetting);
		if(gameStart.thisGame.mapScenes.getOrDefault("SettingScene", null) != null){
			gameStart.thisGame.mapScenes.remove("SettingScene");
		}
		gameStart.thisGame.mapScenes.put("SettingScene", settingScene);
	}

	public static void createLauncherScene(){

		// Launcher 界面绘制
		// flowPane 的基础设置
		// 菜单栏
		Scene Launcher;

		FlowPane flowPane = new FlowPane();
		flowPane.setOrientation(Orientation.VERTICAL);
		flowPane.setAlignment(Pos.CENTER);
		flowPane.setPrefSize(1200, 800);
		flowPane.setVgap(120);
		flowPane.setStyle(
				" -fx-background-image: url(" + "file:src/Resource/Image/Useful/Launcher.jpg" + "); " +
						" -fx-background-size: 120%;");

		// Launcher界面的基础功能

		// 按钮1：新建游戏
		Button btn1 = new Button("新建游戏");
		btn1.setPrefSize(200, 80);
		btn1.setOnAction(event -> {
			createSettingScene();
			gameStart.thisGame.setGameMode(Game.GAMEMODE.PRIMARY);
			myScenes.primaryStage.setScene(gameStart.thisGame.mapScenes.get("SettingScene"));
			myScenes.primaryStage.setTitle("游戏设置");
		});

		// 按钮2：加载游戏
		Button btn2 = new Button("加载存档");
		btn2.setPrefSize(200, 80);
		// 绑定基础功能
		btn2.setOnAction(event -> {
			createGameLoadScene();
			myScenes.primaryStage.setScene(gameStart.thisGame.mapScenes.get("LoadGame"));
			myScenes.primaryStage.setTitle("加载存档");
		});

		Button btn3 = new Button("排行榜");
		btn3.setPrefSize(200, 80);
		btn3.setOnAction(event -> {
			createRankScene();
			primaryStage.setTitle("排行榜");
			primaryStage.setScene(gameStart.thisGame.mapScenes.get("Rank"));
		});

		Button btn4 = new Button("录像回放");
		btn4.setPrefSize(200, 80);
		btn4.setOnAction(event -> {
			primaryStage.setTitle("本地录像");
			primaryStage.setScene(gameStart.thisGame.mapScenes.get("LoadRecord"));
		});

		flowPane.getChildren().addAll(btn1, btn2, btn3, btn4);
		Launcher = new Scene(flowPane);
		gameStart.thisGame.mapScenes.put("Launcher", Launcher);
	}

	public static void createRankScene(){
		TableView tableRank = rankPlayer.tableRank();

		Label labRank = new Label("玩家排行榜");
		labRank.setFont(new Font(24));

		Button btnToMain = new Button("返回菜单");
		btnToMain.setOnAction(event -> {
			primaryStage.setScene(Launcher);
		});

		VBox vboxRank = new VBox();
		vboxRank.setAlignment(Pos.CENTER);

		vboxRank.getChildren().addAll(labRank, tableRank, btnToMain);
		Scene rankScene = new Scene(vboxRank);

		// 刷新排行榜
		gameStart.thisGame.mapScenes.put("Rank", rankScene);
	}

	public static void createGameLoadScene(){
		Scene GameLoader;
		// 将要加入的文件列表
		List<File> savesFiles = new ArrayList<File>();

		Map<String, File> saves = new HashMap<String, File>();

		String rootPath = new String("L:\\SUSTech\\CODE\\ProjectVersion\\Project\\MineSweeper\\src\\Saves");

		File rootFile = new File(rootPath);

		File[] files = rootFile.listFiles();// 获取目录下的所有文件或文件夹

		if(files != null){
			for(File ifile : files){
				savesFiles.add(ifile);
				saves.put(ifile.getName(), ifile);
			}
		}
		// 将目标文件夹下的文件加入到文件列表
//		for(File f1 : savesFiles){
//			// 临时检查文件列表读取是否正确
//			System.out.println(f1.getName());
//		}

		TreeItem<File> treeRoot = new TreeItem<File>(rootFile);

		for(File file : savesFiles){
			TreeItem<File> item = new TreeItem<>(file);
			treeRoot.getChildren().add(item);
		}

		treeRoot.setExpanded(true);

		TreeView<File> fileTreeView = new TreeView<File>(treeRoot);
		fileTreeView.setShowRoot(false);

		fileTreeView.setOnMouseClicked(event -> {
			if(event.getClickCount() == 2){
				TreeItem<File> item = fileTreeView.getSelectionModel().getSelectedItem();
				gameStart.thisGame.loadGame(item.getValue());
			}
		});

		VBox vboxLoadGame = new VBox();
		Button btnLoadToMain = new Button("返回菜单");
		btnLoadToMain.setOnAction(event -> {
			primaryStage.setTitle("主菜单");
			primaryStage.setScene(Launcher);
		});
		vboxLoadGame.getChildren().addAll(fileTreeView, btnLoadToMain);
		vboxLoadGame.setPrefWidth(800);

		GameLoader = new Scene(vboxLoadGame);

		gameStart.thisGame.mapScenes.put("LoadGame", GameLoader);
	}

	public static void createRecordLoadScene(){
		Scene RecordLoader;
		// 将要加入的文件列表
		List<File> savesFiles = new ArrayList<File>();

		Map<String, File> saves = new HashMap<String, File>();

		String rootPath = new String("L:\\SUSTech\\CODE\\ProjectVersion\\Project\\MineSweeper\\src\\Saves");

		File rootFile = new File(rootPath);

		File[] files = rootFile.listFiles();// 获取目录下的所有文件或文件夹

		if(files != null){
			for(File ifile : files){
				savesFiles.add(ifile);
				saves.put(ifile.getName(), ifile);
			}
		}
		// 将目标文件夹下的文件加入到文件列表
//		for(File f1 : savesFiles){
//			// 临时检查文件列表读取是否正确
//			System.out.println(f1.getName());
//		}

		TreeItem<File> treeRoot = new TreeItem<File>(rootFile);

		for(File file : savesFiles){
			TreeItem<File> item = new TreeItem<>(file);
			treeRoot.getChildren().add(item);
		}

		treeRoot.setExpanded(true);

		TreeView<File> fileTreeView = new TreeView<File>(treeRoot);
		fileTreeView.setShowRoot(false);

		fileTreeView.setOnMouseClicked(event -> {
			if(event.getClickCount() == 2){
				TreeItem<File> item = fileTreeView.getSelectionModel().getSelectedItem();
				System.out.println(item.getValue());
				gameStart.thisGame.loadRecord(item.getValue());

			}
		});

		VBox vboxLoadGame = new VBox();
		Button btnLoadToMain = new Button("返回菜单");
		btnLoadToMain.setOnAction(event -> {
			primaryStage.setTitle("主菜单");
			primaryStage.setScene(Launcher);
		});
		vboxLoadGame.getChildren().addAll(fileTreeView, btnLoadToMain);
		vboxLoadGame.setPrefWidth(800);

		RecordLoader = new Scene(vboxLoadGame);

		gameStart.thisGame.mapScenes.put("LoadRecord", RecordLoader);
	}

	public static void createMusicScedne(){
		Scene music;
		// 将要加入的文件列表
		List<File> savesFiles = new ArrayList<File>();

		Map<String, File> saves = new HashMap<String, File>();

		String rootPath = new String("L:\\SUSTech\\CODE\\ProjectVersion\\Project\\MineSweeper\\src\\Resource\\Music");

		File rootFile = new File(rootPath);

		File[] files = rootFile.listFiles();// 获取目录下的所有文件或文件夹

		if(files != null){
			for(File ifile : files){
				savesFiles.add(ifile);
				saves.put(ifile.getName(), ifile);
			}
		}
		// 将目标文件夹下的文件加入到文件列表
//		for(File f1 : savesFiles){
//			// 临时检查文件列表读取是否正确
//			System.out.println(f1.getName());
//		}

		TreeItem<File> treeRoot = new TreeItem<File>(rootFile);

		for(File file : savesFiles){
			TreeItem<File> item = new TreeItem<>(file);
			treeRoot.getChildren().add(item);
		}

		treeRoot.setExpanded(true);

		TreeView<File> fileTreeView = new TreeView<File>(treeRoot);
		fileTreeView.setShowRoot(false);

		fileTreeView.setOnMouseClicked(event -> {
			if(event.getClickCount() == 2){
				TreeItem<File> item = fileTreeView.getSelectionModel().getSelectedItem();
				// 播放音乐

			}
		});

		VBox vboxMusic = new VBox();
		Button btnLoadToMain = new Button("返回菜单");
		btnLoadToMain.setOnAction(event -> {
			primaryStage.setTitle("主菜单");
			primaryStage.setScene(Launcher);
		});
		vboxMusic.getChildren().addAll(fileTreeView, btnLoadToMain);
		vboxMusic.setPrefWidth(800);

		music = new Scene(vboxMusic);

		gameStart.thisGame.mapScenes.put("Music", music);
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
		Menu menuGame = new Menu("游戏");
		// 1.1 NewGame item
		MenuItem itemNewGame = new MenuItem("返回菜单");
		itemNewGame.setOnAction(event -> {
			primaryStage.setScene(Launcher);
		});
		// 绑定功能

		// 1.2 ReStart 选项
		MenuItem itemRestart = new MenuItem("重新开始");
		itemRestart.setOnAction(event -> {
			gameStart.thisGame.reStart();
		});

		// 1.3 Save 选项
		MenuItem itemSave = new MenuItem("保存游戏");
		itemSave.setOnAction(event -> {
//			System.out.println(gameStart.thisGame.getRecorder().toString());
			Game.createSave(gameStart.thisGame.getName());
		});

		// 绑定到 Game
		menuGame.getItems().addAll(itemNewGame, itemRestart, itemSave);

		// 2. "Scheme" 菜单
		Menu menuScheme = new Menu("主题");

		ToggleGroup schemeChoose = new ToggleGroup();
		RadioMenuItem itemA = new RadioMenuItem("Scheme A");
		itemA.setOnAction(event -> {
			if(itemA.isSelected()){
				gameStart.thisGame.setBlocksScheme(Scheme.A);
			}
		});
		RadioMenuItem itemB = new RadioMenuItem("Scheme B");
		itemB.setSelected(true);
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
		Menu menuGameRule = new Menu("游戏选项");

		ToggleGroup typeChoose = new ToggleGroup();

		RadioMenuItem itemSingle = new RadioMenuItem("逐个扫雷");
		itemSingle.setSelected(true);
		RadioMenuItem itemSweep = new RadioMenuItem("连续扫雷");

		typeChoose.getToggles().addAll(itemSingle, itemSweep);

		itemSingle.setOnAction(event -> {
			gameStart.thisGame.setSweepType(Square.sweepType.SINGLE);
		});

		itemSweep.setOnAction(event -> {
			gameStart.thisGame.setSweepType(Square.sweepType.CONTINOUS);
		});
		menuGameRule.getItems().addAll(itemSingle, itemSweep);

		menuBarGame.getMenus().addAll(menuGame, menuScheme, menuGameRule);

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

		// 游戏区域 - 信息面板
		FlowPane infoArea = new FlowPane();
		infoArea.setOrientation(Orientation.HORIZONTAL);

		// 游戏区域 - 雷区面板
		GridPane BoomsPane = myScenes.createBoomsPane(width, height);
		gameStart.thisGame.setGridBooms(BoomsPane);
		// 游戏区域 - END

		// 控制区域
		// 控制区域 - 控制面板
		RadioButton btnCheat = new RadioButton();
		btnCheat.setText("作弊模式");
		btnCheat.setSelected(false);
		btnCheat.setOnAction(event -> {
			if(btnCheat.isSelected()){
				gameStart.thisGame.getInfoArea().appendText("\n启动作弊模式\n");
				gameStart.thisGame.cheatMode(true);
			} else{
				gameStart.thisGame.cheatMode(false);
			}
		});

		Button btnHelp = new Button("提示一下");

		btnHelp.setOnAction(event -> {
			if(gameStart.thisGame.getStepCount() != 0){
				String help;
				int id = gameStart.thisGame.getRecorder().getUnOpenBooms().get(0);
				help = "\n(" + Position.idToX(id) + "," + Position.idToY(id) + ")是地雷！";
				gameStart.thisGame.getInfoArea().appendText(help);
			}

		});

		// 菜单栏、游戏区域、控制区域加入到游戏面板
		// 游戏区域 - 闲话面板

		Label labOutArea = new Label("游戏动态：");

//		TextArea textAreaOut = new TextArea("Mulikas:感谢游玩!");
//		textAreaOut.setPrefSize(gameStart.thisGame.getHeight()*40, 200);

		VBox vboxControlArea = new VBox();
		vboxControlArea.setStyle(
				"-fx-border-style:solid;"
		);

		Text labScore = new Text("得分");
		Text labMistake = new Text("失误");

		Label labPlayerA = new Label("PlayerA:");

		Text txtScoreA = new Text("0");
		Text txtMistakeA = new Text("0");
		gameStart.thisGame.setScoreA(txtScoreA);
		gameStart.thisGame.setMistakeA(txtMistakeA);

		Label labPlayerB = new Label("PlayerB:");
		Text txtScoreB = new Text("0");
		Text txtMistakeB = new Text("0");
		gameStart.thisGame.setScoreB(txtScoreB);
		gameStart.thisGame.setMistakeB(txtMistakeB);

		TextArea txtaInfomation = new TextArea();
		txtaInfomation.setEditable(false);

		gameStart.thisGame.setInfoArea(txtaInfomation);

		// 根据游戏模式选择B的可见性
		if(gameStart.thisGame.getRecorder().getPlayerNumber() == 1){
			labPlayerB.setVisible(false);
			txtScoreB.setVisible(false);
			txtMistakeB.setVisible(false);
		}

		// 悔棋按钮
		Button btnBack = new Button("后退一步");
		btnBack.setOnAction(event -> {
			gameStart.thisGame.back();
		});

		GridPane gridPlayers = new GridPane();
		gridPlayers.setVgap(10);
		gridPlayers.setHgap(10);
		gridPlayers.add(labScore, 2, 1);
		gridPlayers.add(labMistake, 3, 1);

		gridPlayers.add(labPlayerA, 1, 2);
		gridPlayers.add(txtScoreA, 2, 2);
		gridPlayers.add(txtMistakeA, 3, 2);

		gridPlayers.add(labPlayerB, 1, 3);
		gridPlayers.add(txtScoreB, 2, 3);
		gridPlayers.add(txtMistakeB, 3, 3);

		// 功能按钮A
		Text txtControl = new Text("控制区域：");
		GridPane gridControl = new GridPane();
		gridControl.setVgap(20);
		gridControl.add(txtControl, 1, 1);
		gridControl.add(btnCheat, 1, 3);
		gridControl.add(btnHelp, 1, 2);
		gridControl.add(btnBack, 2, 2);

		vboxControlArea.setSpacing(20);
		vboxControlArea.setPrefHeight(gameStart.thisGame.getHeight()*40);
		vboxControlArea.setPrefWidth(400);

		vboxControlArea.getChildren().addAll(labOutArea, gridPlayers, txtaInfomation, gridControl);

		FlowPane flowGamePane = new FlowPane();
		flowGamePane.setOrientation(Orientation.VERTICAL);
		flowGamePane.getChildren().addAll(BoomsPane, vboxControlArea);
		flowGamePane.setHgap(20);

		// 设置游戏场景

		VBox vboxGamePane = new VBox();
		vboxGamePane.getChildren().addAll(menuBarGame, flowGamePane);

		GameScene = new Scene(vboxGamePane);
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

	public static GridPane createBoomsPane(Game.Recorder recorder){
		int width = recorder.getWidth();
		int height = recorder.getHeight();

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
