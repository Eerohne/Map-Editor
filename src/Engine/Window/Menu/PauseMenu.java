/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Window.Menu;

import Engine.Core.Sound.SoundManager;
import Engine.Util.RessourceManager.ResourceLoader;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author child
 */
public class PauseMenu extends GameMenu{
    //sounds
    private MediaPlayer mediaPlayer;
    private static String menuOpen = "sounds/ui/menu_open.wav";
    private static String menuClose = "sounds/ui/menu_close.wav";
    
    public PauseMenu()
    {
        super();
    }
    
    public PauseMenu(String mainScreen)
    {
        super(mainScreen);
    }
    
    @Override
    public void open()
    {
        super.open();
        if(mediaPlayer != null)
          mediaPlayer.dispose();
          mediaPlayer = SoundManager.createPlayer(menuOpen, "menu", false);
          mediaPlayer.play();
    }
    
    @Override
    public void close()
    {
        super.close();
        if(mediaPlayer != null)
          mediaPlayer.dispose();
          mediaPlayer = SoundManager.createPlayer(menuClose, "menu", false);
          mediaPlayer.play();
    }
    
}
