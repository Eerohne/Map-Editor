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
            this.fireSignal("OnWin");
        }
        else if(coinsCollected == true && closeToDoor == false){
            this.fireSignal("OnAllCoinsCollected");
            System.out.println("collected all coins");
        }
        else if(coinsCollected == false && closeToDoor == true){
            System.out.println("Collect all coin and this door will open");
            this.fireSignal("OnDoorClosed");
        }
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
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
                int count = (int) arguments.get(0);
                System.out.println("this is the count : "+count);
                if(count == 0)
                    this.fireSignal("OnCoinCollected", ("coins left : "+count), "-fx-text-fill: rgba(0, 255, 0, 255);");
                else
                    this.fireSignal("OnCoinCollected", ("coins left : "+count), "-fx-text-fill: rgba(255, 255, 255, 255);");
                verifyLogic();
                break;
            case "secondPassed":
                int time = Math.round((float)arguments.get(0));
                String timeText = String.format("%02d:%02d", time/100%60, time/1%60);
                if(time > 0)
                    this.fireSignal("OnSecondPassed", "Time Left : "+timeText, "-fx-text-fill: rgba(255, 255, 255, 255);");
                else
                    this.fireSignal("OnSecondPassed", "Time Left : "+timeText, "-fx-text-fill: rgba(255, 0, 0, 255);");
                break;
            case "gameLost":
                System.out.println("lost!!!");
                this.fireSignal("gameLost");
                    
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
