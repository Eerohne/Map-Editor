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
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//Merouane Issad
//for now, discard any code written here, it's only test nonsense
public class Game extends Application{
    private static WindowManager windowManager;
    private static Level currentLevel;
    
    //flags
    boolean isRunning = true;
    boolean pauseActive = true;
    
    public void start(Stage stage) throws Exception {
        
        windowManager = new WindowManager(stage, 800, 600);
        //give the renderer the canvas graphics context here -> renderer.setCanvasContext(windowManager.getRenderContext);
        
        
        //now load the initial level -> currentLevel = LevelLoader.load(path_to_level_file);
        new AnimationTimer() { //Game main loop

            @Override
            public void handle(long l) {
                if(isRunning)
                {
                    Time.update();
                    stage.setTitle("Optik Engine -> FPS : " + Integer.toString(Time.fps));
                    //update all entities in the level -> currentLevel.update();
                    //render the game -> renderer.render();

                    //test render, this would normally be in the reanderer class
                    windowManager.renderContext.clearRect(0, 0, windowManager.renderContext.getCanvas().getWidth(), windowManager.renderContext.getCanvas().getHeight());
                    windowManager.renderContext.setFill(Color.BLUE);
                    windowManager.renderContext.fillRect(0, 0, windowManager.renderContext.getCanvas().getWidth()/2, 200);
                }
            }
        }.start();
       
        Scene scene = new Scene(windowManager, windowManager.getWidth(), windowManager.getHeight()); //set windows inside the scene
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.show();
    }
    
    public static Level getCurrentLevel()
    {
        return currentLevel;
    }
    
    public static WindowManager getWindowManager()
    {
        return windowManager;
    }
    
    public void pauseGame(boolean state) //true -> pause, false -> play
    {
        this.isRunning = !state;
        windowManager.setPauseMenuVisibility(state);
    }
    
    public void togglepauseGame()
    {
        pauseGame(isRunning);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
