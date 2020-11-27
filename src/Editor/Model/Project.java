/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model;

import java.util.LinkedList;
import javafx.scene.layout.VBox;

/**
 *
 * @author A
 */
public class Project {
    private LinkedList<MapModel> maps;
    private int mainMapID = 0;
    
    public MapModel selectedMap;
    
    private VBox mapList;
    
    public Project() {
        maps  = new LinkedList<>();
    }
    
    public Project(MapModel... map){
        this();
        for (MapModel m : map) {
            maps.add(m);
        }
    }
    
    private VBox setupMapList(){
        return null;
    }
    
    public static void refreshMapList(Project p){
        
    }

    public LinkedList<MapModel> getMaps() {
        return maps;
    }

    public void setMaps(LinkedList<MapModel> maps) {
        this.maps = maps;
    }

    public MapModel getMainMap() {
        return maps.get(mainMapID);
    }

    public void setMainMap(MapModel map) {
        for (int i = 0; i < maps.size(); i++) {
            if(map.equals(maps.get(i))){
                this.mainMapID = i;
            }
        }
    }
    
    public VBox getMapList() {
        return mapList;
    }

    public void setMapList(VBox mapList) {
        this.mapList = mapList;
    }
}
