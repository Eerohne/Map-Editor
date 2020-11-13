/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editor.View.Menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 *
 * @author A
 */
public class TopMenu extends MenuBar{
    private Menu file;
    private Menu edit;
    private Menu run;
    private Menu help;
    
    
    
    public TopMenu() {
        //Add File Options
        MenuItem newMap = new MenuItem("New Map");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        MenuItem exit = new MenuItem("Exit");
        
        this.file = new Menu("File", null, newMap, open, save, exit);
        
        //Add Edit Options
        MenuItem newWall = new MenuItem("Add New Wall");
        MenuItem newEntity = new MenuItem("Create New Entity");
        MenuItem player = new MenuItem("Place Player");
        
        this.edit = new Menu("Edit", null, newWall, newEntity, player);
        
        //Add run options
        MenuItem runOption = new MenuItem("Run Map");
        
        this.run = new Menu("Run", null, runOption);
        
        //Add Help Menu
        MenuItem helpOption = new MenuItem("Optik Editor Tutorial");
        
        this.help = new Menu("Help", null, helpOption);
        
        this.getMenus().addAll(file, edit, run, help);
    }

    public Menu getFile() {
        return file;
    }

    public Menu getEdit() {
        return edit;
    }

    public Menu getRun() {
        return run;
    }

    public Menu getHelp() {
        return help;
    }
}
