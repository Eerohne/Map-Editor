package Editor.View.Grid;

import Editor.Model.WallProfile;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class Grid extends Pane{
    private double cellSize;
    private int width;
    private int length;
    public Cell[][] cells;

    public Grid(int cellSize, int width, int length, WallProfile floorProfile) {
        super();
        this.width = width;
        this.length = length;
        this.cellSize = cellSize;
        
        this.cells = new Cell[width][length];
        
        this.drawGrid(cellSize, floorProfile.getImage());
    }
    
    private void drawGrid(int cellSize, Image floor){
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < length; y++) {
                Cell cell = new Cell(cellSize, floor);
                cell.setStroke(Color.BLACK);
                cell.setXPos(x);
                cell.setYPos(y);
                
                cells[x][y] = cell;
                this.getChildren().add(cell);
            }
        }
    }
    
    public void clear(){
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                cell.clear();
                cell.getTransforms().clear();
            }
        }
    }
    
    public void setMouseDragEvent(EventHandler<MouseEvent> event){
        this.setOnMouseDragged(event);
    }
    
    public void setMousePressEvent(EventHandler<MouseEvent> event){
        this.setOnMousePressed(event);
    }

    public double getCellSize() {
        return cellSize;
    }

    public int getxLength() {
        return width;
    }

    public int getyLength() {
        return length;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCellSize(double scaleFactor) {
        this.cellSize = scaleFactor;//cells[0][0].getDefaultSize()*cells[0][0].getScaleObject().getX();
    }
}
