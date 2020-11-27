/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Grid;

import Editor.MapEditor;
import Editor.Model.WallProfile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 *
 * @author A
 */
public class Cell extends Rectangle{
    
    private int size;
    
    private ImagePattern texture;
    private int wallID;
    
    private Translate tVector = new Translate(0, 0);
    private Scale sMatrix = new Scale(1, 1);
    
    public Cell(int defaultSize, Image img) {
        this(defaultSize);
        this.setImg(img);
        super.setStroke(Color.BLACK);
    }
    
    public Cell(int size){
        super(size, size);
        this.size = size;
        this.getTransforms().addAll(tVector, sMatrix);
    }

    public void clear(){
    }
    
    public int getDefaultSize() {
        return size;
    }
    
    public int getID(){
        return wallID;
    }

    public int getWallID() {
        return wallID;
    }
    
    
    public void setDefaultSize(int size) {
        this.size = size;
        super.setWidth(this.size);
        super.setHeight(this.size);
    }
    
    public void setImg(int paletteID){
        this.wallID = paletteID;
        this.texture = new ImagePattern(WallProfile.palette.get(paletteID));
        this.setFill(texture);
    }
    
    public void setImg(Image img){
        this.wallID = WallProfile.getID(img);
        this.setImg(wallID);
    }
    
    public void setXPos(double value){
        super.setX(value * size);
    }
    
    public void setYPos(double value){
        super.setY(value * size);
    }
    
    public Translate getTranslationObject(){
        return tVector;
    }
    
    public Scale getScaleObject(){
        return sMatrix;
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
