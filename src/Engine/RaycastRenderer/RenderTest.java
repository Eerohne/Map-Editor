/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.RaycastRenderer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Jeffrey
 */
public class RenderTest extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Canvas canvas = new Canvas(400, 270);
        Renderer.test=true;
        Renderer.pV = false;
        
        Renderer.setCanvas(canvas);
        Renderer.setFov(90f);
        Renderer.render();
        
        HBox root = new HBox();
        root.setSpacing(5);
        root.getChildren().addAll(canvas);
        
        Scene scene = new Scene(root);
        
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
