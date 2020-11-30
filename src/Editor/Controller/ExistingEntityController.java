/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;
import Editor.Model.EntityModel;
import Editor.View.New.NewEntityStage;
import Editor.View.Menu.Entity.ExistingEntityModification;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author linuo
 */
public class ExistingEntityController{
    EntityModel model = new EntityModel();
    ExistingEntityModification view = new ExistingEntityModification();
    ObservableList<EntityModel> list = FXCollections.observableArrayList();
    ObservableList<EntityModel> templist = FXCollections.observableArrayList();
    ObservableList selectedCells = FXCollections.observableArrayList();
    Gson gson = new GsonBuilder().setPrettyPrinting().create(); 

    public ExistingEntityController(EntityModel model, ExistingEntityModification view) throws IOException, FileNotFoundException, ParseException {
        this.model = model;
        this.view = view;
        
        try {
            allEntities();
        } catch (ParseException ex) {
            Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
        }
        comboBoxHandler();
        editEntity();
        removeEntity();
        deleteRow();
        switchWindow();
        addRow();        
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
        view.saveEdit.setOnAction((event) -> {
            FileReader reader = null;
            try {
                EntityModel model = new EntityModel(view.propertyText.getText(), view.valueText.getText());
                int selectedIndex = view.table.getSelectionModel().getSelectedIndex();
                JSONParser parser = new JSONParser();
                reader = new FileReader("entities.json");
                JSONArray entitiesArray = (JSONArray) parser.parse(reader);
                String name = view.cb.getValue();
                String key = name.substring(1, name.length()-1);
                int index = view.cb.getItems().indexOf(name);
                
                if(!view.nameText.equals("")){
                    String newName = view.nameText.getText();
                    JSONObject CurrentObj = new JSONObject();
                    CurrentObj = (JSONObject)entitiesArray.get(index);
                    JSONObject childObj = new JSONObject();
                    childObj = (JSONObject) CurrentObj.get(key);
                    //System.out.println(childObj);
                    JSONObject editedObj = new JSONObject();
                    editedObj.put(newName, childObj);
                    entitiesArray.set(index, editedObj);
                    FileWriter writer = new FileWriter("entities.json");
                    gson.toJson(entitiesArray, writer);
                    writer.close();
                }
                
                if(selectedIndex >= 0){
                    view.table.getItems().set(selectedIndex, model);
                    
                    
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
                            entitiesArray.remove(index);
                            entitiesArray.add(obj2);
                            
                            FileWriter writer = new FileWriter("entities.json");
                            gson.toJson(entitiesArray, writer);
                            writer.close();
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ParseException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            });
    }
    
    private void removeEntity() throws FileNotFoundException, IOException, ParseException{
        view.removeEntityBtn.setOnAction((event) -> {
            FileReader reader = null;
            try {
                JSONParser parser = new JSONParser();
                reader = new FileReader("entities.json");
                JSONArray entitiesArray = (JSONArray) parser.parse(reader);
                String name = view.cb.getValue();
                int index = view.cb.getItems().indexOf(name);
                entitiesArray.remove(index);
                FileWriter writer = new FileWriter("entities.json");
                gson.toJson(entitiesArray, writer);
                writer.close();
                view.table.getItems().clear();
                view.cb.getItems().remove(index);
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ParseException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            });
    }
    
    private void deleteRow() throws FileNotFoundException, IOException, ParseException{ 
       view.deleteBtn.setOnAction((event) -> {
           FileReader reader = null;
           try {
               JSONParser parser = new JSONParser();
               reader = new FileReader("entities.json");
               JSONArray entitiesArray = (JSONArray) parser.parse(reader);
               String name = view.cb.getValue();
               String key = name.substring(1, name.length()-1);
               int selectedIndex = view.table.getSelectionModel().getSelectedIndex();
               int index = view.cb.getItems().indexOf(name);
               
               if(selectedIndex >= 0){
                    view.table.getItems().remove(selectedIndex);
                    
                    // getting the entity we will be working on from the array 
                    JSONObject CurrentObj = new JSONObject();
                    CurrentObj = (JSONObject)entitiesArray.get(index);
                    // access property and value inside the selected entity and work with them   
                    JSONObject childObj = new JSONObject();
                    childObj = (JSONObject) CurrentObj.get(key);
                    
                    List keyList = new ArrayList(childObj.keySet());
                    String keyToRemove = (String) keyList.get(selectedIndex);
                    childObj.remove(keyToRemove);
                    
                    FileWriter writer = new FileWriter("entities.json");
                    gson.toJson(entitiesArray, writer);
                    writer.close();  
               }
           } catch (FileNotFoundException ex) {
               Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
           } catch (IOException | ParseException ex) {
               Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
           } finally {
               try {
                   reader.close();
               } catch (IOException ex) {
                   Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           });
    }
    
    private void addRow(){ // seems to be adding property and value in a random order
        view.addBtn.setOnAction((event) -> {
            FileReader reader = null;
            try {
                String property = view.propertyText.getText();
                String value = view.valueText.getText();
                JSONParser parser = new JSONParser();
                reader = new FileReader("entities.json");
                JSONArray entitiesArray = (JSONArray) parser.parse(reader);
                String name = view.cb.getValue();
                String key = name.substring(1, name.length()-1);
                int selectedIndex = view.table.getSelectionModel().getSelectedIndex();
                int index = view.cb.getItems().indexOf(name);
                
                // getting the entity we will be working on from the array 
                JSONObject CurrentObj = new JSONObject();
                CurrentObj = (JSONObject)entitiesArray.get(index);
                // access property and value inside the selected entity and work with them
                JSONObject childObj = new JSONObject();
                childObj = (JSONObject) CurrentObj.get(key);
                    
                List keyList = new ArrayList(childObj.keySet());
                String keyToAdd = property;
                String valueToAdd = value;
                childObj.put(keyToAdd, valueToAdd);
                
                ObservableList<String> keylist = FXCollections.observableArrayList(childObj.keySet());
                ObservableList<String> valuelist = FXCollections.observableArrayList(childObj.values());
                
                    
                for(int j = 0; j < keylist.size(); j++){
                    list.add(new EntityModel(keylist.get(j), valuelist.get(j)));
                    view.table.getItems().setAll(list);
                }
                list.clear();
                    
                FileWriter writer = new FileWriter("entities.json");
                gson.toJson(entitiesArray, writer);
                writer.close();
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | ParseException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
    }
    
    private void switchWindow(){
        view.switchBtn.setOnAction((event) -> {
           Stage stage = new Stage();
           new NewEntityStage(stage);
        });
    }
}
