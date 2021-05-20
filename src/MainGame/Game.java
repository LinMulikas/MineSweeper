package MainGame;

import GameControl.Position;
import GameControl.Square;
import Resource.Scheme.Scheme;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game{
	/**
	 * 视觉组件
	 */
	// Stage 容器
	public Map<String, Stage> mapStages = new HashMap<>();
	public Stage stage = new Stage();
	// Scene 容器
	public Map<String, Scene> mapScenes = new HashMap<>();
	private Square.sweepType sweepType = Square.sweepType.CONTINOUS;
	// Scheme
	private Scheme scheme;
	/**
	 * 游戏核心属性
	 */
	private String GameName;
	private int BoomsNumber;
	private int MaxBoomsNumber;
	// 游戏中的按钮对象
	private Square[] Blocks = null;
	//private String PlayerName;
	// 雷区长宽
	private int Width;
	private int Height;
	
	//========================================初始化必需的属性
	private GAMEMODE GameMode;
	private Recorder recorder = new Recorder();
	private int stepCount = 0;
	
	// Inner群
	
	/**
	 * InnerArea是当前游戏内部的逻辑棋盘
	 * -1:  Flag
	 * 0:   Blank
	 * 1~8: Number
	 * 9:   Boom
	 */
	private int[][] InnerArea;
	
	/**
	 * <pre>
	 *     启动默认游戏
	 * </pre>
	 */
	public Game(){
		this.setGameMode(GAMEMODE.PRIMARY);
		this.sweepType = Square.sweepType.SINGLE;
	}
	
	public Game(GAMEMODE gamemode, Scheme iScheme){
		this.setGameMode(gamemode);
		this.scheme = iScheme;
	}
	
	public String getName(){
		return GameName;
	}
	
	public void setName(String name){
		this.GameName = name;
	}
	
	public Square[] getBlocks(){
		return Blocks;
	}
	
	public void setBlocks(Square[] blocks){
		this.Blocks = blocks;
	}
	
	public Square getABlock(int x, int y){
		return Blocks[Position.positionToId(x, y) - 1];
	}
	
	public void setBlockArea(int[][] area){
	
	}
	
	public int getStepCount(){
		return stepCount;
	}
	
	public void setStepCount(int stepCount){
		this.stepCount = stepCount;
	}
	
	public void count(){
		this.stepCount++;
	}
	
	public int[][] getInnerArea(){
		return InnerArea;
	}
	
	public void setInnerArea(int[][] innerArea){
		InnerArea = innerArea;
	}
	
	public int getBoomsNumber(){
		return BoomsNumber;
	}
	
	public void setBoomsNumber(int boomsNumber){
		BoomsNumber = boomsNumber;
	}
	
	public void setBlocksView(){
		for(Square item : this.Blocks){
			item.setView(gameStart.thisGame.scheme);
		}
	}
	
	public Recorder getRecorder(){
		return recorder;
	}
	
	public void setRecorder(Recorder recorder){
		this.recorder = recorder;
	}
	
	public int getWidth(){
		return Width;
	}
	
	public void setWidth(int width){
		Width = width;
	}
	
	public int getHeight(){
		return Height;
	}
	
	public void setHeight(int height){
		Height = height;
	}
	
	public Scheme getScheme(){
		return scheme;
	}
	
	public void setScheme(Scheme scheme){
		this.scheme = scheme;
	}
	
	public void setBlocksScheme(Scheme iScheme){
		this.scheme = iScheme;
		for(Square item : this.getBlocks()){
			item.setView(iScheme);
		}
	}
	
	public GAMEMODE getGameMode(){
		return GameMode;
	}
	
	/**
	 * 按照某个难度创建游戏
	 *
	 * @param gameMode
	 */
	public void setGameMode(GAMEMODE gameMode){
		switch(gameMode){
			case PRIMARY:
				this.Width = 9;
				this.Height = 9;
				this.BoomsNumber = 10;
				break;
			case MIDDLE:
				this.Width = 16;
				this.Height = 16;
				this.BoomsNumber = 40;
				break;
			case HARD:
				this.Width = 30;
				this.Height = 16;
				this.BoomsNumber = 99;
				break;
		}
	}
	
	public int getMaxBoomsNumber(){
		return MaxBoomsNumber;
	}
	
	public void setMaxBoomsNumber(int maxBoomsNumber){
		MaxBoomsNumber = maxBoomsNumber;
	}
	
	public Square.sweepType getSweepType(){
		return sweepType;
	}
	
	public void setSweepType(Square.sweepType sweepType){
		this.sweepType = sweepType;
	}
	
	public enum GAMEMODE{
		PRIMARY,
		MIDDLE,
		HARD,
		SELF;
	}
	
	public static class Recorder{
		private ArrayList<ArrayList<Integer>> stepList = new ArrayList<>();
		private ArrayList<Integer> step = new ArrayList<>();
		
		// 记录每一步
		public void update(){
			this.stepList.add(this.step);
		}
		
		public ArrayList<ArrayList<Integer>> getStepList(){
			return stepList;
		}
		
		public void setStepList(ArrayList<ArrayList<Integer>> stepList){
			this.stepList = stepList;
		}
		
		public ArrayList<Integer> getStep(){
			return step;
		}
		
		public void setStep(ArrayList<Integer> step){
			this.step = step;
		}
	}
	
}
