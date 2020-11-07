/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Entity;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author A
 */
public class Collectible {
    double xPos;
    double yPos;
    int rad;
    Color color;
    Circle c;
    
    private static ArrayList<Collectible> collectibles = new ArrayList<>();

    public Collectible(double x, double y, int rad, Color color) {
        this.xPos = x;
        this.yPos = y;
        this.rad = rad;
        this.color = color;
        
        c = new Circle(xPos, yPos, rad, color);
        
        collectibles.add(this);
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }
    
    public Circle getCircle(){
        return c;
    }

    public static ArrayList<Collectible> getCollectibles() {
        return collectibles;
    }
    
    
}
