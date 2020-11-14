/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Luncher;

import Editor.MapEditor;
import Engine.Core.Game;
import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author child
 */
public class Launcher extends Application{
    public void start(Stage launcherStage) throws MalformedURLException {
        AnchorPane anchor = new AnchorPane();
        VBox buttonVBox = new VBox();
        buttonVBox.setSpacing(50);
        Label mark = new Label("Optik Game Engine");
        mark.setId("font-mark");
        anchor.getChildren().addAll(mark, buttonVBox);
        anchor.setTopAnchor(buttonVBox, 0.0);
        anchor.setBottomAnchor(buttonVBox, 0.0);
        anchor.setRightAnchor(buttonVBox, 0.0);
        anchor.setLeftAnchor(buttonVBox, 0.0);
        anchor.setBottomAnchor(mark, 0.0);
        anchor.setRightAnchor(mark, 0.0);
        Scene scene = new Scene(anchor, 600, 600);
        
        String pathName = "ressources/style.css" ;
        File file = new File(pathName);
        if (file.exists()) {
            scene.getStylesheets().add(file.toURI().toURL().toExternalForm());
        } else {
           System.out.println("Could not find css file: "+pathName);
        }
        
        //Title
        Label title = new Label("OPTIK ENGINE");
        title.setId("font-title");
        title.setEffect(new Glow(1));
        
        //launch game button
        Button gameButton = new Button("Launch Game");
        gameButton.setMinSize(200, 80);
        gameButton.getStyleClass().add("button1");
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
        
        //launch editor button
        Button editorButton = new Button("Launch Editor");
        editorButton.setMinSize(200, 80);
        editorButton.getStyleClass().add("button1");
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
        
        buttonVBox.getChildren().addAll(title, gameButton, editorButton);
        buttonVBox.setAlignment(Pos.CENTER);
        
        
        launcherStage.setTitle("Optik Engine Launcher");
        launcherStage.setResizable(false);
        launcherStage.setScene(scene);
        launcherStage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
