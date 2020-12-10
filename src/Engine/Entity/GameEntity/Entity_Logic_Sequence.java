/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.Time;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;

/**
 *
 * @author child
 */
public class Entity_Logic_Sequence extends Entity{
    
    //variables here
    private boolean onLevelStart;
    private boolean activated;
    
    private float waitTime, time=0;
    private int signalIndex = 0;
    
    public Entity_Logic_Sequence(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        //parse property map here
        this.onLevelStart = Boolean.valueOf((String) propertyMap.get("onstart"));
    }
    
    @Override
    public void start() {
        //start logic here
        if(onLevelStart)
        {
            activate();
        }
    }

    @Override
    public void update() {
        if(this.activated){
            time -= Time.deltaTime;

            if(time <=0)
            {
                this.fireSignal(this.signals.get(signalIndex).name);

                this.signalIndex++;
                if(signalIndex >= this.signals.size()){
                    this.activated = false;
                }else{
                    int lastArgIndex = this.signals.get(signalIndex).arguments.size()-1;
                    this.time = Float.parseFloat((String) this.signals.get(signalIndex).arguments.get(lastArgIndex));
                }
            }
        }
    }
    
    private void activate()
    {
        this.activated = true;
        this.signalIndex = 0;
        int lastArgIndex = this.signals.get(signalIndex).arguments.size()-1;
        try{
        this.time = Float.parseFloat((String) this.signals.get(signalIndex).arguments.get(lastArgIndex));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            time = 0.0f;
        }
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        switch(signalName) //new signals here
        {
            case "activate":
                activate();
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
