/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Collision.SphereCollider;
import Engine.Core.Game;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Entity.AbstractEntity.Entity_Player;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;

/**
 *
 * @author child
 */
public class Entity_Object_Trigger extends Entity{
    
    private SphereCollider collider;
    private boolean isInside;
    
    private Entity_Player player;
    
    public Entity_Object_Trigger(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        //parse property map here
        collider = new SphereCollider(this.position, Double.parseDouble((String) propertyMap.get("radius")), true);
        this.isInside = false;
    }
    
    @Override
    public void start() {
        this.player = Game.getCurrentLevel().getPlayer();
    }

    @Override
    public void update() {
        if(collider.inside(player.getPosition()) && !isInside)
        {
            this.fireSignal("OnTriggerEnter");
            this.isInside = true;
        }
        else if(!collider.inside(player.getPosition()) && isInside)
        {
            this.fireSignal("OnTriggerExit");
            this.isInside = false;
        }
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        switch(signalName) //new signals here
        {
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
