package MainGame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class Controller_Launcher{
	
	@FXML
	private ResourceBundle resources;
	
	@FXML
	private URL location;
	
	// 单击启动游戏
	@FXML
	void LoadGame(MouseEvent event){
		System.out.println("按照当前设置加载游戏");
	}
	
	@FXML
	void LoadRank(MouseEvent event){
	
	}
	
	@FXML
	void LoadSettings(MouseEvent event){
	
	}
	
	@FXML
	void initialize(){
	
	}
}
