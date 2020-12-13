/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Core.Exceptions.EntityCreationException;
import Engine.Entity.AbstractEntity.Entity;
import Engine.RaycastRenderer.Renderer;
import Engine.Util.Input;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;

/**
 *
 * @author child
 */
public class Entity_Environment extends Entity{
    
    //fog
    private boolean foggy;
    private Color fogColor;
    private float fogNearDistance;
    private float fogFarDistance;
    //sky
    private boolean hasSky;
    private Color skyColor;
    //walls
    private float wallHeight;
    
    //test code for presentation
    boolean fogKeyPressed = false;
    boolean ceilingKeyPressed = false;
    
    public Entity_Environment()
    {
        super("environment", Point2D.ZERO);
        foggy = false;
        fogColor = Color.BLUE;
        fogNearDistance = 1;
        fogFarDistance = 20;
        hasSky = true;
        skyColor = Color.AQUA;
        wallHeight=1;
    }
    
    public Entity_Environment(HashMap<String, Object> propertyMap) throws EntityCreationException
    {
        super(propertyMap);
        try{
            //fog
            this.foggy = Boolean.valueOf((String) propertyMap.get("foggy"));
            JSONArray colorArray = (JSONArray) propertyMap.get("fogcolor");
            this.fogColor = Color.color(
                            (double) colorArray.get(0),
                            (double) colorArray.get(1),
                            (double) colorArray.get(2));
            this.fogNearDistance = Float.valueOf((String) propertyMap.get("fog_near_distance"));
            this.fogFarDistance = Float.valueOf((String) propertyMap.get("fog_far_distance"));

            //sky
            this.hasSky = Boolean.valueOf((String) propertyMap.get("has_sky"));
            JSONArray skyColorArray = (JSONArray) propertyMap.get("skycolor");
            this.skyColor = Color.color(
                            (double) skyColorArray.get(0),
                            (double) skyColorArray.get(1),
                            (double) skyColorArray.get(2));
            //walls
            this.wallHeight = Float.valueOf((String) propertyMap.get("wallheight"));
        }
        catch(Exception e)
        {
            System.out.println(e);
            throw new EntityCreationException("could not initialize entity '"+this.name+"' with the given properties");
        }
    }
    
    @Override
    public void start() {
        //start logic here
        Renderer.setEnvironment(this);
        this.active = true; //this entity never gets updated
    }

    @Override
    public void update() {
        //this is test code for the presentation
        
        if(Input.keyPressed(KeyCode.M)){
            if(!fogKeyPressed)
            {
                fogKeyPressed = true;
                this.foggy = !this.foggy;
                this.refresh();
            }
        }
        else
        {
            fogKeyPressed = false;
        }
        
        if(Input.keyPressed(KeyCode.N)){
            if(!ceilingKeyPressed)
            {
                ceilingKeyPressed = true;
                this.hasSky = !this.hasSky;
                this.refresh();
            }
        }
        else
        {
            ceilingKeyPressed = false;
        }
        
        if(Input.keyPressed(KeyCode.B)){
            this.wallHeight = 2;
        }
        else
        {
            this.wallHeight = 1;
        }
    }
    
    public void refresh()
    {
        Renderer.setEnvironment(this);
    }
    
    @Override
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        switch(signalName) //new signals here
        {
            default:
                super.handleSignal(signalName, arguments);     
        }
    }
    
    //fog
    public boolean isFoggy(){ return foggy; }
    public Color getFogColor(){ return fogColor; }
    public float getFogNearDistance(){ return fogNearDistance; }
    public float getFogFarDistance(){ return fogFarDistance; }
    
    //sky
    public boolean hasSky(){ return hasSky; }
    public Color getSkyColor(){ return skyColor; }
    
    //walls
    public float getWallHeight(){ return wallHeight; }
}
