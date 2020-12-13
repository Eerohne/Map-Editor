/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Menu.Entity;

import Editor.Controller.ExistingEntityController;
import Editor.Model.EntityModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

/**
 *
 * @author linuo
 */
public class ExistingEntityStage{
    ExistingEntityModification view;
    
    
    public ExistingEntityStage(Stage primaryStage) throws IOException, FileNotFoundException, ParseException {
        
        Stage existingEntity = new Stage();
        existingEntity.initOwner(primaryStage);
        existingEntity.initModality(Modality.WINDOW_MODAL);
        
        view = new ExistingEntityModification();
        EntityModel model = new EntityModel();
        ExistingEntityController controller = new ExistingEntityController(model, view);
        existingEntity.setTitle("Edit Existing Entities");
        Scene scene = new Scene(view, 660, 700);
        
        String pathName = "dev/editor/style/style.css" ;
        File file = new File(pathName);
        
        if (file.exists()) {
            scene.getStylesheets().add(file.toURI().toURL().toExternalForm());
        } else {
           System.out.println("Could not find css file: "+pathName);
        }
        
        existingEntity.setScene(scene);
        existingEntity.show();
    }

    public ExistingEntityModification getView() {
        return view;
    }
}
