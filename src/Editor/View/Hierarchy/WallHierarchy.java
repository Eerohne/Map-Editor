/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Hierarchy;

import Editor.View.Metadata.WallContent;
import Editor.Model.Profile.MapProfile;
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

/**
 * Pane that displays the whole list of created WallProfiles.
 * @author A
 */
public class WallHierarchy extends Hierarchy{
    private VBox list;
    private HBox selected;
    private MapProfile map;
    private WallContent display;
    
    public WallHierarchy(WallContent display, MapProfile map){
        this.display = display;
        this.map = map;
        
        list = new VBox(10);
        list.setMinWidth(427);
        
        this.refresh();
        selected = (HBox)list.getChildren().get(0);

        this.setContent(list);
    }
    
    public void refresh(){
        list.getChildren().clear();
        Map<Integer, WallProfile> reversed = new TreeMap<>(Collections.reverseOrder());
        reversed.putAll(map.getWallMap());
        
        for (Map.Entry<Integer, WallProfile> entry : reversed.entrySet()) {
            HBox item = new HBox(10);
            item.setPadding(new Insets(25));
            Label name = new Label(entry.getValue().getName() + " : ");
            try {
                Image txr = new Image(new FileInputStream(MapProfile.getTxrURL(map, entry.getKey())), 100, 100, true, true);
                ImageView preview = new ImageView(txr);
                preview.setFitHeight(32);
                preview.setFitWidth(32);
                
                item.setOnMouseClicked(e -> {
                    select(item);
                    
                    display.changeContent(entry.getValue());
                    
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
    
    private void select(HBox box){
        this.selected.setBackground(Background.EMPTY);
        this.selected = box;
        this.selected.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    public MapProfile getMapModel(){
        return map;
    }
    
    public void setMapModel(MapProfile map){
        this.map = map;
    }
}
