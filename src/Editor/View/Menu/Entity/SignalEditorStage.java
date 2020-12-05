/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Menu.Entity;

import Editor.Controller.ExistingEntityController;
import Editor.Controller.SignalEditorController;
import Editor.Controller.SignalViewerController;
import Editor.Model.SignalModel;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

/**
 *
 * @author linuo
 */
public class SignalEditorStage {

    public SignalEditorStage(Stage stage) throws IOException, FileNotFoundException, ParseException {
        Stage newSignal = new Stage();
        newSignal.initOwner(stage);
        newSignal.initModality(Modality.WINDOW_MODAL);
        
        SignalEditorView sev = new SignalEditorView();
        SignalEditorController sec = new SignalEditorController(new SignalModel(), sev);
        
        Scene s = new Scene(sev, 500, 300);
        newSignal.setTitle("Signal editor");
        newSignal.setScene(s);
        newSignal.show();
    }
    
}
