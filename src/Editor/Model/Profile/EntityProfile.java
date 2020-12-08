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
        int r = (int)((0.5 + Math.random()*0.5)*255);
        int g = (int)((0.5 + Math.random()*0.5)*255);
        int b = (int)((0.5 - Math.random()*0.5)*255);
        this.color = Color.rgb(r, g, b);
        //this.color = this.color.brighter();
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
