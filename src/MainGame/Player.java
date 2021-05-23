package MainGame;

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
	
	public int getMistake(){
		return mistake;
	}
	
	public void setMistake(int mistake){
		this.mistake = mistake;
	}
}
