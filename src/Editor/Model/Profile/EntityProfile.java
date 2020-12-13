/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Model.Profile;

import Editor.Controller.MenuController;
import Editor.Main.MapEditor;
import Editor.View.Grid.EntityDot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author A
 */
public class EntityProfile extends Profile{
    private EntityDot dot;
    
    public EntityProfile(String name) {
        super(name);
        int r = (int)((0.5 + Math.random()*0.5)*255);
        int g = (int)((0.5 + Math.random()*0.5)*255);
        int b = (int)((0.5 - Math.random()*0.5)*255);
        this.saveColor(MapEditor.getProject().getSelectedMap().getName(), name, r, g, b);
        
        this.dot = new EntityDot(Color.rgb(r, g, b));
    }
    
    public EntityProfile(String mapName, String entityName, Color color) {
        super(entityName);
        this.saveColor(mapName, entityName, color.getRed(), color.getGreen(), color.getBlue());
        
        this.dot = new EntityDot(color);
    }

    public EntityDot getDot() {
        return dot;
    }

    public void setDot(EntityDot dot) {
        this.dot = dot;
    }
    
    private void saveColor(String mapName, String entityName, double x, double y, double z){
        
        FileReader reader = null;
        try {
            JSONParser parser = new JSONParser();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            reader = new FileReader("resources/levels/" + mapName + ".lvl");
            JSONObject savefile = (JSONObject) parser.parse(reader);
            JSONArray entities = (JSONArray) savefile.get("entities");
            JSONObject currentEntity = new JSONObject();
            JSONArray color = new JSONArray();
            color.add(x);
            color.add(y);
            color.add(z);
            
            for(int i = 0; i < entities.size(); i++){
                currentEntity = (JSONObject) entities.get(i);
                if(currentEntity.get("name").equals(entityName)){
                    currentEntity.put("color", color);
                    entities.set(i, currentEntity);
                }
            }
            
            savefile.put("entities", entities);
            
            FileWriter writer = new FileWriter("resources/levels/" + mapName + ".lvl");
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
    
    public void destroy(){
        dot.setRadius(0);
    }

    @Override
    public String toString() {
        return "EntityProfile{ name=" + name + ", " + "dot=" + dot + '}';
    }
}
