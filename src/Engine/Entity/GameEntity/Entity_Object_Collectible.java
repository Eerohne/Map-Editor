/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.Entity_Player;
import Engine.Core.Collision.SphereCollider;
import Engine.Core.Game;
import Engine.Entity.AbstractEntity.SpriteEntity;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 *
 * @author child
 */
public class Entity_Object_Collectible extends SpriteEntity{
    
    private SphereCollider collider;
    private Entity_Player player;
    
    public Entity_Object_Collectible(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        collider = new SphereCollider(this.position, Double.parseDouble((String) propertyMap.get("radius")), true);
    }
    
    @Override
    public void start() {
        //Sprite Entity start method
        super.start();
        this.player = Game.getCurrentLevel().getPlayer();
    }

    @Override
    public void update() {
        if(collider.inside(player.getPosition()))
        {
            this.collect();
        }
    }
    
    public void collect()
    {
        //collect behavior
        this.fireSignal("OnCollected");
        super.destroy();
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        switch(signalName) //new signals here
        {
            case "enable":
                Game.getCurrentLevel().addCollider(this.name, collider);
                super.handleSignal(signalName, arguments);
                break;
            case "disable":
                Game.getCurrentLevel().removeCollider(this.name);
                super.handleSignal(signalName, arguments);
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
