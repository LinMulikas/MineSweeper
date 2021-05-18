package MainGame;

import GameControl.Square;
import Settings.GameSetting;
import com.sun.scenario.effect.impl.prism.PrImage;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameContainers{
	/**
	 * 容器群
	 */
	// Stage 容器
	public Map<String, Stage> mapStages = new HashMap<String, Stage>();
	// Scene 容器
	public Map<String, Scene> mapScenes = new HashMap<String, Scene>();
	// Setting 容器
	public Map<String, GameSetting> Map_Setting = new HashMap<String, GameSetting>();
	//	// Containers 定义游戏对象的属性，以便跨文件
//	public static Map<String, Containers> Games= new HashMap<String, Containers>();
	private String playerName;
	private Square[] BlockArea;
	private int MaxWidth;
	private int MaxHeight;
	private int BoomsNumber;
	// 用来记录已经开采的位置
	private ArrayList<Integer> openedID = new ArrayList<>();
	// 用来记录每一步操作
	private ArrayList<ArrayList<Integer>> steps = new ArrayList<>();
	
	/**
	 * InnerArea是当前游戏内部的逻辑棋盘
	 * -1:  Flag
	 * 0:   Blank
	 * 1~8: Number
	 * 9:   Boom
	 */
	private int[][] InnerArea;
	
	private int count = 0;
	private GameSetting setting = GameSetting.DefaultSetting;
	private ArrayList<Integer> BoomsId = new ArrayList<>();
	
	/**
	 * 构造器，传入游戏名字
	 *
	 * @param name
	 */
	public GameContainers(String name){
		this.playerName = name;
	}
	
	public String getName(){
		return playerName;
	}
	
	public void setName(String name){
		this.playerName = name;
	}
	
	public GameSetting getSetting(){
		return setting;
	}
	
	public void setSetting(GameSetting setting){
		this.setting = setting;
	}
	
	public Square[] getBlockArea(){
		return BlockArea;
	}
	
	public void setBlockArea(Square[] blockArea){
		this.BlockArea = blockArea;
	}
	
	public void setBlockArea(int[][] area){
	
	}
	
	public int getCount(){
		return count;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	
	public void count(){
		this.count++;
	}
	
	public int[][] getInnerArea(){
		return InnerArea;
	}
	
	public void setInnerArea(int[][] innerArea){
		InnerArea = innerArea;
	}
	
	public ArrayList<Integer> getBoomsId(){
		return BoomsId;
	}
	
	public void setBoomsId(ArrayList<Integer> boomsId){
		BoomsId = boomsId;
	}
	
	public int getMaxWidth(){
		return MaxWidth;
	}
	
	public void setMaxWidth(int maxWidth){
		MaxWidth = maxWidth;
	}
	
	public int getMaxHeight(){
		return MaxHeight;
	}
	
	public void setMaxHeight(int maxHeight){
		MaxHeight = maxHeight;
	}
	
	public int getBoomsNumber(){
		return BoomsNumber;
	}
	
	public void setBoomsNumber(int boomsNumber){
		BoomsNumber = boomsNumber;
	}
	
	public ArrayList<Integer> getOpenedID(){
		return openedID;
	}
}
