/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Game;
import Engine.Core.Sound.SoundManager;
import Engine.Entity.AbstractEntity.Entity;
import java.util.HashMap;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;

/**
 *
 * @author child
 */
public class Entity_Sound_Point extends Entity_Sound{
    
    //variables here
    private SimpleDoubleProperty volume;
    private float range;
    
    public Entity_Sound_Point(String name, Point2D position, int myVariable)
    {
        super(name, position);
    }
    
    public Entity_Sound_Point(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        //parse property map here
        this.range = Float.parseFloat((String) propertyMap.get("range"));
        this.mediaplayer = SoundManager.createPlayer(this.audioPath, this.channel, this.loop, false);
        mediaplayer.volumeProperty().unbind();
        mediaplayer.setAutoPlay(this.onStart);
        volume = new SimpleDoubleProperty();
        volume.bind(SoundManager.getChannel(this.channel).volume);
        //mediaplayer.volumeProperty().bind(p);
    }
    
    @Override
    public void start() {
        //start logic here
        double distance = range - Game.getCurrentLevel().getPlayer().getPosition().distance(this.position);
        if(distance <=0){
            mediaplayer.volumeProperty().set(0);
        }
    }

    @Override
    public void update() {
        double distance = range - Game.getCurrentLevel().getPlayer().getPosition().distance(this.position);
        if(distance >= 0){
            double distanceVolume = distance/range;
            mediaplayer.volumeProperty().set(distanceVolume * volume.get());
        }
        else
        {
            mediaplayer.volumeProperty().set(0);
        }
    }
    
    @Override
    public void handleSignal(String signalName, Object[] arguments){
        switch(signalName) //new signals here
        {
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
