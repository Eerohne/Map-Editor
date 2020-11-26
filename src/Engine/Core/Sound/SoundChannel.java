/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core.Sound;

import Engine.Core.Settings;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

/**
 *
 * @author child
 */
public class SoundChannel {
    public ReadOnlyObjectWrapper<Double> volume;
    private HashMap<MediaPlayer, Boolean> players;
    private boolean temporaryChannel; //should players in this channel be cleared on map load?
    
    //only when a channel is a slave of a master channel
    private String masterChannelName = "";
    private Double realVolume = 1.0;
    
    public SoundChannel(String configValueName)
    {
        this(configValueName, "", true);
    }
    
    public SoundChannel(String configValueName, String masterChannelName, boolean isTemporary)
    {
        if(!configValueName.equals(null))
            volume = new ReadOnlyObjectWrapper<>(Settings.getDouble(configValueName)); 
        else
            volume = new ReadOnlyObjectWrapper<>(1.0);//in case the config file doesnt hold the volume value

        
        if(!masterChannelName.isEmpty()){
            this.masterChannelName = masterChannelName;
            SoundManager.getChannel(masterChannelName).volume.addListener((ObservableValue<? extends Double> observable, Double oldValue, Double newValue) -> {
                this.setVolume(this.realVolume);
            });
        }
        else
            this.masterChannelName = "";
        
        this.temporaryChannel = isTemporary;
        players = new HashMap<>();
    }
    
    public void setVolume(Double volume)
    {
        this.realVolume = volume;
        if(this.masterChannelName.isEmpty())//is not a slave channel
        {
            this.volume.set(volume);
        }
        else
        {
            Double masterVolume = SoundManager.getChannel(masterChannelName).volume.get();
            this.volume.set(realVolume * masterVolume);
        }
    }
    
    public void pause()
    {
        players.forEach((k, v) -> {
            if(k.getStatus().equals(Status.PLAYING))
                players.put(k, true);
            else
                players.put(k, false);
            k.pause();
        });
    }
    
    public void play()
    {
        players.forEach((k, v) -> {
            if(v.equals(true)) //only play if player was previously playing
                k.play();
        });
    }
    
    public void addPlayer(MediaPlayer player)
    {
        //players.put(player.get, true);
        System.out.println(player.getStatus());
        player.volumeProperty().bind(this.volume);
        System.out.println(this.volume);
    }
    
    public void removePlayer(MediaPlayer player)
    {
        players.forEach((k, v) -> {
            if(k.equals(player)){
                players.remove(v);
                player.dispose();
            }
        });
    }
    
    public boolean isTemporary()
    {
        return temporaryChannel;
    }
    
    public void clear()
    {
        players.forEach((k, v) -> {
            k.dispose();
        });
        players.clear();
    }
}
