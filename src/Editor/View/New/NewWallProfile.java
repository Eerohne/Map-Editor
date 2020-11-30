/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.New;

import Editor.Model.Profile.WallProfile;
import Editor.View.Hierarchy.WallHierarchy;
import java.io.File;
import java.util.Arrays;
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
        
        TextField nameField = new TextField(selectedImage);
        
        ObservableList<String> flagsList = FXCollections.observableArrayList();
        flagsList.addAll(Arrays.asList(WallProfile.flagArray));
        
        ComboBox<String> flagBox = new ComboBox<>(flagsList);
        flagBox.setValue(flagsList.get(0));
        
        VBox view = new VBox();
        
        HBox nameContainer = new HBox(name, nameField);
        HBox flagContainer = new HBox(flag, flagBox);
        
        view.getChildren().addAll(nameContainer, flagContainer);
        
        this.next.setDisable(true);
        this.finish.setDisable(false);
        this.finish.setOnAction(e -> {
            wallList.getMapModel().createWallProfile(nameField.getText(), selectedImage, WallProfile.getWallFlag(flagBox.getValue()));
            stage.close();
            wallList.refresh();
        });
        
        return view;
    }
}
