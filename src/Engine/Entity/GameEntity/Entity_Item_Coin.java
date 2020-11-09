
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.SpriteEntity;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

//Merouane Issad
public class Entity_Item_Coin extends SpriteEntity{
    protected int scorepoint;
    
    public Entity_Item_Coin(String name, Point2D position, Color color, int scorepoint)
    {
        super(name, position, color);
        this.scorepoint = scorepoint;
    }
    
    public Entity_Item_Coin(HashMap<String, String> propertyMap)
    {
        super(propertyMap);
        
        int score = Integer.parseInt(propertyMap.get("scorepoint"));
    }
    
    @Override
    public void update()
    {
        System.out.println("Coin : " + this.name);
    }
    
    public void collect()
    {
        System.out.println("Coin : " + this.name + " was collected");
        this.destroy();
    }
}
