package Editor.Controller;

import Editor.Model.WallProfile;
import Editor.View.Grid.Cell;
import Editor.View.Grid.Grid;
import Editor.View.Info;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 *
 * @author A
 */
public class GridController{
    //
    public static WallProfile selectedWallProfile;
    
    //Objects to be controlled
    Scene scene;
    Grid grid;
    
    //Colors - to be replacve with a palette
    
    String cssLayout = "-fx-border-color: red;\n" +
                   "-fx-border-insets: 5;\n" +
                   "-fx-border-width: 3;\n" +
                   "-fx-border-style: dashed;\n";
    
    //Editing mode
    int mode = 1;
    boolean wallMode = true;
    
    //Grid Panning Vector Variables
    double preMouseX;
    double preMouseY; 
    double mouseX;
    double mouseY;
    
    double zoom = 1.0d;

    Cell hoverCell = new Cell(1);
    Info info = new Info();
    
    public GridController(Scene scene, Grid grid) {
        this.scene = scene;
        this.grid = grid;
        
        //Scene Events
        scene.setOnKeyPressed( event -> {
            if(event.getCode().equals(KeyCode.R)){
                this.grid.clear();
            }
            if(event.getCode().equals(KeyCode.W)){
                System.out.println("**********\n" 
                        + "Mouse : (" + mouseX + ", " + mouseY + ")\n"
                        + "Grid : (" + getGridX() + ", " + getGridY() + ")\n" 
                        +"cellSize : "+grid.getCellSize()+"\n"
                        + "MaxY : " + getPaneBounds().getMaxY() + "\n" 
                        + "Zoom : " + zoom + "\n" 
                        + grid.cells[0][0].getTransforms() + "\n" );
            }
        });
        
        //Grid Events
        
        //Zoom Event
        grid.setOnScroll(event -> {
            double scaleShift = 0;
            if(zoom > 1.99) {
                if (event.getDeltaY() < 0) {
                    scaleShift -= 0.05;
                    scale(scaleShift);
                }
            }
            else{
                if (event.getDeltaY() < 0) {
                    scaleShift -= 0.05;
                } else{
                    scaleShift += 0.05;
                }
                
                scale(scaleShift);
            }
        });
        
        //Hover Event
        for (Cell[] cells : grid.getCells()) {
            for (Cell cell : cells) {
                cell.setOnMouseEntered(event -> {
                    onHover(cell);
                });
            }
        }
        
        //Update Mouse Position on Mouse Moved
        grid.setOnMouseMoved(event -> {
            updateMousePos(event);
        });
        
        //Place Wall on Mouse Pressed
        grid.setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY))
                this.placeWall();
        });
        
        grid.setOnMouseEntered(e -> {grid.setStyle(cssLayout);});
        grid.setOnMouseExited(e -> {grid.setStyle(null);});
        
        //Wall Placement when Mouse Dragged
        grid.setOnMouseDragged(new WallPlacerEvent());
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
    
    class GridEvent implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            updateMousePos(event);
            
            //Grid Translation Implementation
            if(event.getButton().equals(MouseButton.MIDDLE)){
                //Translation vecxtor
                Translate vector = new Translate((mouseX - preMouseX), (mouseY - preMouseY));
                
                //Every cell is translating with the vector above
                for (Cell[] cells : grid.getCells()) {
                    for (Cell cell : cells) {
                        cell.addTranslationVector(vector);
                    }
                }
                
                grid.getSelectionCell().addTranslationVector(vector);
            }
        }
    }
    
    private void updateMousePos(MouseEvent event){
        preMouseX = mouseX;
        preMouseY = mouseY;
        this.mouseX = event.getX();
        this.mouseY = event.getY();
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public double getZoom() {
        return zoom;
    }
    
    private double getGlobalX(){
        return mouseX + getPaneBounds().getMinX();
    } 
    
    private double getGlobalY(){
        return mouseY + getPaneBounds().getMinY();
    }
    
    private Bounds getPaneBounds(){
        return grid.getCells()[0][0].getParent().localToScene(grid.getCells()[0][0].getParent().getBoundsInLocal());
    } 
    
    private Bounds getGridBounds(){
        return grid.getCells()[0][0].localToScene(grid.getCells()[0][0].getBoundsInLocal());
    } 
    
    public double getGridX(){
        return (this.getGlobalX() - getGridBounds().getMinX())/grid.getCellSize();
    }
    
    public double getGridY(){
        return (this.getGlobalY() - getGridBounds().getMinY())/grid.getCellSize();
    }
    
    private void placeWall(){
        //Fix Mouse Drag Leak
        try {
            if(!(mouseX < 0 || mouseY < 0 || mouseX > getPaneBounds().getMaxX() || mouseY > getPaneBounds().getMaxY())){
                Cell c = this.grid.getCells()[(int)getGridX()][(int)getGridY()];
                c.setImg(selectedWallProfile.getImage());
                onHover(c);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void onHover(Cell hoverCell){
        this.hoverCell = hoverCell;
        this.highlight();
    }
    
    private void highlight(){
        grid.getSelectionCell().setX(hoverCell.getX());
        grid.getSelectionCell().setY(hoverCell.getY());
    }
    
    private void scale(double scaleFactor){
        Scale scale = new Scale(scaleFactor, scaleFactor);
        
        grid.setCellSize(scaleFactor);
        //Fix Scale Corrector
        //Translate scaleCorrector = new Translate((getLocalX() - aX*grid.getCellSize()), (getLocalY() - aY*grid.getCellSize()));

        for (Cell[] cells : grid.getCells()) {
            for (Cell cell : cells) {
                //cell.addTranslationVector(scaleCorrector);
                cell.addScaleMatrix(scale);
            }
        }
        grid.getSelectionCell().addScaleMatrix(scale);
        
        grid.setCellSize(grid.getCells()[0][0].getDefaultSize() * grid.getCells()[0][0].getScaleObject().getX());
        zoom = this.getScaleRatio();
    }
    
    private double getScaleRatio(){
        return grid.getCells()[0][0].getScaleObject().getX();
    }
}


