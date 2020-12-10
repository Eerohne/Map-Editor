/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Metadata;

import Editor.Model.Profile.Profile;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author A
 */
public class EntityContent extends DataView{
    private Button openEdit;
    
    public EntityContent(Profile profile) {
        super(profile);
        
        openEdit = new Button("Open Entity Editing Window");
        
        this.getChildren().add(setupPane());
    }

    @Override
    protected VBox setupPane() {
        Insets padding = new Insets(10); // Padding
        Region space = new Region(); //GUI Gap
        
        openEdit.setPadding(padding);
        
        return new VBox(openEdit, space, super.getButtonForEntities(padding));
    }

    @Override
    public void reset() {
        
    }
    
    public Button getOpenEditingButton(){
        return openEdit;
    }
}
