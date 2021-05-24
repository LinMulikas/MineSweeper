package GameControl;

import javafx.scene.control.Button;

public abstract class Block extends Button{
	private int XIndex;
	private int YIndex;
	private int numID;
	
	private int PreNumber;
	
	public int getXIndex(){
		return XIndex;
	}
	
	public void setXIndex(int XIndex){
		this.XIndex = XIndex;
	}
	
	public int getYIndex(){
		return YIndex;
	}
	
	public void setYIndex(int YIndex){
		this.YIndex = YIndex;
	}
	
	public int getNumId(){
		return numID;
	}
	
	public void setNumID(int numID){
		this.numID = numID;
	}
	
	abstract void setStatus(PreStatus status);
	
	public enum PreStatus{
		CLOSE,
		FLAG,
		BOOM,
		SAFE,
		NUM1,
		NUM2,
		NUM3,
		NUM4,
		NUM5,
		NUM6,
		NUM7,
		NUM8,
	}
}


