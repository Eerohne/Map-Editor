/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core;

import Engine.Level.Level;
import Engine.Util.Time;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
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
//for now, discard any code written here, it's only test nonsense
public class Game extends Application{
    private static Level currentLevel;
    
    public void start(Stage stage) throws Exception {
        
        AnchorPane root = new AnchorPane();
        Canvas renderLayer = new Canvas(800, 600);
        renderLayer.widthProperty().bind(root.widthProperty());
        renderLayer.heightProperty().bind(root.heightProperty());
        GraphicsContext cont = renderLayer.getGraphicsContext2D();
        
        Scene scene = new Scene(root, 800, 600);
        
        Button button1 = new Button("fill");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
               redraw(cont);
            }
        });
        
        root.getChildren().addAll(renderLayer, button1);
        root.setTopAnchor(renderLayer, 0.0);
        root.setBottomAnchor(renderLayer, 0.0);
        root.setRightAnchor(renderLayer, 0.0);
        root.setLeftAnchor(renderLayer, 0.0);
        
        new AnimationTimer() { //Game main loop

            @Override
            public void handle(long l) {
                Time.update();
                redraw(cont);
                stage.setTitle("Optik Engine -> FPS : " + Integer.toString(Time.fps));
                long a = 0;
                int b = 50;
                for(int x = 0; x < b; x++)
                {
                    for(int y = 0; y < b; y++)
                    {
                        for(int z = 0; z < b; z++)
                        {
                            a+= Math.pow(x, Math.pow(y, z));
                        }
                    }
                }
            }
        }.start();
       
        stage.setScene(scene);
        stage.show();
    }
    
    public static Level getCurrentLevel()
    {
        return currentLevel;
    }
    
    public static void redraw(GraphicsContext cont)
    {
        //System.out.println(cont.getCanvas().getWidth() +" : "+ cont.getCanvas().getHeight());
        cont.setFill(Color.YELLOW);
        cont.fillRect(0,10,cont.getCanvas().getWidth(),cont.getCanvas().getHeight());
    }
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
