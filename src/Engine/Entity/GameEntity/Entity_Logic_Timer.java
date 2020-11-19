
package Engine.Entity.GameEntity;

import Engine.Core.Game;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.Time;
import java.util.HashMap;
import javafx.geometry.Point2D;

//Merouane Issad
public class Entity_Logic_Timer extends Entity{
    protected float time;
    protected float maxTime;
    protected boolean counting;
    
    public Entity_Logic_Timer(String name, Point2D position, float maxTime, boolean onStart) {
        super(name, position);
        this.maxTime = maxTime;
        this.counting = onStart;
    }
    
    public Entity_Logic_Timer(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        this.maxTime = Float.parseFloat((String) propertyMap.get("maxtime"));
        this.counting = Boolean.parseBoolean((String) propertyMap.get("onstart"));
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
                System.out.println("logic_timer logic");
                fireSignal("OnTimerEnded");
                time = maxTime;
            }
        }
    }
    
    @Override
    public void handleSignal(String signalName, Object[] arguments){
        
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
            case "printMessage":
                System.out.println("message called in the timer, success!!!");
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
    
}
