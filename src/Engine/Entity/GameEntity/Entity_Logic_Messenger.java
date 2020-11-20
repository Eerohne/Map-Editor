/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.Entity;
import java.util.HashMap;
import javafx.geometry.Point2D;

/**
 *
 * @author child
 */
public class Entity_Logic_Messenger extends Entity{
    
    public Entity_Logic_Messenger(String name, Point2D position, int myVariable)
    {
        super(name, position);
    }
    
    public Entity_Logic_Messenger(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
    }
    
    @Override
    public void start() {
    }

    @Override
    public void update() {
        //update logic here
    }
    
    @Override
    public void handleSignal(String signalName, Object[] arguments){
        switch(signalName) //new signals here
        {
            case "printMessage":
                try{
                System.out.println((String) arguments[0]);
                }catch(ArrayIndexOutOfBoundsException e){
                    System.out.println(e+ " no message specified for input 'printMessage' in entity '"+name+"'");
                }
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
