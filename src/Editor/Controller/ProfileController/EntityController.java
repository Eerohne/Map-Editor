/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller.ProfileController;

import Editor.Controller.ExistingEntityController;
import Editor.Controller.MenuController;
import Editor.Main.MapEditor;
import Editor.Model.EntityModel;
import Editor.Model.Profile.MapProfile;
import Editor.View.Grid.Grid;
import Editor.View.Menu.Entity.ExistingEntityModification;
import Editor.View.Menu.Entity.ExistingEntityStage;
import Editor.View.Inspector.EntityContent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author A
 */
public class EntityController extends InspectorController{
    MapProfile map;
    Grid grid;
    Stage stage;
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    
    public EntityController(EntityContent content, MapProfile map, Stage stage) {
        super(content);
        this.map = map;
        this.grid = map.getGridView();
        this.stage = stage;
        
        ((EntityContent)content).getOpenEditingButton().setOnAction(e -> {
            try {
                ExistingEntityStage ees = new ExistingEntityStage(stage);
                ees.getView().getCb().setValue(MapEditor.project.getSelectedMap().getGc().getSelectedEntityProfile().getName());
            } catch (IOException ex) {
                Logger.getLogger(EntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(EntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        ((EntityContent)content).getDuplicateButton().setOnAction(e -> {
            duplicate(MapEditor.getProject().getSelectedMap().getGc().getSelectedEntityProfile().getName());
            MapEditor.getEntityHierarchy().refresh();
        });
    }

     
    //Actually this is the duplication event
    @Override
    protected void saveAction() {
        //Event left to ExistingEntityController
    }

    @Override
    protected void deleteAction() {
        try {
            ExistingEntityModification view = new ExistingEntityModification();
            ExistingEntityController ec = new ExistingEntityController(new EntityModel(), view);
            view.getCb().setValue(MapEditor.project.getSelectedMap().getGc().getSelectedEntityProfile().getName());
            view.getRemoveEntityBtn().fire();
        } catch (IOException ex) {
            Logger.getLogger(EntityController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(EntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void duplicate(String name){
        double r = 0.5 + Math.random()*0.5;
        double g = 0.5 + Math.random()*0.5;
        double b = 0.5 - Math.random()*0.5;
        String newName = name + "1";
        FileReader reader = null;
        try {
            JSONParser parser = new JSONParser();
            reader = new FileReader(MapEditor.getProject().getSelectedMapPath());
            JSONObject savefile = (JSONObject) parser.parse(reader);
            JSONArray entities = (JSONArray) savefile.get("entities");
            JSONObject entity = new JSONObject();
            JSONObject entityToDuplicate = new JSONObject();
            
            for(int i = 0; i < entities.size(); i++){
                entity = (JSONObject) entities.get(i);
                if(entity.get("name").equals(name)){
                    entityToDuplicate.putAll(entity);
                    entityToDuplicate.put("name", newName);
                    //entityToDuplicate.remove("color");
                    entityToDuplicate.remove("position");
                    MapEditor.getProject().getSelectedMap().createEntityProfile(newName, r, g, b);
                }
            }
            
            entities.add(entityToDuplicate);
            savefile.put("entities",entities);
            
            FileWriter writer = new FileWriter(MapEditor.getProject().getSelectedMapPath());
            gson.toJson(savefile, writer);
            writer.close();
            
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
