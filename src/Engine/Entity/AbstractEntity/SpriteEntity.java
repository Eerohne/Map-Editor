package Engine.Entity.AbstractEntity;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

//Merouane Issad
public abstract class SpriteEntity extends Entity{ //A type of entity that provides itself to the renderer, it is taken in consideration when rendering the game level (not the game UI)
    
    public Color color; //temp code until we get sprites working, for now they only have a color
    
    public SpriteEntity(String name, Point2D position, Color color)
    {
        super(name, position);
        this.color = color;
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
    
    public void addToRenderList() {
        //add to render list
    }
    
    public void removeFromRenderList() {
        //remove from render list
    }
    
}
