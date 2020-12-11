/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Model.Profile.MapProfile;
import Editor.View.Info;
import Editor.View.Grid.Grid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 *
 * @author linuo
 */
public class InfoController{

    Info info;
    Grid grid;
    GridController gc;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public InfoController(Info info, MapProfile map) {
        this.info = info;
        this.grid = map.getGridView();
        this.gc = map.getGc();
        
        this.info.setupInfoBar(gc);
        
        
    }
    
    public void refreshInfoBar(){
        info.setMouseX(gc.getMouseX());
        info.setMouseY(gc.getMouseY());
        info.setZoom(gc.getZoom());
        
        info.reset();
    }
    
    public void setMap(MapProfile map){
        this.grid = map.getGridView();
        this.gc = map.getGc();
    }
    
    
}
