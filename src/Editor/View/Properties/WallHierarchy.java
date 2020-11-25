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
import java.util.Map;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Pane that displays the whole list of created WallProfiles.
 * @author A
 */
public class WallHierarchy extends ScrollPane{
    private VBox list;
    private HBox selected;
    
    public WallHierarchy(){
        list = new VBox(10);
        list.setMinWidth(500);
        String cssLayout = "-fx-border-color: red;\n" +
                   "-fx-border-insets: 5;\n" +
                   "-fx-border-width: 3;\n" +
                   "-fx-border-style: dashed;\n";
        list.setStyle(cssLayout);
        
        this.refresh();
        selected = (HBox)list.getChildren().get(0);

        this.setContent(list);
    }
    
    public void refresh(){
        list.getChildren().clear();
        
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
                    if(e.getButton().equals(MouseButton.PRIMARY)){
                        GridController.selectedWallProfile = entry.getValue();
                    }
                });
                
                item.setFillHeight(true);
                item.setOnMouseClicked(e -> select(item));
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
}
