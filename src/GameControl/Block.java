package GameControl;

import Settings.SHAPE;
import javafx.scene.control.Button;

abstract class Block extends Button{
	static SHAPE blockShape;
	private int XIndex;
	private int YIndex;
	private int numID;
	private PreStatus status;
	private int PreNumber;
	
	abstract int idToX(int id);
	
	abstract int idToY(int id);
	
	public SHAPE getBlockShape(){
		return blockShape;
	}
	
	public void setBlockShape(SHAPE blockShape){
		blockShape = blockShape;
	}
	
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
	
	public int getNumIDId(){
		return numID;
	}
	
	public void setNumID(int numID){
		this.numID = numID;
	}
	
	abstract boolean sweep(Position P);
	
	public PreStatus getStatus(){
		return status;
	}
	
	public void setStatus(PreStatus status){
		this.status = status;
	}
	
	enum PreStatus{
		OPEN,
		CLOSE,
		FLAG,
		BOOM;
	}
}


