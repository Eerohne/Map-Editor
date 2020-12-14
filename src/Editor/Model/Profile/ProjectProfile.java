/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model.Profile;

import Editor.Main.MapEditor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 *
 * @author A
 */
public class ProjectProfile extends Profile{
    private LinkedList<MapProfile> maps;
    private int mainMapID = 0;
    
    private String resourceFolder;
    private String imageFolder;
    private String levelFolder;
    
    public MapProfile selectedMap;

    public ProjectProfile(String name) {
        super(name);
        this.resourceFolder = "resources/";
        this.imageFolder = this.resourceFolder + "images/textures/";
        this.levelFolder = this.resourceFolder + "levels/";
        this.maps = new LinkedList<>();
    }
    
    public static boolean openProject(){
            ProjectProfile p = new ProjectProfile("optik_editor");
            p.loadProject();
            p.setSelectedMap(p.getMaps().get(0));
            MapEditor.setProject(p);
            return true;
    }
    
    public boolean loadProject(){
        
        File mapDir = new File(levelFolder);
        File[] mapList = mapDir.listFiles();
        
        if(mapList.length == 0){
            this.addMap(new MapProfile("Map", 10, 10), true);
        }
        else {
            for (File file : mapList) {
                this.loadMap(MapEditor.load(file));
            }
        }
        
        return true;
    }

    public String getResourceFolder() {
        return resourceFolder;
    }

    public String getImageFolder() {
        return imageFolder;
    }

    public String getLevelFolder() {
        return levelFolder;
    }
    
    public LinkedList<MapProfile> getMaps() {
        return maps;
    }

    public void setMaps(LinkedList<MapProfile> maps) {
        this.maps = maps;
    }

    public void addMap(MapProfile map, boolean isNew){
        this.maps.add(map);
        if(isNew){
            this.setSelectedMap(map);
            MapEditor.refreshEditor();
        }
    }
    
    public void loadMap(MapProfile map){
        this.addMap(map, false);
        if(map.isMainMap())
            this.setMainMap(map);
    }
    
    public MapProfile getMainMap() {
        return maps.get(mainMapID);
    }

    public void setMainMap(MapProfile map) {
        for (int i = 0; i < maps.size(); i++) {
            map.setMainMap(false);
            if(map.equals(maps.get(i))){
                this.mainMapID = i;
                map.setMainMap(true);
            }
        }
    }
    
    public MapProfile getSelectedMap(){
        return selectedMap;
    }

    public void setSelectedMap(MapProfile selectedMap) {
        this.selectedMap = selectedMap;
    }
    
    public String getSelectedMapPath(){
        return MapEditor.getProject().getLevelFolder() + MapEditor.getProject().getSelectedMap().getName() + ".lvl";
    }
    
    public MapProfile getMapByName(String name) throws FileNotFoundException{
        for (MapProfile map : maps) {
            if(map.getName().equals(name))
                return map;
        }
        
        throw new FileNotFoundException();
    }
}
