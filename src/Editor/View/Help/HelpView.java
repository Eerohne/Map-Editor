/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Help;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class HelpView{

    public HelpView(Stage owner) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        webEngine.load( getClass().getResource("/Editor/View/Help/helpPage.htm").toExternalForm());
        
        Scene helpScene = new Scene(webView);
        Stage helpStage = new Stage();
        try {
            helpStage.getIcons().add(new Image(new FileInputStream(new File("dev/editor/window/optik_editor_icon.png"))));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HelpView.class.getName()).log(Level.SEVERE, null, ex);
        }
        helpStage.setResizable(false);
        helpStage.setTitle("Help");
        helpStage.setScene(helpScene);
        helpStage.initOwner(owner);
        helpStage.show();
    }
    
}
