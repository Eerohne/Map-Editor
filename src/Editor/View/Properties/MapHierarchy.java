/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Properties;

import Editor.Model.Profile.MapProfile;
import Editor.Model.Profile.ProjectProfile;
import Editor.View.Metadata.DataView;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author A
 */
public class MapHierarchy extends ScrollPane{
    ProjectProfile p;
    VBox mapList;
    HBox selected;
    DataView display;

    public MapHierarchy(ProjectProfile p, DataView display) {
        this.p = p;
        mapList = new VBox(10);
        this.display = display;
    }
    
    public void refresh(){
        mapList.getChildren().clear();
        
        for (MapProfile map : p.getMaps()) {
            HBox item = new HBox(10);
            item.setPadding(new Insets(25));
            Label mapName = new Label(map.getName());
            
            item.setOnMouseClicked(e -> {
                select(item);
                p.setSelectedMap(map);
            });
        }
    }
    
    private void select(HBox box){
        this.selected.setBackground(Background.EMPTY);
        this.selected = box;
        this.selected.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
