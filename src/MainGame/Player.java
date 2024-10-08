package MainGame;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.io.Serializable;

public class Player implements Serializable{
	public String playerName;
	private int id = 0;
	private int score = 0;
	private int mistake = 0;
	
	public Player(String name){
		this.playerName = name;
	}
	
	public Player(String name, int id){
		this.playerName = name;
	}
	
	public int getScore(){
		return score;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public void addScore(){
		this.score++;
	}
	
	public void minusScore(){
		this.score--;
	}
	
	public void addMistake(){
		this.mistake++;
	}
	
	public void minusMistake(){
		this.mistake--;
	}
	
	public int getMistake(){
		return mistake;
	}
	
	public void setMistake(int mistake){
		this.mistake = mistake;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
}
