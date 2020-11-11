package Editor.Controller;

import Editor.Entity.Collectible;
import Editor.Grid.Cell;
import Editor.Grid.Grid;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 *
 * @author A
 */
public class GridController {
    //Objects to be controlled
    Scene scene;
    Grid grid;
    Button toggle;
    ColorPicker picker;
    
    //Colors - to be replacve with a palette
    Color winColor = Color.DEEPSKYBLUE;
    Color floorColor = Color.WHITE;
    Color wallColor = Color.AQUA;
    
    //Editing mode
    int mode = 1;
    boolean wallMode = true;
    
    //Grid Panning Vector Variables
    double preMouseX;
    double preMouseY; 
    double mouseX;
    double mouseY;
    
    double aX;
    double aY;
    
    float zoom = 1.0f;

    Cell hoverCell = new Cell(1);
    
    public GridController(Scene scene, Grid grid, Button toggle, ColorPicker picker) {
        this.scene = scene;
        this.grid = grid;
        this.toggle = toggle;
        this.picker = picker;
        
        //Scene Events
        scene.setOnKeyPressed( event -> {
            if(event.getCode().equals(KeyCode.R)){
                this.grid.clear();
            }
            if(event.getCode().equals(KeyCode.W)){
                System.out.println("Mouse : (" + mouseX + ", " + mouseY + ")\n"
                        + "Grid : (" + aX + ", " + aY + ")\n" 
                        + "Local : (" + getLocalX() + ", " + getLocalY() + ")\n" 
                        + "Zoom : " + zoom);
            }
        });
        
        //Grid Events
        grid.setOnScroll(event -> {
            double scaleFactor = 1;
            if(zoom < 2.0f) {
                if (event.getDeltaY() < 0) {
                    scaleFactor -= 0.05;
                    zoom -= 0.05f;
                } else{
                    scaleFactor += 0.05;
                    zoom += 0.05f;
                }
                
                scale(scaleFactor);
            } else{
                if (event.getDeltaY() < 0) {
                    scaleFactor -= 0.05;
                    zoom -= 0.05f;
                    
                    scale(scaleFactor);
                }
            }
        });
        
        grid.setOnMouseMoved(event -> {
            updateMousePos(event);
            
            this.hoverEvent();
        });
        
        //On Mouse Press Event
        grid.setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY))
                this.placeWall();
        });
        
        //Wall Placement when Mouse Dragged
        grid.setOnMouseDragged(new WallPlacerEvent());
        
        
        //ColorPicker Events
        this.picker.setOnAction(e -> {
            this.wallColor = picker.getValue();
        });
    }
    
    /**
     * Defines the event necessary to place a wall.
     */
    class WallPlacerEvent extends GridEvent{

        @Override
        public void handle(MouseEvent event) {
            super.handle(event);
            
            //Grid Wall Drawing
            if(event.getButton().equals(MouseButton.PRIMARY)){
                placeWall();
            }  
        }
    }
    
    class CollectiblePlacerEvent implements EventHandler<MouseEvent>{

        @Override
        public void handle(MouseEvent event) {
            double xPos = event.getX();
            double yPos = event.getY();
            
            if(grid.getCells()[(int)(xPos/grid.getCellSize())][(int)(yPos/grid.getCellSize())].getFill().equals(Color.WHITE)){
                Collectible col = new Collectible(xPos, yPos, 10, Color.CORAL);
                //grid.drawCollectible(col);
            }
        }
    }
    
    class GridEvent implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            updateMousePos(event);
            hoverEvent();
            //Grid Translation Implementation
            if(event.getButton().equals(MouseButton.MIDDLE)){
                //Translation vecxtor
                Translate vector = new Translate((mouseX - preMouseX), (mouseY - preMouseY));
                
                //Every cell is translating with the vector above
                for (Cell[] cells : grid.getCells()) {
                    for (Cell cell : cells) {
                        cell.getTransforms().add(vector);
                    }
                }
            }
        }
    }
    
    private void updateMousePos(MouseEvent event){
        preMouseX = mouseX;
        preMouseY = mouseY;
        this.mouseX = event.getX();
        this.mouseY = event.getY();
        
        this.aX = this.getGridX();
        this.aY = this.getGridY();
    }
    
    private double getLocalX(){
        Bounds paneBound = grid.getCells()[0][0].localToScene(grid.getCells()[0][0].getBoundsInLocal());
        return mouseX - paneBound.getMinX();
    } 
    
    private double getLocalY(){
        Bounds paneBound = grid.getCells()[0][0].localToScene(grid.getCells()[0][0].getBoundsInLocal());
        return (mouseY - paneBound.getMinY());
    }
    
    public double getGridX(){
        return this.getLocalX()/grid.getCellSize();
    }
    
    public double getGridY(){
        return this.getLocalY()/grid.getCellSize();
    }
    
    private void placeWall(){
        if(!(getLocalX() < 0) && !(getLocalY() < 0)){
            hoverCell.setColor(wallColor);
        }
    }
    
    private void hoverEvent(){
        hoverCell.isSelected(false);
        
        hoverCell = grid.getCells()[(int)aX][(int)aY];
        
        hoverCell.isSelected(true);
    }
    private void scale(double scaleFactor){
        Scale scale = new Scale(scaleFactor, scaleFactor);
        
        grid.setCellSize(scaleFactor);
        Translate scaleCorrector = new Translate((getLocalX() - aX*grid.getCellSize()), (getLocalY() - aY*grid.getCellSize()));

        for (Cell[] cells : grid.getCells()) {
            for (Cell cell : cells) {
                cell.getTransforms().addAll(scale, scaleCorrector);

            }
        }
    }
}


