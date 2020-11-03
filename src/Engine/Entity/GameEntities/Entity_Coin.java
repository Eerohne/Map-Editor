
package Engine.Entity.GameEntities;

import Engine.Entity.AbstractEntity.SpriteEntity;

//Merouane Issad
public class Entity_Coin extends SpriteEntity{
    
    @Override
    public void update()
    {
        System.out.println("coin");
    }
    
    public void collect()
    {
        System.out.println("coinCollected");
    }
}
