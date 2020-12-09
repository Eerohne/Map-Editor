/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;

/**
 *
 * @author child
 */
public class Entity_Logic_Start extends Entity{
    
    public Entity_Logic_Start(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
    }
    
    @Override
    public void start() {
        this.fireSignal("OnLevelLoad");
        this.active=false;
    }

    @Override
    public void update() {
        //update logic here
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
