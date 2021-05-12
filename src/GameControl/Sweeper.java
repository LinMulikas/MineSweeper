package GameControl;

import Settings.SHAPE;

abstract class Sweeper{
	private SHAPE iSHAPE;
	
	// 传入坐标进行扫雷
	abstract boolean sweep(Position P);
	
}
