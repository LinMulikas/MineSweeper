package MainGame;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Player{
	public String playerName;
	private int score = 0;
	private int mistake = 0;
	
	public Player(String name){
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
	
	public int getMistake(){
		return mistake;
	}
	
	public void setMistake(int mistake){
		this.mistake = mistake;
	}
	
}
