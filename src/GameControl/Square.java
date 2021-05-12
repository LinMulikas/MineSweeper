package GameControl;

import MainGame.Containers;
import Resource.Scene.SceneCreater;
import Settings.SHAPE;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Square extends Block{
	private Position position = new Position();
	
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
		// 按钮被点了
		this.setOnAction(event -> {
			System.out.printf("按钮%d被点击,位置(%d,%d)\n", this.getNumIDId(), this.position.getX(), this.position.getY());
			// 调用扫雷器
			this.sweep(this.position);
		});
		
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
		return true;
	}
}
