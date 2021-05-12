package Settings;

import MainGame.Containers;

public class Setting{
	// 雷区长宽
	private int AreaWidth;
	private int AreaHeight;
	// 游戏模式
	private GAMEMODE myMODE;
	private SHAPE SHAPE = this.SHAPE.SQUARE;
	
	/**
	 * 默认构造器，初始化默认游戏，区分游戏难度
	 */
	public Setting(){
		this.myMODE = GAMEMODE.MIDDLE;
	}
	
	public Setting(GAMEMODE iMode){
		// 加入容器
		Containers.Map_Setting.put(this.getClass().getSimpleName(), this);
		this.myMODE = iMode;
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
	
	// 枚举方块形状
	enum SHAPE{
		SQUARE;
	}
	
	// 枚举游戏类型
	enum GAMEMODE{
		PRIMARY,
		MIDDLE,
		HARD,
		SELF_DESIGN;
	}
	
}
