/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Metadata;

import Editor.Model.Profile.MapProfile;
import Editor.Model.Profile.Profile;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author A
 */
public class MapContent extends DataView{
    Label nameLabel = new Label("Name : ");
    Label mainMap = new Label("Start Map : ");
    
    TextField nameField;
    CheckBox mainBox;
    
    public MapContent(MapProfile profile){
        super(profile);
        
        mainBox = new CheckBox();
        mainBox.setIndeterminate(false);
        
        this.reset();
    }
    
    @Override
    protected VBox setupPane() {
        Insets padding = new Insets(10); // Padding
        Region space = new Region(); //GUI Gap
        
        //Initialize the Name Section
        HBox nameBox = new HBox(nameLabel, nameField);
        nameBox.setPadding(padding);
        nameBox.setSpacing(10);
        
        HBox startBox = new HBox(mainMap, mainBox);
        startBox.setPadding(padding);
        nameBox.setSpacing(10);
        
        VBox.setVgrow(space, Priority.ALWAYS);
        
        return new VBox(nameBox, startBox, super.getButtonBox(padding));
    }

    @Override
    public final void reset() {
        this.nameField = new TextField(profile.getName());
        this.mainBox.setSelected(getMapProfile().isMainMap());
    }
    
    public MapProfile getMapProfile(){
        return (MapProfile)profile;
    }
}
