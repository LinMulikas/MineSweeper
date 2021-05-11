package MainGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    
    /**
     * 本地段预计使用Pane布局，设计Image类型的数组来储存对象
     * 然后对于Image类型点击产生相应更换图片地址
     * 基本上用不到SceneBuilder
     */
    
    @Override
    public void start(Stage primaryStage)
            throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
