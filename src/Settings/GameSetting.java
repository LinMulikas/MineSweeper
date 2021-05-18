package Settings;

import MainGame.Game;

public class GameSetting{
	public static GameSetting DefaultSetting = new GameSetting(9, 9, GAMEMODE.PRIMARY, SHAPE.SQUARE);
	
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
	}
	
	public GameSetting(int width, int height, GAMEMODE iMode, SHAPE iShape){
		this.AreaWidth = width;
		this.AreaHeight = height;
		this.mode = iMode;
		BlockShape = iShape;
	}
	
	public GameSetting(GAMEMODE iMode){
		// 加入容器
		Game.thisGame.Map_Setting.put(this.getClass().getSimpleName(), this);
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
}
