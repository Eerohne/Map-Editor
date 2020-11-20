/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Menu;

import Editor.Controller.ExistingEntityController;
import Editor.Model.EntityModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author linuo
 */
public class ExistingEntityTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        ExistingEntityModification view = new ExistingEntityModification();
        EntityModel model = new EntityModel();
        ExistingEntityController controller = new ExistingEntityController(model, view);
        
        Scene scene = new Scene(view, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
