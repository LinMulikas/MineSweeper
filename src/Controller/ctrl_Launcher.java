package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import MainGame.Containers;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ctrl_Launcher extends RootController{
	private Scene scene;
	private Stage stage;
	
	@FXML
	private ResourceBundle resources;
	
	@FXML
	private URL location;
	
	// 单击启动游戏
	@FXML
	void LoadGame(MouseEvent event){
		System.out.println("按照当前设置加载游戏 <Scene_MainGame> ");

//		this.scene = Scene_Choose_Mode;
//		GameSetter iSetter = new GameSetter();
	}
	
	// 排行榜
	@FXML
	void LoadRank(MouseEvent event){
		System.out.println("加载排行榜 <Scene_Rank> ");
	}
	
	// 加载游戏设置
	@FXML
	void LoadSettings(MouseEvent event){
		System.out.println("加载设置界面 <Scene_Settings>");
//		GameSetter iSetter
//		this.stage.setScene(Containers.Map_Scenes.get("scn_Setting"));
	}
	
	@FXML
	void initialize(){
	
	}
	
	public void setStage(Stage stage){
		this.stage = stage;
	}
	
	public void setScene(Scene scene){
		this.scene = scene;
	}
}
