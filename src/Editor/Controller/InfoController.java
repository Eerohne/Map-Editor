/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Model.MapModel;
import java.io.FileNotFoundException;
import Editor.View.Info;
import Editor.View.Grid.Grid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 *
 * @author linuo
 */
public class InfoController{

    Info info;
    Grid grid;
    GridController gc;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public InfoController(Info info, MapModel map) {
        this.info = info;
        this.grid = map.getGridView();
        this.gc = map.getGc();
        
        this.info.setupInfoBar(gc);
        
        EventHandler saveHandler = new EventHandler() {
             @Override
             public void handle(Event event) {
                 try {
                     save();
                 } catch (IOException ex) {
                     Logger.getLogger(InfoController.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
         }; 
        info.save.setOnAction(saveHandler);
    }
    
    public void refreshInfoBar(){
        info.setMouseX(gc.getMouseX());
        info.setMouseY(gc.getMouseY());
        info.setZoom(gc.getZoom());
        
        info.reset();
    }
    
    public void setMap(MapModel map){
        this.grid = map.getGridView();
        this.gc = map.getGc();
    }
    
    public void save() throws IOException{
        JSONArray editorSaveArray = new JSONArray();// array for all info to be saved
        JSONObject gridX = new JSONObject(); // width of grid
        JSONObject gridY = new JSONObject(); // length of grid
        JSONObject gridCells = new JSONObject(); // the 2-d array of the map
        JSONObject paletteObj = new JSONObject(); // palette in wallprofile 
        
        gridX.put("grid width", grid.getxLength());
        gridY.put("grid length", grid.getyLength());
        
        JSONArray cellsArray = new JSONArray();
        for(int i = 0; i < grid.getCells().length; i++){
            JSONArray array = new JSONArray();
            for(int j = 0; j < grid.cells[i].length; j++){
                array.add(grid.cells[i][j].getWallID());
            }
            cellsArray.add(array);
        }
        gridCells.put("map", cellsArray);
        paletteObj.putAll(WallProfile.wallMap);
        editorSaveArray.add(gridX);
        editorSaveArray.add(gridY);
        editorSaveArray.add(gridCells);
        editorSaveArray.add(paletteObj);
        
        FileWriter writer = new FileWriter("grid.json");
        gson.toJson(editorSaveArray, writer);
        writer.close();
        
    }
    
    public void load() throws FileNotFoundException, IOException, ClassNotFoundException{
        
    }
}
