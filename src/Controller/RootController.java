package Controller;

import MainGame.Containers;

public class RootController{
	public RootController(){
		Containers.Map_Controllers.put(this.getClass().getSimpleName(), this);
	}
	
}
