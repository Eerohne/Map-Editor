package Engine.Entity.AbstractEntity;

import Engine.Core.Exceptions.EntityCreationException;
import Engine.RaycastRenderer.Renderer;
import Engine.Util.RessourceManager.ResourceLoader;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;

//Merouane Issad
public abstract class SpriteEntity extends Entity{ //A type of entity that provides itself to the renderer, it is taken in consideration when rendering the game level (not the game UI)
    
    public Image texture;
    private float size;
    
    public SpriteEntity(String name, Point2D position, String texture, float size)
    {
        super(name, position);
        this.texture = ResourceLoader.loadImage("images/sprite1.png");
        this.size = size;
    }
    
    public SpriteEntity(HashMap<String, Object> propertyMap)
    {
        super(propertyMap);
        
        this.texture = ResourceLoader.loadImage((String) propertyMap.get("texture"));
        this.size = Float.parseFloat((String) propertyMap.get("size"));
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
    public void handleSignal(String signalName, ArrayList<Object> arguments){
        
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
    
    public float getSize(){return this.size;}
    
    public void setSize(float height){this.size = size;}
    
}
