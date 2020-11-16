/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.RaycastRenderer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Jeffrey
 */
public class RenderTest extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Canvas canvas = new Canvas(800, 450);
        Renderer.setCanvas(canvas);
        Renderer.setPos(2.5, 2.5);
        Renderer.setDir(0f);
        Renderer.setFov(90f);
        
        canvas.setWidth(400);canvas.setHeight(225);
        Renderer.resize();
        
        Renderer.MiniMap.generate();
        
        HBox root = new HBox();
        root.setSpacing(10); root.setPadding(new Insets(0, 10, 0, 10));
        root.getChildren().addAll(Renderer.MiniMap.minimap, canvas);
        
        Renderer.render();
        
        Scene scene = new Scene(root, canvas.getWidth()+40*5+20, canvas.getHeight());
        
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
