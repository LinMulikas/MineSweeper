package MainGame;

import Settings.GameSettings;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Containers{
	static{
	
	}
	
	public static JoniRegExp.Factory factory = new JoniRegExp.Factory();
	
	// controller 容器
	public static Map<String, Object> Map_Controllers = new HashMap<String, Object>();
	
	// Stage 容器
	public static Map<String, Stage> mapStages = new HashMap<String, Stage>();
	
	// Scene 容器
	public static Map<String, Scene> mapScenes = new HashMap<String, Scene>();
	
	// Setting 容器
	public static Map<String, GameSettings> Map_Setting = new HashMap<String, GameSettings>();
	
}
