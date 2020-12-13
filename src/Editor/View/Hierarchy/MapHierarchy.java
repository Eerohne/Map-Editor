/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Hierarchy;

import Editor.Main.MapEditor;
import Editor.Model.Profile.MapProfile;
import Editor.Model.Profile.ProjectProfile;
import Editor.View.Metadata.DataView;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author A
 */
public class MapHierarchy extends Hierarchy{
    ProjectProfile project;
    HBox selected;
    DataView display;

    public MapHierarchy() {
        super(null);
    }
    
    public MapHierarchy(ProjectProfile p, DataView display) {
        super(display);
        this.project = p;
        
        this.refresh();
    }
    
    @Override
    public void refresh(){
        list.getChildren().clear();
        
        for (MapProfile map : project.getMaps()) {
            HBox item = new HBox(10);
            item.setPadding(new Insets(25));
            Label mapName = new Label(map.getName());
            item.getChildren().add(mapName);
            item.setOnMouseClicked(e -> {
                super.select(item);
                project.setSelectedMap(map);
                map.getGc().setEditingMode(0);
                MapEditor.refreshEditor();
            });
            
            list.setFillWidth(true);
            list.getChildren().add(item);
        }
    }

    public void setProject(ProjectProfile project) {
        this.project = project;
        this.refresh();
    }
    
}
