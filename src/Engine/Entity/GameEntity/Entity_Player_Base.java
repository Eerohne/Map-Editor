/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Game;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author child
 */
public class Entity_Player_Base extends Entity_Player{

    protected float playerSpeed, walkSpeed, runSpeed;
    
    public Entity_Player_Base(String name, Point2D position, float rotation, float walkSpeed, float runSpeed) {
        super(name, position, rotation);
        this.walkSpeed = walkSpeed;
        this.runSpeed = runSpeed;
    }
    
    public Entity_Player_Base(HashMap<String, String> propertyMap)
    {
        super(propertyMap);
        
        this.walkSpeed = Float.parseFloat(propertyMap.get("walkspeed"));
        this.runSpeed = Float.parseFloat(propertyMap.get("runspeed"));
    }

    @Override
    public void start() {
        this.playerSpeed = walkSpeed;
        
        Game.scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke) {
                if(ke.getCode() == KeyCode.A) {
                    System.out.println(walkSpeed);
                }
            }
        });
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
