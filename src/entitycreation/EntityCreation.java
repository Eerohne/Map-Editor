/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitycreation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author linuo
 */
public class EntityCreation extends Application{
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        View view = new View();
        Controller controller = new Controller(new EntityModel(), view);
        controller.setData();
        Scene scene = new Scene(view, 500, 300);
        primaryStage.setTitle("Entity Creation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String [] args){
        launch(args);
    }
}
