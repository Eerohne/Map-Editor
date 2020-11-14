/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Luncher;

import Editor.MapEditor;
import Engine.Core.Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author child
 */
public class Launcher extends Application{
    public void start(Stage launcherStage) {
        VBox buttonVBox = new VBox();
        buttonVBox.setSpacing(50);
                
        Button gameButton = new Button("Launch Game");
        gameButton.setMinSize(200, 80);
        gameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                Stage engine = new Stage();
                Game game = new Game();
                game.start(engine);
                launcherStage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Launcher Error");
                alert.setContentText("Engine cannot be oppened");
                alert.showAndWait();
            }
            }
        });
        
        Button editorButton = new Button("Launch Editor");
        editorButton.setMinSize(200, 80);
        editorButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                Stage ediorStage = new Stage();
                MapEditor editor = new MapEditor();
                editor.start(ediorStage);
                launcherStage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Launcher Error");
                alert.setContentText("Editor cannot be oppened");
                alert.showAndWait();
            }
            }
        });
        
        buttonVBox.getChildren().addAll(gameButton, editorButton);
        buttonVBox.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(buttonVBox, 600, 600);
        launcherStage.setTitle("Optik Engine Launcher");
        launcherStage.setResizable(false);
        launcherStage.setScene(scene);
        launcherStage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
