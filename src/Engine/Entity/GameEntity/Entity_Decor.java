/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.SpriteEntity;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 *
 * @author child
 */
public class Entity_Decor extends SpriteEntity{
    
    public Entity_Decor(String name, Point2D position, Color color, String texture, float size)
    {
        super(name, position, texture, size);
    }
    
    public Entity_Decor(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
    }
    
    @Override
    public void start() {
        //Sprite Entity start method
        super.start();
    }

    @Override
    public void update() {
        //System.out.println("update");
    }
    
    @Override
    public void handleSignal(String signalName, Object[] arguments){
        switch(signalName) //new signals here
        {
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
