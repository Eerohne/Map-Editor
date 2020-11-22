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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
                editEntity();
            }
        }
        );
    }
    
    private void allEntities() throws ParseException{
        JSONParser parser = new JSONParser();
        List entityList = new ArrayList();
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
                //System.out.println(newValue);
                JSONParser parser = new JSONParser();
                List entityList = new ArrayList();
                Gson gson = new Gson();
                String objName = newValue;
                
                try(FileReader reader = new FileReader("entities.json")){
            
                    JSONArray entitiesArray = (JSONArray) parser.parse(reader);
                    
                    for(int i = 0; i < entitiesArray.size(); i++){
                        JSONObject entity = new JSONObject();
                        entity = (JSONObject) entitiesArray.get(i);
                        String str = entity.keySet().toString();
                        if(newValue.equals(str)){
                            //System.out.println(entity);
                            for(Object keyStr : entity.keySet()){
                                JSONObject obj1 = new JSONObject();
                                obj1 = (JSONObject) entity.get(keyStr);
                                //System.out.println(obj1);
                                ObservableList<String> keylist = FXCollections.observableArrayList(obj1.keySet());
                                //System.out.println(keylist);
                                ObservableList<String> valuelist = FXCollections.observableArrayList(obj1.values());
                                //System.out.println(valuelist);
                                for(int j = 0; j < keylist.size(); j++){
                                    list.add(new EntityModel(keylist.get(j), valuelist.get(j)));
                                    templist = list;
                                    //System.out.println(list);
                                    view.table.getItems().setAll(templist);
                                }
                                templist.clear();
                            }
                            
                            //System.out.println(entity.keySet());

                        }

                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(ExistingEntityController.class.getName()).log(Level.SEVERE, null, ex);
                }

                }
        });
    }
    public void editEntity(){
        EntityModel model = new EntityModel(view.propertyText.getText(), view.valueText.getText());
        int selectedIndex = view.table.getSelectionModel().getSelectedIndex();
        
        if(selectedIndex >= 0){
            view.table.getItems().set(selectedIndex, model);    
            list.add(model);
        }
    }

    public void add(){
        String property = view.propertyText.getText();
        String value = view.valueText.getText();
        list.add(new EntityModel(property, value));
        view.table.getItems().setAll(list);
    }
    
    public void delete(){
        int selectedIndex = view.table.getSelectionModel().getSelectedIndex();
        
        if(selectedIndex >= 0){
            view.table.getItems().remove(selectedIndex);
            list.remove(list.get(selectedIndex));
        }
    }
    
    public void setData(){
        view.table.getItems().setAll(list);
    }
    
    
}
