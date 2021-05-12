package GameControl;

import Settings.SHAPE;

abstract class Block{
	private SHAPE blockShape;
	private Position blockPosition;
	
	abstract Position idToPosition(int id);
	
}
