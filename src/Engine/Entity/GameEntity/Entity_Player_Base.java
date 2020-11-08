/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import javafx.geometry.Point2D;

/**
 *
 * @author child
 */
public class Entity_Player_Base extends Entity_Player{

    float playerSpeed, walkSpeed, runSpeed;
    
    public Entity_Player_Base(String name, Point2D position, float rotation, float walkSpeed, float runSpeed) {
        super(name, position, rotation);
        this.walkSpeed = walkSpeed;
        this.runSpeed = runSpeed;
    }

    @Override
    public void start() {
        this.playerSpeed = walkSpeed;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
