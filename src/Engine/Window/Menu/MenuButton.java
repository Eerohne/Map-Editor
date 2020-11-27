/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Window.Menu;

import Engine.Core.Sound.SoundManager;
import Engine.Util.RessourceManager.ResourceLoader;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author child
 */
public class MenuButton extends Button{
    public  boolean valid = true;
    private MediaPlayer mediaPlayer;
    private static String mouseEnterSound = "sounds/ui/button_hover.wav";
    private static String mouseClickSound = "sounds/ui/button_click.wav";
    private static String mouseClickInvalideSound = "sounds/ui/button_click_invalide.wav";
    
    public MenuButton(String text)
    {
        super(text);
        
        this.getStyleClass().add("button1");
        this.addEventHandler(MouseEvent.MOUSE_ENTERED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
              mediaPlayer = SoundManager.createPlayer(mouseEnterSound, "menu", false);
              mediaPlayer.play();
          }
        });

        
        this.addEventHandler(MouseEvent.MOUSE_CLICKED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
              if(valid)
                mediaPlayer = SoundManager.createPlayer(mouseClickSound, "menu", false);
              else
                  mediaPlayer = SoundManager.createPlayer(mouseClickInvalideSound, "menu", false);
              mediaPlayer.play();
          }
        });

    }
}
