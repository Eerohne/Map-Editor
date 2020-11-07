
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.SpriteEntity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

//Merouane Issad
public class Entity_Coin extends SpriteEntity{
    private int scorepoint;
    
    public Entity_Coin(String name, Point2D position, Color color, int scorepoint)
    {
        super(name, position, color);
        this.scorepoint = scorepoint;
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
