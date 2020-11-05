/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Util;

/**
 *
 * @author child
 */
public class Time {
    public static double deltaTime;
    public static int fps = 0;
    //change the speed of time
    public static float timeScale = 1f;
    
    private static double lastTime = 0;
    private static int fpsCounter;
    private static long lastFrameTime = getTime();
    
    public static void update()
    {
        deltaTime = (getTime() - lastTime) * timeScale;
        lastTime = getTime();
        
        //System.out.println("delta : " + deltaTime);
        if(deltaTime > 10)
            deltaTime=0;
        CalculateFrameRate();
    }
    
    private static void CalculateFrameRate()
    {
//        System.out.println("curTime : " + getTime() + " lastTime : " + lastFrameTime);
//        System.out.println("cur - last : " + (getTime() - lastFrameTime));
        if (getTime() - lastFrameTime > 1000) {
            System.out.println("FPS : " + fps);
            fps = fpsCounter;
            fpsCounter = 0; //reset the FPS counter
            lastFrameTime = getTime(); //add one second
        }
        fpsCounter++;
        //System.out.println("FPS_COUNTER : " + fpsCounter);
    }
    
    private static long getTime() 
    {
        return System.currentTimeMillis();
    }
}
