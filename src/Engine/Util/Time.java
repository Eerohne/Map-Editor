package Engine.Util;

//Merouane Issad
public class Time {
    public static double deltaTime; //time passed between now and the last frame. ALWAYS use this inside time calculations, 
                                    //continuous mouvement or any behavior that spans more than one frame
    public static int fps = 0; //Frames Per Seconds, how much game loops were completed every second
    public static float timeScale = 1f; //change the speed of time, bigger is faster. 
                                        //This will probaly never be used in our games, but it's here in case we need slow-motion or something
    
    //Ignore
    private static double lastTime = 0;
    private static int fpsCounter;
    private static long lastFrameTime = getTime();
    
    public static void update()
    {
        deltaTime = ((getTime() - lastTime)/1000) * timeScale; //calculate time passed from the last time update
        if(deltaTime > 1) //failsafe in case the value goes crazy
            deltaTime = 0;
        lastTime = getTime();
        
        CalculateFrameRate();
    }
    
    private static void CalculateFrameRate()
    {
        if (getTime() - lastFrameTime > 1000) { //if one second has passed (1000 in miliseconds), then fps is the fpsCounter we've been incrementing every game cycle
            fps = fpsCounter;
            fpsCounter = 0;
            lastFrameTime = getTime();
            System.out.println("FPS : " + fps);
        }
        fpsCounter++;
    }
    
    private static long getTime() //get current time (in milliseconds)
    {
        return System.currentTimeMillis();
    }
}
