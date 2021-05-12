package Settings;

public class Setting{
	// 雷区长宽
	private int BOOMS_WIDTH;
	private int BOOMS_HEIGHT;
	// 游戏模式
	private GAMEMODE GAMEMODE;
	private SHAPE SHAPE = this.SHAPE.SQUARE;
	
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
	
	/**
	 * 默认构造器，初始化默认游戏，区分游戏难度
	 */
	
	public Setting(GAMEMODE iMode){
		this.GAMEMODE = iMode;
		switch(iMode){
		
		}
	}
	
}
