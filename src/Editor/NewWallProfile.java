/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import Editor.Controller.GridController;
import Editor.Model.WallProfile;
import Editor.View.Properties.WallHierarchy;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author A
 */
public class NewWallProfile {
    private String filepath;
    private String selectedImage;
    private HBox selectedOption = new HBox();
    private HBox buttonBar;
    
    WallHierarchy wallList;
    
    Button next;
    Button cancel;
    
    public NewWallProfile(Stage parent, String filepath, WallHierarchy wallList) {
        this.filepath = filepath;
        this.wallList = wallList;
        
        Stage newWallWindow = new Stage();
        newWallWindow.initOwner(parent);
        newWallWindow.initModality(Modality.WINDOW_MODAL);
        
        buttonBar = buttonContainer(newWallWindow);
        
        Scene scene = new Scene(setupScene(textureView()), 600, 500);
        newWallWindow.setResizable(false);
        newWallWindow.initStyle(StageStyle.UTILITY);
        newWallWindow.setTitle("New Wall Profile");
        newWallWindow.setScene(scene);
        newWallWindow.show();
    }
    
    private BorderPane setupScene(Node center){
        BorderPane pane = new BorderPane();
        pane.setCenter(center);
        pane.setBottom(buttonBar);
        
        return pane;
    }
    
    private ScrollPane textureView(){
        VBox temp = new VBox();
        
        File resourceDir = new File(filepath);
        String textures[] = resourceDir.list();
        
        for (String texture : textures) {
            if(texture.endsWith(".png") || texture.endsWith(".jpg") || texture.endsWith(".jpeg")){
                HBox item = new HBox();
                try {
                    Image txr = new Image(new FileInputStream(filepath + texture), 100, 100, true, true);
                    ImageView preview = new ImageView(txr);
                    
                    item.getChildren().add(preview);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                
                Label txrName = new Label(texture);
                item.getChildren().add(txrName);
                item.setOnMouseClicked(e -> {
                    select(item);
                });
                item.setSpacing(10);
                item.setPadding(new Insets(25));
                item.setMinWidth(586);
                
                temp.getChildren().add(item);
            }
        }
        
        System.out.println((HBox) temp.getChildren().get(0));
        //select((HBox) temp.getChildren().get(0));
        return new ScrollPane(temp);
    }
    
    private HBox buttonContainer(Stage stage){
        HBox buttonBox = new HBox(5);
        buttonBox.setPadding(new Insets(5));
        
        this.next = new Button("Next >");
        this.next.setOnAction(e -> {
            optionView(stage);
        });
        this.cancel = new Button("Cancel");
        this.cancel.setOnAction(e -> {
            stage.close();
        });
        Region space = new Region();
        
        next.setDisable(true);
        
        HBox.setHgrow(space, Priority.ALWAYS);
        
        buttonBox.getChildren().addAll(space, next, cancel);
        
        return buttonBox;
    }
    
    private void select(HBox box){
        this.selectedOption.setBackground(Background.EMPTY);
        this.selectedOption = box;
        this.selectedOption.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        this.selectedImage = ((Label)box.getChildren().get(1)).getText();
        
        this.next.setDisable(false);
    }
    
    private void optionView(Stage stage){
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
        
        this.next.setText("Finish");
        this.next.setOnAction(e -> {
            wallList.getMapModel().getGc().setSelectedWallProfile(wallList.getMapModel().createWallProfile(nameField.getText(), selectedImage, WallProfile.getWallFlag(flagBox.getValue())));
            stage.close();
            wallList.refresh();
        });
        
        Scene finishScene = new Scene(setupScene(view), 600, 500);
        stage.setScene(finishScene);
    }
}
