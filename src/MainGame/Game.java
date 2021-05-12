package MainGame;

import Controller.GameController;
import Controller.LauncherController;
import Controller.SettingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resourse.Scene.iScene;

import java.io.IOException;

public class Game extends Application{
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		try{
			// 加载 Launcher 界面的，动态读取界面
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resourse/Scene/LauncherScene.fxml"));
			Parent root = fxmlLoader.load();
			// 构建所需界面和控制器
			// 1. 登陆器
			Scene LauncherScene = new iScene(root, 600, 400);
			LauncherController iLauncher = fxmlLoader.getController();
			
			// 2. 设置界面
			Scene SettingScene;
			// 设置界面需要向某个游戏实例
			SettingController iSetting;
			
			// 3. 游戏界面
			Scene GameScene;
			GameController iGame;
			
			// 让控制端具有修改场景权限
			iLauncher.setStage(primaryStage);
			iLauncher.setScene(LauncherScene);
			
			// 显示登录窗口
			primaryStage.setTitle("Launcher");
			primaryStage.setScene(LauncherScene);
			primaryStage.show();
		}
		catch(IOException e){
			System.out.println("未成功建立scene_launcher");
			e.printStackTrace();
		}
		
	}
}
