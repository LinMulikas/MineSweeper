package GameControl;

import MainGame.Game;
import com.sun.xml.internal.bind.v2.runtime.property.PropertyFactory;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.*;

public class rankPlayer{
	
	private final SimpleStringProperty name;
	private final SimpleStringProperty score;
	private final SimpleStringProperty mistake;
	
	public rankPlayer(String name, String score, String mistake){
		this.name = new SimpleStringProperty(name);
		this.score = new SimpleStringProperty(score);
		this.mistake = new SimpleStringProperty(mistake);
	}
	
	public static TableView tableRank(){
		ObservableList<rankPlayer> data =
				FXCollections.observableArrayList();
		
		ArrayList<String> rankData = Game.loadRank();
//		System.out.println(rankData.toString());
		System.out.println(rankData.size());
		int number = (rankData.size())/3;
		System.out.println(number);
		
		for(int i = 1; i < number; i++){
			data.add(
					new rankPlayer(rankData.get(i*3), rankData.get(i*3 + 1), rankData.get(i*3 + 2)));
		}
		
		TableView tableRank = new TableView();
		
		TableColumn nameCol = new TableColumn("玩家姓名");
		nameCol.setMinWidth(100);
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn scoreCol = new TableColumn("玩家得分");
		scoreCol.setMinWidth(100);
		scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
		
		TableColumn mistakeCol = new TableColumn("失误次数");
		mistakeCol.setMinWidth(100);
		mistakeCol.setCellValueFactory(new PropertyValueFactory<>("mistake"));
		
		tableRank.setItems(data);
		tableRank.getColumns().addAll(nameCol, scoreCol, mistakeCol);
		
		return tableRank;
	}
	
	public String getName(){
		return name.get();
	}
	
	public SimpleStringProperty nameProperty(){
		return name;
	}
	
	public String getScore(){
		return score.get();
	}
	
	public SimpleStringProperty scoreProperty(){
		return score;
	}
	
	public String getMistake(){
		return mistake.get();
	}
	
	public SimpleStringProperty mistakeProperty(){
		return mistake;
	}
	
}
