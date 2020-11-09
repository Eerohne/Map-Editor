/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitycreation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author linuo
 */
public class Controller {
    
    EntityModel model = new EntityModel();
    View view = new View();
    
    ObservableList<EntityModel> list = FXCollections.observableArrayList();

    public Controller(EntityModel model, View view) {
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
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
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
        JSONArray array = new JSONArray();
        Map<String, String> data = new HashMap<String, String>();
        for(int i = 0; i < list.size(); i++){
            data.put(list.get(i).getProperty(), list.get(i).getValue());
        }
        JSONObject o = new JSONObject();
        o.putAll(data);
        JSONObject object = new JSONObject();
        object.put(view.nameText.getText(), o);
        array.add(object);

        try (FileWriter file = new FileWriter("entities.json", true)) {
             
            file.write(array.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
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
