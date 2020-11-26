/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class NewWallProfile {
    private String filepath;
    
    public NewWallProfile(Stage parent, String filepath) {
        this.filepath = filepath;
        
        Stage newWallWindow = new Stage();
        newWallWindow.initOwner(parent);
        newWallWindow.initModality(Modality.WINDOW_MODAL);
        
        Scene scene = new Scene(setupBrowser(), 300, 500);
        newWallWindow.setTitle("New Wall Profile");
        newWallWindow.setScene(scene);
        newWallWindow.show();
    }
    
    private BorderPane setupBrowser(){
        BorderPane pane = new BorderPane();
        pane.setCenter(textureView());
        pane.setBottom(buttonContainer());
        
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
                item.setSpacing(10);
                item.setPadding(new Insets(25));
                
                temp.getChildren().add(item);
            }
        }
        
        return new ScrollPane(temp);
    }
    
    private HBox buttonContainer(){
        HBox buttonBox = new HBox(5);
        buttonBox.setPadding(new Insets(5));
        
        Button next = new Button("Next >");
        Button cancel = new Button("Cancel");
        Region space = new Region();
        
        HBox.setHgrow(space, Priority.ALWAYS);
        
        buttonBox.getChildren().addAll(space, next, cancel);
        
        return buttonBox;
    }
}
