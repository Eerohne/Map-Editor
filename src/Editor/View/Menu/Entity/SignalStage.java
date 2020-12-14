/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Menu.Entity;

import Editor.Controller.SignalController;
import Editor.Model.SignalModel;
import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author linuo
 */
public class SignalStage{

    public SignalStage(Stage stage) throws MalformedURLException {
        Stage newSignal = new Stage();
        newSignal.initOwner(stage);
        newSignal.initModality(Modality.WINDOW_MODAL);
        
        SignalView sv = new SignalView();
        SignalController sc = new SignalController(new SignalModel(), sv);
        
        Scene s = new Scene(sv, 600, 300);
        
        String pathName = "dev/editor/style/style.css" ;
        File file = new File(pathName);
        if (file.exists()) {
            s.getStylesheets().add(file.toURI().toURL().toExternalForm());
        } else {
           System.out.println("Could not find css file: "+pathName);
        }
        
        newSignal.setResizable(false);
        newSignal.setTitle("create signal");
        newSignal.setScene(s);
        newSignal.show();
        
    }
    
}
