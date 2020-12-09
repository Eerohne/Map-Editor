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
            System.out.println("you have escaped the dangeon to live another day...");
            this.fireSignal("OnPlayerWon");
        }
        else if(coinsCollected == true && closeToDoor == false)
            System.out.println("Escape before your time runs out");
        else if(coinsCollected == false && closeToDoor == true)
            System.out.println("Collect all coin and this door will open");
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        System.out.println("collected"+this.coinsCollected+"door"+this.closeToDoor);
        switch(signalName) //new signals here
        {
            case "openDoor":
                this.closeToDoor = true;
                verifyLogic();
                break;
            case "closeDoor":
                this.closeToDoor = false;
                break;
            case "allCoinsCollected":
                this.coinsCollected = true;
                verifyLogic();
                break;
            case "coinCollected":
                System.out.println("only one");
                System.out.println((int) arguments.get(0));
                int count = (int) arguments.get(0);
                System.out.println("this is the count : "+count);
                if(count == 0)
                    this.fireSignal("OnCoinCollected", ("coins left : "+count), "-fx-text-fill: rgba(0, 255, 0, 255);");
                else
                    this.fireSignal("OnCoinCollected", ("coins left : "+count), "-fx-text-fill: rgba(255, 255, 255, 255);");
                verifyLogic();
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
