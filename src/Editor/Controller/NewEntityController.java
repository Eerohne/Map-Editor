/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Model.EntityModel;
import Editor.View.Menu.ExistingEntityModification;
import Editor.View.Menu.NewEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author linuo
 */
public class NewEntityController {
    
    EntityModel model = new EntityModel();
    NewEntity view = new NewEntity();
    ExistingEntityModification eem = new ExistingEntityModification();
    
    ObservableList<EntityModel> list = FXCollections.observableArrayList();
    ObservableList<String> nameList = FXCollections.observableArrayList();

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
                    EntityModel.entityList.add(list);
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
        view.addBtn.setOnAction(handler);
        view.deleteBtn.setOnAction(deleteHandler);
        view.exportBtn.setOnAction(exportHandler);
        view.newEntityBtn.setOnAction(newEntityHandler);
    }
    
    public void delete(){
        int selectedIndex = view.table.getSelectionModel().getSelectedIndex();
        
        if(selectedIndex >= 0){
            view.table.getItems().remove(selectedIndex);
            list.remove(list.get(selectedIndex));
        }
    }
    
    public void add(){
        String property = view.propertyText.getText();
        String value = view.valueText.getText();
        list.add(new EntityModel(property, value));
        view.table.getItems().setAll(list);
    }
    
    public void export() throws IOException{
      
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
        JSONArray array = new JSONArray();
        File newFile = new File("entities.json");
        Map<String, String> data = new HashMap<>();
        FileWriter writer = new FileWriter("entities.json", true);
        if(newFile.length() == 0 || !newFile.exists()){
            for(int i = 0; i < list.size(); i++){
                data.put(list.get(i).getProperty(), list.get(i).getValue());
            }
            JSONObject o = new JSONObject();
            o.putAll(data);
            JSONObject o1 = new JSONObject();
            o1.put(view.nameText.getText(), data);
            array.add(o1);
            gson.toJson(array, writer);
            writer.close();
        }
        else{
            try {
                
                FileReader reader = new FileReader(newFile);
                JSONArray existingArray = (JSONArray) new JSONParser().parse(reader);

                for(int i = 0; i < list.size(); i++){
                    data.put(list.get(i).getProperty(), list.get(i).getValue());
                }
                JSONObject o2 = new JSONObject();
                o2.putAll(data);
                JSONObject obj = new JSONObject();
                obj.put(view.nameText.getText(), o2);
                existingArray.add(obj);
                //file writer2 here used to truncate the json file, so that the entities do not repeat
                FileWriter writer2 = new FileWriter(newFile);
                gson.toJson(existingArray, writer);
                writer.close();
            } catch (ParseException ex) {
                Logger.getLogger(NewEntityController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
        nameList.add(view.nameText.getText());
        System.out.println(nameList.toString());
        
        
    }
    
    public void newEntity(){
        ObservableList<EntityModel> tempList = FXCollections.observableArrayList();
        tempList = list;
        tempList.removeAll(list);
        view.table.getItems().setAll(tempList);
    }
    
    public void setData(){
        view.table.getItems().setAll(list);
    }
}
