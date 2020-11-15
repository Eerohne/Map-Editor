/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Window.Menu;

import Engine.Util.RessourceManager.RessourceLoader;
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
    private MediaPlayer mediaPlayer;
    private static Media mouseEnterSound = RessourceLoader.loadAudio("sounds/ui/button_hover.wav");
    private static Media mouseClickSound = RessourceLoader.loadAudio("sounds/ui/button_click.wav");
    
    public MenuButton(String text)
    {
        super(text);
        this.addEventHandler(MouseEvent.MOUSE_ENTERED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
              if(mediaPlayer != null)
                mediaPlayer.dispose();
              mediaPlayer = new MediaPlayer(mouseEnterSound);
              mediaPlayer.play();
          }
        });

        
        this.addEventHandler(MouseEvent.MOUSE_CLICKED,
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
              if(mediaPlayer != null)
                mediaPlayer.dispose();
              mediaPlayer = new MediaPlayer(mouseClickSound);
              mediaPlayer.play();
          }
        });

    }
}
