/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.Entity_Player;
import Engine.Core.Exceptions.EntityCreationException;
import Engine.Core.Game;
import Engine.Core.Sound.SoundManager;
import Engine.RaycastRenderer.Renderer;
import Engine.Util.Input;
import Engine.Util.RessourceManager.ResourceLoader;
import Engine.Util.Time;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

/**
 *
 * @author child
 */
public class Entity_Player_Simple extends Entity_Player{

    protected float playerSpeed, walkSpeed, runSpeed;
    protected Point2D velocity;
    protected float colisionRadius = 0.1f;
    
    private Label label;
    private float headBobTime;
    private float headBobRange = 0.02f;
    private float headBobFrequency = 10f;
    private double footstepDistance = 1.0;
    
    private String[] walkfootstepPaths = {"sounds/fx/footsteps_walk1.wav", "sounds/fx/footsteps_walk3.wav", "sounds/fx/footsteps_walk4.wav"};
    private MediaPlayer[] footstepMediaPlayers;
    public double distanceTraveled=0;
    
    public Entity_Player_Simple(HashMap<String, Object> propertyMap) throws EntityCreationException
    {
        super(propertyMap);
        this.walkSpeed = Float.parseFloat((String) propertyMap.get("walkspeed"));
        this.runSpeed = Float.parseFloat((String) propertyMap.get("runspeed"));
        
        footstepMediaPlayers = new MediaPlayer[walkfootstepPaths.length];
        int mediaplayerCounter=0;
        for(String path : walkfootstepPaths)
        {
            Media walkfootstepMedia= ResourceLoader.loadAudio(path);
            footstepMediaPlayers[mediaplayerCounter] = SoundManager.createPlayer(walkfootstepMedia, "game", false, false);
            mediaplayerCounter++;
        }
    }

    @Override
    public void start() {
        super.start();
        
        this.playerSpeed = walkSpeed;
    }

    @Override
    public void update() {
        System.out.println("player height : "+this.height);
        Point2D dir = Point2D.ZERO;
        float newHeadBob = 0;
        if(!Game.isPaused)
        {
            if(Input.keyPressed(KeyCode.W))
                dir = dir.add(Math.cos(Math.toRadians(this.rotation)), Math.sin(Math.toRadians(this.rotation)));
            if(Input.keyPressed(KeyCode.S))
                dir = dir.add(-Math.cos(Math.toRadians(this.rotation)), -Math.sin(Math.toRadians(this.rotation)));
            if(Input.keyPressed(KeyCode.A))
                dir = dir.add(Math.sin(Math.toRadians(this.rotation)), -Math.cos(Math.toRadians(this.rotation)));
            if(Input.keyPressed(KeyCode.D))
                dir = dir.add(-Math.sin(Math.toRadians(this.rotation)), Math.cos(Math.toRadians(this.rotation)));

            if(Input.keyPressed(KeyCode.LEFT))
                this.rotation -= 100 * Time.deltaTime;
            if(Input.keyPressed(KeyCode.RIGHT))
                this.rotation += 100 * Time.deltaTime;

            if(Input.keyPressed(KeyCode.SHIFT)){
                playerSpeed = runSpeed;
                newHeadBob = headBobFrequency*(runSpeed/walkSpeed);
            }else{
                playerSpeed = walkSpeed;
                newHeadBob = headBobFrequency;
            }
        }
        
            dir = Game.getCurrentLevel().checkCollision(position, dir, colisionRadius); //gets the final direction vector after colision detection
            //dir = dir.normalize();
            dir = dir.multiply(playerSpeed * Time.deltaTime); //move the player in the calculated direction 
            position = position.add(dir);
            distanceTraveled += dir.magnitude();
            //System.out.println(dir.magnitude());
            //headbob code
            if(dir.magnitude() != 0){
                this.height = (float)Math.sin(Time.timePassed * newHeadBob) * headBobRange;
            }
            if(distanceTraveled > footstepDistance && true==true)
            {
                int rnd = (int)(Time.timePassed%1 * (walkfootstepPaths.length));
                this.footstepMediaPlayers[rnd].play();
                distanceTraveled = 0;
            }
            if(label != null)
                label.setText(this.position.toString());
    }
    
}
