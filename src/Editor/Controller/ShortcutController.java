/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.View.Help;
import Editor.View.Menu.ShortcutBar;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class ShortcutController {

    public ShortcutController(ShortcutBar shortcutBar, Stage owner) {
        shortcutBar.getHelp().setOnAction(e -> {
            new Help(owner);
        });
    }
    
}
