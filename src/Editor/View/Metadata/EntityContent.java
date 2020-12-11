/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Metadata;

import Editor.Model.Profile.Profile;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author A
 */
public class EntityContent extends DataView{
    private Button openEdit;
    private Button duplicate;
    
    public EntityContent(Profile profile) {
        super(profile);
        
        openEdit = new Button("Open Entity Editing Window");
        duplicate = new Button("Duplicate Entity");
        
        this.getChildren().add(setupPane());
    }

    @Override
    protected VBox setupPane() {
        Insets padding = new Insets(10); // Padding
        Region space = new Region(); //GUI Gap
        
        //HBox.setHgrow(openEdit, Priority.ALWAYS);
        HBox.setHgrow(duplicate, Priority.ALWAYS);
        HBox.setHgrow(delete, Priority.ALWAYS);
        VBox.setVgrow(space, Priority.ALWAYS);
        
        openEdit.setMinWidth(Double.MAX_VALUE);
        
        HBox editBox = new HBox(openEdit);
        editBox.setPadding(padding);
        editBox.setSpacing(10);
        
        HBox dupliBox = new HBox(duplicate);
        dupliBox.setPadding(padding);
        dupliBox.setSpacing(10);
        
        HBox deleteBox = new HBox(delete);
        deleteBox.setPadding(padding);
        deleteBox.setSpacing(10);
        
        VBox view = new VBox(openEdit, duplicate, space, delete);
        //view.widthProperty().
        return view;
    }

    @Override
    public void reset() {
        
    }
    
    public Button getOpenEditingButton(){
        return openEdit;
    }
    
    public Button getDuplicateButton(){
        return duplicate;
    }
}
