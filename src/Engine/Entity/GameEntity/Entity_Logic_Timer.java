
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.Entity;
import java.util.HashMap;
import javafx.geometry.Point2D;

//Merouane Issad
public class Entity_Logic_Timer extends Entity{
    protected float time;
    protected float maxTime;
    
    public Entity_Logic_Timer(String name, Point2D position, float maxTime) {
        super(name, position);
        this.maxTime = maxTime;
        this.time = maxTime;
    }
    
    public Entity_Logic_Timer(HashMap<String, String> propertyMap)
    {
        super(propertyMap);
        this.maxTime = Float.parseFloat(propertyMap.get("maxtime"));
    }
    
    @Override
    public void start() {
    }
    
    @Override
    public void update() {
        System.out.println("logic!");
    }
    
}
