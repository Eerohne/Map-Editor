/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Main.MapEditor;
import Editor.Model.EntityModel;
import Editor.View.Menu.Entity.ExistingEntityModification;
import Editor.View.Menu.Entity.ExistingEntityStage;
import Editor.View.Menu.Entity.NewEntity;
import Editor.View.Menu.Entity.SignalStage;
import Editor.View.Menu.Entity.SignalViewerStage;
import Editor.View.New.NewEntityStage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



/**
 *
 * @author linuo
 */
public class NewEntityController{
    
    EntityModel model = new EntityModel();
    NewEntity view = new NewEntity();
    ExistingEntityModification eem = new ExistingEntityModification();
    JSONObject savefile = new JSONObject();
    
    JSONObject signalObj = new JSONObject();
    
    ObservableList<EntityModel> list = FXCollections.observableArrayList();
    ObservableList<String> nameList = FXCollections.observableArrayList();
    
    ExistingEntityModification existView = new ExistingEntityModification();
    ObservableList<EntityModel> existEntityList = FXCollections.observableArrayList();
    ObservableList<EntityModel> templist = FXCollections.observableArrayList();
    ObservableList selectedCells = FXCollections.observableArrayList();
    Gson gson = new GsonBuilder().setPrettyPrinting().create(); 

    public NewEntityController(EntityModel model, NewEntity view) {
        this.model = model;
        this.view = view;
        
        EventHandler handler = new EventHandler() {
            @Override
            public void handle(Event event) {
                add();
            }
        }; 
        
         EventHandler deleteHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                delete();
            }
        }; 
         
         EventHandler exportHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    export();
                } catch (IOException ex) {
                    Logger.getLogger(NewEntityController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }; 
         
         EventHandler newEntityHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                newEntity();
            }
        }; 
        viewSignal();
        signalWindow();
        switchWindow();
        view.addBtn.setOnAction(handler);
        view.deleteBtn.setOnAction(deleteHandler);
        view.exportBtn.setOnAction(exportHandler);
        closeWindow();
        disableButton();
        
        //view.newEntityBtn.setOnAction(newEntityHandler);
    }
    
    public NewEntityController(JSONObject signal){
        this.signalObj = signal;
    }
    
    public void delete(){
        int selectedIndex = view.table.getSelectionModel().getSelectedIndex();
        
        if(selectedIndex >= 0){
            view.table.getItems().remove(selectedIndex);
            list.remove(list.get(selectedIndex));
        }
    }
    
    private void disableButton(){
        view.nameTf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                view.exportBtn.setDisable(nameCheck(view.nameTf.getText()));
            }
        
        });
    }
    
    public void add(){
        String property = view.propertyText.getText();
        String value = view.valueText.getText();
        list.add(new EntityModel(property, value));
        view.table.getItems().setAll(list);
        view.propertyText.clear();
        view.valueText.clear();
    }
    
    public void export() throws IOException{
      
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
        JSONArray array = new JSONArray();
        JSONObject allEntities = new JSONObject();
        JSONParser parser = new JSONParser();
        File newFile = new File("savefile.json");
        JSONObject data = new JSONObject();
        FileWriter writer = new FileWriter("savefile.json", true);
        File signalFile = new File("signals.json");
        double[] initialPosition = {0.0 , 0.0};
        
        
        String entityName = view.nameTf.getText();
        
        // create or write to the entities.json if no entities exist
        if(newFile.length() == 0 || !newFile.exists()){
            data.put("classname", view.classNameTf.getText());
            data.put("name", view.nameTf.getText());
            data.put("position", initialPosition);

            
            for(int i = 0; i < list.size(); i++){
                if(isArray(list.get(i).getValue()) == true)
                    data.put(list.get(i).getProperty(), list.get(i).getValue().split(","));
                else
                    data.put(list.get(i).getProperty(), list.get(i).getValue());
            }
            
            //verify if a signal is created for the current entity 
            if(signalFile.length() != 0){
                try {
                    FileReader signalReader = new FileReader(signalFile);
                    JSONArray signalObj = (JSONArray) parser.parse(signalReader);
                    data.put("signal", signalObj);   
                    array.add(data);
                    allEntities.put("entities", array);
                    gson.toJson(allEntities, writer);
                    writer.close();
                    
                    FileWriter writer3 = new FileWriter("signals.json");
                    
                } catch (ParseException ex) {
                    Logger.getLogger(NewEntityController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                array.add(data);
                savefile.put("entities", array);
                gson.toJson(savefile, writer);
                writer.close();
            }
        }
        
        //write to entities.json file if file is not empty 
        else{
            try {
                
                FileReader reader = new FileReader(newFile);
                savefile = (JSONObject) parser.parse(reader);
                if(savefile.containsKey("entities")){
                     data.put("classname", view.classNameTf.getText());
                     data.put("name", view.nameTf.getText());
                     data.put("position", initialPosition);
                     for(int i = 0; i < list.size(); i++){
                         if(isArray(list.get(i).getValue()) == true){
                             data.put(list.get(i).getProperty(), list.get(i).getValue().split(","));
                         }else{
                             data.put(list.get(i).getProperty(), list.get(i).getValue());
                         }
                     }

                    JSONArray existingEntities = (JSONArray) savefile.get("entities");

                    if(signalFile.length() != 0){
                        FileReader signalReader = new FileReader(signalFile);
                        JSONArray signalObj = (JSONArray) parser.parse(signalReader);
                        data.put("signal", signalObj);
                        existingEntities.add(data);
                        savefile.put("entities", existingEntities);
                        newEntity();
                    }
                    else{
                        existingEntities.add(data);
                        savefile.put("entities", existingEntities);
                        newEntity();
                    }
                }
                else{
                     JSONArray existingEntities = new JSONArray();
                      if(signalFile.length() != 0){
                        FileReader signalReader = new FileReader(signalFile);
                        JSONArray signalObj = (JSONArray) parser.parse(signalReader);
                        data.put("signal", signalObj);
                        existingEntities.add(data);
                        savefile.put("entities", existingEntities);
                    }
                    else{
                        existingEntities.add(data);
                        savefile.put("entities", existingEntities);
                    }  
                }
                
                // new File writer initialized to clear all content of the associated file
                
                FileWriter writer3 = new FileWriter(signalFile);
                FileWriter writer2 = new FileWriter(newFile);
                gson.toJson(savefile, writer);
                writer.close();
            } catch (ParseException ex) {
                Logger.getLogger(NewEntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
        nameList.add(view.nameTf.getText());
        MapEditor.project.getSelectedMap().createEntityProfile(entityName);
        MapEditor.getEntityHierarchy().refresh();
    }
    
    public void newEntity(){
        ObservableList<EntityModel> tempList = FXCollections.observableArrayList();
        tempList = list;
        tempList.removeAll(list);
        view.table.getItems().setAll(tempList);
        view.nameTf.clear();
        view.classNameTf.clear();
    }
    
    private void switchWindow(){
        view.switchBtn.setOnAction((event) -> {
            Stage stage = new Stage();
            try {
                new ExistingEntityStage(stage);
            } catch (IOException ex) {
                Logger.getLogger(NewEntityController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(NewEntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void signalWindow(){
        view.signalBtn.setOnAction((event) -> {
            Stage stage = new Stage();
            new SignalStage(stage);
        });
    }
    
    private void viewSignal(){
        view.viewSignal.setOnAction((event) -> {
            Stage stage = new Stage();
            try {
                new SignalViewerStage(stage);
            } catch (ParseException ex) {
                Logger.getLogger(NewEntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    private boolean nameCheck(String name){
        
        boolean result = false;
        FileReader reader = null;
        try {
            JSONParser parser = new JSONParser();
            reader = new FileReader("savefile.json");
            JSONObject savefile = (JSONObject) parser.parse(reader);
            JSONArray entities = (JSONArray) savefile.get("entities");
            JSONObject whatever = new JSONObject();
            
            for(int i = 0; i < entities.size(); i++){
                JSONObject namecheckObj = (JSONObject) entities.get(i);
                System.out.println(namecheckObj);
                if(namecheckObj.values().contains(name)){
                    whatever = namecheckObj;
                }
            }
            
            if(whatever.values().contains(name)){
                result = true;
            }else{
                result = false;
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewEntityController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewEntityController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(NewEntityController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(NewEntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
    
    
    public void setData(){
        view.table.getItems().setAll(list);
    }
    
    private boolean isArray(String str){
        boolean isArray = false;
        if(str.contains(","))
            isArray = true;
        else
            isArray = false;
        return isArray;
    }
    
    private void closeWindow(){
        view.close.setOnAction((event) -> {
          
        });
    }
}
