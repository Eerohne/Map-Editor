/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Window.Menu;

import Engine.Util.RessourceManager.ResourceLoader;
import java.util.HashMap;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author child
 */
public class GameMenu {
    private HashMap<String, Pane> screens;
    private String currentScreen;
    private String mainScreen;
    
    
    
    public GameMenu()
    {
        screens = new HashMap<>();
        currentScreen = "";
        mainScreen = "main";
    }
    public GameMenu(String mainScreen)
    {
        screens = new HashMap<>();
        currentScreen = "";
        this.mainScreen = mainScreen;
    }
    public void open()
    {         
        for(Pane p :screens.values())
        {
            p.setVisible(false);
            p.setManaged(false);
        }
        Pane main = screens.get(mainScreen);
        main.setVisible(true);
        main.setManaged(true);
        currentScreen = "main";
    }
    public void close()
    {
        for(Pane p :screens.values())
        {
            p.setVisible(false);
            p.setManaged(false);
        }
    }
    
    public void addScreen(String name, Pane screen)
    {
        try{
        screens.put(name, screen);
        screen.setVisible(false);
        screen.setManaged(false);
        }
        catch(NullPointerException e)
        {
            System.out.println(e);
        }
    }
    
    public Pane getScreen(String name)
    {
        return screens.get(name);
    }
    
    public void removeScreen(String name)
    {
        screens.remove(name);
    }
    
    public void transitionToScreen(String name)
    {
        if(currentScreen != null)
        {
            try{
                Pane current = getScreen(currentScreen);
                current.setVisible(false);
                current.setManaged(false);
            }
            catch(NullPointerException e)
            {
                System.out.println(e+ " the menu screen " +name+ " doesnt exist");
            }
        }
        
        try{
        Pane target = getScreen(name);
        target.setVisible(true);
        target.setManaged(true);
        currentScreen = name;
        }
        catch(NullPointerException e)
        {
            System.out.println(e+ " the menu screen " +name+ " doesnt exist");
        }
    }
}
