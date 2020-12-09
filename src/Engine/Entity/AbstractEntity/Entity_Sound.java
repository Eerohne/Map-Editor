/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.AbstractEntity;

import Engine.Core.Exceptions.EntityCreationException;
import Engine.Core.Sound.SoundManager;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.RessourceManager.ResourceLoader;
import Engine.Util.Time;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 *
 * @author child
 */
public abstract class Entity_Sound extends Entity{
    
    //variables here
    protected MediaPlayer mediaplayer;
    protected String audioPath;
    protected String channel;
    protected double initialVolume;
    protected boolean onStart;
    protected boolean loop;
    protected boolean onlyOnce;
    
    protected SimpleDoubleProperty channelVolume;
    protected SimpleDoubleProperty playerVolume;
    
    protected float time;
    
    public Entity_Sound(HashMap<String, Object> propertyMap) throws EntityCreationException
    {
        super(propertyMap);
        try{
            this.audioPath = (String) propertyMap.get("audiopath");
            this.channel = (String) propertyMap.get("channel");
            this.initialVolume = Double.parseDouble((String) propertyMap.get("volume"));
            this.onStart = Boolean.parseBoolean((String) propertyMap.get("onstart"));
            this.loop = Boolean.parseBoolean((String) propertyMap.get("loop"));
            this.onlyOnce = Boolean.parseBoolean((String) propertyMap.get("onlyonce"));

            mediaplayer = SoundManager.createPlayer(this.audioPath, this.channel, this.loop, this.onlyOnce);
            mediaplayer.volumeProperty().unbind();
            mediaplayer.setAutoPlay(this.onStart);
            channelVolume = new SimpleDoubleProperty();
            channelVolume.bind(SoundManager.getChannel(this.channel).volume);

            channelVolume = new SimpleDoubleProperty();
            channelVolume.bind(SoundManager.getChannel(this.channel).volume);
            playerVolume = new SimpleDoubleProperty();
            playerVolume.set(this.initialVolume);
        }
        catch(Exception e)
        {
            System.out.println(e);
            throw new EntityCreationException("could not initialize entity '"+this.name+"' with the given properties");
        }
        
    }
    
    public void destroy()
    {
        SoundManager.getChannel(channel).removePlayer(mediaplayer);
        super.destroy();
    }
    
    public void fadeOut()
    {
        final Timeline timeline = new Timeline();
        //timeline.setCycleCount(Timeline.INDEFINITE);
        final KeyValue kv = new KeyValue(playerVolume, 0,
         Interpolator.EASE_BOTH);
        final KeyFrame kf = new KeyFrame(Duration.millis(3000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    
    public void fadeIn()
    {
        final Timeline timeline = new Timeline();
        //timeline.setCycleCount(Timeline.INDEFINITE);
        final KeyValue kv = new KeyValue(playerVolume, this.initialVolume,
         Interpolator.EASE_BOTH);
        final KeyFrame kf = new KeyFrame(Duration.millis(3000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        switch(signalName) //new signals here
        {
            case "play":
                if(!this.onlyOnce && !this.loop)
                    mediaplayer.stop();
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
            case "fadeout":
                fadeOut();
                break;
            case "fadein":
                fadeIn();
                break;
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
