/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Sound.SoundManager;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.RessourceManager.ResourceLoader;
import Engine.Util.Time;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

/**
 *
 * @author child
 */
public abstract class Entity_Sound extends Entity{
    
    //variables here
    protected MediaPlayer mediaplayer;
    protected String audioPath;
    protected String channel;
    protected boolean onStart;
    protected boolean loop;
    protected boolean onlyOnce;
    
    protected float time;
    
    public Entity_Sound(String name, Point2D position)
    {
        super(name, position);
        mediaplayer = SoundManager.createPlayer("sounds/music/digital_attack.wav", "master", true, true);
        mediaplayer.setAutoPlay(true);
    }
    
    public Entity_Sound(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        this.audioPath = (String) propertyMap.get("audiopath");
        this.channel = (String) propertyMap.get("channel");
        this.loop = Boolean.parseBoolean((String) propertyMap.get("loop"));
        this.onStart = Boolean.parseBoolean((String) propertyMap.get("onstart"));
    }
    
    public void destroy()
    {
        SoundManager.getChannel(channel).removePlayer(mediaplayer);
        super.destroy();
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
