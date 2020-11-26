/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

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
public class NewMap {

    public NewMap(Stage owner) {
        Stage newMap = new Stage();
        newMap.initOwner(owner);
        newMap.initModality(Modality.APPLICATION_MODAL);
        newMap.initStyle(StageStyle.UTILITY);
        newMap.setTitle("New Map");
        
        Label mapName = new Label("Map Name : ");
        Label gridResolution = new Label("Grid Resolution : ");
        Label mapSaveFolder = new Label("Map Location : ");
        Label resourceFolder = new Label("Resource Folder Location : ");
        Label x = new Label("x");
        
        TextField gridWidth = new TextField();
        TextField gridLength = new TextField();
        TextField mapNameField = new TextField();
        TextField mapLocation = new TextField("C:/");
        TextField resourcesLocation = new TextField("C:/");
        
        Button browseMapLocation = new Button("Browse");
        Button browseResourcesLocation = new Button("Browse");
        Button save = new Button("Create");
        Button cancel = new Button("Cancel");
        
        HBox gridResolutionKit = new HBox(gridWidth, x, gridLength);
        HBox controlButtons = new HBox(save, cancel);
        GridPane newMapPane = new GridPane();
        
        newMapPane.add(mapName, 0, 0);
        newMapPane.add(gridResolution, 0, 1);
        newMapPane.add(mapSaveFolder, 0, 2);
        newMapPane.add(resourceFolder, 0, 3);
        
        newMapPane.add(mapNameField, 1, 0);
        newMapPane.add(gridResolutionKit, 1, 1);
        newMapPane.add(mapLocation, 1, 2);
        newMapPane.add(resourcesLocation, 1, 3);
        
        newMapPane.add(browseMapLocation, 2, 2);
        newMapPane.add(browseResourcesLocation, 2, 3);
        
        newMapPane.add(controlButtons, 2, 5);
        
        Scene newMapScene = new Scene(newMapPane, 500, 500);
        newMap.setScene(newMapScene);
        newMap.show();
    }
    
}
