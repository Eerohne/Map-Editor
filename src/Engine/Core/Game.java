/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core;

import Engine.Level.Level;
import Engine.Entity.GameEntity.Entity_Coin;
import Engine.Renderer.*;
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
    private static WindowManager windowManager;
    
    private static Level currentLevel;
    
    public void start(Stage stage) throws Exception {
        
        windowManager = new WindowManager(stage, 800, 600);
        //give the renderer the canvas graphics context here -> renderer.setCanvasContext(windowManager.getRenderContext);
        
        
        //now load the initial level -> currentLevel = LevelLoader.load(path_to_level_file);
        new AnimationTimer() { //Game main loop

            @Override
            public void handle(long l) {
                Time.update();
                stage.setTitle("Optik Engine -> FPS : " + Integer.toString(Time.fps));
                
                currentLevel.update();
                //render the game -> renderer.render();
            }
        }.start();
       
        Scene scene = new Scene(windowManager, windowManager.getWidth(), windowManager.getHeight()); //set windows inside the scene
        stage.setScene(scene);
        stage.show();
    }
    
    public static Level getCurrentLevel()
    {
        return currentLevel;
    }
    
    public static void resizeWindow(int width, int height)
    {
        System.out.println(windowManager.renderContext.getCanvas().getWidth());
        windowManager.resizeWindow(width, height);
        System.out.println(windowManager.renderContext.getCanvas().getWidth());
        //specify to the renderer the new render width and height -> Renderer.setRendererSize
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
