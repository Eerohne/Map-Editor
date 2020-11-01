/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeditor;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author A
 */
public class GridController {
    Scene scene;
    Grid grid;
    Button toggle;
    ColorPicker picker;
    
    Color winColor = Color.DEEPSKYBLUE;
    Color floorColor = Color.WHITE;
    Color wallColor = Color.BLACK;
    
    int mode = 1;
    boolean wallMode = true;
    
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
        this.grid.setMouseDragEvent(new WallPlacerEvent());
        this.grid.setMousePressEvent(new WallPlacerEvent());
        
        //ColorPicker Events
        this.picker.setOnAction(e -> {
            if (picker.getValue() == floorColor){
                this.wallColor = picker.getValue();
                System.out.println("NOOOOO");
            }
            else
                picker.setValue(wallColor);
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
                grid.drawCollectible(col);
            }
        }
    }
}


