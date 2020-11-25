/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core.Sound;

import Engine.Core.Settings;
import java.util.ArrayList;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author child
 */
public class SoundChannel {
    public ReadOnlyObjectWrapper<Double> volume;
    private ArrayList<MediaPlayer> players;
    
    //only when a channel is a slave of a master channel
    private String masterChannelName = "";
    private Double realVolume;
    
    public SoundChannel(String configValueName)
    {
        this(configValueName, "");
    }
    
    public SoundChannel(String configValueName, String masterChannelName)
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
        
        players = new ArrayList<>();
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
            //System.out.println("master : "+masterVolume+" slave : " +this.realVolume);
        }
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
