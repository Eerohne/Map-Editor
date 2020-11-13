/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.NewEntityStage;
import Editor.View.Menu.TopMenu;
import Engine.Core.Game;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class MenuController {
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
                System.out.println("YAY");
                Stage engine = new Stage();
                Game game = new Game();
                game.start(engine);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Engine Error");
                alert.setContentText("Engine Not Executed");
                alert.showAndWait();
            }
        });
        
    }
}
