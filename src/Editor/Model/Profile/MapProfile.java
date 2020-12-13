/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model.Profile;

import Editor.Controller.GridController;
import Editor.Main.MapEditor;
import Editor.View.Grid.Grid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    public WallProfile defaultWall;
    
    private boolean mainMap;
    
    private Map<Integer, WallProfile> wallMap = new TreeMap<>();
    private Map<String, EntityProfile> entityMap = new TreeMap<>();
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    

    public MapProfile(String name, int gridWidth, int gridLength) {
        super(name);
        this.mainMap = false;
        
        this.defaultWall = new WallProfile();
        this.wallMap.put(defaultWall.getID(), defaultWall);
        
        this.gridView = new Grid(50, 10, gridWidth, gridLength, defaultWall);
        setChildrenClipping(gridView);
        
        this.gc = new GridController(gridView);
        gc.setSelectedWallProfile(defaultWall);
    }
    
    public MapProfile(String name){
        super(name);
        this.defaultWall = new WallProfile();
        this.wallMap.put(defaultWall.getID(), defaultWall);
    }
    
    public MapProfile(File mapFile){
        this(mapFile.getName().substring(0, mapFile.getName().lastIndexOf(".")));
        
        MapEditor.load(mapFile);
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
        WallProfile wall = new WallProfile(wallCounter, name, imgName, flag);
        this.wallMap.put(wallCounter, wall);
        wallCounter++;
        this.gc.setSelectedWallProfile(wall);
        return wall;
    }
    
    public EntityProfile createEntityProfile(String name){
        EntityProfile entity = new EntityProfile(name);
        this.entityMap.put(name, entity);
        this.gc.setSelectedEntityProfile(entity);
        this.gc.setupDot(entity.getDot());
        return entity;
    }
    
    public WallProfile loadWallProfile(String name, String imgName, int flag, int id){
        System.out.println("WHYYYYYYYYYYYYYYYYYYYY");
        WallProfile wall = new WallProfile(id, name, imgName, flag);
        this.wallMap.put(id, wall);
        wallCounter++;
        this.gc.setSelectedWallProfile(wall);
        return wall;
    }
    
     public EntityProfile loadEntityProfile(String mapName, String entityName, float[] color, double gridX, double gridY){
        
        EntityProfile entity = new EntityProfile(mapName, entityName, Color.rgb((int)(color[0]*255), (int)(color[1]*255), (int)(color[2]*255)));
        this.entityMap.put(entityName, entity);
        this.gc.setSelectedEntityProfile(entity);
        this.gc.setupDot(entity.getDot());
        
        entity.getDot().initialize(gridX * 50, gridY * 50, 10);
        return entity;
    }
    
    public static String getTxrURL(MapProfile map, int id){
        for (Map.Entry<Integer, WallProfile> entry : map.wallMap.entrySet()) {
            if(entry.getKey() == id){
                return MapEditor.getProject().getImageFolder() + entry.getValue().getImageName();
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
    public void save(){

    }
    
    
    
    
/************************************
 * Getters/Setters And Equals 
*************************************/    
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
    
    public Map<String, EntityProfile> getEntityMap() {
        return entityMap;
    }

    public boolean isMainMap() {
        return mainMap;
    }

    public void setMainMap(boolean mainMap) {
        this.mainMap = mainMap;
    }
}
