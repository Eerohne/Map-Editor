/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Main.MapEditor;
import Editor.Model.Profile.MapProfile;
import Editor.Model.Profile.WallProfile;
import Editor.View.Help.Help;
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
import java.net.MalformedURLException;
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
    
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
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
            //MapEditor.load();
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
            new NewWallProfile(editorStage, MapEditor.getProject().getImageFolder(), MapEditor.getWallHierarchy());
        });
        
        //Edit -> New Entity : Open New Entity Window
        editItems.get(1).setOnAction(e -> {
            try {
                new NewEntityStage(editorStage);
            } catch (MalformedURLException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    
    public static void save() throws ParseException{
        JSONObject grid = new JSONObject();
        FileReader reader = null;
        try {
            File file = new File(MapEditor.getProject().getSelectedMapPath());
            
            //FileWriter writer2 = new FileWriter(MapEditor.getProject().getSelectedMapPath());
            JSONParser parser = new JSONParser();
            JSONObject savefileObj = new JSONObject(); 
            JSONArray palette = new JSONArray();
            
            
            if(file.length() == 0 || !file.exists()){
                
                file.createNewFile();
                //savefileObj = (JSONObject) parser.parse(reader);
                grid.put("width", Integer.toString(MapEditor.project.selectedMap.getGridView().getxLength()));
                grid.put("height", Integer.toString(MapEditor.project.selectedMap.getGridView().getyLength()));
                JSONArray cellsArray = new JSONArray();
                int maxValue = 0;

                for(int i = 0; i< MapEditor.project.selectedMap.getGridView().getxLength(); i++){
                    JSONArray array = new JSONArray();
                    for(int j = 0; j < MapEditor.project.selectedMap.getGridView().getyLength(); j++){
                        array.add(MapEditor.project.selectedMap.getGridView().cells[i][j].getWallID());
                        if(MapEditor.project.selectedMap.getGridView().cells[i][j].getWallID() > maxValue){
                            maxValue = MapEditor.project.selectedMap.getGridView().cells[i][j].getWallID();
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
                FileWriter writer = new FileWriter(file);
                gson.toJson(savefileObj, writer);
                writer.close();
            }
            else{
                reader = new FileReader(file); 
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
                            array.add(MapEditor.project.selectedMap.getGridView().cells[i][j].getWallID());
                            if(MapEditor.project.selectedMap.getGridView().cells[i][j].getWallID() > maxValue){
                                maxValue = MapEditor.project.selectedMap.getGridView().cells[i][j].getWallID();
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
                    FileWriter writer = new FileWriter(MapEditor.getProject().getSelectedMapPath());
                    gson.toJson(savefileObj, writer);
                    reader.close();
                    writer.close();
                }
                else{
                    reader = new FileReader(file); 
                    grid.put("width", Integer.toString(MapEditor.project.selectedMap.getGridView().getxLength()));
                    grid.put("height", Integer.toString(MapEditor.project.selectedMap.getGridView().getyLength()));
                    JSONArray cellsArray = new JSONArray();
                    int maxValue = 0;

                    for(int i = 0; i< MapEditor.project.selectedMap.getGridView().cells.length; i++){
                        JSONArray array = new JSONArray();
                        for(int j = 0; j < MapEditor.project.selectedMap.getGridView().cells[i].length; j++){
                            array.add(MapEditor.project.selectedMap.getGridView().cells[i][j].getWallID());
                            if(MapEditor.project.selectedMap.getGridView().cells[i][j].getWallID() > maxValue){
                                maxValue = MapEditor.project.selectedMap.getGridView().cells[i][j].getWallID();
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

                    FileWriter writer = new FileWriter(file);
                    gson.toJson(savefileObj, writer);
                    reader.close();
                    writer.close();
                }
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
        
    }
    
//    private void load(){
//        load(MapEditor.getProject().getSelectedMapPath());
//    } 
}
