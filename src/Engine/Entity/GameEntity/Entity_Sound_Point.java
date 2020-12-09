/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.Entity_Player;
import Engine.Entity.AbstractEntity.Entity_Sound;
import Engine.Core.Exceptions.EntityCreationException;
import Engine.Core.Game;
import Engine.Core.Sound.SoundManager;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.Input;
import Engine.Util.Time;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

/**
 *
 * @author child
 */
public class Entity_Sound_Point extends Entity_Sound{
    
    //variables here
    private float range;
    
    private Entity_Player player;
    
    public Entity_Sound_Point(HashMap<String, Object> propertyMap) throws EntityCreationException
    {
        super(propertyMap);
        
        //parse property map here
        this.range = Float.parseFloat((String) propertyMap.get("range"));
    }
    
    @Override
    public void start() {
        player = Game.getCurrentLevel().getPlayer();
        //start logic here
        double distance = Game.getCurrentLevel().getPlayer().getPosition().distance(this.position)/range;
        if(distance >= 0 && distance <= range){
            mediaplayer.volumeProperty().set(0);
        }
    }

    @Override
    public void update() {
        
        Point2D vectorPlayer = player.getPosition().subtract(this.position);
        Point2D front = new Point2D(Math.cos(Math.toRadians(player.getRotation())), Math.sin(Math.toRadians(player.getRotation())));
        Point2D right = new Point2D(Math.sin(Math.toRadians(player.getRotation())), -Math.cos(Math.toRadians(player.getRotation())));
        Point2D left = new Point2D(-Math.sin(Math.toRadians(player.getRotation())), Math.cos(Math.toRadians(player.getRotation())));
        double frontAngle = vectorPlayer.angle(front);
        double rightAngle = vectorPlayer.angle(right);
        double leftAngle = vectorPlayer.angle(left);
        double dot = vectorPlayer.dotProduct(Math.sin(Math.toRadians(player.getRotation())), -Math.sin(Math.toRadians(player.getRotation())));
        
        double d = ((this.getPosition().getX() - player.getPosition().getX())*(front.getY()-player.getPosition().getY())) - ((this.getPosition().getY() - player.getPosition().getY()) * (front.getX() - player.getPosition().getX()) );
        
        if(rightAngle < leftAngle)
            mediaplayer.setBalance(-Math.sin(Math.toRadians(frontAngle+180)));
        else
            mediaplayer.setBalance(Math.sin(Math.toRadians(frontAngle+180)));
        
        double distance = player.getPosition().distance(this.position);
        if(distance >= 0 && distance <= range){
            double distanceVolume = -(0.6) * Math.log10(distance/range);
            mediaplayer.volumeProperty().set(playerVolume.get() * distanceVolume * channelVolume.get());
        }
        else
        {
            mediaplayer.volumeProperty().set(0);
        }
        
        this.mediaplayer.setAudioSpectrumNumBands(1000);
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        switch(signalName) //new signals here
        {
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
}
