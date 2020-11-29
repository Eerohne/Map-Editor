/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core;

import Engine.Core.SettingsManager.Settings;
import Engine.Core.Sound.SoundManager;
import Engine.Core.Exceptions.LevelCreationException;
import Engine.Entity.GameEntity.Entity_Player;
import Engine.Entity.GameEntity.Entity_Sound;
import Engine.Util.Input;
import Engine.Window.WindowManager;
import Engine.Level.Level;
import Engine.RaycastRenderer.Renderer;
import Engine.Util.RessourceManager.ResourceLoader;
import static Engine.Util.RessourceManager.ResourceLoader.loadLevel;
import Engine.Util.Time;
import java.net.MalformedURLException;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Merouane Issad
//for now, discard any code written here, it's only test nonsense
public class Game extends Application{
    public static Scene scene;
    private static Stage gameStage;
    private static WindowManager windowManager;
    private static Level currentLevel;
    //flags
    public static boolean isRunning = true;
    public static boolean isRendering = true;
    public static boolean pauseActive = true;
    
    private static AnimationTimer anim;
    
    public void start(Stage stage){
        initEngine(stage);
        
        anim = new AnimationTimer() { //Game main loop

            @Override
            public void handle(long l) {
                //if(isRunning)
                //{
                    Time.update(); //update time
                    stage.setTitle(Settings.get("g_gamename") + " -> FPS : " + Integer.toString(Time.fps));
                    currentLevel.update(); //update all entities in the level
                //}
                if(isRendering)
                    Renderer.render();
            }
        };
        anim.start();
        
        stage.show();
    }
    
    private static void initEngine(Stage stage)
    {
        //load config.cgf file
        Settings.init(); 
        
        //next initialise the window and stage
        windowManager = new WindowManager(stage, Settings.getInt("r_window_width"), Settings.getInt("r_window_height"), Settings.getBoolean("r_window_fullscreen"));
        scene = new Scene(windowManager, windowManager.getWidth(), windowManager.getHeight()); //set windows inside the scene
        stage.setScene(scene);
        stage.setResizable(false);
        gameStage = stage;
        
        //temporary very ugly code to set the view position
        Renderer.setFov(Settings.getFloat("r_fov"));
        
        //load .css style
        String pathName = Settings.get("e_stylepath");
        String styleSheet = ResourceLoader.loadStyleFile(pathName);
        if(styleSheet != null)
            scene.getStylesheets().add(ResourceLoader.loadStyleFile(pathName));
        
        //initialise input
        Input.init();
        SoundManager.init();
        
        //now load the initial level
        loadLevel(Settings.get("e_initiallevel"));
    }
    
    public static Level getCurrentLevel()
    {
        return currentLevel;
    }
    
    public static void loadLevel(String path)
    {
        try {
            Game.getWindowManager().reloadWindow();
            SoundManager.clear();
            currentLevel = ResourceLoader.loadLevel(path);
            isRunning = true;
            isRendering = true;
        } 
        catch(LevelCreationException ex) {
            System.out.println(ex);
            if(currentLevel == null){
                isRunning = false;
                isRendering = false;
            }
        }
    }
    
    public static void reloadCurrentLevel()
    {
        if(currentLevel != null)
            loadLevel(currentLevel.path);
            //loadLevel(Settings.get("initiallevel"));
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
        anim.stop();
        SoundManager.clear();
        gameStage.close();
        System.gc();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
