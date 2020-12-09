/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Main.MapEditor;
import Editor.View.New.NewEntityStage;
import Editor.View.Menu.Entity.ExistingEntityStage;
import Editor.View.Menu.TopMenu;
import Engine.Core.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
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
        fileItems.get(fileItems.size()-1).setOnAction(e -> {
            editorStage.close();
        });
        fileItems.get(2).setOnAction((event) -> {
            try {
                save();
            } catch (ParseException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //Run Engine Event
        List<MenuItem> runItems = run.getItems();
        runItems.get(0).setOnAction(e ->{
            try {
                Stage engine = new Stage();
                Game game = new Game();
                engine.initOwner(editorStage);
                game.start(engine);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Engine Error");
                alert.setContentText("Engine Not Executed");
                alert.showAndWait();
            }
        });
        
        List<MenuItem> editItems = edit.getItems();
        editItems.get(1).setOnAction(e -> {
            new NewEntityStage(editorStage);
        });
        editItems.get(2).setOnAction((event) -> {
            try {
                new ExistingEntityStage(editorStage);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    
    
}
