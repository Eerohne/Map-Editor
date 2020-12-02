/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.Time;
import java.util.HashMap;
import javafx.geometry.Point2D;

/**
 *
 * @author child
 */
public class Entity_Player_Idle extends Entity_Player{
    
    private float rotationSpeed;
    private float headBobTime = 0;
    private float headBobFrequency;
    private float headBobRange;
    
    public Entity_Player_Idle(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        //parse property map here
        this.rotationSpeed = Float.valueOf((String) propertyMap.get("rotationspeed"));
        
        this.headBobFrequency = Float.valueOf((String) propertyMap.get("zfrequency"));
        this.headBobRange = Float.valueOf((String) propertyMap.get("zrange"));
    }
    
    @Override
    public void start() {
        //start logic here
        super.start();
    }

    @Override
    public void update() {
        this.rotation += rotationSpeed * Time.deltaTime;
        
        headBobTime += Time.deltaTime;
        this.height = (float)Math.sin(headBobTime * headBobFrequency) * headBobRange;
    }
    
    @Override
    public void handleSignal(String signalName, Object[] arguments){
        switch(signalName) //new signals here
        {
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
