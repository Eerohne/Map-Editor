/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Hierarchy;

import Editor.Model.EntityModel;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author A
 */
public class EntityHierarchy extends Accordion{
    private ArrayList<TitledPane> entities = new ArrayList<>();

    public EntityHierarchy() {
        ObservableList<EntityModel> testList = FXCollections.observableArrayList();
        testList.addAll(new EntityModel("Type", "Player"), new EntityModel("Name", "Player1"), new EntityModel("HP", "100"));
        EntityModel.entityList.add(testList);
        
        
        for (ObservableList<EntityModel> entity : EntityModel.entityList) {
            TableColumn<EntityModel, String> property = new TableColumn<>("Property");
            property.setCellValueFactory(new PropertyValueFactory<>("property"));
            
            TableColumn<EntityModel, String> value = new TableColumn<>("Value");
            value.setCellValueFactory(new PropertyValueFactory<>("value"));
            
            TableView entityTable = new TableView(entity);
            entityTable.getColumns().addAll(property, value);
            
            TitledPane entityPane = new TitledPane("Entity Test", entityTable);
            this.getPanes().add(entityPane);
        }
    }
}