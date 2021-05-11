package Settings;

public class GameSetter{
	// 雷区长宽
	private int BOOMS_WIDTH;
	private int BOOMS_HEIGHT;
	// 游戏模式
	private GameMode gameMode = GameMode.MIDDLE;
	
	/**
	 * 默认构造器，初始化默认游戏，区分游戏难度
	 */
	public GameSetter(GameMode iMode){
		this.gameMode = iMode;
		
	}
	
}
