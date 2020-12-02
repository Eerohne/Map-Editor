/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core;

import Commons.SettingsManager.Settings;
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
import java.awt.AWTException;
import java.awt.Robot;
import java.net.MalformedURLException;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

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
    public static boolean isPaused = false;
    
    public static SimpleStringProperty errorMessage = new SimpleStringProperty();
    
    private static AnimationTimer anim;
    
    public void start(Stage stage){
        initEngine(stage);
        
        anim = new AnimationTimer() { //Game main loop

            @Override
            public void handle(long l) {
                if(isRunning) {
                    Time.update(); //update time
                    stage.setTitle(Settings.get("g_gamename") + " -> FPS : " + Integer.toString(Time.fps));
                    currentLevel.update(); //update all entities in the level
                }
                if(isRendering)
                    Renderer.render();
                
                if(!isPaused && gameStage.isFocused()) { //put the cursor position in the middle of the window when not in pause menu and window is focused
                    try {
                        new Robot().mouseMove((int)(gameStage.getX() + gameStage.getWidth()/2), (int)(gameStage.getY() + gameStage.getHeight()/2));
                    } catch (AWTException ex) {
                        Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            }
        };
        anim.start();
        stage.show();
    }
    
    private static void initEngine(Stage stage)
    {
        errorMessage.set("Engine error");
        //load config.cgf file
        Settings.init(); 
        stage.getIcons().add(ResourceLoader.loadImage(Settings.get("e_iconpath" )));
        
        //next initialise the window and stage
        windowManager = new WindowManager(stage, Settings.getInt("r_window_width"), Settings.getInt("r_window_height"), Settings.getBoolean("r_window_fullscreen"));
        scene = new Scene(windowManager, windowManager.getWidth(), windowManager.getHeight()); //set windows inside the scene
        scene.setCursor(Cursor.NONE);//when in game, hide the cursor
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
            getWindowManager().setErrorMessageVisibility(false);
        } 
        catch(LevelCreationException ex) {
            currentLevel = null;
            isRunning = false;
            isRendering = false;
            errorMessage.set("- "+ex.getMessage()+" -");
            getWindowManager().setErrorMessageVisibility(true);
        }
        finally {
            if(getWindowManager().getPauseMenuVisibility())
                getWindowManager().setPauseMenuVisibility(false);
        }
    }
    
    public static void reloadCurrentLevel()
    {
        //if(currentLevel != null)
            //loadLevel(currentLevel.path);
        loadLevel("levels/level1.lvl");
    }
    
    public static WindowManager getWindowManager()
    {
        return windowManager;
    }
    
    public static void setPause(boolean pause)
    {
        isPaused = pause;
        windowManager.setPauseMenuVisibility(pause);
        if(isPaused)
            scene.setCursor(Cursor.DEFAULT);
        else
            scene.setCursor(Cursor.NONE);
    }
    public static void togglePause()
    {
        isPaused = !isPaused;
        if(isPaused)
            scene.setCursor(Cursor.DEFAULT);
        else
            scene.setCursor(Cursor.NONE);
        windowManager.setPauseMenuVisibility(isPaused);
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
