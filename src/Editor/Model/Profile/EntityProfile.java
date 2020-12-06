/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model.Profile;

import javafx.scene.paint.Color;

/**
 *
 * @author A
 */
public class EntityProfile extends Profile{
    private Color color;
    private int id;
    
    public EntityProfile(String name, int id) {
        super(name);
        this.color = Color.rgb((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
