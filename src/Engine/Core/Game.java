/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core;

import Engine.Core.Exceptions.LevelCreationException;
import Engine.Entity.GameEntity.Entity_Player;
import Engine.Util.Input;
import Engine.Window.WindowManager;
import Engine.Level.Level;
import Engine.RaycastRenderer.Renderer;
import Engine.Util.RessourceManager.RessourceLoader;
import static Engine.Util.RessourceManager.RessourceLoader.loadLevel;
import Engine.Util.Time;
import java.net.MalformedURLException;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

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
    
    public void start(Stage stage){
        initEngine(stage);
        
        //temporary very ugly code to set the view position
        Renderer.setFov(70);
        float speed = 1.5f;
        new AnimationTimer() { //Game main loop

            @Override
            public void handle(long l) {
                if(isRunning)
                {
                    Time.update(); //update time
                    stage.setTitle("Optik Engine -> FPS : " + Integer.toString(Time.fps));
                    currentLevel.update(); //update all entities in the level
                }
                if(isRendering)
                    Renderer.render();
            }
        }.start();
        
        stage.show();
    }
    
    private static void initEngine(Stage stage)
    {
        //config file stuff
        //window size
        windowManager = new WindowManager(stage, screenWidth, screenHeight);
        scene = new Scene(windowManager, windowManager.getWidth(), windowManager.getHeight()); //set windows inside the scene
        stage.setScene(scene);

        Input.init();
        Renderer.setCanvas(windowManager.getRenderCanvas());
        
        //load .css style
        String pathName = "style/style.css" ;
        try {
            scene.getStylesheets().add(RessourceLoader.loadStyleFile(pathName));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        stage.setResizable(false);
        gameStage = stage;
        
        //now load the initial level, path in the config file
        try {
            currentLevel = loadLevel("levels/level1.lvl");
        } 
        catch(LevelCreationException ex) {
            System.out.println(ex);
            isRunning = false;
        }
    }
    
    public static Level getCurrentLevel()
    {
        return currentLevel;
    }
    
    public static void reloadCurrentLevel()
    {
        windowManager.reloadWindow();
        try {
            currentLevel = loadLevel("levels/level1.lvl");
        } 
        catch(LevelCreationException ex) {
            System.out.println(ex);
            isRunning = false;
        }
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
