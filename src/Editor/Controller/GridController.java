/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Entity.Collectible;
import Editor.Grid.Cell;
import Editor.Grid.Grid;
import javafx.event.EventHandler;
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
    Color wallColor = Color.BLACK;
    
    //Editing mode
    int mode = 1;
    boolean wallMode = true;
    
    //Grid Panning Vector Variables
    double preMouseX;
    double preMouseY; 
    
    double mouseX;
    double mouseY; 
    
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
        });
        
        this.toggle.setOnAction(e -> {
            String modeName = "";
            
            switch(mode++){
                case 0: //Place Wall
                    this.grid.setMouseDragEvent(new WallPlacerEvent());
                    this.grid.setMousePressEvent(new WallPlacerEvent());
                    wallColor = Color.BLACK;
                    modeName = "Place WALL";
                    picker.setDisable(false);
                    break;
                case 1: //Place Collectible
                    this.grid.setMousePressEvent(new CollectiblePlacerEvent());
                    this.grid.setMouseDragEvent(null);
                    modeName = "Place COLLECTIBLE";
                    picker.setDisable(false);
                    break;
                case 2: //Place Winning Cell
                    this.grid.setMouseDragEvent(new WallPlacerEvent());
                    this.grid.setMousePressEvent(new WallPlacerEvent());
                    wallColor = winColor;
                    modeName = "Place WIN CELL";
                    picker.setDisable(true);
                    break;
                case 3: //Remove Wall/Winning Cell
                    this.grid.setMouseDragEvent(new WallPlacerEvent());
                    this.grid.setMousePressEvent(new WallPlacerEvent());
                    wallColor = floorColor;
                    picker.setDisable(true);
                    modeName = "Reset CELL";
                    break;
                case 4: //Remove Collectible
                    modeName = "Remove COLLECTIBLE";
                    picker.setDisable(true);
                    break;
            }
            
            if(mode > 4){
                mode = 0;
            }
            toggle.setText("Mode: " + modeName);
        });
        
        //Grid Events
        //this.grid.setMouseDragEvent(new WallPlacerEvent());
        //this.grid.setMousePressEvent(new WallPlacerEvent());
        grid.setOnScroll(event -> {
            double scaleFactor = 1.05;
            if (event.getDeltaY() < 0) {
                scaleFactor = 0.95;
            }
            Scale scale = new Scale(scaleFactor, scaleFactor);
            for (Cell[] cells : grid.getCells()) {
                for (Cell cell : cells) {
                    cell.getTransforms().add(scale);
                }
            }
        });
        
        grid.setOnMousePressed(event -> {
            if(event.getButton().equals(MouseButton.MIDDLE)){
                this.preMouseX = this.mouseX = event.getX();
                this.preMouseY = this.mouseY = event.getY();
            }
        });
        
        grid.setOnMouseDragged(event -> {
            //Grid Translation Implementation
            if(event.getButton().equals(MouseButton.MIDDLE)){
                //Tail of translation vector
                this.preMouseX = this.mouseX;
                this.preMouseY = this.mouseY;
                //Head of translation vector
                this.mouseX = event.getX();
                this.mouseY = event.getY();
                
                //Translation vecxtor
                Translate vector = new Translate(this.mouseX - this.preMouseX, this.mouseY - this.preMouseY);
                
                //Every cell is translating with the vector above
                for (Cell[] cells : grid.getCells()) {
                    for (Cell cell : cells) {
                        cell.getTransforms().add(vector);
                    }
                }
            }
            
            //Grid Wall Drawing
            if(event.getButton().equals(MouseButton.PRIMARY)){
                int xPos = (int)event.getX()/grid.getCellSize();
                int yPos = (int)event.getY()/grid.getCellSize();

                grid.getCells()[xPos][yPos].setFill(wallColor);
            }
        });
        
        //ColorPicker Events
        this.picker.setOnAction(e -> {
            //if (picker.getValue() == floorColor){
                this.wallColor = picker.getValue();
                //System.out.println("NOOOOO");
            //}
            //else
                //picker.setValue(wallColor);
        });
    }
    
    class WallPlacerEvent implements EventHandler<MouseEvent>{

        @Override
        public void handle(MouseEvent event) {
            int xPos = (int)event.getX()/grid.getCellSize();
            int yPos = (int)event.getY()/grid.getCellSize();
            
            grid.getCells()[xPos][yPos].setFill(wallColor);        
        }
    }
    
    class CollectiblePlacerEvent implements EventHandler<MouseEvent>{

        @Override
        public void handle(MouseEvent event) {
            double xPos = event.getX();
            double yPos = event.getY();
            
            if(grid.getCells()[(int)xPos/grid.getCellSize()][(int)yPos/grid.getCellSize()].getFill().equals(Color.WHITE)){
                Collectible col = new Collectible(xPos, yPos, 10, Color.CORAL);
                //grid.drawCollectible(col);
            }
        }
    }
}


