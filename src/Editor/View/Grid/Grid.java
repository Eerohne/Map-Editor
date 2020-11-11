package Editor.View.Grid;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class Grid extends Pane{
    private double cellSize;
    private int width;
    private int length;
    private Cell[][] cells;

    public Grid(int cellSize, int width, int length) {
        super();
        
        this.width = width;
        this.length = length;
        this.cellSize = cellSize;
        
        this.cells = new Cell[width][length];
        
        this.drawGrid(cellSize);
    }
    
    private void drawGrid(int cellSize){
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < length; y++) {
                Cell cell = new Cell(cellSize);
                cell.setStroke(Color.BLACK);
                cell.setXPos(x);
                cell.setYPos(y);
                
                cells[x][y] = cell;
                this.getChildren().add(cell);
            }
        }
    }
    
//    public void drawCollectible(Collectible c){
//        this.getChildren().add(c.getCircle());
//    }
    
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
        this.cellSize = cellSize*scaleFactor;
    }
    
    
}
