
package Engine.Entity.GameEntities;

import Engine.Entity.AbstractEntity.Entity;

//Merouane Issad
public class Entity_Timer extends Entity{

    private float time;
    private float maxTime;
    
    @Override
    public void start() {
        this.time = maxTime;
    }
    
    @Override
    public void update() {
        System.out.println("logic!");
    }
    
}
