/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;
import Editor.Model.EntityModel;
import Editor.Model.SignalModel;
import Editor.View.New.NewEntityStage;
import Editor.View.Menu.Entity.ExistingEntityModification;
import Editor.View.Menu.Entity.SignalEditorStage;
import Editor.View.Menu.Entity.SignalEditorView;
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
        
        launchSignalEditor();
        comboBoxHandler();
        editEntity();
        removeEntity();
        deleteRow();
        switchWindow();
        addRow();   
        editName();
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
    
    private void launchSignalEditor(){
        view.signalBtn.setOnAction((event) -> {
            try {
                Stage stage = new Stage();
                new SignalEditorStage(stage);
            } catch (IOException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    
    private void comboBoxHandler(){
        view.cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                JSONParser parser = new JSONParser();
                try(FileReader reader = new FileReader("entities.json")){
            
                    JSONArray entitiesArray = (JSONArray) parser.parse(reader);
                    list.clear();
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
                                    view.table.getItems().setAll(list);
                                }
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
    
    private void editName(){
        view.editName.setOnAction((event) -> {
            
            FileReader reader = null;
            try {
                reader = new FileReader("entities.json");
                JSONParser parser = new JSONParser();
                String name = view.cb.getValue();
                String key = name.substring(1, name.length()-1);
                int index = view.cb.getItems().indexOf(name);
                JSONArray entitiesArray = (JSONArray) parser.parse(reader);
                
                JSONObject entity = (JSONObject) entitiesArray.get(index);
                JSONObject currentEntity = (JSONObject) entity.get(key);
                JSONObject updatedEntity = new JSONObject();
                updatedEntity.put(view.nameText.getText(), currentEntity);
                entitiesArray.set(index, updatedEntity);
                
                FileWriter writer = new FileWriter("entities.json");
                gson.toJson(entitiesArray, writer);
                writer.close();
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
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
                
                if(selectedIndex >= 0){
                    view.table.getItems().set(selectedIndex, model);
                    
                    for(int i = 0; i < entitiesArray.size(); i++){
                        JSONObject entity = (JSONObject) entitiesArray.get(i);
                        if(entity.keySet().toString().equals(name)){
                            JSONObject currentEntity = (JSONObject) entity.get(key);
                            List currentEntityKeys = new ArrayList(currentEntity.keySet());
                            String currentKey = (String) currentEntityKeys.get(selectedIndex);
                            currentEntity.remove(currentKey);
                            currentEntity.put(view.propertyText.getText(), view.valueText.getText());
                            JSONObject updatedEntity = new JSONObject();
                            updatedEntity.put(key, currentEntity);
                            entitiesArray.set(index, updatedEntity);
                            
                            FileWriter writer = new FileWriter("entities.json");
                            gson.toJson(entitiesArray, writer);
                            writer.close();
                            
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
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
    
    private void addRow(){ 
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
                
                //getting the json object that will be added a property
                JSONObject entity = (JSONObject) entitiesArray.get(index);
                JSONObject currentEntity = (JSONObject) entity.get(key);
                JSONObject updatedEntity = new JSONObject();
                currentEntity.put(property, value);
                updatedEntity.put(key, currentEntity);
                entitiesArray.set(index, updatedEntity);
                
                FileWriter writer = new FileWriter("entities.json");
                gson.toJson(entitiesArray, writer);
                writer.close();
                
                //display the newly added property and its value to table
                List keyList = new ArrayList(currentEntity.keySet());
                List values = new ArrayList(currentEntity.values());
                list.clear();
                for(int i = 0; i < keyList.size(); i++){
                    if(!keyList.get(i).equals("signal")){
                        ObservableList<String> propertyList = FXCollections.observableArrayList();
                        propertyList.add(keyList.get(i).toString());
                        ObservableList<String> valueList = FXCollections.observableArrayList();
                        valueList.add(values.get(i).toString()); 
                        
                        for(int j = 0; j < propertyList.size(); j++){
                            list.add(new EntityModel(propertyList.get(j), valueList.get(j)));
                        }
                    }
                }
                view.table.getItems().setAll(list);
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
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
