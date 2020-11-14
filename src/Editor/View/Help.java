/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class Help{

    public Help(Stage owner) {
        VBox helpBox = new VBox();
        
        helpBox.setPadding(new Insets(10));
        helpBox.setSpacing(10);
        
        Label panning = new Label("To move around the grid, hold the middle mouse button and move the cursor.");
        Label zooming = new Label("To zoom on the grid, roll the mouse wheel.");
        Label place = new Label("To place a wall, click on the cell of your choice. (FEATURE CURRENTLY UNDER CONSTRUCTION)");
        Label rest = new Label("More Feautures To Come Soon!");
        
        helpBox.getChildren().addAll(panning, zooming, place, rest);
        
        
        Scene helpScene = new Scene(helpBox);
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.initOwner(owner);
        helpStage.show();
    }
    
}
