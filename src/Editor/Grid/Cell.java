/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Grid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author A
 */
public class Cell extends Rectangle{
    private int size;
    private Color cellColor;
    private Color floorColor = Color.WHITE;

    public Cell(int defaultSize) {
        super(defaultSize, defaultSize);
        super.setFill(floorColor);
        super.setStroke(Color.BLACK);
        
        this.size = defaultSize;
        this.cellColor = this.floorColor;
    }

    public void clear(){
        this.cellColor = this.floorColor;
        this.setFill(cellColor);
    }
    
    public int getDefaultSize() {
        return size;
    }

    public void setDefaultSize(int size) {
        this.size = size;
        super.setWidth(this.size);
        super.setHeight(this.size);
    }

    public Color getCellColor() {
        return cellColor;
    }

    public void setCellColor(Color cellColor) {
        this.cellColor = cellColor;
        super.setFill(cellColor);
    }
    
    public void setXPos(double value){
        super.setX(value * size);
    }
    
    public void setYPos(double value){
        super.setY(value * size);
    }
}
