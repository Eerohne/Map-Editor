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
import Engine.Util.RessourceManager.RessourceLoader;
import Engine.Util.Time;
import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
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
        initEngine(stage);
        
        Renderer.setPos(2.5, 2.5);
        new AnimationTimer() { //Game main loop

            @Override
            public void handle(long l) {
                if(isRunning)
                {
                    Time.update();
                    stage.setTitle("Optik Engine -> FPS : " + Integer.toString(Time.fps));
                    //update all entities in the level -> currentLevel.update();
                    
                    //Camera rotation test
                    //Renderer.camA += 90*Time.deltaTime;
                    
                    //this code should be in the player entity update
                    if(Input.keyPressed(KeyCode.W))
                        Renderer.cam = Renderer.cam.add(0, -3*Time.deltaTime);
                    if(Input.keyPressed(KeyCode.S))
                        Renderer.cam = Renderer.cam.add(0, 3*Time.deltaTime);
                    if(Input.keyPressed(KeyCode.A))
                        Renderer.cam = Renderer.cam.add(-3*Time.deltaTime, 0);
                    if(Input.keyPressed(KeyCode.D))
                        Renderer.cam = Renderer.cam.add(3*Time.deltaTime, 0);
                    if(Input.keyPressed(KeyCode.LEFT))
                        Renderer.camA -= 100 * Time.deltaTime;
                    if(Input.keyPressed(KeyCode.RIGHT))
                        Renderer.camA += 100 * Time.deltaTime;
                }
                if(isRendering)
                    Renderer.render();
            }
        }.start();
        
        stage.show();
    }
    
    private static void initEngine(Stage stage) throws MalformedURLException
    {
        //config file stuff
        //window size
        windowManager = new WindowManager(stage, screenWidth, screenHeight);
        scene = new Scene(windowManager, windowManager.getWidth(), windowManager.getHeight()); //set windows inside the scene
        stage.setScene(scene);

        Input.init();
        Renderer.setCanvas(windowManager.getRenderCanvas());
        
        //style path
        String pathName = "style/style.css" ;
        scene.getStylesheets().add(RessourceLoader.loadStyleFile(pathName));
        
        stage.setResizable(false);
        gameStage = stage;
        //now load the initial level -> currentLevel = LevelLoader.load(path_to_level_file);
        
        //some general input setup
        /*scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent key) {
                System.out.println("AHAHAHAHHAHA");
                if(key.getCode() == KeyCode.ESCAPE)
                    Game.togglepauseGame();
            }
        });*/
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
