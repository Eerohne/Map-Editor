/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model.Profile;

import Editor.Controller.GridController;
import Editor.View.Grid.Grid;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * A wrapper for all the necessary tools for building a Level in the Engine
 * 
 * @author A
 */
public class MapProfile extends Profile{
    private int wallCounter = 1;
    
    //private String resourcePath;
    //private String mapLocation;
    
    private Grid gridView;
    private GridController gc;
    private WallProfile defaultWall;
        
    private boolean mainMap;
    
    private Map<Integer, WallProfile> wallMap;
    

    public MapProfile(String name, int gridWidth, int gridLength) {
        super(name);
        this.mainMap = false;
        
        this.wallMap  = new TreeMap<>();
        this.defaultWall = new WallProfile();
        this.wallMap.put(defaultWall.getID(), defaultWall);
        
        this.gridView = new Grid(50, gridWidth, gridLength, defaultWall);
        setChildrenClipping(gridView);
        
        this.gc = new GridController(gridView);
        gc.setSelectedWallProfile(defaultWall);
    }
    
    private static void setChildrenClipping(Pane pane) {
        Rectangle clip = new Rectangle();
        pane.setClip(clip);    
        
        pane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            clip.setWidth(newValue.getWidth());
            clip.setHeight(newValue.getHeight());
        });
    }

//    public static void selectMap(Map map){
//        
//    }
    
    public WallProfile createWallProfile(String name, String imgName, int flag){
        WallProfile wall = new WallProfile(wallCounter++, name, imgName, flag);
        this.wallMap.put(wallCounter, wall);
        this.gc.setSelectedWallProfile(wall);
        return wall;
    }
    
    public static String getTxrURL(MapProfile map, int id){
        for (Map.Entry<Integer, WallProfile> entry : map.wallMap.entrySet()) {
            if(entry.getKey() == id){
                return WallProfile.resourceFolder + entry.getValue().getImageName();
            }
        }
        
        return null;
    }

    public void setWallImg(WallProfile wall, String img){
        wall.setImg(img);
    }
    
//    public int getWall(Image img){
//        for (Map.Entry<Integer, WallProfile> entry : wallMap.entrySet()) {
//            if (entry.getValue().getImage().equals(img)) {
//                return entry.getKey();
//            }
//        }
//        return -1;
//    }
//    
//    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.gridView);
        hash = 41 * hash + Objects.hashCode(this.gc);
        hash = 41 * hash + Objects.hashCode(this.defaultWall);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MapProfile other = (MapProfile) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.gridView, other.gridView)) {
            return false;
        }
        if (!Objects.equals(this.gc, other.gc)) {
            return false;
        }
        if (!Objects.equals(this.defaultWall, other.defaultWall)) {
            return false;
        }
        return true;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getMapLocation() {
//        return mapLocation;
//    }
//
//    public void setMapLocation(String mapLocation) {
//        this.mapLocation = mapLocation;
//    }

    public Grid getGridView() {
        return gridView;
    }

    public void setGridView(Grid gridView) {
        this.gridView = gridView;
    }

    public GridController getGc() {
        return gc;
    }

    public void setGc(GridController gc) {
        this.gc = gc;
    }

    public WallProfile getDefaultWall() {
        return defaultWall;
    }

    public void setDefaultWall(WallProfile defaultWall) {
        this.defaultWall = defaultWall;
    }

    public Map<Integer, WallProfile> getWallMap() {
        return wallMap;
    }

    public boolean isMainMap() {
        return mainMap;
    }

    public void setMainMap(boolean mainMap) {
        this.mainMap = mainMap;
    }
}
