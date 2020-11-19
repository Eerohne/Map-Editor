/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Game;
import Engine.RaycastRenderer.Renderer;
import Engine.Util.Input;
import Engine.Util.Time;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

/**
 *
 * @author child
 */
public class Entity_Player_Base extends Entity_Player{

    protected float playerSpeed, walkSpeed, runSpeed;
    
    private Label label;
    
    public Entity_Player_Base(String name, Point2D position, float rotation, float walkSpeed, float runSpeed) {
        super(name, position, rotation);
        this.walkSpeed = walkSpeed;
        this.runSpeed = runSpeed;
    }
    
    public Entity_Player_Base(HashMap<String, String> propertyMap)
    {
        super(propertyMap);
        
        this.walkSpeed = Float.parseFloat(propertyMap.get("walkspeed"));
        this.runSpeed = Float.parseFloat(propertyMap.get("runspeed"));
    }

    @Override
    public void start() {
        this.playerSpeed = walkSpeed;
        AnchorPane display = Game.getWindowManager().getIngameDisplay();
        label = new Label();
        label.setFont(Font.font("Cambria", 20));
        display.getChildren().add(label);
        display.setTopAnchor(label, 10.0);
    }

    @Override
    public void update() {
        if(Input.keyPressed(KeyCode.W))
            this.position = this.position.add(playerSpeed* Math.cos(Math.toRadians(this.rotation))* Time.deltaTime, playerSpeed* Math.sin(Math.toRadians(this.rotation)) *Time.deltaTime);
        if(Input.keyPressed(KeyCode.S))
            this.position = this.position.add(playerSpeed* -Math.cos(Math.toRadians(this.rotation))* Time.deltaTime, playerSpeed* -Math.sin(Math.toRadians(this.rotation)) *Time.deltaTime);
        if(Input.keyPressed(KeyCode.A))
            this.position = this.position.add(playerSpeed* Math.sin(Math.toRadians(this.rotation))* Time.deltaTime, playerSpeed* -Math.cos(Math.toRadians(this.rotation)) *Time.deltaTime);
        if(Input.keyPressed(KeyCode.D))
            this.position = this.position.add(playerSpeed* -Math.sin(Math.toRadians(this.rotation))* Time.deltaTime, playerSpeed* Math.cos(Math.toRadians(this.rotation)) *Time.deltaTime);
        
        if(Input.keyPressed(KeyCode.LEFT))
            this.rotation -= 100 * Time.deltaTime;
        if(Input.keyPressed(KeyCode.RIGHT))
            this.rotation += 100 * Time.deltaTime;
        
        if(Input.keyPressed(KeyCode.SHIFT))
            playerSpeed = runSpeed;
        else
            playerSpeed = walkSpeed;
        
        Renderer.cam = this.position;
        Renderer.camA = this.rotation;
        
        label.setText(this.position.toString());
    }
    
}
