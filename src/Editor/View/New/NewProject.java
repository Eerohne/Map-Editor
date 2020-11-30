/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.New;

import Editor.MapEditor;
import Editor.Model.Profile.MapProfile;
import Editor.Model.Profile.ProjectProfile;
import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class NewProject extends NewObject{
    private NewMap newMap = new NewMap();
    
    private TextField nameField;
    private TextField projLoc;
    
    public NewProject(Stage owner){
        super(owner, "New Project");
        
        frame.setCenter(init());
        
        new NewProjectController(this, newMap);
        
        newWindow.show();
    }
    
    public GridPane init(){
        GridPane pane = new GridPane();
        
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(25));
        
        
        Label nameLabel = new Label("Project Name : ");
        Label pathLabel = new Label("Project Location : ");
        
        nameField = new TextField("New Project");
        projLoc = new TextField("C:/");
        
        Button browser = new Button("Browse Folders");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Search Project Location");
        
        browser.setOnAction(e -> {
            File chosenDirectory = directoryChooser.showDialog(newWindow);
            String path = chosenDirectory.getAbsolutePath();
            
            if(path != null)
                projLoc.setText(path);
        });
        
        pane.add(nameLabel, 1, 1);
        pane.add(pathLabel, 1, 2);
        pane.add(nameField, 2, 1);
        pane.add(projLoc, 2, 2);
        pane.add(browser, 3, 2);
        
        return pane;
    }

    public NewMap getNewMap() {
        return newMap;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getProjLoc() {
        return projLoc;
    }
}

class NewProjectController{
//    String projectPath;
//    String projectName;
    
    public NewProjectController(NewProject np, NewMap nm) {
        np.getNext().setOnAction(e -> {
            np.getFrame().setCenter(nm.init());
            np.getBack().setDisable(false);
            np.getFinish().setDisable(false);
            np.getNext().setDisable(true);
        });
        
        np.getBack().setOnAction(e -> {
            np.getNext().setDisable(false);
            np.getFinish().setDisable(true);
            np.getBack().setDisable(false);
            np.getFrame().setCenter(np.init());
        });
        
        np.getFinish().setOnAction(e -> {
            MapEditor.setProject(new ProjectProfile(np.getNameField().getText(), np.getProjLoc().getText(), new MapProfile(nm.getMapNameField().getText(), Integer.parseInt(nm.getGridWidth().getText()), Integer.parseInt(nm.getGridLength().getText()))));
            np.getCancel().fire();
        });
    }
}
