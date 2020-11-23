/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Menu;

import Editor.Model.EntityModel;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author linuo
 */

public class ExistingEntityModification extends GridPane{
    
    public TableView table = new TableView();
    public TableColumn<?, ?> propertyCol = new TableColumn<>("property");
    public TableColumn<?, ?> valueCol = new TableColumn<>("value");
    public TextField nameText = new TextField();
    public TextField propertyText = new TextField();
    public TextField valueText = new TextField();
    //public Button nameEdit = new Button("edit the name");
    public Button switchBtn = new Button("open entity creation window");
    public Button addBtn = new Button("add row");
    public Button deleteBtn = new Button("delete selected row");
    public Button removeEntityBtn = new Button("remove entity");
    public ComboBox<String> cb = new ComboBox();
    public VBox vbox = new VBox();

    public Button saveEdit = new Button("save modification");

    public ExistingEntityModification() {
        
        table.setEditable(true);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10));
        this.add(table, 1, 0);
        
        vbox.setPadding(new Insets(10));
        vbox.getChildren().add(cb);
        vbox.getChildren().add(nameText);
        vbox.getChildren().add(propertyText);
        vbox.getChildren().add(valueText);
        vbox.getChildren().add(saveEdit);
        vbox.getChildren().add(addBtn);
        vbox.getChildren().add(deleteBtn);
        vbox.getChildren().add(removeEntityBtn);
        vbox.getChildren().add(switchBtn);
        
        this.add(vbox, 2, 0);
        propertyCol.setCellValueFactory(new PropertyValueFactory<>("property"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        table.getColumns().addAll(propertyCol, valueCol);
        cb.setPromptText("select an entity");
        nameText.setPromptText("change name of the entity");
        propertyText.setPromptText("add or change property here");
        valueText.setPromptText("add or change value here");
    }
    
    
}
