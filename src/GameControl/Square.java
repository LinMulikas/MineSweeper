package GameControl;

import Settings.SHAPE;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Square extends Block{
	static MouseHandler mouseHandler = new MouseHandler();
	private Position position = new Position();
	private int PreNumber;
	
	// 构造器
	public Square(int id, int x, int y){
		this.setBlockShape(SHAPE.SQUARE);
		this.setWidth(40);
		this.setHeight(40);
	}
	
	public Square(int id, Position position){
		this.setBlockShape(SHAPE.SQUARE);
	}
	
	public Square(int id){
		this();
		this.position.setY(1 + (id - 1)/9);
		this.position.setX(id - 9*(this.position.getY() - 1));
		this.setNumID(id);
	}
	
	// 基础的正方形，含所有鼠标注册事件
	public Square(){
		this.setPrefSize(40, 40);
		this.setBlockShape(SHAPE.SQUARE);
		this.setStatus(PreStatus.CLOSE);
		// 注册鼠标处理器
		this.setOnMouseClicked(mouseHandler);
		// 初始化数字ID
		this.setPreNumber(1);
	}
	
	@Override
	public int idToX(int id){
		
		return 0;
	}
	
	@Override
	public int idToY(int id){
		return 0;
	}
	
	@Override
	boolean sweep(Position P){
		// 调用底层清扫
		int x = P.getX();
		int y = P.getY();
//		sweepLeft(x, y);
//		sweepRight(x, y);
//		sweepUp(x, y);
//		sweepDown(x, y);
		return true;
	}
	
	/**
	 * 视觉更新方法
	 */
	public boolean setView(){
		switch(this.getStatus()){
			case OPEN:
				
				break;
			case CLOSE:
				break;
			case FLAG:
				break;
			case BOOM:
				break;
		}
		return true;
	}
	
	public int getPreNumber(){
		return PreNumber;
	}
	
	public void setPreNumber(int preNumber){
		PreNumber = preNumber;
	}
	
	/**
	 * sweep群
	 */
//	boolean isOnArea(int x, int y){
//		if((x >= 0) && (x <=))
//	}
//
//	boolean sweepLeft(int x, int y){
//
//	}
//
//	boolean sweepRight(int x, int y){
//
//	}
//
//	boolean sweepUp(int x, int y){
//
//	}
//
//	boolean sweepDown(int x, int y){
//
//	}
	
	/**
	 * 处理器群
	 */
	
	// 鼠标处理器
	
	/**
	 * 鼠标处理器的静态类
	 */
	static class MouseHandler implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent event){
			// 得到点击次数和事件源Object
			int clickCnt = event.getClickCount();
			Square iSquare = (Square) event.getSource();
			MouseButton iButton = event.getButton();
			
			// 单击事件
			if(clickCnt == 1){
				switch(event.getButton()){
					case PRIMARY:
						System.out.printf("按钮%d被左键单击,位置(%d,%d)\n", iSquare.getNumIDId(), iSquare.position.getX(),
								iSquare.position.getY());
						iSquare.sweep(iSquare.position);
						break;
					case SECONDARY:
						System.out.printf("按钮%d被右键单击,位置(%d,%d)\n", iSquare.getNumIDId(), iSquare.position.getX(),
								iSquare.position.getY());
						iSquare.sweep(iSquare.position);
				}
				
			}
			
		}
		
	}
	
}