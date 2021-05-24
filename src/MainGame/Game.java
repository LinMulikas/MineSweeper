package MainGame;

import GameControl.Block;
import GameControl.Position;
import GameControl.Square;
import Resource.Scheme.Scheme;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static GameControl.myScenes.createGameScene;
import static GameControl.myScenes.primaryStage;

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
	// Node
	private GridPane gridBooms = null;
	// 计分板
	private Text scoreA = null;
	private Text scoreB = null;
	private Text mistakeA = null;
	private Text mistakeB = null;
	// 信息面板
	private TextArea infoArea = null;
	// Winner
	private Text txtWinner = null;
	
	// Scheme
	private Scheme scheme;
	
	/**
	 * 游戏核心属性
	 */
	
	private String GameName;
	private int BoomsNumber;
	private int MaxBoomsNumber;
	
	private Player thisPlayer = null;
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
	private int[][] InnerArea = null;
	
	/**
	 * <pre>
	 *     启动默认游戏
	 * </pre>
	 */
	public Game(){
		this.setGameMode(GAMEMODE.MIDDLE);
		this.sweepType = Square.sweepType.SINGLE;
		this.scheme = Scheme.B;
		thisPlayer = recorder.players[0];
	}
	
	public Game(GAMEMODE gamemode, Scheme iScheme){
		this.setGameMode(gamemode);
		this.sweepType = Square.sweepType.SINGLE;
		this.scheme = iScheme;
	}
	
	public void loadGame(File file){
		Recorder theRecord = loadSave(file);
		this.setRecorder(theRecord);
		
		this.setWidth(this.recorder.width);
		this.setHeight(this.recorder.height);
		this.setInnerArea(this.recorder.inner);
		this.setStepCount(this.recorder.getStepCount() - 1);
		this.count();
		
		createGameScene();
		
		for(int i : recorder.openedID){
			this.getBlocks()[i - 1].openHere(false);
		}
		
		this.getScoreA().setText("" + recorder.players[0].getScore());
		this.getMistakeA().setText("" + recorder.players[0].getMistake());
		this.getScoreB().setText("" + recorder.players[1].getScore());
		this.getMistakeB().setText("" + recorder.players[1].getMistake());
		
		primaryStage.setTitle(gameStart.thisGame.getName());
		primaryStage.setScene(gameStart.thisGame.mapScenes.get("GameScene"));
	}
	
	public static void createSave(String saveName){
		// 存档覆盖有问题
		ObjectOutputStream oos = null;
		String path = "L:\\SUSTech\\CODE\\ProjectVersion\\Project\\MineSweeper\\src\\Saves";
		// 已经存在的文件读取有问题
		File file = new File(path, saveName + ".txt");
		if(file.exists()){
			System.out.println("exist");
			file.delete();
		}
		try{
			file.createNewFile();// 创建文件
			System.out.println("创建文件成功");
		}
		catch(IOException e1){
			System.out.println("创建文件失败");
		}
		
		try{
			oos = new ObjectOutputStream(
					new FileOutputStream(file));
			oos.writeObject(gameStart.thisGame.getRecorder());
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println("写入文件失败");
		}
		finally{
			try{
				oos.close();
			}
			catch(IOException e){
				e.printStackTrace();
				System.out.println("oos关闭失败");
			}
		}
	}
	
	public static Recorder loadSave(File file){
		ObjectInputStream ois = null;
		try{
			ois = new ObjectInputStream(new FileInputStream(file));
			// 数据的读取。每一次读取都会把相应的游标往下移动一位
			// 在输入流中读取对象
			Recorder newRecorder = (Recorder) ois.readObject();
			// 临时检测
//			System.out.println(newRecorder.getPlayerNumber());
			return newRecorder;
		}
		catch(Exception e){
			System.out.println(e.getMessage() + "\n读取出错!");
		}
		finally{
			try{
				ois.close();// 关闭输入流
			}
			catch(IOException e){
			}
		}
		return null;
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
		this.recorder.setStepCount(this.stepCount);
		
		if(this.recorder.playerNumber == 1){
			thisPlayer = recorder.players[0];
		}
		
		if(this.recorder.playerNumber == 2){
			int a =
					(stepCount - 1)/gameStart.thisGame.getRecorder().getStepsChance();
			
			int b = a%2;
			
			// 加到玩家头上
			if(b == 0){
				thisPlayer = recorder.players[0];
			}
			if(b == 1){
				thisPlayer = recorder.players[1];
			}
		}
		
	}
	
	public Text getThisMistakeText(){
		switch(thisPlayer.playerName){
			case "A":
				return mistakeA;
			case "B":
				return mistakeB;
		}
		return null;
	}
	
	public Text getThisScoreText(){
		switch(thisPlayer.playerName){
			case "A":
				return scoreA;
			case "B":
				return scoreB;
		}
		return null;
	}
	
	public int[][] getInnerArea(){
		return InnerArea;
	}
	
	public void setInnerArea(int[][] innerArea){
		this.InnerArea = innerArea.clone();
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
				this.GameMode = GAMEMODE.PRIMARY;
				this.Width = 9;
				this.Height = 9;
				this.BoomsNumber = 10;
				break;
			case MIDDLE:
				this.GameMode = GAMEMODE.MIDDLE;
				this.Width = 16;
				this.Height = 16;
				this.BoomsNumber = 40;
				break;
			case HARD:
				this.GameMode = GAMEMODE.HARD;
				this.Width = 30;
				this.Height = 16;
				this.BoomsNumber = 99;
				break;
			case SELF:
				this.GameMode = GAMEMODE.SELF;
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
	
	public Player getThisPlayer(){
		return thisPlayer;
	}
	
	public void setThisPlayer(Player thisPlayer){
		this.thisPlayer = thisPlayer;
	}
	
	public Text getScoreA(){
		return scoreA;
	}
	
	public void setScoreA(Text scoreA){
		this.scoreA = scoreA;
	}
	
	public Text getScoreB(){
		return scoreB;
	}
	
	public void setScoreB(Text scoreB){
		this.scoreB = scoreB;
	}
	
	public Text getMistakeA(){
		return mistakeA;
	}
	
	public void setMistakeA(Text mistakeA){
		this.mistakeA = mistakeA;
	}
	
	public Text getMistakeB(){
		return mistakeB;
	}
	
	public void setMistakeB(Text mistakeB){
		this.mistakeB = mistakeB;
	}
	
	// 游戏胜负判定
	public void judgeWinner(){
		if(recorder.playerNumber == 1){
			if(recorder.unOpenBooms.size() == 0){
				// 单人模式全开雷，玩家A获胜
				txtWinner.setText(thisPlayer.playerName + "获胜！");
				gameStart.thisGame.mapStages.get("primaryStage").setScene(gameStart.thisGame.mapScenes.get("Winner"));
				
			}
		}
		if(recorder.playerNumber == 2){
			int distance = Math.abs(recorder.players[0].getScore() - recorder.players[1].getScore());
			// case1:
			if(distance > recorder.unOpenBooms.size()){
				if(recorder.players[0].getScore() > recorder.players[1].getScore()){
					txtWinner.setText(recorder.players[0].playerName + "获胜！");
					gameStart.thisGame.mapStages.get("primaryStage").setScene(gameStart.thisGame.mapScenes.get("Winner"
					));
					
				} else{
					txtWinner.setText(recorder.players[1].playerName + "获胜！");
					gameStart.thisGame.mapStages.get("primaryStage").setScene(gameStart.thisGame.mapScenes.get("Winner"
					));
					
				}
			}
			if(recorder.unOpenBooms.size() == 0){
				if(recorder.players[0].getScore() > recorder.players[1].getScore()){
					txtWinner.setText(recorder.players[0].playerName + "获胜！");
					gameStart.thisGame.mapStages.get("primaryStage").setScene(gameStart.thisGame.mapScenes.get("Winner"
					));
					
				} else if(recorder.players[0].getScore() < recorder.players[1].getScore()){
					txtWinner.setText(recorder.players[1].playerName + "获胜！");
					gameStart.thisGame.mapStages.get("primaryStage").setScene(gameStart.thisGame.mapScenes.get("Winner"
					));
					
				} else{
					if(recorder.players[0].getMistake() > recorder.players[1].getMistake()){
						txtWinner.setText(recorder.players[1].playerName + "获胜！");
						gameStart.thisGame.mapStages.get("primaryStage").setScene(gameStart.thisGame.mapScenes.get(
								"Winner"));
						
					} else if(recorder.players[0].getMistake() < recorder.players[1].getMistake()){
						txtWinner.setText(recorder.players[0].playerName + "获胜！");
						gameStart.thisGame.mapStages.get("primaryStage").setScene(gameStart.thisGame.mapScenes.get(
								"Winner"));
						
					} else{
						txtWinner.setText("平局啦！");
						gameStart.thisGame.mapStages.get("primaryStage").setScene(gameStart.thisGame.mapScenes.get(
								"Winner"));
						
					}
				}
			}
		}
		
	}
	
	public TextArea getInfoArea(){
		return infoArea;
	}
	
	public void setInfoArea(TextArea infoArea){
		this.infoArea = infoArea;
	}
	
	
	public void cheatMode(Boolean bool){
		if(bool){
			for(int i : recorder.unOpenBooms){
				this.getBlocks()[i - 1].openHere(false);
			}
		}
		
		if(!bool){
			for(int i : recorder.unOpenBooms){
				this.getBlocks()[i - 1].setStatus(Block.PreStatus.CLOSE);
			}
		}
	}
	
	public Text getTxtWinner(){
		return txtWinner;
	}
	
	public void setTxtWinner(Text txtWinner){
		this.txtWinner = txtWinner;
	}
	
	public GridPane getGridBooms(){
		return gridBooms;
	}
	
	public void setGridBooms(GridPane gridBooms){
		this.gridBooms = gridBooms;
	}
	
	public enum GAMEMODE{
		PRIMARY,
		MIDDLE,
		HARD,
		SELF;
	}
	
	public static class Recorder implements Serializable{
		private Player[] players = {new Player("A"), new Player("B"), new Player("C")};
		private int playerNumber = 1;
		private int stepsChance = 0;
		private int width, height;
		private int stepCount = 0;
		
		private static final long serialVersionUID = 1L;
		
		private ArrayList<Integer> openedID = new ArrayList<Integer>();
		private ArrayList<Integer> unOpenBooms = new ArrayList<Integer>();
		private ArrayList<Integer> allBooms = new ArrayList<Integer>();
		private int[][] inner = null;
		
		private ArrayList<ArrayList<Integer>> stepList = new ArrayList<>();
		private ArrayList<Integer> step = new ArrayList<>();
		
		public int getStepsChance(){
			return this.stepsChance;
		}
		
		public void setStepsChance(int stepsChance){
			this.stepsChance = stepsChance;
		}
		
		// 记录每一步
		public void update(){
			this.stepList.add((ArrayList<Integer>) this.step.clone());
//			System.out.println(stepList.get(gameStart.thisGame.getStepCount()-1).toString());
			this.step.clear();
		}
		
		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder();
			for(ArrayList<Integer> list : stepList){
//				System.out.println(list.toString());
				sb.append(list.toString());
//				System.out.println(list.toString());
			}
			return sb.toString();
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
		
		public int getPlayerNumber(){
			return playerNumber;
		}
		
		public void setPlayerNumber(int playerNumber){
			this.playerNumber = playerNumber;
		}
		
		public Player[] getPlayers(){
			return players;
		}
		
		public void setPlayers(Player[] players){
			this.players = players;
		}
		
		public ArrayList<Integer> getUnOpenBooms(){
			return unOpenBooms;
		}
		
		public void setUnOpenBooms(ArrayList<Integer> unOpenBooms){
			this.unOpenBooms = unOpenBooms;
		}
		
		public ArrayList<Integer> getAllBooms(){
			return allBooms;
		}
		
		public void setAllBooms(ArrayList<Integer> allBooms){
			this.allBooms = allBooms;
		}
		
		public int getWidth(){
			return width;
		}
		
		public void setWidth(int width){
			this.width = width;
		}
		
		public int getHeight(){
			return height;
		}
		
		public void setHeight(int height){
			this.height = height;
		}
		
		public ArrayList<Integer> getOpenedID(){
			return openedID;
		}
		
		public void setOpenedID(ArrayList<Integer> openedID){
			this.openedID = openedID;
		}
		
		public int[][] getInner(){
			return inner;
		}
		
		public void setInner(int[][] inner){
			this.inner = inner;
		}
		
		public int getStepCount(){
			return stepCount;
		}
		
		public void setStepCount(int stepCount){
			this.stepCount = stepCount;
		}
	}
	
}
