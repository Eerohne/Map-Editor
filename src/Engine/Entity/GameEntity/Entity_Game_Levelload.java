/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Game;
import Engine.Entity.AbstractEntity.Entity;
import java.util.HashMap;
import javafx.geometry.Point2D;

/**
 *
 * @author child
 */
public class Entity_Game_Levelload extends Entity{
    
    //variables here
    private String levelPath;
    
    public Entity_Game_Levelload(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        //parse property map here
        this.levelPath = (String) propertyMap.get("levelpath");
        this.active = false;
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
    public void handleSignal(String signalName, Object[] arguments){
        switch(signalName) //new signals here
        {
            case "load":
                Game.loadLevel(levelPath);
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
