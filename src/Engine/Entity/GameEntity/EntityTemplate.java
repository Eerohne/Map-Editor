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
public class EntityTemplate extends Entity{
    
    //variables here
    private int myVariable;
    
    public EntityTemplate(String name, Point2D position, int myVariable)
    {
        super(name, position);
        this.myVariable = myVariable;
    }
    
    public EntityTemplate(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        //parse property map here
        this.myVariable = Integer.valueOf((String) propertyMap.get("myvariable"));
    }
    
    @Override
    public void start() {
        //start logic here
    }

    @Override
    public void update() {
        //update logic here
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        switch(signalName) //new signals here
        {
            case "signalexmaple":
                //signal code here
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
