
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
    
    public Entity_Logic_Timer(String name, Point2D position, float maxTime) {
        super(name, position);
        this.maxTime = maxTime;
    }
    
    public Entity_Logic_Timer(HashMap<String, String> propertyMap)
    {
        super(propertyMap);
        this.maxTime = Float.parseFloat(propertyMap.get("maxtime"));
    }
    
    @Override
    public void start() {
        this.time = maxTime;
    }
    
    @Override
    public void update() {
        time -= Time.deltaTime;
        if(time <=0)
        {
            System.out.println("logic timer logic");
            Entity counter = Game.getCurrentLevel().getEntity("counter1");
            if(counter != null)
                counter.trigger(1);
            else
                System.out.println("counter not found");
            time = maxTime;
        }
    }
    
}
