/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.New;

import Editor.Controller.GridController;
import Editor.Model.Profile.WallProfile;
import Editor.View.Hierarchy.WallHierarchy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author A
 */
public class NewWallProfile extends NewObject{
    private String filepath;
    private String selectedImage;
    private HBox selectedOption = new HBox();
    
    WallHierarchy wallList;
    
    public NewWallProfile(Stage parent, String filepath, WallHierarchy wallList) {
        super(parent, "New Wall Profile");
        this.filepath = filepath;
        this.wallList = wallList;
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Textures", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Choose A Wall Texture");
        fileChooser.setInitialDirectory(new File(filepath));
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
            wallList.getMapModel().getGc().setSelectedWallProfile(wallList.getMapModel().createWallProfile(nameField.getText(), selectedImage, WallProfile.getWallFlag(flagBox.getValue())));
            stage.close();
            wallList.refresh();
        });
        
        return view;
    }
}
