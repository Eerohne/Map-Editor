
package Engine.Entity.GameEntities;

import Engine.Entity.AbstractEntity.SpriteEntity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

//Merouane Issad
public class Entity_Coin extends SpriteEntity{
    private int scorePoints;
    
    public Entity_Coin(String name, Point2D position, Color color, int scorePoints)
    {
        super(name, position, color);
        this.scorePoints = scorePoints;
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
