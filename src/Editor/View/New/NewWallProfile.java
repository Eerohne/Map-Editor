/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.New;

import Editor.Main.MapEditor;
import Editor.Model.Profile.WallProfile;
import Editor.View.Hierarchy.WallHierarchy;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class NewWallProfile extends NewObject{
    private String filepath;
    private String selectedImage;
    
    WallHierarchy wallList;
    
    public NewWallProfile(Stage parent, String filepath, WallHierarchy wallList) {
        super(parent, "New Wall Profile");
        this.filepath = filepath;
        this.wallList = wallList;
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Textures", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Choose A Wall Texture");
        fileChooser.setInitialDirectory(new File(this.filepath));
        File textureFile = fileChooser.showOpenDialog(parent);
        
        this.selectedImage = textureFile.getName();
        
        frame.setCenter(optionView(newWindow));
        
        newWindow.show();
    }
    
    private VBox optionView(Stage stage){
        Label name = new Label("Name : ");
        Label flag = new Label("Flag : ");
        
        TextField nameField = new TextField();
        
        ObservableList<String> flagsList = FXCollections.observableArrayList();
        flagsList.addAll(Arrays.asList(WallProfile.flagArray));
        
        ComboBox<String> flagBox = new ComboBox<>(flagsList);
        flagBox.setValue(flagsList.get(1));
        
        VBox view = new VBox();
        
        HBox nameContainer = new HBox(name, nameField);
        HBox flagContainer = new HBox(flag, flagBox);
        
        view.getChildren().addAll(nameContainer, flagContainer);
        
        
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.finish.setDisable(nameCheck(newValue));
        });
        this.next.setDisable(true);
        this.finish.setDisable(false);
        this.finish.setOnAction(e -> {
            wallList.getMapProfile().createWallProfile(nameField.getText(), selectedImage, WallProfile.getWallFlag(flagBox.getValue()));
            stage.close();
            wallList.refresh();
        });
        
        nameField.setText(selectedImage);
        
        return view;
    }
    
    private boolean nameCheck(String name){
        for (Map.Entry<Integer, WallProfile> entry : MapEditor.getProject().getSelectedMap().getWallMap().entrySet()) {
            if (entry.getValue().getName().equals(name) || name.equals(""))
                return true;
        }
        
        return false;
    }
}
