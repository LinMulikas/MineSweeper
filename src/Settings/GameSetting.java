package Settings;

import MainGame.MainGame;

import java.util.HashMap;
import java.util.Map;

public class GameSetting{
	public static GameSetting DefaultSetting = new GameSetting(9, 9, GAMEMODE.PRIMARY, SHAPE.SQUARE);
	// 主题库
	private static Map<String, Map<String, String>> Schemes = new HashMap<String, Map<String, String>>();
	
	static Map<String, String> pics1 = new HashMap<String, String>();
	static Map<String, String> pics2 = new HashMap<String, String>();
	static Map<String, String> pics3 = new HashMap<String, String>();
	
	static{
		pics1.put("ENTER", "file:src/Resource/Image/Useful/ENTER_1.png");
		pics1.put("CLOSE", "file:src/Resource/Image/Useful/CLOSE_1.png");
		pics2.put("ENTER", "file:src/Resource/Image/Useful/ENTER_2.png");
		pics2.put("CLOSE", "file:src/Resource/Image/Useful/CLOSE_2.png");
		pics3.put("ENTER", "file:src/Resource/Image/Useful/ENTER_3.png");
		pics3.put("CLOSE", "file:src/Resource/Image/Useful/CLOSE_3.png");
	}
	
	private int schemeID = 0;
	// 当前游戏的主题
	private Map<String, String> scheme = new HashMap<String, String>();
	
	// 雷区长宽
	private int AreaWidth;
	private int AreaHeight;
	// 游戏模式
	private GAMEMODE mode;
	private SHAPE BlockShape = SHAPE.SQUARE;
	
	/**
	 * 默认构造器，初始化默认游戏，区分游戏难度
	 */
	public GameSetting(){
		this.AreaWidth = DefaultSetting.getAreaWidth();
		this.AreaHeight = DefaultSetting.getAreaHeight();
		this.mode = DefaultSetting.getMode();
		BlockShape = DefaultSetting.getBlockShape();
		
		// 初始化主题
		Map<String, String> pics1 = new HashMap<String, String>();
		Map<String, String> pics2 = new HashMap<String, String>();
		Map<String, String> pics3 = new HashMap<String, String>();
		
		pics1.put("ENTER", "file:src/Resource/Image/Useful/ENTER_1.png");
		pics1.put("CLOSE", "file:src/Resource/Image/Useful/CLOSE_1.png");
		Schemes.put("1", pics1);
		
		pics2.put("ENTER", "file:src/Resource/Image/Useful/ENTER_2.png");
		pics2.put("CLOSE", "file:src/Resource/Image/Useful/CLOSE_2.png");
		Schemes.put("2", pics2);
		
		pics3.put("ENTER", "file:src/Resource/Image/Useful/ENTER_3.png");
		pics3.put("CLOSE", "file:src/Resource/Image/Useful/CLOSE_3.png");
		Schemes.put("3", pics3);
		
	}
	
	public GameSetting(int width, int height, GAMEMODE iMode, SHAPE iShape){
		this.AreaWidth = width;
		this.AreaHeight = height;
		this.mode = iMode;
		BlockShape = iShape;
	}
	
	public GameSetting(GAMEMODE iMode){
		// 加入容器
		MainGame.thisGame.Map_Setting.put(this.getClass().getSimpleName(), this);
		this.mode = iMode;
		switch(iMode){
		
		}
	}
	
	public int getAreaWidth(){
		return AreaWidth;
	}
	
	public void setAreaWidth(int areaWidth){
		AreaWidth = areaWidth;
	}
	
	public int getAreaHeight(){
		return AreaHeight;
	}
	
	public void setAreaHeight(int areaHeight){
		AreaHeight = areaHeight;
	}
	
	public GAMEMODE getMode(){
		return mode;
	}
	
	public void setMode(GAMEMODE mode){
		this.mode = mode;
	}
	
	public SHAPE getBlockShape(){
		return BlockShape;
	}
	
	public void setBlockShape(SHAPE blockShape){
		BlockShape = blockShape;
	}
	
	public Map<String, String> getScheme(){
		return scheme;
	}
	
	public void setScheme(int id){
		this.schemeID = id;
		switch(id){
			case 1:
				// 初始化主题
				this.scheme = pics1;
				break;
			case 2:
				// 初始化主题
				this.scheme = pics2;
				break;
			case 3:
				// 初始化主题
				this.scheme = pics3;
				break;
		}
	}
	
	public int getSchemeID(){
		return schemeID;
	}
	
	public void setSchemeID(int schemeID){
		this.schemeID = schemeID;
	}
}
