/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.io.FileInputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//Merouane Issad

public class Game extends Application{
    
    public void start(Stage stage) throws Exception {
        
        AnchorPane root = new AnchorPane();
        
        FileInputStream inputstream = new FileInputStream("ressources/brick.png"); 
        Image image = new Image(inputstream);
        
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        
        
        root.getChildren().addAll(imageView);
        Scene scene = new Scene(root, 800, 600);
        
        
        //root.setTopAnchor(imageView, 0.0);
        root.setBottomAnchor(imageView, 0.0);
        root.setRightAnchor(imageView, 0.0);
        //root.setLeftAnchor(imageView, 0.0);
       
        stage.setScene(scene);
        stage.show();
    }
    
   
    public static void main(String[] args) {
        launch(args);
    }
}
