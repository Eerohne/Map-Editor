/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model.Profile;

import Editor.View.Grid.EntityDot;
import javafx.scene.paint.Color;

/**
 *
 * @author A
 */
public class EntityProfile extends Profile{
    private int id;
    private EntityDot dot;
    
    public EntityProfile(String name, int id) {
        super(name);
        int r = (int)((0.5 + Math.random()*0.5)*255);
        int g = (int)((0.5 + Math.random()*0.5)*255);
        int b = (int)((0.5 - Math.random()*0.5)*255);
        //Color.rgb(r, g, b);

        this.id = id;
        
        this.dot = new EntityDot(Color.rgb(r, g, b));
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public EntityDot getDot() {
        return dot;
    }

    public void setDot(EntityDot dot) {
        this.dot = dot;
    }
}
