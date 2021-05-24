package MainGame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileTest{
	public static void writeFile(){
		try{
			File writeName = new File("output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
			writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
			try(FileWriter writer = new FileWriter(writeName);
			    BufferedWriter out = new BufferedWriter(writer)
			){
				out.write("我会写入文件啦1\r\n"); // \r\n即为换行
				out.write("我会写入文件啦2\r\n"); // \r\n即为换行
				out.flush(); // 把缓存区内容压入文件
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
