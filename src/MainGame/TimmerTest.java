package MainGame;

import java.util.Timer;
import java.util.TimerTask;

public class TimmerTest{
	/**
	 * 测试方法
	 */
	public static void main(){
		Timer timer = new Timer();
		timer.schedule(new MyTask(), 800);
	}
	
	private static class MyTask extends TimerTask{
		
		/**
		 * 运行方法
		 */
		@Override
		public void run(){
			System.out.println("输出");
		}
	}
}