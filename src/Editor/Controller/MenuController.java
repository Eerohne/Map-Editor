/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.Controller;

import Editor.NewEntityStage;
import Editor.View.Menu.Menu;
import Engine.Core.Game;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author A
 */
public class MenuController {
    private Button newWall;
    private Button newEntity;
    private Button placePlayer;
    private Button help;

    private Menu menu;
    
    public MenuController(Menu menu, Stage stage) {
        this.menu = menu;
        
        this.newWall = menu.getNewWall();
        this.newEntity = menu.getNewEntity();
        this.placePlayer = menu.getPlacePlayer();
        this.help = menu.getHelp();
        
        newWall.setOnAction(e -> {
            Game t = new Game();
            try{
                t.start(new Stage());
            } catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        });
        newEntity.setOnAction(e -> {
            new NewEntityStage(stage);
        });
    }
}
