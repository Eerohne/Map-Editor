/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model;

import Editor.Controller.GridController;
import Editor.View.Grid.Grid;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * A wrapper for all the necessary tools for building a Level in the Engine
 * 
 * @author A
 */
public class Map {
    private String name;
    private String resourcePath;
    private String mapLocation;
    
    private Grid gridView;
    private GridController gc;
    private WallProfile defaultWall;
    
    

    public Map(String name, String mapLocation, String resourcePath, int gridWidth, int gridLength, GridController gc) {
        this.name = name;
        this.mapLocation = mapLocation;
        this.resourcePath = resourcePath;
        
        this.gridView = new Grid(50, gridWidth, gridLength, defaultWall);
        setChildrenClipping(gridView);
        
        this.gc = gc;
    }
    
    private static void setChildrenClipping(Pane pane) {
        Rectangle clip = new Rectangle();
        pane.setClip(clip);    
        
        pane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            clip.setWidth(newValue.getWidth());
            clip.setHeight(newValue.getHeight());
        });
    }
}
