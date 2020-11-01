package mapeditor;

import java.util.Arrays;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Grid extends Pane{
    private int cellSize;
    private int xLength;
    private int yLength;
    private Rectangle[][] cells;

    public Grid(int pixelSize, int xLength, int yLength) {
        super();
        
        this.cellSize = pixelSize;
        this.xLength = xLength;
        this.yLength = yLength;
        
        this.cells = new Rectangle[xLength][yLength];
        
        this.drawGrid();
    }
    
    private void drawGrid(){
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                Rectangle cell = new Rectangle(cellSize, cellSize, Color.WHITE);
                cell.setStroke(Color.BLACK);
                cell.setX(x*cellSize);
                cell.setY(y*cellSize);
                
                cells[x][y] = cell;
                this.getChildren().add(cell);
            }
        }
    }
    
    public void drawCollectible(Collectible c){
        this.getChildren().add(c.getCircle());
    }
    
    public void clear(){
        for (Rectangle[] cell : cells) {
            for (Rectangle rectangle : cell) {
                rectangle.setFill(Color.WHITE);
            }
        }
        Collectible.getCollectibles().clear();
        this.getChildren().clear();
        for (Rectangle[] cell : cells) {
            this.getChildren().addAll(Arrays.asList(cell));
        }
    }
    
    public void setMouseDragEvent(EventHandler<MouseEvent> event){
        this.setOnMouseDragged(event);
    }
    
    public void setMousePressEvent(EventHandler<MouseEvent> event){
        this.setOnMousePressed(event);
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getxLength() {
        return xLength;
    }

    public int getyLength() {
        return yLength;
    }

    public Rectangle[][] getCells() {
        return cells;
    }

}
