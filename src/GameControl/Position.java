package GameControl;

import MainGame.gameStart;

public class Position{
	private int x, y;
	
	public Position(){
	}
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
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
	
	public int toId(){
		return (this.getY() - 1)*9 + this.getX();
	}
	
	// 必须已经知道最大宽度才能得到id --> x,y
	public static int toX(int id){
		int y = toY(id);
		return (id - (gameStart.thisGame.getWidth())*(y - 1));
	}
	
	public static int toY(int id){
		return 1 + (id - 1)/(gameStart.thisGame.getWidth());
	}
	
}
