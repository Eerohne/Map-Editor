/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import static Engine.Core.Game.mediaPlayer;
import Engine.Core.Sound.SoundManager;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.RessourceManager.ResourceLoader;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.media.MediaPlayer;

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
        mediaPlayer = SoundManager.createPlayer("sounds/music/digital_attack.wav", "master", true);
        mediaPlayer.setAutoPlay(true);
    }
    
    public Entity_Sound_Global(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        System.out.println("entity");
        mediaPlayer = SoundManager.createPlayer("sounds/music/digital_attack.wav", "music", false);
        //mediaPlayer = new MediaPlayer(ResourceLoader.loadAudio("sounds/music/digital_attack.wav"));
        mediaPlayer.setAutoPlay(true);
        //mediaPlayer.play();
        //System.out.println(mediaPlayer.getStatus());
        //parse property map here
        //this.myVariable = Integer.valueOf((String) propertyMap.get("myvariable"));
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
                this.mediaplayer.play();
                break;
            case "pause":
                this.mediaplayer.pause();
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
