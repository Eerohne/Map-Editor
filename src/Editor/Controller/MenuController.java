/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.NewEntityStage;
import Editor.ExistingEntityStage;
import Editor.View.Menu.TopMenu;
import Engine.Core.Game;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

/**
 *
 * @author A
 */
public class MenuController{
    private Menu file;
    private Menu edit;
    private Menu run;
    private Menu help;
    private TopMenu menu;
    
    
    
    public MenuController(TopMenu menu, Stage editorStage) {
        this.menu = menu;
        
        this.file = menu.getFile();
        this.edit = menu.getEdit();
        this.run = menu.getRun();
        this.help = menu.getHelp();
        
        //File Events
        List<MenuItem> fileItems = file.getItems();
        fileItems.get(fileItems.size()-1).setOnAction(e -> {
            editorStage.close();
        });
        
        //Run Engine Event
        List<MenuItem> runItems = run.getItems();
        runItems.get(0).setOnAction(e ->{
            try {
                Stage engine = new Stage();
                Game game = new Game();
                engine.initOwner(editorStage);
                game.start(engine);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Engine Error");
                alert.setContentText("Engine Not Executed");
                alert.showAndWait();
            }
        });
        
        List<MenuItem> editItems = edit.getItems();
        editItems.get(1).setOnAction(e -> {
            new NewEntityStage(editorStage);
        });
        editItems.get(2).setOnAction((event) -> {
            try {
                new ExistingEntityStage(editorStage);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
