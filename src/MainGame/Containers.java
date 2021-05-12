package MainGame;

import javafx.scene.Scene;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;

import java.util.HashMap;
import java.util.Map;

public class Containers{
	public static JoniRegExp.Factory factory = new JoniRegExp.Factory();
	
	// controller 容器
	public static Map<String, Object> Map_Controllers = new HashMap<String, Object>();
	
	// Scene 容器
	public static Map<String, Scene> Map_Scenes = new HashMap<String, Scene>();
	
}
