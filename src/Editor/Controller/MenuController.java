/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Main.MapEditor;
import Editor.Model.Profile.MapProfile;
import Editor.Model.Profile.WallProfile;
import Editor.View.Help;
import Editor.View.New.NewEntityStage;
import Editor.View.Menu.Entity.ExistingEntityStage;
import Editor.View.Menu.TopMenu;
import Editor.View.New.NewMap;
import Editor.View.New.NewWallProfile;
import Engine.Core.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author A
 */
public class MenuController{
    private Menu file;
    private Menu edit;
    private Menu run;
    private Menu help;
    private TopMenu menu;
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public MenuController(TopMenu menu, Stage editorStage) {
        
        //this.map = new MapProfile("name", , 0), map.getGridView().getxLength());
        this.menu = menu;
        
        this.file = menu.getFile();
        this.edit = menu.getEdit();
        this.run = menu.getRun();
        this.help = menu.getHelp();
        
        //File Events
        List<MenuItem> fileItems = file.getItems();
        
        //File -> Exit : Closes the Editor Stage
        fileItems.get(fileItems.size()-1).setOnAction(e -> {
            editorStage.close();
        });
        
        //File -> Load Map : Loads a Map from a file
        fileItems.get(3).setOnAction((ActionEvent event) -> {
            load();
        });
        
        //File -> Save Map : Saves the currently viewed map
        fileItems.get(2).setOnAction(e -> {
            try {
                save();
            } catch (ParseException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //File -> New Map : Opens Stage for New Map Instantiation
        fileItems.get(0).setOnAction(e -> {
            new NewMap(editorStage);
        });
        
        //Run -> Run Map : Runs Current Map
        List<MenuItem> runItems = run.getItems();
        runItems.get(0).setOnAction(e ->{
            try {
                Stage engine = new Stage();
                Game game = new Game();
                engine.initOwner(editorStage);
                game.editorModeStart(engine, "levels/level2.lvl");
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Engine Error");
                alert.setContentText("Engine Not Executed");
                alert.showAndWait();
            }
        });
        
        List<MenuItem> editItems = edit.getItems();
        
        //Edit -> New Wall : Open New Wall Window
        editItems.get(0).setOnAction(e -> {
            new NewWallProfile(editorStage, WallProfile.resourceFolder, MapEditor.getWallHierarchy());
        });
        
        //Edit -> New Entity : Open New Entity Window
        editItems.get(1).setOnAction(e -> {
            new NewEntityStage(editorStage);
        });
        
        //Edit -> Edit Entities : Open Edit Existing Entity Window
        editItems.get(2).setOnAction(e -> {
            try {
                new ExistingEntityStage(editorStage);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        List<MenuItem> helpItems = help.getItems();

        helpItems.get(0).setOnAction(e -> {
            new Help(editorStage);
        });
    }
    
    private void save() throws ParseException{
        JSONObject grid = new JSONObject();
        FileReader reader = null;
        try {
            reader = new FileReader("savefile.json"); 
            //FileWriter writer2 = new FileWriter("savefile.json");
            File file = new File("savefile.json");
            JSONParser parser = new JSONParser();
            JSONObject savefileObj = new JSONObject(); 
            JSONArray palette = new JSONArray();
            
            if(file.length() == 0 || !file.exists()){
                //savefileObj = (JSONObject) parser.parse(reader);
                grid.put("width", Integer.toString(MapEditor.project.selectedMap.getGridView().getxLength()));
                grid.put("height", Integer.toString(MapEditor.project.selectedMap.getGridView().getyLength()));
                JSONArray cellsArray = new JSONArray();
                int maxValue = 0;

                for(int i = 0; i< MapEditor.project.selectedMap.getGridView().cells.length; i++){
                    JSONArray array = new JSONArray();
                    for(int j = 0; j < MapEditor.project.selectedMap.getGridView().cells[i].length; j++){
                        array.add(MapEditor.project.selectedMap.getGridView().cells[j][i].getWallID());
                        if(MapEditor.project.selectedMap.getGridView().cells[j][i].getWallID() > maxValue){
                            maxValue = MapEditor.project.selectedMap.getGridView().cells[j][i].getWallID();
                        }
                    }
                    cellsArray.add(array);
                }
                grid.put("data", cellsArray);

                for(int i = 1; i <= maxValue; i++){
                    JSONObject paletteObj = new JSONObject();

                    paletteObj.put("flag", Integer.toString(MapEditor.project.selectedMap.getWallMap().get(i).getFlag()));
                    paletteObj.put("id", Integer.toString(MapEditor.project.selectedMap.getWallMap().get(i).getID()));
                    paletteObj.put("texture", "images/textures/" + MapEditor.project.selectedMap.getWallMap().get(i).getImageName());
                    paletteObj.put("name", MapEditor.project.selectedMap.getWallMap().get(i).getImageName());

                    palette.add(paletteObj);

                }

                grid.put("palette", palette);

                savefileObj.put("grid", grid);
                FileWriter writer = new FileWriter("savefile.json");
                gson.toJson(savefileObj, writer);
                writer.close();
            }
            else{
                savefileObj = (JSONObject) parser.parse(reader);
                if(savefileObj.containsKey("grid")){
                    
                    grid = (JSONObject) savefileObj.get("grid");
                    grid.put("width", Integer.toString(MapEditor.project.selectedMap.getGridView().getxLength()));
                    grid.put("height", Integer.toString(MapEditor.project.selectedMap.getGridView().getyLength()));
                    JSONArray cellsArray = new JSONArray();
                    int maxValue = 0;

                    for(int i = 0; i< MapEditor.project.selectedMap.getGridView().cells.length; i++){
                        JSONArray array = new JSONArray();
                        for(int j = 0; j < MapEditor.project.selectedMap.getGridView().cells[i].length; j++){
                            array.add(MapEditor.project.selectedMap.getGridView().cells[j][i].getWallID());
                            if(MapEditor.project.selectedMap.getGridView().cells[j][i].getWallID() > maxValue){
                                maxValue = MapEditor.project.selectedMap.getGridView().cells[j][i].getWallID();
                            }
                        }
                        cellsArray.add(array);
                    }
                    grid.put("data", cellsArray);

                    for(int i = 1; i <= maxValue; i++){
                        JSONObject paletteObj = new JSONObject();

                        paletteObj.put("flag", Integer.toString(MapEditor.project.selectedMap.getWallMap().get(i).getFlag()));
                        paletteObj.put("id", Integer.toString(MapEditor.project.selectedMap.getWallMap().get(i).getID()));
                        paletteObj.put("texture", "images/textures/" + MapEditor.project.selectedMap.getWallMap().get(i).getImageName());
                        paletteObj.put("name", MapEditor.project.selectedMap.getWallMap().get(i).getImageName());

                        palette.add(paletteObj);

                    }

                    grid.put("palette", palette);
                    savefileObj.put("grid", grid);
                    FileWriter writer = new FileWriter("savefile.json");
                    gson.toJson(savefileObj, writer);
                    writer.close();
                }
                else{
                    grid.put("width", Integer.toString(MapEditor.project.selectedMap.getGridView().getxLength()));
                    grid.put("height", Integer.toString(MapEditor.project.selectedMap.getGridView().getyLength()));
                    JSONArray cellsArray = new JSONArray();
                    int maxValue = 0;

                    for(int i = 0; i< MapEditor.project.selectedMap.getGridView().cells.length; i++){
                        JSONArray array = new JSONArray();
                        for(int j = 0; j < MapEditor.project.selectedMap.getGridView().cells[i].length; j++){
                            array.add(MapEditor.project.selectedMap.getGridView().cells[j][i].getWallID());
                            if(MapEditor.project.selectedMap.getGridView().cells[j][i].getWallID() > maxValue){
                                maxValue = MapEditor.project.selectedMap.getGridView().cells[j][i].getWallID();
                            }
                        }
                        cellsArray.add(array);
                    }
                    grid.put("data", cellsArray);

                    for(int i = 1; i <= maxValue; i++){
                        JSONObject paletteObj = new JSONObject();

                        paletteObj.put("flag", Integer.toString(MapEditor.project.selectedMap.getWallMap().get(i).getFlag()));
                        paletteObj.put("id", Integer.toString(MapEditor.project.selectedMap.getWallMap().get(i).getID()));
                        paletteObj.put("texture", "images/textures/" + MapEditor.project.selectedMap.getWallMap().get(i).getImageName());
                        paletteObj.put("name", MapEditor.project.selectedMap.getWallMap().get(i).getImageName());

                        palette.add(paletteObj);

                    }

                    grid.put("palette", palette);

                    savefileObj.put("grid", grid);

                    FileWriter writer = new FileWriter("savefile.json");
                    gson.toJson(savefileObj, writer);
                    writer.close();
                }
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private void load(){
        FileReader reader = null;
        try {
            JSONParser parser = new JSONParser();
            reader = new FileReader("savefile.json");
            JSONObject savefile = (JSONObject) parser.parse(reader);
            JSONObject mapInfo = (JSONObject) savefile.get("grid");
            JSONArray entities = (JSONArray) savefile.get("entities");
            JSONArray gridData = new JSONArray();
            
            // getting width and height of the grid
            String gridWidthStr =  (String) mapInfo.get("width");
            String gridHeightStr = (String) mapInfo.get("height");
            int gridWidth = Integer.parseInt(gridWidthStr);
            int gridHeight = Integer.parseInt(gridHeightStr);
            
            
            // getting the grid array
            int[][] gridArray = new int[gridHeight][gridWidth];
            gridData = (JSONArray) mapInfo.get("data");
            Iterator<JSONArray> rowIterator = gridData.iterator();
            int rowNumber = 0;
            
            while(rowIterator.hasNext()){
                JSONArray columns = rowIterator.next();
                Iterator<Long> colIterator = columns.iterator();
                int colNumber = 0;
                while(colIterator.hasNext()){
                    gridArray[rowNumber][colNumber] = colIterator.next().intValue();
                    colNumber++;
                }
                rowNumber++;
            }
            
            
            //getting grid palette
            JSONArray paletteArray = (JSONArray) mapInfo.get("palette");
            Iterator<Object> paletteIterator = paletteArray.iterator();
            MapProfile mapToLoad = new MapProfile("savefile.json", gridWidth, gridHeight);
            
            while(paletteIterator.hasNext()){
                JSONObject palette = (JSONObject) paletteIterator.next();
                String idStr = (String) palette.get("id");
                int id = Integer.parseInt(idStr); 
                String imagePath = (String) palette.get("texture");
                String flagStr = (String) palette.get("flag");
                int flag = Integer.parseInt(flagStr); 
                String imageName = (String) palette.get("name");
                System.out.println(id + " , " + imagePath + " , " + flag + " , " + imageName);
                
                mapToLoad.loadWallProfile(imagePath, imageName, flag, id);
                mapToLoad.getGc().loadPalette(gridArray);
            }
            
            for(int i = 0; i < mapToLoad.getGridView().cells.length; i++){
                for(int j = 0; j < mapToLoad.getGridView().cells[i].length; j++){
                    System.out.print(mapToLoad.getGridView().cells[i][j].getWallID());
                }
                System.out.println();
            }
            
            //getting entities 
            Iterator<Object> entitiesIterator = entities.iterator();
            double [] position = new double[2];
            float [] color = new float[3];
            String entityName = "";
            while(entitiesIterator.hasNext()){
                JSONObject entity = (JSONObject) entitiesIterator.next();
                if(entity.containsKey("position")){
                    JSONArray positionArray = (JSONArray) entity.get("position");
                    Iterator<Double> positionIterator = positionArray.iterator();
                    int positionCounter = 0;
                    while(positionIterator.hasNext()){
                        position[positionCounter] = positionIterator.next().doubleValue();
                        positionCounter++;
                    }
                }
                if(entity.containsKey("color")){
                    JSONArray colorArray = (JSONArray) entity.get("color");
                    Iterator<Double> colorIterator = colorArray.iterator();
                    int colorCounter = 0;
                    while(colorIterator.hasNext()){
                        color[colorCounter] = (float) colorIterator.next().doubleValue();
                        colorCounter++;
                    }
                    
                }
                entityName = (String) entity.get("name");
                
                mapToLoad.loadEntityProfile(entityName, Color.color(color[0], color[1], color[2]) , position[0], position[1]);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    
}
