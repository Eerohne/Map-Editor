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
    String testStr = "123testing testing testing";
    
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
        
//        System.out.println(Arrays.deepToString(gridRender.getCells()));
//        System.out.println(gridRender.getxLength());
//        System.out.println(gridRender.getyLength());
        JSONArray gridData = new JSONArray();
        JSONObject gridObj = new JSONObject();
//        Map<Integer, Integer> gridDimension = new HashMap<>();
//        gridDimension.put(grid.getxLength(), grid.getyLength());
        JSONObject gridX = new JSONObject();
        gridX.put("length", grid.getxLength());
        
        JSONObject gridY = new JSONObject();
        gridY.put("width", grid.getyLength());
        
        gridObj.put(gridX, gridY);
        JSONObject gridObj2 = new JSONObject();
        
        JSONArray array = new JSONArray();
        for(int i = 0; i < grid.getCells().length; i++){
            JSONArray array2 = new JSONArray();
            for(int j = 0; j < grid.cells[i].length; j++){
                array2.add(grid.cells[i][j].getId());
            }
            array.add(array2);
        }
        JSONObject mapObj = new JSONObject();
        mapObj.put("map", array);
        gridObj2.put(gridObj, mapObj);
        JSONObject gridObj3 = new JSONObject();
        
      
        gridObj3.put("grid", gridObj2);
        //gridData.add(gridObj4);
        
        gridObj3.toString().replaceAll("\\\\", "");
        gridObj2.toString().replace("\\\\", "");
        gridObj.toString().replace("\\\\", "");
        
         try (FileWriter file = new FileWriter("grid.json")) {
             
            file.write(gridObj3.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public void load() throws FileNotFoundException, IOException, ClassNotFoundException{
        
    }
}
