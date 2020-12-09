/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

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
public class Entity_Object_Decor extends SpriteEntity{
    
    SphereCollider col;
    
    public Entity_Object_Decor(String name, Point2D position, Color color, String texture, float size)
    {
        super(name, position, texture, size);
    }
    
    public Entity_Object_Decor(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        col = new SphereCollider(this.position, Double.parseDouble((String) propertyMap.get("radius")), false);
    }
    
    @Override
    public void start() {
        //Sprite Entity start method
        super.start();
        if(col.getRadius() >0){
            Game.getCurrentLevel().addCollider(this.name, col);
        }
    }

    @Override
    public void update() {
        //update
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        switch(signalName) //new signals here
        {
            case "enable":
                if(col.getRadius() >0){
                    Game.getCurrentLevel().addCollider(this.name, col);
                    super.handleSignal(signalName, arguments);
                }
                break;
            case "disable":
                if(col.getRadius() >0){
                    Game.getCurrentLevel().removeCollider(this.name);
                    super.handleSignal(signalName, arguments);
                }
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
