/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitycreation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

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
        view.btn.setOnAction(handler);
        view.deleteBtn.setOnAction(deleteHandler);
    }
    
    public void delete(){
        int selectedIndex = view.table.getSelectionModel().getSelectedIndex();
        
        if(selectedIndex >= 0){
            view.table.getItems().remove(selectedIndex);
        }
    }
    
    public void add(){
        String property = view.propertyText.getText();
        String value = view.valueText.getText();
        list.add(new EntityModel(property, value));
        view.table.getItems().setAll(list);
    }

    
    public void setData(){
        view.table.getItems().setAll(list);
    }
}
