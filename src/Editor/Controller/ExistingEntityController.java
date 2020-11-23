/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;
import Editor.Model.EntityModel;
import Editor.View.Menu.ExistingEntityModification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javax.swing.SwingUtilities;
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
    ObservableList<EntityModel> templist = FXCollections.observableArrayList();
    ObservableList selectedCells = FXCollections.observableArrayList();
    Gson gson = new GsonBuilder().setPrettyPrinting().create(); 

    public ExistingEntityController(EntityModel model, ExistingEntityModification view) {
        this.model = model;
        this.view = view;
        
        try {
            allEntities();
        } catch (ParseException ex) {
            Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        comboBoxHandler();
        
        view.saveEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    editEntity();
                } catch (IOException ex) {
                    Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
        
        view.removeEntityBtn.setOnAction((event) -> {
            try {
                removeEntity();
            } catch (IOException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        view.deleteBtn.setOnAction((event) -> {
            deleteRow();
        });
        
        addButtonHandler();
    }
    
    private void allEntities() throws ParseException{
        JSONParser parser = new JSONParser();
        
        try(FileReader reader = new FileReader("entities.json")){
            
            JSONArray entitiesArray = (JSONArray) parser.parse(reader);
            for(int i = 0; i < entitiesArray.size(); i++){
                JSONObject entity = new JSONObject();
                entity = (JSONObject) entitiesArray.get(i);
                String str = entity.keySet().toString();
                
                view.cb.getItems().add(str);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void comboBoxHandler(){
        view.cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                JSONParser parser = new JSONParser();
                try(FileReader reader = new FileReader("entities.json")){
            
                    JSONArray entitiesArray = (JSONArray) parser.parse(reader);
                    
                    for(int i = 0; i < entitiesArray.size(); i++){
                        JSONObject entity = (JSONObject) entitiesArray.get(i);
                        String str = entity.keySet().toString();
                        if(newValue.equals(str)){
                            
                            for(Object keyStr : entity.keySet()){
                                JSONObject obj1 = (JSONObject) entity.get(keyStr);
                                ObservableList<String> keylist = FXCollections.observableArrayList(obj1.keySet());
                                ObservableList<String> valuelist = FXCollections.observableArrayList(obj1.values());
                                for(int j = 0; j < keylist.size(); j++){
                                    list.add(new EntityModel(keylist.get(j), valuelist.get(j)));
                                    templist = list;
                                    view.table.getItems().setAll(templist);
                                }
                                templist.clear();
                            }
                        }
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void editEntity() throws FileNotFoundException, IOException, ParseException{  // name editing to be completed
        EntityModel model = new EntityModel(view.propertyText.getText(), view.valueText.getText());
        int selectedIndex = view.table.getSelectionModel().getSelectedIndex();
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("entities.json");
                
        if(selectedIndex >= 0){
            view.table.getItems().set(selectedIndex, model);
            JSONArray entitiesArray = (JSONArray) parser.parse(reader);
            String name = view.cb.getValue();
            String key = name.substring(1, name.length()-1);
            
            for(int i = 0; i < entitiesArray.size(); i++){
                JSONObject entity = new JSONObject();
                entity = (JSONObject) entitiesArray.get(i);
                
                if(entity.keySet().toString().equals(name)){
                    JSONObject obj = (JSONObject) entity.get(key);
                    
                    ArrayList objKeys = new ArrayList(obj.keySet());
                    String objKey = (String) objKeys.get(selectedIndex);
                    obj.remove(objKey);
                    obj.put(view.propertyText.getText(),view.valueText.getText());
                    JSONObject obj2 = new JSONObject();
                    obj2.put(key, obj);
                    int index = view.cb.getItems().indexOf(name);
                    entitiesArray.remove(index);
                    entitiesArray.add(obj2);
                    FileWriter writer = new FileWriter("entities.json");
                    gson.toJson(entitiesArray, writer);
                    writer.close();
                }
            }
        }
    }
    
    private void removeEntity() throws FileNotFoundException, IOException, ParseException{
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("entities.json");
        JSONArray entitiesArray = (JSONArray) parser.parse(reader);
        String name = view.cb.getValue();
        int index = view.cb.getItems().indexOf(name);
        
        entitiesArray.remove(index);
        FileWriter writer = new FileWriter("entities.json");
        gson.toJson(entitiesArray, writer);
        writer.close();
        
        view.table.getItems().clear();
        view.cb.getItems().remove(index);
    }

    private void addButtonHandler(){
        view.addBtn.setOnAction((event) -> {
            String property = view.propertyText.getText();
            String value = view.valueText.getText();
            ObservableList<EntityModel> addlist = FXCollections.observableArrayList();
            addlist.add(new EntityModel(property, value));
            view.table.getItems().add(addlist.get(0));
        });
    }
    
    public void deleteRow(){ // need to add everything to JSONArray then export it again 
        int selectedIndex = view.table.getSelectionModel().getSelectedIndex();
        ObservableList deletelist = view.table.getSelectionModel().getSelectedItems();
        if(selectedIndex >= 0){
            view.table.getItems().remove(selectedIndex);
            list.remove(deletelist.get(selectedIndex));
        }
    }
    
    public void setData(){
        view.table.getItems().setAll(list);
    }
    
    
}
