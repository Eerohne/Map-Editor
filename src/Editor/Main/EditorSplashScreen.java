/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Main;

import Editor.View.New.NewMap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import Editor.View.New.NewProject;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author A
 */
public class EditorSplashScreen {
    
    public EditorSplashScreen(Stage parent){
        Stage splashScreenStage = new Stage();
        
        splashScreenStage.initStyle(StageStyle.UNDECORATED);
        splashScreenStage.initOwner(parent);
        
        try { 
           ImageView splash = new ImageView(new Image(new FileInputStream("dev/editor/window/optik_editor_splash.png"), 600, 338, true, false));
           VBox screen = new VBox(splash);
        
            new SplashScreenController(splashScreenStage, parent);


            Scene splashScene = new Scene(screen, 600, 338);

            splashScreenStage.setScene(splashScene);
            splashScreenStage.show();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditorSplashScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class SplashScreenController{

    public SplashScreenController(Stage current, Stage parent) {
        current.focusedProperty().addListener(((observable, pastFoc, isFoc) -> {
            if(!isFoc)
                current.close();
        }));
    }
}