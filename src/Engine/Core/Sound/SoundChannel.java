/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core.Sound;

import Engine.Core.Settings;
import java.util.ArrayList;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author child
 */
public class SoundChannel {
    public ReadOnlyObjectWrapper<Double> volume;
    private ArrayList<MediaPlayer> players;
    
    public SoundChannel(String configValueName)
    {
        if(!configValueName.equals(null))
            volume = new ReadOnlyObjectWrapper<>(Settings.getDouble(configValueName)); 
        else
            volume = new ReadOnlyObjectWrapper<>(1.0);//in case the config file doesnt hold the volume value
        
        players = new ArrayList<>();
    }
    
    public void addPlayer(MediaPlayer player)
    {
        players.add(player);
        player.volumeProperty().bind(this.volume);
    }
    
    public void removePlayer(MediaPlayer player)
    {
        for(MediaPlayer p : players)
        {
            if(p.equals(player)){
                players.remove(p);
                break;
            }
        }
    }
}
