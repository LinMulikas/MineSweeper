package GameControl;

import MainGame.gameStart;

import java.io.Serializable;

public class Position{
	private int x, y;
	
	public Position(){
	}
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	// 必须已经知道最大宽度才能得到id --> x,y
	public static int idToX(int id){
		int y = idToY(id);
		return (id - (gameStart.thisGame.getWidth())*(y - 1));
	}
	
	public static int idToY(int id){
		return 1 + (id - 1)/(gameStart.thisGame.getWidth());
	}
	
	public static int positionToId(int x, int y){
		int id = (y - 1)*gameStart.thisGame.getWidth() + x;
		return id;
	}
	
	public int getX(){
		return x;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
}
