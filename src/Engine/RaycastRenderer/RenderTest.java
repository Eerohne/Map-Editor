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
        int[][] map = {
        {3, 4, 3, 4, 3, 4},
        {4, 0, 0, 0, 0, 3},
        {3, 0, 0, 0, 0, 4},
        {4, 0, 0, 0, 0, 3},
        {3, 0, 0, 0, 0, 4},
        {4, 3, 4, 3, 4, 3}
        };
        Renderer.setMap(map);
        Canvas canvas = new Canvas(400, 225);
        Renderer.test=true;
        Renderer.setCanvas(canvas);
        Renderer.setPos(3.0, 3.0);
        Renderer.setDir(90f);
        Renderer.setFov(90f);
        Renderer.MiniMap.generate();
        Renderer.render();
        
        HBox root = new HBox();
        root.setSpacing(5);
        root.getChildren().addAll(Renderer.MiniMap.minimap, canvas);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Render Test: ("+Renderer.getPos().getX()+", "+Renderer.getPos().getY()+"), "+Renderer.getDir()+" deg, fov: "+Renderer.fov);
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
