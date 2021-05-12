package Resource.Scene;

import GameControl.Square;
import MainGame.Containers;
import Settings.GAMEMODE;
import Settings.GameSettings;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class SceneCreater{
	// 静态成员
	
	/**
	 * 包含初始绘制的一些界面
	 */
	public static void createBasicScene(){
		// 画Launcher
		FlowPane flowPane = new FlowPane();
		flowPane.setOrientation(Orientation.VERTICAL);
		flowPane.setVgap(100);
		
		Button btn1 = new Button("启动设置");

//		btn1.addEventHandler();
		
		flowPane.getChildren().add(btn1);
		Scene LauncherScene = new Scene(flowPane, 200, 400);
		Containers.mapScenes.put("LauncherScene", LauncherScene);
		
	}
	
	// 游戏绘图器
	public static void createGameScene(GAMEMODE iMode){
		switch(iMode){
			case PRIMARY:
				GridPane gridPane1 = new GridPane();
//				Button[] buttons = new Button[81];
				Square[] squares = new Square[81];
				for(int i = 0; i < 81; i++){
					squares[i] = new Square(i + 1);
					gridPane1.add(squares[i], (i)%9, (i)/9);
				}
				Scene GameScene = new Scene(gridPane1);
				Containers.mapScenes.put("Game", GameScene);
			
		}
	}
	
	public static void createGameScene(String iStr, GameSettings iSet){
	
	}
	
}
