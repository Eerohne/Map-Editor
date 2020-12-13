/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Menu.Entity;

import Editor.Controller.SignalViewerController;
import Editor.Model.SignalModel;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

/**
 *
 * @author linuo
 */
public class SignalViewerStage {
    
    public SignalViewerStage(Stage stage) throws ParseException, MalformedURLException {
        Stage newSignal = new Stage();
        newSignal.initOwner(stage);
        newSignal.initModality(Modality.WINDOW_MODAL);
        
        SignalViewer sv = new SignalViewer();
        SignalViewerController sc = new SignalViewerController(sv, new SignalModel());
        
        Scene s = new Scene(sv, 600, 300);
        
        String pathName = "dev/editor/style/style.css" ;
        File file = new File(pathName);
        if (file.exists()) {
            s.getStylesheets().add(file.toURI().toURL().toExternalForm());
        } else {
           System.out.println("Could not find css file: "+pathName);
        }
        
        newSignal.setTitle("Signal Viewer");
        newSignal.setScene(s);
        newSignal.show();
        
    }
    
}
