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
                String property = view.propertyText.getText();
                String value = view.valueText.getText();
                list.add(new EntityModel(property, value));
                view.table.getItems().setAll(list);
            }
        }; 
        view.btn.setOnAction(handler);
    }
    
        public void addBtn(){
        Callback<TableColumn<EntityModel, Void>, TableCell<EntityModel, Void>> cellFactory = new Callback<TableColumn<EntityModel, Void>, TableCell<EntityModel, Void>>() {
            public TableCell<EntityModel, Void> call(TableColumn<EntityModel, Void> p) {
                TableCell<EntityModel, Void> cell = new TableCell<EntityModel, Void>(){
                    Button btn = new Button("delete");
                    {
                        btn.setOnAction((ActionEvent event) ->{
                            list.remove(view.table.getItems().get(getIndex()));
                            view.table.getItems().setAll(list);
                        });
                    }
                    public void updateItem(Void item, boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setGraphic(null);
                        }
                        else{
                            setGraphic(btn);
                        }
                    }
                };
               return cell; 
            }
        };
        
        view.deleteCol.setCellFactory(cellFactory);
        view.table.getColumns().add(view.deleteCol);   
    }
    
    public void setData(){
        view.table.getItems().setAll(list);
        addBtn();
    }
}
