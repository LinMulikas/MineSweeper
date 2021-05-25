package MainGame;

import GameControl.Block;
import GameControl.Position;
import GameControl.Square;
import GameControl.rankPlayer;
import Resource.Scheme.Scheme;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
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
		this.setGameMode(GAMEMODE.PRIMARY);
		this.sweepType = Square.sweepType.SINGLE;
		this.scheme = Scheme.B;
		thisPlayer = recorder.players[0];
		if(recorder.playerNumber == 2){
			this.infoArea.appendText("\n现在是" + thisPlayer.playerName + "的回合！");
		}
	}
	
	public Game(GAMEMODE gamemode, Scheme iScheme){
		this.setGameMode(gamemode);
		this.sweepType = Square.sweepType.SINGLE;
		this.scheme = iScheme;
	}
	
	public static void createSave(String saveName){
		// 存档覆盖有问题
		ObjectOutputStream oos = null;
		String path = "L:\\SUSTech\\CODE\\ProjectVersion\\Project\\MineSweeper\\src\\Saves";
		// 已经存在的文件读取有问题
		File file = new File(path, saveName + ".txt");
		if(file.exists()){
			System.out.println("exist");
			if(file.delete()){
				System.out.println("删除成功");
			} else{
				System.out.println("删除失败");
			}
		}
		try{
			if(file.createNewFile()){
				System.out.println("创建文件成功");
			} else{
				System.out.println("创建文件失败");
			}
		}
		catch(IOException e1){
			System.out.println("创建文件时出错");
		}
		
		try{
			oos = new ObjectOutputStream(
					new FileOutputStream(file));
			System.out.println(gameStart.thisGame.getRecorder().getStepCount());
			oos.writeObject(gameStart.thisGame.getRecorder());
			System.out.println("写入成功");
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
//			System.out.println(newRecorder.getStepCount());
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
	
	public static ArrayList<String> loadRank(){
		//第一步：先获取csv文件的路径，通过BufferedReader类去读该路径中的文件
		File rankCSV = new File("L:\\SUSTech\\CODE\\ProjectVersion\\Project\\MineSweeper\\src\\Rank\\Rank.txt");
		ArrayList<String> rankData = new ArrayList<>();
		
		try{
			//第二步：从字符输入流读取文本，缓冲各个字符，从而实现字符、数组和行（文本的行数通过回车符来进行判定）的高效读取。
			BufferedReader fileReader = new BufferedReader(new FileReader(rankCSV));
			
			String lineData = "";
			//第三步：将文档的下一行数据赋值给lineData，并判断是否为空，若不为空则输出
			while((lineData = fileReader.readLine()) != null){
				rankData.add(lineData);
//				System.out.println(lineData);
			}
//			System.out.println(rankData.toString());
		}
		catch(FileNotFoundException e){
			System.out.println("没有找到指定文件");
		}
		catch(IOException e){
			System.out.println("文件读写出错");
		}
		return rankData;
	}
	
	public void loadRecord(File file){
		Recorder theRecord = loadSave(file);
		this.setRecorder(theRecord);
		
		this.setName(file.getName().substring(0, file.getName().length() - 4));
		this.setWidth(this.recorder.width);
		this.setHeight(this.recorder.height);
		
		this.recorder.stepList.clear();
		this.recorder.step.clear();
		
		this.setStepCount(0);
		createGameScene();
		
		this.setInnerArea(this.recorder.inner);
		
		primaryStage.setTitle(gameStart.thisGame.getName());
		primaryStage.setScene(gameStart.thisGame.mapScenes.get("GameScene"));
		
		for(ArrayList<Integer> stepList : recorder.stepList){
			System.out.println("SSS");
			for(int i : stepList){
				System.out.println(i);
				try{
					Thread.sleep(100);
				}
				catch(InterruptedException e1){
					//捕获异常
					e1.printStackTrace();
				}
				this.Blocks[i - 1].openHere(false);
			}
		}
		
	}
	
	public void back(){
		if(stepCount == 0){
			return;
		}
		ArrayList<Integer> lastStep = recorder.stepList.get(recorder.stepList.size() - 1);
		
		// 回溯最后一步的方块
		// proj模式中回溯只有一步
		int steps = lastStep.size();
//		System.out.println(steps);
		int cnt = 0;
		for(int id : lastStep){
//			System.out.println(id);
			if(cnt == steps){
				break;
			}
			if(this.getBlocks()[id - 1].getStatus().equals(Block.PreStatus.BOOM)){
				this.recorder.openedID.remove(recorder.openedID.size() - 1 - cnt);
				this.recorder.unOpenBooms.add(id);
			} else{
				this.recorder.openedID.remove(recorder.openedID.size() - 1 - cnt);
			}
			this.getBlocks()[id - 1].setStatus(Block.PreStatus.CLOSE);
			cnt++;
		}
		
		// 回溯最后一步的分数
		switch(this.recorder.scoreList.get(this.recorder.scoreList.size() - 1)){
			case 1:
				thisPlayer.minusScore();
				break;
			case 2:
				thisPlayer.minusMistake();
				break;
			case -1:
				thisPlayer.addScore();
				break;
		}
		
		// 刷新信息栏
		int lastIndex = recorder.stepCount - 1;
		this.renewTextInfo();
		// 刷新信息提示
		this.infoArea.appendText("\n玩家" + thisPlayer.playerName + "撤回了一步！");
		
		// 刷新recorder
		this.recorder.scoreList.remove(lastIndex);
		this.recorder.stepList.remove(lastIndex);
		this.recorder.step.clear();
		
		// 刷新步数
		this.setStepCount(this.stepCount - 2);
		this.count();
		
	}
	
	public void saveScore(){
		File rankCSV = new File("L:\\SUSTech\\CODE\\ProjectVersion\\Project\\MineSweeper\\src\\Rank\\Rank.txt");
		
		try{
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(rankCSV, true), "UTF-8"));
			if(this.recorder.playerNumber == 1){
				writer.write(this.recorder.players[0].playerName + "");
				writer.write("\n");
				writer.write(this.recorder.players[0].getScore() + "");
				writer.write("\n");
				writer.write(this.recorder.players[0].getMistake() + "");
				writer.write("\n");
			}
			if(this.recorder.playerNumber == 2){
				writer.write(this.recorder.players[0].playerName + "");
				writer.write("\n");
				writer.write(this.recorder.players[0].getScore() + "");
				writer.write("\n");
				writer.write(this.recorder.players[0].getMistake() + "");
				writer.write("\n");
				writer.write(this.recorder.players[1].playerName);
				writer.write("\n");
				writer.write(this.recorder.players[1].getScore() + "");
				writer.write("\n");
				writer.write(this.recorder.players[1].getMistake() + "");
				writer.write("\n");
			}
			writer.flush();
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void loadGame(File file){
		Recorder theRecord = loadSave(file);
		this.setRecorder(theRecord);
		
		this.setName(file.getName().substring(0, file.getName().length() - 4));
		
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
	
	public void renewTextInfo(){
		
		this.getScoreA().setText("" + this.recorder.players[0].getScore());
		this.getMistakeA().setText("" + this.recorder.players[0].getMistake());
		this.getScoreB().setText("" + this.recorder.players[1].getScore());
		this.getMistakeB().setText("" + this.recorder.players[1].getMistake());
		
	}
	
	public void reStart(){
		this.recorder.players[0].setScore(0);
		this.recorder.players[0].setMistake(0);
		this.recorder.players[1].setScore(0);
		this.recorder.players[1].setMistake(0);
		this.recorder.getStepList().clear();
		this.recorder.getStep().clear();
		
		if(this.stepCount != 0){
			this.setStepCount(0);
		}
		
		this.renewTextInfo();
		for(Square iSquare : this.getBlocks()){
			iSquare.setStatus(Block.PreStatus.CLOSE);
		}
		
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
		if(thisPlayer.playerName.equals(recorder.players[0].playerName)){
			return mistakeA;
		}
		if(thisPlayer.playerName.equals(recorder.players[1].playerName)){
			return mistakeB;
		}
		return null;
	}
	
	public Text getThisScoreText(){
		if(thisPlayer.playerName.equals(recorder.players[0].playerName)){
			return scoreA;
		}
		if(thisPlayer.playerName.equals(recorder.players[1].playerName)){
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
	
	public void clearGame(){
		
		this.recorder.players[0].setScore(0);
		this.recorder.players[0].setMistake(0);
		this.recorder.players[1].setScore(0);
		this.recorder.players[1].setMistake(0);
		this.recorder.getStepList().clear();
		this.recorder.getStep().clear();
		this.recorder.unOpenBooms.clear();
		this.recorder.allBooms.clear();
		this.recorder.scoreList.clear();
		
		if(this.stepCount != 0){
			this.setStepCount(0);
		}
		
		this.renewTextInfo();
		if(this.recorder.playerNumber == 2){
			gameStart.thisGame.getInfoArea().appendText("\n现在是" + gameStart.thisGame.getThisPlayer().playerName +
					"的回合！\n");
		}
		
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
		private static final long serialVersionUID = 1L;
		private Player[] players = {new Player("A"), new Player("B"), new Player("C")};
		private int playerNumber = 1;
		private int stepsChance = 0;
		private int width, height;
		private int stepCount = 0;
		private GAMEMODE gamemode = null;
		
		private ArrayList<Integer> openedID = new ArrayList<Integer>();
		private ArrayList<Integer> unOpenBooms = new ArrayList<Integer>();
		private ArrayList<Integer> allBooms = new ArrayList<Integer>();
		private ArrayList<Integer> scoreList = new ArrayList<Integer>();
		
		private int[][] inner = null;
		private ArrayList<ArrayList<Integer>> stepList = new ArrayList<>();
		private ArrayList<Integer> step = new ArrayList<>();
		
		public void clear(){
			this.players[0].setScore(0);
			this.players[0].setMistake(0);
			this.players[1].setScore(0);
			this.players[1].setMistake(0);
			this.players[2].setScore(0);
			this.players[2].setMistake(0);
			this.step.clear();
			this.stepList.clear();
			this.scoreList.clear();
			this.playerNumber = 1;
			this.players[0].playerName = "A";
			
		}
		
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
		
		public ArrayList<Integer> getScoreList(){
			return scoreList;
		}
		
		public void setScoreList(ArrayList<Integer> scoreList){
			this.scoreList = scoreList;
		}
		
		public GAMEMODE getGamemode(){
			return gamemode;
		}
		
		public void setGamemode(GAMEMODE gamemode){
			this.gamemode = gamemode;
		}
	}
	
}
