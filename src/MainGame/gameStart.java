package MainGame;

import GameControl.myScenes;
import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

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
		thisGame = new Game();
		thisGame.stage = primaryStage;
		thisGame.mapStages.put("primaryStage", primaryStage);
		// 把初始界面加入Map
		// 显示登录窗口
		primaryStage.setTitle("Launcher");
		primaryStage.setResizable(false);
		primaryStage.setScene(myScenes.Launcher);
		primaryStage.show();

//		File iMusic =
//				new File("file:\\L:\\SUSTech\\CODE\\ProjectVersion\\Project\\MineSweeper\\src\\Resource\\Music" +
//						"\\Bad_Apple.mp3");
//		System.out.println(iMusic.getName());
	
	}
}
