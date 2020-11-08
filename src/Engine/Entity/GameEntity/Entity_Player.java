/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.Entity;
import javafx.geometry.Point2D;

//Merouane Issad
public abstract class Entity_Player extends Entity{

    protected float rotation; //view rotation in the world
    
    public Entity_Player(String name, Point2D position, float rotation) {
        super(name, position);
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    
}
