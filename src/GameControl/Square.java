package GameControl;

import MainGame.MainGame;
import Settings.SHAPE;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jdk.jfr.internal.tool.Main;

import java.util.ArrayList;
import java.util.Random;

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
		this.position.setY(Position.toY(id));
		this.position.setX(Position.toX(id));
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
	
	// 创建一个合理的内部雷区
	
	public static boolean createInner(int xExcept, int yExcept){
		int x = MainGame.thisGame.getSetting().getAreaWidth();
		int y = MainGame.thisGame.getSetting().getAreaHeight();
		// 最大雷数
		int boomsNumber = MainGame.thisGame.getBoomsNumber();
		// 内部雷盘
		int[][] inner = new int[x][y];
		// 存雷id的列表
		ArrayList<Integer> boomsID = new ArrayList<>();
		// 随机初始化指定数量的雷
		for(int cnt = 0; cnt < boomsNumber; cnt++){
			Random ran = new Random();
			int theID = 1 + ran.nextInt(x*y);
			if(!boomsID.contains(theID)){
				System.out.printf("第%d个雷,id:%d\n", cnt + 1, theID);
				boomsID.add(theID);
				inner[Position.toX(theID) - 1][Position.toY(theID) - 1] = 9;
				System.out.printf("(%d,%d)是雷.\n", Position.toX(theID), Position.toY(theID));
			}
		}

//		if(boomsID.contains(xExcept*yExcept)){
//
//		}
		
		// 更新其他区域的值
		for(int j = 0; j < y; j++){
			for(int i = 0; i < x; i++){
				if(inner[i][j] == 9){
					int cntBooms = 0;
					int px = i + 1;
					int py = j + 1;
					// ←
					if(Square.isOnArea(px - 1, py)){
						if(inner[i - 1][j] == 9){
							cntBooms++;
						}
					}
					// →
					if(Square.isOnArea(px + 1, py)){
						if(inner[i + 1][j] == 9){
							cntBooms++;
						}
					}
					// ↑
					if(Square.isOnArea(px, py - 1)){
						if(inner[i][j - 1] == 9){
							cntBooms++;
						}
					}
					// ↓
					if(Square.isOnArea(px, py + 1)){
						if(inner[i][j + 1] == 9){
							cntBooms++;
						}
					}
					// ↖
					if(Square.isOnArea(px - 1, py - 1)){
						if(inner[i - 1][j - 1] == 9){
							cntBooms++;
						}
					}
					// ↗
					if(Square.isOnArea(px + 1, py - 1)){
						if(inner[i + 1][j - 1] == 9){
							cntBooms++;
						}
					}
					// ↙
					if(Square.isOnArea(px - 1, py + 1)){
						if(inner[i - 1][j + 1] == 9){
							cntBooms++;
						}
					}
					// ↘
					if(Square.isOnArea(px + 1, py + 1)){
						if(inner[i + 1][j + 1] == 9){
							cntBooms++;
						}
					}
					if(cntBooms == 8){
						return false;
					}
					continue;
				}
				int px = i + 1;
				int py = j + 1;
				// 初始化9个方向的数字
				// ←
				if(Square.isOnArea(px - 1, py)){
					if(inner[i - 1][j] == 9){
						inner[i][j]++;
					}
				}
				// →
				if(Square.isOnArea(px + 1, py)){
					if(inner[i + 1][j] == 9){
						inner[i][j]++;
					}
				}
				// ↑
				if(Square.isOnArea(px, py - 1)){
					if(inner[i][j - 1] == 9){
						inner[i][j]++;
					}
				}
				// ↓
				if(Square.isOnArea(px, py + 1)){
					if(inner[i][j + 1] == 9){
						inner[i][j]++;
					}
				}
				// ↖
				if(Square.isOnArea(px - 1, py - 1)){
					if(inner[i - 1][j - 1] == 9){
						inner[i][j]++;
					}
				}
				// ↗
				if(Square.isOnArea(px + 1, py - 1)){
					if(inner[i + 1][j - 1] == 9){
						inner[i][j]++;
					}
				}
				// ↙
				if(Square.isOnArea(px - 1, py + 1)){
					if(inner[i - 1][j + 1] == 9){
						inner[i][j]++;
					}
				}
				// ↘
				if(Square.isOnArea(px + 1, py + 1)){
					if(inner[i + 1][j + 1] == 9){
						inner[i][j]++;
					}
				}
				
			}
		}
		
		return true;
	}
	
	/**
	 * 检验当前Position是否在界内
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	static boolean isOnArea(int x, int y){
		if((x >= 1) && (x <= MainGame.thisGame.getSetting().getAreaWidth())){
			if((y >= 1) && (y <= MainGame.thisGame.getSetting().getAreaHeight())){
				return true;
			}
		}
		return false;
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
	boolean sweep(int x, int y){
		return false;
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

//	boolean sweepHere(int x, int y){
//
//	}
//
//	boolean sweepLeft(int x, int y){
//
//		if(isOnArea(x - 1, y)){
//
//		}
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
	 * sweep群
	 */
	
	public void setPreNumber(int preNumber){
		PreNumber = preNumber;
	}
	
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
						MainGame.thisGame.count();
						System.out.printf("当前点击了%d次\n", MainGame.thisGame.getCount());
						if(MainGame.thisGame.getCount() == 1){
							while(Square.createInner(iSquare.position.getX(), iSquare.position.getY())){ ; }
//							MainGame.thisGame.setBlockArea(innerArea);
						}
						iSquare.sweep(iSquare.position.getX(), iSquare.position.getY());
						break;
					case SECONDARY:
						System.out.printf("按钮%d被右键单击,位置(%d,%d)\n", iSquare.getNumIDId(), iSquare.position.getX(),
								iSquare.position.getY());
						iSquare.sweep(iSquare.position.getX(), iSquare.position.getY());
				}
				
			}
			
		}
		
	}
}