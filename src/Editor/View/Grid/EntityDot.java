/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Grid;

import Editor.Model.Profile.EntityProfile;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 *
 * @author A
 */
public class EntityDot extends Circle{
    private int entityId;
    private Color color;
    
    private Translate tVector = new Translate(0, 0);
    private Scale sMatrix = new Scale(1, 1);

    public EntityDot(int entityId, Color color, double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        this.entityId = entityId;
        this.color = color;
        this.setFill(color);
        this.getTransforms().addAll(tVector, sMatrix);
    }
    
    public EntityDot(EntityProfile ep, double centerX, double centerY, double radius){
        this(ep.getID(), ep.getColor(), centerX, centerY, radius);
    }

    public void setColor(Color color) {
        this.color = color;
        this.setFill(color);
    }
    
    public Translate getTranslationObject(){
        return tVector;
    }
    
    public Scale getScaleObject(){
        return sMatrix;
    }
    
    public void setScaleObject(Scale matrix){
        this.getTransforms().clear();
        sMatrix = matrix;
        this.getTransforms().addAll(tVector, sMatrix);
    }
    
    public void setTranslationObject(Translate vector){
        this.getTransforms().clear();
        tVector = vector;
        this.getTransforms().addAll(tVector, sMatrix);
    }
    
    public void addTranslationVector(Translate vector){
        double x = vector.getX() + tVector.getX();
        double y = vector.getY() + tVector.getY();
        
        this.tVector.setX(x);
        this.tVector.setY(y);
    }
    
    public void addScaleMatrix(Scale matrix){
        double x = matrix.getX() + sMatrix.getX();
        double y = matrix.getY() + sMatrix.getY();
        
        this.sMatrix.setX(x);
        this.sMatrix.setY(y);
    }
}
