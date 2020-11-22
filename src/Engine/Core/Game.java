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
import Engine.Util.RessourceManager.ResourceLoader;
import static Engine.Util.RessourceManager.ResourceLoader.loadLevel;
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
                    stage.setTitle(Settings.get("gamename") + " -> FPS : " + Integer.toString(Time.fps));
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
        //load config.cgf file
        Settings.init(); 
        
        //next initialise the window and stage
        windowManager = new WindowManager(stage, Settings.getInt("r_window_width"), Settings.getInt("r_window_height"));
        scene = new Scene(windowManager, windowManager.getWidth(), windowManager.getHeight()); //set windows inside the scene
        stage.setScene(scene);
        stage.setResizable(false);
        gameStage = stage;
        
        //give the render canvas to the renderer
        Renderer.setCanvas(windowManager.getRenderCanvas());
        
        //load .css style
        String pathName = Settings.get("stylesheetpath");
        String styleSheet = ResourceLoader.loadStyleFile(pathName);
        if(styleSheet != null)
            scene.getStylesheets().add(ResourceLoader.loadStyleFile(pathName));
        
        //initialise input
        Input.init();
        
        //now load the initial level
        loadLevel(Settings.get("initiallevel"));
    }
    
    public static Level getCurrentLevel()
    {
        return currentLevel;
    }
    
    public static void loadLevel(String path)
    {
        try {
            currentLevel = ResourceLoader.loadLevel(path);
        } 
        catch(LevelCreationException ex) {
            System.out.println(ex);
            isRunning = false;
            isRendering = false;
        }
    }
    
    public static void reloadCurrentLevel()
    {
        if(currentLevel != null)
            loadLevel(currentLevel.path);
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
