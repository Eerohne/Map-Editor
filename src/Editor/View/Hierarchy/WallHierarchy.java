/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Hierarchy;

import Editor.Controller.ProfileController.WallController;
import Editor.MapEditor;
import Editor.View.Metadata.WallContent;
import Editor.Model.Profile.MapProfile;
import Editor.Model.Profile.Profile;
import Editor.Model.Profile.WallProfile;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Pane that displays the whole list of created WallProfiles.
 * @author A
 */
public class WallHierarchy extends Hierarchy{
    private MapProfile map;
    private Stage stage;

    public WallHierarchy(Stage stage) {
        super(null);
        this.stage = stage;
    }
    
    public WallHierarchy(WallContent display, MapProfile map){
        super(display);
        this.map = map;
        
        this.refresh();
        selected = (HBox)list.getChildren().get(0);
    }
    
    @Override
    public void refresh(){
        list.getChildren().clear();
        Map<Integer, WallProfile> reversed = new TreeMap<>(Collections.reverseOrder());
        reversed.putAll(map.getWallMap());
        
        for (Map.Entry<Integer, WallProfile> entry : reversed.entrySet()) {
            HBox item = new HBox(10);
            item.setPadding(new Insets(25));
            Label name = new Label(entry.getValue().getName() + " : ");
            try {
                Image txr;
                if(entry.getValue().isDelete())
                    txr = entry.getValue().getDeleteImage();
                else
                    txr = new Image(new FileInputStream(MapProfile.getTxrURL(map, entry.getKey())), 100, 100, true, true);
                ImageView preview = new ImageView(txr);
                preview.setFitHeight(32);
                preview.setFitWidth(32);
                
                item.setOnMouseClicked(e -> {
                    select(item);
                    WallProfile wp = entry.getValue();
                    WallContent wallContent = new WallContent(wp);
                    WallController wc = new WallController(wallContent, map, stage);
                    MapEditor.setDataView(wallContent);
                    
                    if(e.getButton().equals(MouseButton.PRIMARY)){
                        map.getGc().setSelectedWallProfile(entry.getValue());
                    }
                });
                item.getChildren().addAll(name, preview);
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            }
      
            list.setFillWidth(true);
            list.getChildren().add(item);
        }
     }
    
    public MapProfile getMapProfile(){
        return map;
    }
    
    public void setMapProfile(MapProfile map){
        this.map = map;
        this.refresh();
    }
}
