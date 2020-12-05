/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Menu.Entity;

import Editor.Controller.SignalViewerController;
import Editor.Model.SignalModel;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

/**
 *
 * @author linuo
 */
public class SignalViewerStage {
    
    public SignalViewerStage(Stage stage) throws ParseException {
        Stage newSignal = new Stage();
        newSignal.initOwner(stage);
        newSignal.initModality(Modality.WINDOW_MODAL);
        
        SignalViewer sv = new SignalViewer();
        SignalViewerController sc = new SignalViewerController(sv, new SignalModel());
        
        Scene s = new Scene(sv, 500, 300);
        newSignal.setTitle("Signal Viewer");
        newSignal.setScene(s);
        newSignal.show();
        
    }
    
}
