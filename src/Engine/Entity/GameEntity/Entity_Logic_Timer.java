
package Engine.Entity.GameEntity;

import Engine.Core.Game;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.Time;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;

//Merouane Issad
public class Entity_Logic_Timer extends Entity{
    private float time;
    private float maxTime;
    private boolean counting;
    private boolean canReset;
    
    public Entity_Logic_Timer(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        this.maxTime = Float.parseFloat((String) propertyMap.get("maxtime"));
        this.counting = Boolean.parseBoolean((String) propertyMap.get("onstart"));
        this.canReset = Boolean.parseBoolean((String) propertyMap.get("canreset"));
        this.time = maxTime;
    }
    
    @Override
    public void start() {
    }
    
    @Override
    public void update() {
        if(this.counting){
            time -= Time.deltaTime;
            if(time <=0)
            {
                fireSignal("OnTimerEnded");
                if(canReset){
                    time = maxTime;
                }else{
                    this.counting = false;
                }
            }
            
            if(time %1 <=0.05)
                fireSignal("OnSecondPassed", time);
        }
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        
        switch(signalName)
        {
            case "start":
                this.counting = true;
                break;
            case "stop":
                this.counting = false;
                break;
            case "reset":
                this.time = maxTime;
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
    
}
