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
	private int X, Y;
	private PreStatus status;

	// 构造器
	public Square(int id, int x, int y){
		this.setWidth(40);
		this.setHeight(40);
	}

	public Square(int id){
		this();
		this.position.setY(Position.idToY(id));
		this.setY(this.position.getY());
		this.position.setX(Position.idToX(id));
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

	public static boolean createInner(int xExcept, int yExcept){
		int width = gameStart.thisGame.getWidth();
		int height = gameStart.thisGame.getHeight();

		int clickedID = Position.positionToId(xExcept, yExcept);
//		System.out.println(clickedID);

		// 最大雷数
		int boomsNumber = gameStart.thisGame.getBoomsNumber();

		// 内部雷盘
		int[][] inner = new int[width][height];
		// 存雷id的列表
		ArrayList<Integer> boomsID = new ArrayList<>();

		// 随机初始化指定数量的雷
		for(int cnt = 0; cnt < boomsNumber; cnt++){
			Random ran = new Random();
			// 生成雷的id
			int theID = 1 + ran.nextInt(width*height);
			// 排除了首发触雷
			if(theID != clickedID){
				// 排除已经出现的雷
				if(!boomsID.contains(theID)){
//					System.out.printf("第%d个雷,id:%d\n", cnt + 1, theID);
					boomsID.add(theID);
					inner[Position.idToX(theID) - 1][Position.idToY(theID) - 1] = 9;
//					System.out.printf("(%d,%d)是雷.\n", Position.idToX(theID), Position.idToY(theID));

				} else{
					cnt--;
				}
			} else{
				cnt--;
			}
		}

		// 更新其他区域的值并检查密集程度
		for(int j = 0; j < height; j++){
			for(int i = 0; i < width; i++){
				// 排除密集雷
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

		// 首发必须为空
		if(inner[xExcept - 1][yExcept - 1] != 0){
//			System.out.println("到了");
			return false;
		}

		// 成功生成了一个可用的InnerArea
		gameStart.thisGame.setInnerArea(inner);

		// 记录雷的位置
		for(int j = 0; j < gameStart.thisGame.getHeight(); j++){
			for(int i = 0; i < gameStart.thisGame.getWidth(); i++){
//				System.out.printf("%d ", gameStart.thisGame.getInnerArea()[i][j]);
				if(gameStart.thisGame.getInnerArea()[i][j] == 9){
					gameStart.thisGame.getRecorder().getUnOpenBooms().add(Position.positionToId(i + 1, j + 1));
				}
			}
//			System.out.println();
		}
//		System.out.println(gameStart.thisGame.getRecorder().getUnOpenBooms().toString());
		return true;
	}

	// 创建一个合理的内部雷区

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

	public PreStatus getStatus(){
		return status;
	}

	@Override
	public void setStatus(PreStatus status){
		this.status = status;
		this.setView(gameStart.thisGame.getScheme());
	}

	/**
	 * <pre>
	 *     单个方块的打开方法
	 *     后台更新 stepCount
	 *     后台更新 stepList
	 * </pre>
	 */
	public void openHere(boolean type){
		int clickID = this.getNumId();
//		System.out.println(gameStart.thisGame.getCount());
		//
		switch(this.findInnerNumber()){
			case -1:
				this.setStatus(PreStatus.FLAG);
				break;
			case 0:
				this.setStatus(PreStatus.SAFE);
				break;
			case 9:
				this.setStatus(PreStatus.BOOM);
				break;
			case 1:
				this.setStatus(PreStatus.NUM1);
				break;
			case 2:
				this.setStatus(PreStatus.NUM2);
				break;
			case 3:
				this.setStatus(PreStatus.NUM3);
				break;
			case 4:
				this.setStatus(PreStatus.NUM4);
				break;
			case 5:
				this.setStatus(PreStatus.NUM5);
				break;
			case 6:
				this.setStatus(PreStatus.NUM6);
				break;
			case 7:
				this.setStatus(PreStatus.NUM7);
				break;
			case 8:
				this.setStatus(PreStatus.NUM8);
				break;
		}

		// 改变视觉效果
		// 记录操作(open是强制点击)
		if(type){
			gameStart.thisGame.getRecorder().getStep().add(clickID);
		}
//		System.out.printf("id:%d 被打开\n", clickID);
		// 级联扫雷
		if(gameStart.thisGame.getSweepType().equals(sweepType.CONTINOUS)){
			if(this.getStatus().equals(PreStatus.SAFE)){
				this.sweep();
			}
		}
	}

	// 级联扫雷
	// 当前方块已经被揭开,并且是空白,向周围扩散
	public void sweep(){
		int x = this.getX();
		int y = this.getY();
		// 如果是安全区域就打开周围
		if(isOnArea(x - 1, y)){
			// 检查是否已经开采
			if(gameStart.thisGame.getABlock(x - 1, y).getStatus().equals(PreStatus.CLOSE)){
				// 只能自动开采关闭的方块
				// 动画效果等待0.001秒
//				try{
//					Thread.sleep(1);
//				}
//				catch(InterruptedException e1){
//					//捕获异常
//					e1.printStackTrace();
//				}

				gameStart.thisGame.getABlock(x - 1, y).openHere(true);
				// 顺便如果是SAFE，进行sweep级联
				if(gameStart.thisGame.getABlock(x - 1, y).getStatus().equals(PreStatus.SAFE)){
					gameStart.thisGame.getABlock(x - 1, y).sweep();
				}
			}
		}
		if(isOnArea(x + 1, y)){
			// 检查是否已经开采
			if(gameStart.thisGame.getABlock(x + 1, y).getStatus().equals(PreStatus.CLOSE)){
				// 只能自动开采关闭的方块
				// 动画效果等待0.001秒
//				try{
//					Thread.sleep(1);
//				}
//				catch(InterruptedException e1){
//					//捕获异常
//					e1.printStackTrace();
//				}
				gameStart.thisGame.getABlock(x + 1, y).openHere(true);
				// 顺便如果是SAFE，进行sweep级联
				if(gameStart.thisGame.getABlock(x + 1, y).getStatus().equals(PreStatus.SAFE)){
					gameStart.thisGame.getABlock(x + 1, y).sweep();
				}
			}
		}
		if(isOnArea(x, y - 1)){
			// 检查是否已经开采
			if(gameStart.thisGame.getABlock(x, y - 1).getStatus().equals(PreStatus.CLOSE)){
				// 只能自动开采关闭的方块
				// 动画效果等待0.001秒
//				try{
//					Thread.sleep(1);
//				}
//				catch(InterruptedException e1){
//					//捕获异常
//					e1.printStackTrace();
//				}
				gameStart.thisGame.getABlock(x, y - 1).openHere(true);
				// 顺便如果是SAFE，进行sweep级联
				if(gameStart.thisGame.getABlock(x, y - 1).getStatus().equals(PreStatus.SAFE)){
					gameStart.thisGame.getABlock(x, y - 1).sweep();
				}
			}
		}
		if(isOnArea(x, y + 1)){
			// 检查是否已经开采
			if(gameStart.thisGame.getABlock(x, y + 1).getStatus().equals(PreStatus.CLOSE)){
				// 只能自动开采关闭的方块
				gameStart.thisGame.getABlock(x, y + 1).openHere(true);
				// 顺便如果是SAFE，进行sweep级联
				if(gameStart.thisGame.getABlock(x, y + 1).getStatus().equals(PreStatus.SAFE)){
					gameStart.thisGame.getABlock(x, y + 1).sweep();
				}
			}
		}
		if(isOnArea(x - 1, y - 1)){
			// 检查是否已经开采
			if(gameStart.thisGame.getABlock(x - 1, y - 1).getStatus().equals(PreStatus.CLOSE)){
				// 只能自动开采关闭的方块
				// 动画效果等待0.001秒
//				try{
//					Thread.sleep(1);
//				}
//				catch(InterruptedException e1){
//					//捕获异常
//					e1.printStackTrace();
//				}
				gameStart.thisGame.getABlock(x - 1, y - 1).openHere(true);
				// 顺便如果是SAFE，进行sweep级联
				if(gameStart.thisGame.getABlock(x - 1, y - 1).getStatus().equals(PreStatus.SAFE)){
					gameStart.thisGame.getABlock(x - 1, y - 1).sweep();
				}
			}
		}
		if(isOnArea(x - 1, y + 1)){
			// 检查是否已经开采
			if(gameStart.thisGame.getABlock(x - 1, y + 1).getStatus().equals(PreStatus.CLOSE)){
				// 只能自动开采关闭的方块
				// 动画效果等待0.001秒
//				try{
//					Thread.sleep(1);
//				}
//				catch(InterruptedException e1){
//					//捕获异常
//					e1.printStackTrace();
//				}
				gameStart.thisGame.getABlock(x - 1, y + 1).openHere(true);
				// 顺便如果是SAFE，进行sweep级联
				if(gameStart.thisGame.getABlock(x - 1, y + 1).getStatus().equals(PreStatus.SAFE)){
					gameStart.thisGame.getABlock(x - 1, y + 1).sweep();
				}
			}
		}
		if(isOnArea(x + 1, y - 1)){
			// 检查是否已经开采
			if(gameStart.thisGame.getABlock(x + 1, y - 1).getStatus().equals(PreStatus.CLOSE)){
				// 只能自动开采关闭的方块
				// 动画效果等待0.001秒
//				try{
//					Thread.sleep(1);
//				}
//				catch(InterruptedException e1){
//					//捕获异常
//					e1.printStackTrace();
//				}
				gameStart.thisGame.getABlock(x + 1, y - 1).openHere(true);
				// 顺便如果是SAFE，进行sweep级联
				if(gameStart.thisGame.getABlock(x + 1, y - 1).getStatus().equals(PreStatus.SAFE)){
					gameStart.thisGame.getABlock(x + 1, y - 1).sweep();
				}
			}
		}
		if(isOnArea(x + 1, y + 1)){
			// 检查是否已经开采
			if(gameStart.thisGame.getABlock(x + 1, y + 1).getStatus().equals(PreStatus.CLOSE)){
				// 只能自动开采关闭的方块
				// 动画效果等待0.001秒
//				try{
//					Thread.sleep(1);
//				}
//				catch(InterruptedException e1){
//					//捕获异常
//					e1.printStackTrace();
//				}
				gameStart.thisGame.getABlock(x + 1, y + 1).openHere(true);
				// 顺便如果是SAFE，进行sweep级联
				if(gameStart.thisGame.getABlock(x + 1, y + 1).getStatus().equals(PreStatus.SAFE)){
					gameStart.thisGame.getABlock(x + 1, y + 1).sweep();
				}
			}
		}
	}

	/**
	 * 更新当前方块的主题
	 *
	 * @param iScheme 传入的主题
	 */
	public void setView(Scheme iScheme){
		switch(this.getStatus()){
			case CLOSE:
				this.setStyle(
						"-fx-background-image: url(" + iScheme.pics.get("CLOSE") + ");"
				);
				break;
			case FLAG:
				this.setStyle(
						"-fx-background-image: url(" + iScheme.pics.get("FLAG") + ");"
				);
				break;
			case BOOM:
				this.setStyle(
						"-fx-background-image: url(" + iScheme.pics.get("BOOM") + ");"
				);
				break;
			case SAFE:
				this.setStyle(
						"-fx-background-image: url(" + iScheme.pics.get("SAFE") + ");"
				);
				break;
			case NUM1:
				this.setStyle(
						"-fx-background-image: url(" + iScheme.pics.get("NUM1") + ");"
				);
				break;
			case NUM2:
				this.setStyle(
						"-fx-background-image: url(" + iScheme.pics.get("NUM2") + ");"
				);
				break;
			case NUM3:
				this.setStyle(
						"-fx-background-image: url(" + iScheme.pics.get("NUM3") + ");"
				);
				break;
			case NUM4:
				this.setStyle(
						"-fx-background-image: url(" + iScheme.pics.get("NUM4") + ");"
				);
				break;
			case NUM5:
				this.setStyle(
						"-fx-background-image: url(" + iScheme.pics.get("NUM5") + ");"
				);
				break;
			case NUM6:
				this.setStyle(
						"-fx-background-image: url(" + iScheme.pics.get("NUM6") + ");"
				);
				break;
			case NUM7:
				this.setStyle(
						"-fx-background-image: url(" + iScheme.pics.get("NUM7") + ");"
				);
				break;
			case NUM8:
				this.setStyle(
						"-fx-background-image: url(" + iScheme.pics.get("NUM8") + ");"
				);
				break;
		}
	}

	/**
	 * sweep群
	 */

	public int findInnerNumber(){
		int i = this.getX() - 1;
		int j = this.getY() - 1;
		return gameStart.thisGame.getInnerArea()[i][j];
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

	public enum sweepType{
		CONTINOUS,
		SINGLE;
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
					// 如果点击了未开放的方块
					if(iSquare.getStatus().equals(PreStatus.CLOSE)){
						// 计数
						gameStart.thisGame.count();
						//						gameStart.thisGame.getInfoArea().appendText("ID:" + iSquare.getNumId()
						//						+ "\n\n");
//
						if((gameStart.thisGame.getStepCount() == 1)){
							boolean judge = false;
							while(!judge){
								judge = Square.createInner(iSquare.getX(), iSquare.getY());
							}
						}

//						// 临时视觉检验
//						for(int j = 0; j < gameStart.thisGame.getHeight(); j++){
//							for(int i = 0; i < gameStart.thisGame.getWidth(); i++){
//								System.out.printf("%d ", gameStart.thisGame.getInnerArea()[i][j]);
//							}
//							System.out.println();
//						}

						// 扫雷
						gameStart.thisGame.getRecorder().setInner(gameStart.thisGame.getInnerArea());
						iSquare.openHere(true);
						gameStart.thisGame.getRecorder().update();
						gameStart.thisGame.getRecorder().getOpenedID().add(iSquare.getNumId());

						// 信息输出
						gameStart.thisGame.getInfoArea().appendText("\n");
						gameStart.thisGame.getInfoArea().appendText("\n");
						gameStart.thisGame.getInfoArea().appendText("第" + gameStart.thisGame.getStepCount() + "步：\n"
						);
						gameStart.thisGame.getInfoArea().appendText("玩家" +
								gameStart.thisGame.getThisPlayer().playerName +
								"点击了(" + iSquare.position.getX() + "," + iSquare.position.getY() + ").");
						// 信息板更新
//						// 临时检验
//						for(int i : gameStart.thisGame.getRecorder().getStep()){
//							System.out.printf("%d ", i);
//						}

						// 更新分数
						// 如果踩雷了
						if(iSquare.findInnerNumber() == 9){
							gameStart.thisGame.getRecorder().getUnOpenBooms().remove(gameStart.thisGame.getRecorder().getUnOpenBooms().indexOf(iSquare.getNumId()));
							gameStart.thisGame.getThisPlayer().minusScore();
							gameStart.thisGame.getRecorder().getScoreList().add(-1);
							gameStart.thisGame.getInfoArea().appendText("\n");
							gameStart.thisGame.getInfoArea().appendText("\n");
							gameStart.thisGame.getInfoArea().appendText("玩家" + gameStart.thisGame.getThisPlayer().playerName
									+ "踩中了地雷！\n");
							gameStart.thisGame.getThisScoreText().setText("" + gameStart.thisGame.getThisPlayer().getScore());
							gameStart.thisGame.getThisMistakeText().setText("" + gameStart.thisGame.getThisPlayer().getMistake());
							gameStart.thisGame.judgeWinner();

							// 双人模式的信息提示
							if(gameStart.thisGame.getRecorder().getPlayerNumber() == 2){
								int lastChance =
										gameStart.thisGame.getStepCount()%gameStart.thisGame.getRecorder().getStepsChance();

								if(lastChance == 0){
									String name;
									if(gameStart.thisGame.getThisPlayer() == gameStart.thisGame.getRecorder().getPlayers()[0]){
										name = gameStart.thisGame.getRecorder().getPlayers()[1].playerName;
									} else{
										name = gameStart.thisGame.getRecorder().getPlayers()[0].playerName;
									}
									gameStart.thisGame.getInfoArea().appendText("\n现在是" + name + "的回合！\n");
								} else{
									gameStart.thisGame.getInfoArea().appendText("\n" + gameStart.thisGame.getThisPlayer().playerName + "还有" + (gameStart.thisGame.getRecorder().getStepsChance() - lastChance) + "步！\n");
								}
							}
						}
						// 如果没踩雷
						else{
//							gameStart.thisGame.getThisPlayer().addScore();
							gameStart.thisGame.getRecorder().getScoreList().add(0);
							gameStart.thisGame.getThisScoreText().setText("" + gameStart.thisGame.getThisPlayer().getScore());
							gameStart.thisGame.judgeWinner();
							// 双人模式的信息提示
							if(gameStart.thisGame.getRecorder().getPlayerNumber() == 2){
								int lastChance =
										gameStart.thisGame.getStepCount()%gameStart.thisGame.getRecorder().getStepsChance();

								if(lastChance == 0){
									String name;
									if(gameStart.thisGame.getThisPlayer() == gameStart.thisGame.getRecorder().getPlayers()[0]){
										name = gameStart.thisGame.getRecorder().getPlayers()[1].playerName;
									} else{
										name = gameStart.thisGame.getRecorder().getPlayers()[0].playerName;
									}
									gameStart.thisGame.getInfoArea().appendText("\n现在是" + name + "的回合！\n");
								} else{
									gameStart.thisGame.getInfoArea().appendText("\n" + gameStart.thisGame.getThisPlayer().playerName + "还有" + (gameStart.thisGame.getRecorder().getStepsChance() - lastChance) + "步！\n");
								}
							}
						}
					}
					break;
				case SECONDARY:
					// 排除未初始化
					if(gameStart.thisGame.getStepCount() == 0){
						break;
					}
					// 如果是CLOSE的块可以标记
					if(iSquare.getStatus().equals(PreStatus.CLOSE)){
						gameStart.thisGame.count();
						iSquare.openHere(true);
						gameStart.thisGame.getRecorder().update();
						gameStart.thisGame.getRecorder().getOpenedID().add(iSquare.getNumId());

						// 信息输出
						gameStart.thisGame.getInfoArea().appendText("\n");
						gameStart.thisGame.getInfoArea().appendText("\n");
						gameStart.thisGame.getInfoArea().appendText("第" + gameStart.thisGame.getStepCount() + "步：\n"
						);
						gameStart.thisGame.getInfoArea().appendText("玩家" +
								gameStart.thisGame.getThisPlayer().playerName +
								"标记了(" + iSquare.position.getX() + "," + iSquare.position.getY() + ").\n");
//						gameStart.thisGame.getInfoArea().appendText("ID:" + iSquare.getNumId() + "\n");

						if(gameStart.thisGame.getInnerArea()[iSquare.getX() - 1][iSquare.getY() - 1] == 9){
							iSquare.setStatus(PreStatus.FLAG);
							gameStart.thisGame.getRecorder().getUnOpenBooms().remove(gameStart.thisGame.getRecorder().getUnOpenBooms().indexOf(iSquare.getNumId()));
							gameStart.thisGame.getInfoArea().appendText("玩家" + gameStart.thisGame.getThisPlayer().playerName
									+ "标记正确！\n");
							gameStart.thisGame.getThisPlayer().addScore();
							gameStart.thisGame.getRecorder().getScoreList().add(1);
							gameStart.thisGame.getThisScoreText().setText("" + gameStart.thisGame.getThisPlayer().getScore());
							// 记录分数
							iSquare.setStatus(PreStatus.FLAG);
							gameStart.thisGame.judgeWinner();
							// 双人模式的信息提示
							if(gameStart.thisGame.getRecorder().getPlayerNumber() == 2){
								int lastChance =
										gameStart.thisGame.getStepCount()%gameStart.thisGame.getRecorder().getStepsChance();

								if(lastChance == 0){
									String name;
									if(gameStart.thisGame.getThisPlayer() == gameStart.thisGame.getRecorder().getPlayers()[0]){
										name = gameStart.thisGame.getRecorder().getPlayers()[1].playerName;
									} else{
										name = gameStart.thisGame.getRecorder().getPlayers()[0].playerName;
									}
									gameStart.thisGame.getInfoArea().appendText("\n现在是" + name + "的回合！\n");
								} else{
									gameStart.thisGame.getInfoArea().appendText("\n" + gameStart.thisGame.getThisPlayer().playerName + "还有" + (gameStart.thisGame.getRecorder().getStepsChance() - lastChance) + "步！\n");
								}
							}
						} else{
							// 记录失误数
							gameStart.thisGame.getInfoArea().appendText("玩家" + gameStart.thisGame.getThisPlayer().playerName
									+ "标记错误！\n");
//							gameStart.thisGame.getThisPlayer().minusScore();
							gameStart.thisGame.getThisPlayer().addMistake();
							gameStart.thisGame.getRecorder().getScoreList().add(2);
							gameStart.thisGame.getThisScoreText().setText("" + gameStart.thisGame.getThisPlayer().getScore());
							gameStart.thisGame.getThisMistakeText().setText("" + gameStart.thisGame.getThisPlayer().getMistake());
							gameStart.thisGame.judgeWinner();
							// 双人模式的信息提示
							if(gameStart.thisGame.getRecorder().getPlayerNumber() == 2){
								int lastChance =
										gameStart.thisGame.getStepCount()%gameStart.thisGame.getRecorder().getStepsChance();

								if(lastChance == 0){
									String name;
									if(gameStart.thisGame.getThisPlayer() == gameStart.thisGame.getRecorder().getPlayers()[0]){
										name = gameStart.thisGame.getRecorder().getPlayers()[1].playerName;
									} else{
										name = gameStart.thisGame.getRecorder().getPlayers()[0].playerName;
									}
									gameStart.thisGame.getInfoArea().appendText("\n现在是" + name + "的回合！\n");
								} else{
									gameStart.thisGame.getInfoArea().appendText("\n" + gameStart.thisGame.getThisPlayer().playerName + "还有" + (gameStart.thisGame.getRecorder().getStepsChance() - lastChance) + "步！\n");
								}
							}
						}
						break;
					}

					// 普通规则部分之去除旗子
//					if(iSquare.getStatus().equals(PreStatus.FLAG)){
//						gameStart.thisGame.count();
//						System.out.printf("按钮%d被右键单击,位置(%d,%d)\n", iSquare.getNumId(), iSquare.position.getX(),
//								iSquare.position.getY());
//						iSquare.setStatus(PreStatus.CLOSE);
//						break;
//					}

					break;
			}

		}

	}

	static class MouseEntered implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent event){
			// 得到事件源方块
			Square iSquare = (Square) event.getSource();
			// 设置鼠标悬停图片
			// 排除已经点击方块
			if(iSquare.getStatus().equals(PreStatus.CLOSE)){
				iSquare.setStyle(
						"-fx-background-image: url(" + gameStart.thisGame.getScheme().pics.get("ENTER") + ");"
				);
			}

		}
	}

	static class MouseExited implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent event){
			// 得到事件源方块
			Square iSquare = (Square) event.getSource();

			if(iSquare.getStatus().equals(PreStatus.CLOSE)){
				iSquare.setStyle(
						"-fx-background-image: url(" + gameStart.thisGame.getScheme().pics.get("CLOSE") + ");"
				);
			}
		}
	}
}