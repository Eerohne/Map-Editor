/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.Model.Profile.WallProfile;
import Editor.View.New.NewWallProfile;
import Editor.View.Help;
import Editor.View.Menu.ShortcutBar;
import Editor.View.Hierarchy.WallHierarchy;
import Editor.View.New.NewEntityStage;
import Engine.Core.Game;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class ShortcutController{

    public ShortcutController(ShortcutBar shortcutBar, Stage owner, WallHierarchy wallList) {
        shortcutBar.getHelp().setOnAction(e -> {
            new Help(owner);
        });
        
        shortcutBar.getRunShort().setOnAction(e -> {
            try {
                Stage engine = new Stage();
                Game game = new Game();
                engine.initOwner(owner);
                game.start(engine);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Engine Error");
                alert.setContentText("Engine Not Executed");
                alert.showAndWait();
            }
        });
        
        shortcutBar.getWallShort().setOnAction(e -> {
            new NewWallProfile(owner, WallProfile.resourceFolder, wallList);
        });
        
        shortcutBar.getEntityShort().setOnAction(e -> {
            new NewEntityStage(owner);
        });
    }
    
}
