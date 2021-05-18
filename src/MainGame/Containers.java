package MainGame;

import Settings.GameSetting;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Containers{
	private String playerName;

//	// Containers 储存所有的游戏对象的引用，以便跨文件
//	public static Map<String, Containers> Games= new HashMap<String, Containers>();
	
	// controller 容器
	public Map<String, Object> Map_Controllers = new HashMap<String, Object>();
	
	// Stage 容器
	public Map<String, Stage> mapStages = new HashMap<String, Stage>();
	
	// Scene 容器
	public Map<String, Scene> mapScenes = new HashMap<String, Scene>();
	
	private GameSetting setting = new GameSetting();
	
	// Setting 容器
	public Map<String, GameSetting> Map_Setting = new HashMap<String, GameSetting>();
	
	/**
	 * 构造器，传入游戏名字
	 *
	 * @param name
	 */
	public Containers(String name){
		this.playerName = name;
	}
	
	public String getName(){
		return playerName;
	}
	
	public void setName(String name){
		this.playerName = name;
	}
	
	public GameSetting getSetting(){
		return setting;
	}
	
	public void setSetting(GameSetting setting){
		this.setting = setting;
	}
	
}
