package Editor.Controller;

import Editor.Model.Profile.EntityProfile;
import Editor.Model.Profile.Profile;
import Editor.Model.Profile.WallProfile;
import Editor.View.Grid.Cell;
import Editor.View.Grid.EntityDot;
import Editor.View.Grid.Grid;
import Editor.View.Info;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author A
 */
public class GridController{
    //Objects to be controlled
    Grid grid;
    
    //EntityDot ed = new EntityDot(Color.CYAN);

    
    private Profile selectedProfile;

    //Grid Panning Vector Variables
    double preMouseX;
    double preMouseY; 
    double mouseX;
    double mouseY;
    
    private int editingMode = 1; // 0: Editing Disabled | 1: Wall Placement | 2: Entity Placement
    
    double zoom = 1.0d;

    Cell hoverCell = new Cell(1);
    EntityDot dot = new EntityDot(0, Color.BLACK, 0, 0, 1);
    Info info = new Info();
    
    double dotX = 0;
    double dotY = 0;
    
    public GridController(Grid grid) {
        //this.scene = scene;
        this.grid = grid;
        
        
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
            else if(zoom < 0.05) {
                if (event.getDeltaY() > 0) {
                    scaleShift += 0.05;
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
            if(event.getButton().equals(MouseButton.PRIMARY)){
                switch(editingMode){
                    case 1:
                        placeWall();
                        break;
                    case 2:
                        placeEntity();
                        break;
                    default:
                        break;
                }
            }
        });
        
//        grid.setOnMouseEntered(e -> {grid.setStyle(cssLayout);});
//        grid.setOnMouseExited(e -> {grid.setStyle(null);});
        
        //Wall Placement when Mouse Dragged
        grid.setOnMouseDragged(new GridEvent());
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
                
                
                dot.addTranslationVector(vector);
                dotX += (mouseX - preMouseX);
                dotY += (mouseY - preMouseY);
                
                grid.getSelectionCell().addTranslationVector(vector);
            }
            
            //
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(editingMode == 1)
                    placeWall();
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
    
    public int getEditingMode(){
        return editingMode;
    }
    
    public void setEditingMode(int mode){
        this.editingMode = mode;
    }
    
    public WallProfile getSelectedWallProfile() {
        return (WallProfile)selectedProfile;
    }

    public void setSelectedWallProfile(WallProfile selectedWallProfile) {
        this.selectedProfile = selectedWallProfile;
    }
    
    public EntityProfile getSelectedEntityProfile() {
        //this.selectedProfile  = new EntityProfile("Test", 1);//To remove
        return (EntityProfile)selectedProfile;
    }

    public void setSelectedEntityProfile(EntityProfile selectedEntityProfile) {
        this.selectedProfile = selectedEntityProfile;
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
                this.setImg(c, getSelectedWallProfile().getImage());
                onHover(c);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void placeEntity(){
        try {
            if(!(mouseX < 0 || mouseY < 0 || mouseX > getPaneBounds().getMaxX() || mouseY > getPaneBounds().getMaxY())){
                EntityDot ed = getSelectedEntityProfile().getDot();
                ed.initialize((mouseX - dotX)/dot.getScaleObject().getX(), (mouseY - dotY)/dot.getScaleObject().getX(), 10);

                savePosition(getSelectedEntityProfile().getName(), getGridX(), getGridY());
            }
        } catch(Exception e){
            System.out.println("Entity : " + e);
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
        
        System.out.println("******************************************");
        this.dot.addScaleMatrix(scale);
        System.out.println(dot.getScaleObject());
       
        grid.setEntityDotSize(10 * zoom);
        grid.getSelectionCell().addScaleMatrix(scale);
        
        grid.setCellSize(grid.getCells()[0][0].getDefaultSize() * grid.getCells()[0][0].getScaleObject().getX());
        zoom = this.getScaleRatio();
    }
    
    private double getScaleRatio(){
        return grid.getCells()[0][0].getScaleObject().getX();
    }
    
    public void setImg(Cell cell, Image img){
        WallProfile wp = (WallProfile)selectedProfile;
        int wallId = wp.getID();
        this.setImg(cell, wallId);
    }
    
    public void setImg(Cell cell, int paletteID){
        cell.setID(paletteID);
        cell.setTexture(new ImagePattern(((WallProfile)selectedProfile).getImage()));
    }
    
    public void setupDot(EntityDot newDot){
        newDot.initialize(0, 0, 1);
        newDot.setScaleObject(dot.getScaleObject());
        newDot.setTranslationObject(dot.getTranslationObject());

        grid.getEntities().add(newDot);
        grid.getChildren().add(newDot);
    }
    
    private void savePosition(String name, double x, double y){
        
        FileReader reader = null;
        try {
            JSONParser parser = new JSONParser();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            reader = new FileReader("savefile.json");
            JSONObject savefile = (JSONObject) parser.parse(reader);
            JSONArray entities = (JSONArray) savefile.get("entities");
            JSONObject currentEntity = new JSONObject();
            JSONArray position = new JSONArray();
            int counter = 0;
            position.add(x);
            position.add(y);
            
            for(int i = 0; i < entities.size(); i++){
                currentEntity = (JSONObject) entities.get(i);
                if(currentEntity.get("name").equals(name)){
                    currentEntity.put("position", position);
                    counter++;
                }
            }
            
            entities.set(counter, currentEntity);
            savefile.put("entities", entities);
            
            FileWriter writer = new FileWriter("savefile.json");
            gson.toJson(savefile, writer);
            writer.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}


