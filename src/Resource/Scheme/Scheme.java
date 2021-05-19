package Resource.Scheme;

import java.util.HashMap;
import java.util.Map;

public enum Scheme{
	A(1),
	B(2),
	C(3);
	
	public Map<String, String> pics = new HashMap<>();
	
	Scheme(int id){
		switch(id){
			case 1:
				this.pics.put("ENTER", "file:src/Resource/Image/Useful/ENTER_1.png");
				this.pics.put("CLOSE", "file:src/Resource/Image/Useful/CLOSE_1.png");
			case 2:
				this.pics.put("ENTER", "file:src/Resource/Image/Useful/ENTER_2.png");
				this.pics.put("CLOSE", "file:src/Resource/Image/Useful/CLOSE_2.png");
			case 3:
				this.pics.put("ENTER", "file:src/Resource/Image/Useful/ENTER_3.png");
				this.pics.put("CLOSE", "file:src/Resource/Image/Useful/CLOSE_3.png");
		}
	}
	
}
