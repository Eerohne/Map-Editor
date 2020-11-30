/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.New;

import Editor.MapEditor;
import Editor.Model.Profile.MapProfile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author A
 */
public class NewMap extends NewObject{

    TextField gridWidth;
    TextField gridLength;
    TextField mapNameField;
    
    
    public NewMap(){
        gridWidth = new TextField();
        gridLength = new TextField();
        mapNameField = new TextField();
    }
    
    public NewMap(Stage owner) {
        super(owner, "New Map");
        
        gridWidth = new TextField();
        gridLength = new TextField();
        mapNameField = new TextField();
        
        next.setDisable(true);
        finish.setDisable(false);
        
        frame.setCenter(init());
        
        newWindow.show();
    }
    
    public GridPane init(){
        Label mapName = new Label("Map Name : ");
        Label gridResolution = new Label("Grid Resolution : ");
        Label x = new Label("x");
        
        HBox gridResolutionKit = new HBox(gridWidth, x, gridLength);
        GridPane newMapPane = new GridPane();
        newMapPane.setAlignment(Pos.CENTER);
        newMapPane.setPadding(new Insets(10));
        
        newMapPane.add(mapName, 0, 0);
        newMapPane.add(gridResolution, 0, 1);
        
        newMapPane.add(mapNameField, 1, 0);
        newMapPane.add(gridResolutionKit, 1, 1);
        
        return newMapPane;
    }

    public TextField getGridWidth() {
        return gridWidth;
    }

    public TextField getGridLength() {
        return gridLength;
    }

    public TextField getMapNameField() {
        return mapNameField;
    }
}

class NewMapController{

    public NewMapController(NewMap nm) {
        nm.getFinish().setOnAction(e -> {
            MapProfile map = new MapProfile(nm.getMapNameField().getText(), Integer.parseInt(nm.getGridWidth().getText()), Integer.parseInt(nm.getGridLength().getText()));
            MapEditor.getProject().addMap(map);
        });
    }
}
