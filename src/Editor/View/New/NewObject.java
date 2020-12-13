
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.New;

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author A
 */
public class NewObject {
    Stage newWindow;
    BorderPane frame;
    Button next = new Button("Next >");
    Button back = new Button("< Back");
    Button cancel = new Button("Cancel");
    Button finish = new Button("Finish");
    
    public NewObject(Stage owner, String name){
        this.newWindow = new Stage();
        newWindow.initOwner(owner);
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.initStyle(StageStyle.UTILITY);
        newWindow.setTitle(name);
        newWindow.setResizable(false);
        
        frame = new BorderPane();
        frame.setPadding(new Insets(10));
        
        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);
        HBox bottom = new HBox(space, back, next, finish, cancel);
        
        bottom.setSpacing(5);
        bottom.setPadding(new Insets(5));
        
        frame.setBottom(bottom);
        
        Scene scene = new Scene(frame);
        newWindow.setScene(scene);
        
        String pathName = "dev/editor/style/style.css" ;
        File file = new File(pathName);
        if (file.exists()) {
            try {
                scene.getStylesheets().add(file.toURI().toURL().toExternalForm());
            } catch (MalformedURLException ex) {
                Logger.getLogger(NewObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
           System.out.println("Could not find css file: "+pathName);
        }
        
        back.setDisable(true);
        finish.setDisable(true);
        cancel.setOnAction(e -> {
            newWindow.close();
        });
    }

    public NewObject() {
    }

    public Stage getNewWindow() {
        return newWindow;
    }

    public BorderPane getFrame() {
        return frame;
    }

    public Button getNext() {
        return next;
    }

    public Button getBack() {
        return back;
    }

    public Button getFinish() {
        return finish;
    }

    public Button getCancel() {
        return cancel;
    }
}
