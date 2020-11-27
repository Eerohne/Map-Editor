/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Sound.SoundManager;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.RessourceManager.ResourceLoader;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

/**
 *
 * @author child
 */
public class Entity_Sound_Global extends Entity{
    
    //variables here
    private double volume;
    private boolean paused;
    private MediaPlayer mediaplayer;
    
    public Entity_Sound_Global(String name, Point2D position)
    {
        super(name, position);
        mediaplayer = SoundManager.createPlayer("sounds/music/digital_attack.wav", "master", true);
        mediaplayer.setAutoPlay(true);
    }
    
    public Entity_Sound_Global(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        this.mediaplayer = SoundManager.createPlayer((String) propertyMap.get("audiopath"), "music", Boolean.valueOf((String) propertyMap.get("loop")));
        mediaplayer.setAutoPlay(Boolean.valueOf((String) propertyMap.get("onstart")));
    }
    
    @Override
    public void start() {
        //start logic here
    }

    @Override
    public void update() {
        //update logic here
    }
    
    @Override
    public void handleSignal(String signalName, Object[] arguments){
        switch(signalName) //new signals here
        {
            case "play":
                mediaplayer.play();
                break;
            case "pause":
                this.mediaplayer.pause();
                break;
            case "toggle":
                if(this.mediaplayer.getStatus() == Status.PLAYING)
                    this.mediaplayer.pause();
                else
                    this.mediaplayer.play();
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
