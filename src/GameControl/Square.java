package GameControl;

import MainGame.gameStart;
import Resource.Scheme.Scheme;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Random;

public class Square extends Block{
	// 创建基础的处理器
	static MouseClicked mouseClicked = new MouseClicked();
	static MouseEntered mouseEntered = new MouseEntered();
	static MouseExited mouseExited = new MouseExited();
	
	private Position position = new Position();
	private int PreNumber;
	private int X, Y;
	
	// 构造器
	public Square(int id, int x, int y){
		this.setWidth(40);
		this.setHeight(40);
	}
	
	public Square(int id){
		this();
		this.position.setY(Position.toY(id));
		this.setY(this.position.getY());
		this.position.setX(Position.toX(id));
		this.setX(this.position.getX());
		this.setNumID(id);
	}
	
	// 构造器 缺省
	// 基础的正方形，含所有鼠标注册事件
	public Square(){
		this.setPrefSize(40, 40);
		this.setStatus(PreStatus.CLOSE);
//		System.out.println(this.getStatus().toString());
		// 注册鼠标处理器
		this.setOnMouseClicked(mouseClicked);
		this.setOnMouseEntered(mouseEntered);
		this.setOnMouseExited(mouseExited);
		
		// 初始化数字ID
//		// 设置 初始 背景图片
//		this.setStyle(
//				"-fx-background-image: url(" + gameStart.thisGame.getScheme().pics.get("CLOSE") + ");"
//		);
	}
	
	// 创建一个合理的内部雷区
	
	public static boolean createInner(int xExcept, int yExcept){
		int x = gameStart.thisGame.getWidth();
		int y = gameStart.thisGame.getHeight();
		// 最大雷数
		int boomsNumber = gameStart.thisGame.getBoomsNumber();
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
		
		// 更新其他区域的值并检查密集程度
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

//		// 临时视觉检验
//		for(int j = 0; j < y; j++){
//			for(int i = 0; i < x; i++){
//				System.out.printf("%d ", inner[i][j]);
//			}
//			System.out.println();
//		}
		
		// 将生成的内部雷盘绑到当前游戏
		gameStart.thisGame.setInnerArea(inner);
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
		if((x >= 1) && (x <= gameStart.thisGame.getWidth())){
			if((y >= 1) && (y <= gameStart.thisGame.getHeight())){
				return true;
			}
		}
		return false;
	}

//	public static void setBlockStyle(Square[] blocks){
//		for(int i = 0; i < blocks.length; i++){
//			blocks[i].setScheme();
//		}
//	}
	
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
	 * 单个雷的视觉更新方法
	 */
	public void setView(Scheme iScheme){
		switch(this.getStatus()){
			case CLOSE:
//				if(this.getNumId() == 1){
//					System.out.println("设置为A");
//				}
				
				this.setStyle(
						"-fx-background-image: url(" + gameStart.thisGame.getScheme().pics.get("CLOSE") + ");"
				);
				break;
			case FLAG:
				break;
			case BOOM:
				break;
			case SAFE:
				break;
			case NUM1:
				break;
			case NUM2:
				break;
			case NUM3:
				break;
			case NUM4:
				break;
			case NUM5:
				break;
			case NUM6:
				break;
			case NUM7:
				break;
			case NUM8:
				break;
			case NUM9:
				break;
		}
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
	public int getPreNumber(){
		return PreNumber;
	}
	
	/**
	 * sweep群
	 */
	
	public void setPreNumber(int preNumber){
		PreNumber = preNumber;
	}
	
	public int getX(){
		return X;
	}
	
	public void setX(int x){
		X = x;
	}
	
	public int getY(){
		return Y;
	}
	
	/**
	 * 鼠标处理器的静态类
	 */
	
	public void setY(int y){
		Y = y;
	}
	
	/**
	 * 扫雷核心算法
	 */
	static class MouseClicked implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent event){
			// 得到点击次数和事件源Object
			int clickCnt = event.getClickCount();
			// 得到事件源方块
			Square iSquare = (Square) event.getSource();
			// 得到鼠标按键信息
			MouseButton iButton = event.getButton();
			// 分左右键讨论按键情况
			switch(event.getButton()){
				case PRIMARY:
					// 位置检验
					System.out.printf("按钮%d被左键单击,位置(%d,%d)\n", iSquare.getNumId(), iSquare.position.getX(),
							iSquare.position.getY());
					// 记录点击次数
					gameStart.thisGame.count();
					// 计数检验
					System.out.printf("当前点击了%d次\n", gameStart.thisGame.getCount());
					// 首击生成棋盘
					if(gameStart.thisGame.getCount() == 1){
						boolean judge = false;
						while(!judge){
							judge = Square.createInner(iSquare.getX(), iSquare.getY());
						}
					}
					
					// 临时视觉
					// 临时视觉检验
					for(int j = 0; j < gameStart.thisGame.getHeight(); j++){
						for(int i = 0; i < gameStart.thisGame.getWidth(); i++){
							System.out.printf("%d ", gameStart.thisGame.getInnerArea()[i][j]);
						}
						System.out.println();
					}
					
					iSquare.sweep(iSquare.position.getX(), iSquare.position.getY());
					break;
				case SECONDARY:
					System.out.printf("按钮%d被右键单击,位置(%d,%d)\n", iSquare.getNumId(), iSquare.position.getX(),
							iSquare.position.getY());
					iSquare.sweep(iSquare.position.getX(), iSquare.position.getY());
			}
			
		}
		
	}
	
	static class MouseEntered implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent event){
			// 得到事件源方块
			Square iSquare = (Square) event.getSource();
			// 设置鼠标悬停图片
			iSquare.setStyle(
					"-fx-background-image: url(" + gameStart.thisGame.getScheme().pics.get("ENTER") + ");"
			);
		}
	}
	
	static class MouseExited implements EventHandler<MouseEvent>{
		
		@Override
		public void handle(MouseEvent event){
			// 得到事件源方块
			Square iSquare = (Square) event.getSource();
			iSquare.setStyle(
					"-fx-background-image: url(" + gameStart.thisGame.getScheme().pics.get("CLOSE") + ");"
			);
		}
	}
}