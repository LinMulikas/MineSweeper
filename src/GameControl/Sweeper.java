package GameControl;

import Settings.SHAPE;

public class Sweeper{
	private SHAPE iSHAPE;
	
	static boolean sweep(Position P){
		System.out.printf("即将清扫(%d,%d)", P.getX(), P.getY());
		return true;
	}
	
}
	
