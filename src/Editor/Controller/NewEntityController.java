/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Model.EntityModel;
import Editor.Model.SignalModel;
import Editor.View.Menu.Entity.ExistingEntityModification;
import Editor.View.Menu.Entity.ExistingEntityStage;
import Editor.View.Menu.Entity.NewEntity;
import Editor.View.Menu.Entity.SignalStage;
import Editor.View.Menu.Entity.SignalView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
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
    
    JSONObject signalObj = new JSONObject();
    SignalModel smodel = new SignalModel();
    SignalView sview = new SignalView();
    //SignalController scontroller = new SignalController(smodel, sview);
    
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
        test();
        signalWindow();
        switchWindow();
        view.addBtn.setOnAction(handler);
        view.deleteBtn.setOnAction(deleteHandler);
        view.exportBtn.setOnAction(exportHandler);
        view.newEntityBtn.setOnAction(newEntityHandler);
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

    public void setSignalObj(JSONObject obj) {
        this.signalObj = obj;
    }
    
    
    
    private void test(){
        view.test.setOnAction((event) -> {
            System.out.println(signalObj);
        });
        
    }
    
    
    public void setData(){
        view.table.getItems().setAll(list);
    }
}
