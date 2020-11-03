/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core;

import Engine.Level.Level;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//Merouane Issad

public class Game extends Application{
    private static Level currentLevel;
    
    public void start(Stage stage) throws Exception {
        
        AnchorPane root = new AnchorPane();
        Canvas renderLayer = new Canvas(800, 600);
        renderLayer.widthProperty().bind(root.widthProperty());
        renderLayer.heightProperty().bind(root.heightProperty());
        //renderLayer.draw();
        GraphicsContext cont = renderLayer.getGraphicsContext2D();
        cont.setFill(Color.BLUE);
        cont.fillRect(0,0,800,600);
        
        cont.setFill(Color.RED);
        cont.fillRect(0,10,1000,800);
        
        Scene scene = new Scene(root, 800, 600);
        
        Button button1 = new Button("new");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
               renderLayer.resize(scene.getWidth(), scene.getHeight());
               System.out.println(scene.getWidth());
               //root.getChildren().remove(renderLayer);
               //root.getChildren().get(0).resize(scene.getWidth(), scene.getHeight());
               redraw(cont, renderLayer);
            }
        });
        
        root.getChildren().addAll(renderLayer, button1);
        root.setTopAnchor(renderLayer, 0.0);
        root.setBottomAnchor(renderLayer, 0.0);
        root.setRightAnchor(renderLayer, 0.0);
        root.setLeftAnchor(renderLayer, 0.0);
       
        stage.setScene(scene);
        stage.show();
    }
    
    public static Level getCurrentLevel()
    {
        return currentLevel;
    }
    
    public static void redraw(GraphicsContext cont, Canvas renderLayer)
    {
        cont = renderLayer.getGraphicsContext2D();
        cont.setFill(Color.YELLOW);
        cont.fillRect(0,10,cont.getCanvas().getWidth(),cont.getCanvas().getHeight());
    }
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
