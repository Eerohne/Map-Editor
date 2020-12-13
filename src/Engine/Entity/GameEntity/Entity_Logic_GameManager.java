/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Game;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.Input;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

/**
 *
 * @author child
 */
public class Entity_Logic_GameManager extends Entity{
    
    private boolean coinsCollected;
    private boolean closeToDoor;
    
    private boolean lost = false;
    
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
        //restart the level when the 'Q' key is pressed
        if(Input.keyPressed(KeyCode.Q)){
            Game.reloadCurrentLevel();
        }
    }
    
    private void verifyLogic()
    {
        if(coinsCollected == true && closeToDoor == true && lost == false)
        {
            this.fireSignal("OnWin");
        }
        else if(coinsCollected == true && closeToDoor == false){
            this.fireSignal("OnAllCoinsCollected");
        }
        else if(coinsCollected == false && closeToDoor == true){
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
                if(count == 0)
                    this.fireSignal("OnCoinCollected", ("coins left : "+count), "-fx-text-fill: rgba(0, 255, 0, 255);");
                else
                    this.fireSignal("OnCoinCollected", ("coins left : "+count), "-fx-text-fill: rgba(255, 255, 255, 255);");
                verifyLogic();
                break;
            case "secondPassed":
                int time = Math.round((float)arguments.get(0));
                String timeText = String.valueOf(time);
                if(time > 0)
                    this.fireSignal("OnSecondPassed", "Time Left : "+timeText, "-fx-text-fill: rgba(255, 255, 255, 255);");
                else
                    this.fireSignal("OnSecondPassed", "Time Left : "+timeText, "-fx-text-fill: rgba(255, 0, 0, 255);");
                break;
            case "gameLost":
                this.lost = true;
                this.fireSignal("OnLost");
                    
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
