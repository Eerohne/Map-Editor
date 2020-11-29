/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Game;
import Engine.Core.Sound.SoundManager;
import Engine.Entity.AbstractEntity.Entity;
import Engine.Util.Input;
import Engine.Util.Time;
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
    private SimpleDoubleProperty volume;
    private float range;
    
    private Entity_Player player;
    
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
        this.time += Time.deltaTime;
        
        Point2D vectorPlayer = player.getPosition().subtract(this.position);
        //vectorPlayer = vectorPlayer.multiply(-1);
        Point2D front = new Point2D(Math.cos(Math.toRadians(player.getRotation())), Math.sin(Math.toRadians(player.getRotation())));
        Point2D right = new Point2D(Math.sin(Math.toRadians(player.getRotation())), -Math.cos(Math.toRadians(player.getRotation())));
        Point2D left = new Point2D(-Math.sin(Math.toRadians(player.getRotation())), Math.cos(Math.toRadians(player.getRotation())));
        double frontAngle = vectorPlayer.angle(front);
        double rightAngle = vectorPlayer.angle(right);
        double leftAngle = vectorPlayer.angle(left);
        double dot = vectorPlayer.dotProduct(Math.sin(Math.toRadians(player.getRotation())), -Math.sin(Math.toRadians(player.getRotation())));
        /*System.out.println("vector p to s : "+vectorPlayer);
        System.out.println("angle between p and s : "+(angle));
        System.out.println("dot between p and s : "+(dot));
        System.out.println("final balance : "+Math.sin(Math.toRadians(angle+180)));*/
        
        double d = ((this.getPosition().getX() - player.getPosition().getX())*(front.getY()-player.getPosition().getY())) - ((this.getPosition().getY() - player.getPosition().getY()) * (front.getX() - player.getPosition().getX()) );
        //System.out.println("d : "+d);
        if(rightAngle < leftAngle)
            mediaplayer.setBalance(-Math.sin(Math.toRadians(frontAngle+180)));
        else
            mediaplayer.setBalance(Math.sin(Math.toRadians(frontAngle+180)));
        //System.out.println(Game.getCurrentLevel().getPlayer().getPosition().angle(0, 1));
        
        double distance = player.getPosition().distance(this.position);
        if(distance >= 0 && distance <= range){
            double distanceVolume;
            if(Input.keyPressed(KeyCode.E))
                distanceVolume = distance/range;
            else
                distanceVolume = -(0.6) * Math.log10(distance/range);
            mediaplayer.volumeProperty().set(distanceVolume * volume.get());
            //System.out.println("distance 'unit' : "+ Game.getCurrentLevel().getPlayer().getPosition().distance(this.position) +" distance : "+ (int)(distance*100) + "% distance volume : " + distanceVolume);
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
