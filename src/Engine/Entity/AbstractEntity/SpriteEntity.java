package Engine.Entity.AbstractEntity;

import Engine.Core.Exceptions.EntityCreationException;
import Engine.RaycastRenderer.Renderer;
import Engine.Util.RessourceManager.RessourceLoader;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;

//Merouane Issad
public abstract class SpriteEntity extends Entity{ //A type of entity that provides itself to the renderer, it is taken in consideration when rendering the game level (not the game UI)
    
    public Image texture;
    public Color color; //temp code until we get sprites working, for now they only have a color
    
    public SpriteEntity(String name, Point2D position, Color color)
    {
        super(name, position);
        this.color = color;
        
        texture = new Image(RessourceLoader.ressourcePath+"images/sprite1.png", true);
    }
    
    public SpriteEntity(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        JSONArray colArray = (JSONArray) propertyMap.get("color");
        this.color = Color.rgb((int) ((double) colArray.get(0)), (int) ((double) colArray.get(0)), (int) ((double) colArray.get(0)));
        
        if(propertyMap.containsKey("height"))
            this.height = Float.parseFloat((String) propertyMap.get("height"));
        else
            this.height = 0;
        
        texture = RessourceLoader.loadImage("images/sprite1.png");
    }
    
    @Override
    public void start() { //on creation, we want all SpriteEntities to submit itself the renderer list
        addToRenderList();
    }
    
    @Override
    public void destroy() {
        removeFromRenderList();
        super.destroy();
    }
    
    @Override
    public void handleSignal(String signalName, Object[] arguments){
        
        switch(signalName)
        {
            default:
                super.handleSignal(signalName, arguments);
                
        }
    }
    
    public void addToRenderList() {
        Renderer.addEntity(this);
    }
    
    public void removeFromRenderList() {
        Renderer.removeEntity(name);
    }
    
    public Image getTexture(){return texture;}
    
}
