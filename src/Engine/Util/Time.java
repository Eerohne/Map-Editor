package Engine.Util;

//Merouane Issad

import Engine.Core.Game;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class Time {
    public static double deltaTime; //time passed between now and the last frame. ALWAYS use this inside time calculations, 
                                    //continuous mouvement or any behavior that spans more than one frame
    public static int fps = 0; //Frames Per Seconds, how much game loops were completed every second
    public static float timeScale = 1f; //change the speed of time, bigger is faster. 
                                        //This will probaly never be used in our games, but it's here in case we need slow-motion or something
    public static float timePassed = 0; //time in seconds that passed
    private static Label fpsLabel;
    
    //Ignore
    private static double lastTime = 0;
    private static int fpsCounter;
    private static long lastFrameTime = getTime();
    
    public static void init()
    {
        fpsLabel = new Label();
        fpsLabel.setFont(Font.font("Cambria", 20));
        fpsLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        AnchorPane pane = Game.getWindowManager().getPauseMenu();
        pane.getChildren().add(fpsLabel);
        pane.setTopAnchor(fpsLabel, 50.0);
        pane.setRightAnchor(fpsLabel, 2.0);
    }
    public static void update()
    {
        deltaTime = ((getTime() - lastTime)/1000) * timeScale; //calculate time passed from the last time update
        if(deltaTime > 1) //failsafe in case the value goes crazy
            deltaTime = 0;
        lastTime = getTime();
        
        
        timePassed += deltaTime;
        CalculateFrameRate();
    }
    
    private static void CalculateFrameRate()
    {
        if (getTime() - lastFrameTime > 1000) { //if one second has passed (1000 in miliseconds), then fps is the fpsCounter we've been incrementing every game cycle
            fps = fpsCounter;
            fpsCounter = 0;
            lastFrameTime = getTime();
            fpsLabel.setText("fps : "+fps);
            //System.out.println(fps);
        }
        fpsCounter++;
    }
    
    private static long getTime() //get current time (in milliseconds)
    {
        return System.currentTimeMillis();
    }
    
    
}
