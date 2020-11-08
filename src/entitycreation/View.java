/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitycreation;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;


/**
 *
 * @author linuo
 */
public class View extends GridPane{
    
    public TableView table = new TableView();
    public TableColumn<EntityModel, String> propertyCol = new TableColumn<>("property");
    public TableColumn<EntityModel, String> valueCol = new TableColumn<>("value");
    public TableColumn<EntityModel, Void> deleteCol = new TableColumn<>("delete");
    public TextField propertyText = new TextField("pro");
    public TextField valueText = new TextField("val");
    public Button btn = new Button("add");
    
    
    public View(){
        
        table.setEditable(true);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10));
        this.add(table, 1, 0);
        this.add(propertyText, 1, 1);
        this.add(valueText, 2, 1);
        this.add(btn, 3, 1);
        propertyCol.setCellValueFactory(new PropertyValueFactory<EntityModel, String>("property"));
        valueCol.setCellValueFactory(new PropertyValueFactory<EntityModel, String>("value"));
        table.getColumns().addAll(propertyCol, valueCol);
        
    }
    
    public void setButtonHandler(EventHandler handler){
        btn.setOnAction(handler);
    }
}
