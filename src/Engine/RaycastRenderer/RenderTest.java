/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.RaycastRenderer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Jeffrey
 */
public class RenderTest extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Renderer.render();
        Canvas canvas = Renderer.frame;
        Pane root = new Pane();
        root.getChildren().addAll(canvas);
        
        Scene scene = new Scene(root, canvas.getWidth(), canvas.getHeight());
        
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
