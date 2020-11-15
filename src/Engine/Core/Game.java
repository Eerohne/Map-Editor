/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core;

import Engine.Window.WindowManager;
import Engine.Entity.EntityCreator;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Level.Level;
import Engine.RaycastRenderer.Renderer;
import Engine.Util.Time;
import java.io.File;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

//Merouane Issad
//for now, discard any code written here, it's only test nonsense
public class Game extends Application{
    public static Scene scene;
    private static Stage gameStage;
    private static WindowManager windowManager;
    private static Level currentLevel;
    
    //settings
    public static int screenWidth = 1280;
    public static int screenHeight = 800;
    //flags
    public static boolean isRunning = true;
    public static boolean isRendering = true;
    public static boolean pauseActive = true;
    
    public void start(Stage stage) throws Exception {
        windowManager = new WindowManager(stage, screenWidth, screenHeight);
        Renderer.setCanvas(windowManager.getRenderCanvas());
        
        //now load the initial level -> currentLevel = LevelLoader.load(path_to_level_file);
        new AnimationTimer() { //Game main loop

            @Override
            public void handle(long l) {
                if(isRunning)
                {
                    Time.update();
                    stage.setTitle("Optik Engine -> FPS : " + Integer.toString(Time.fps));
                    //update all entities in the level -> currentLevel.update();
                }
                if(isRendering)
                    Renderer.render();
            }
        }.start();
        
        scene = new Scene(windowManager, windowManager.getWidth(), windowManager.getHeight()); //set windows inside the scene
        stage.setScene(scene);
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent key) {
                if(key.getCode() == KeyCode.ESCAPE) {
                    Game.togglepauseGame();
                }
                
                if(key.getCode() == KeyCode.W) {
                    Renderer.cam = Renderer.cam.add(0, -2 * Time.deltaTime);
                }
                if(key.getCode() == KeyCode.S) {
                    Renderer.cam = Renderer.cam.add(0, 2 * Time.deltaTime);
                }
                if(key.getCode() == KeyCode.A) {
                    Renderer.cam = Renderer.cam.add(-2 * Time.deltaTime, 0);
                }
                if(key.getCode() == KeyCode.D) {
                    Renderer.cam = Renderer.cam.add(2 * Time.deltaTime, 0);
                }
                
                if(key.getCode() == KeyCode.RIGHT) {
                    Renderer.camA += 100 * Time.deltaTime;
                }
                if(key.getCode() == KeyCode.LEFT) {
                    Renderer.camA -= 100 * Time.deltaTime;
                }
            }
        });
        
        String pathName = "ressources/style.css" ;
        File file = new File(pathName);
        if (file.exists()) {
            scene.getStylesheets().add(file.toURI().toURL().toExternalForm());
        } else {
           System.out.println("Could not find css file: "+pathName);
        }
        
        
        
        stage.setResizable(false);
        gameStage = stage;
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
    
    public static void pauseGame(boolean state) //true -> pause, false -> play
    {
        isRunning = !state;
        windowManager.setPauseMenuVisibility(state);
    }
    
    public static void togglepauseGame()
    {
        pauseGame(isRunning);
    }
    
    public static void exit()
    {
        System.out.println("game exit");
        gameStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
