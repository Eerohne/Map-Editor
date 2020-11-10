/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.RaycastRenderer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Jeffrey
 */
public class RenderTest extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Renderer renderer = new Renderer();
        Canvas canvas1 = renderer.frame;
        renderer.render();
        VBox root = new VBox();
        root.getChildren().addAll(canvas1);
        
        Scene scene = new Scene(root, canvas1.getWidth(), canvas1.getHeight());
        
        primaryStage.setTitle("Render Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
