/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Properties;

import Editor.Controller.GridController;
import Editor.Model.WallProfile;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author A
 */
public class WallTab extends ScrollPane{
    private ArrayList<WallPane> walls = new ArrayList<>();
    
    private VBox list;
    
    public WallTab(){
        list = new VBox(10);
        
        for (Map.Entry<Integer, WallProfile> entry : WallProfile.wallMap.entrySet()) {
            HBox item = new HBox(10);
            item.setPadding(new Insets(25));
            Label name = new Label(entry.getValue().getName() + " : ");
            try {
                Image txr = new Image(new FileInputStream(WallProfile.getTxrURL(entry.getKey())), 100, 100, true, true);
                ImageView preview = new ImageView(txr);
                preview.setFitHeight(32);
                preview.setFitWidth(32);
                preview.setOnMouseClicked(e -> {
                    if(e.getButton().equals(MouseButton.PRIMARY))
                        GridController.selectedWallProfile = entry.getValue();
                });
                
                item.getChildren().addAll(name, preview);
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            }
            
            
            //Add controllers
            list.getChildren().add(item);
        }
        
        this.setContent(list);
    }
    
//    public WallTab(WallProfile... profiles) {
//        for (WallProfile profile : profiles) {
//            WallPane pane = new WallPane(profile);
//            walls.add(pane);
//            this.getPanes().add(pane);
//        }
//    }

    public ArrayList<WallPane> getWalls() {
        return walls;
    }
}
