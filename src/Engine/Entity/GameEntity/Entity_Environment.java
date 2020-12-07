/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Entity.GameEntity;

import Engine.Entity.AbstractEntity.Entity;
import Engine.RaycastRenderer.Renderer;
import java.util.HashMap;
import javafx.geometry.Point2D;
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
    
    public Entity_Environment(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
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
    
    @Override
    public void start() {
        //start logic here
        Renderer.setEnvironment(this);
        this.active = false; //this entity never gets updated
    }

    @Override
    public void update() {
        //update logic here
    }
    
    @Override
    public void handleSignal(String signalName, Object[] arguments){
        switch(signalName) //new signals here
        {
            default:
                super.handleSignal(signalName, arguments);     
        }
    }
    
    //fog
    public boolean isFoggy(){ return foggy; }
    public Color getFogColor(){ return Color.BLUE; }
    public float getFogNearDistance(){ return fogNearDistance; }
    public float getFogFarDistance(){ return fogFarDistance; }
    
    //sky
    public boolean hasSky(){ return hasSky; }
    public Color getSkyColor(){ return skyColor; }
    
    //walls
    public float getWallHeight(){ return wallHeight; }
}
