/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;
import Editor.Model.EntityModel;
import Editor.View.Menu.ExistingEntityModification;
import Editor.View.Menu.NewEntity;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 *
 * @author linuo
 */
public class ExistingEntityController {
    EntityModel model = new EntityModel();
    ExistingEntityModification view = new ExistingEntityModification();
    ObservableList<EntityModel> list = FXCollections.observableArrayList();

    public ExistingEntityController(EntityModel model, ExistingEntityModification view) {
        try {
            this.model = model;
            this.view = view;
            
            allEntities();
        } catch (ParseException ex) {
            Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void allEntities() throws ParseException{
        JSONParser parser = new JSONParser();
        
        try(FileReader reader = new FileReader("entities.json")){
            
            JSONArray obj = (JSONArray) parser.parse(reader);
            System.out.println(obj);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
