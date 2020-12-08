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
public class Entity_Logic_GameManager extends Entity{
    
    private boolean coinsCollected;
    private boolean closeToDoor;
    
    public Entity_Logic_GameManager(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        //parse property map here
        this.coinsCollected = false;
        this.closeToDoor = false;
    }
    
    @Override
    public void start() {
        //start logic here
    }

    @Override
    public void update() {
        //update logic here
    }
    
    private void verifyLogic()
    {
        if(coinsCollected == true && closeToDoor == true)
        {
            this.fireSignal("OnPlayerWon");
        }
    }
    
    @Override
    public void handleSignal(String signalName, Object[] arguments){
        switch(signalName) //new signals here
        {
            case "openDoor":
                this.closeToDoor = true;
                break;
            case "closeDoor":
                this.closeToDoor = false;
                break;
            case "coinsCollected":
                this.coinsCollected = true;
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
        
        verifyLogic();
    }
}
