/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model.Profile;

import java.util.LinkedList;

/**
 *
 * @author A
 */
public class ProjectProfile extends Profile{
    private LinkedList<MapProfile> maps;
    private int mainMapID = 0;
    
    public MapProfile selectedMap;
    
    public ProjectProfile(String name) {
        super(name);
        maps  = new LinkedList<>();
    }
    
    public ProjectProfile(String name, MapProfile... map){
        this(name);
        for (MapProfile m : map) {
            maps.add(m);
        }
    }

    public LinkedList<MapProfile> getMaps() {
        return maps;
    }

    public void setMaps(LinkedList<MapProfile> maps) {
        this.maps = maps;
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
}
