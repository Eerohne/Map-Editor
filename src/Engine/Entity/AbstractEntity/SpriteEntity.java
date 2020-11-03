
package Engine.Entity.AbstractEntity;

import javafx.scene.paint.Color;

//Merouane Issad

public abstract class SpriteEntity extends Entity{
    public Color color; //temp code until we get sprites working, for now they only have a color
    
    public void addToRenderList()
    {
        //add to render list
    }
    
    public void removeFromRenderList()
    {
        //remove from render list
    }
    
    @Override
    public void start() //on creation, we want all spriteentities to submit itself the renderer list
    {
        addToRenderList();
        System.out.println("entity sent to render list");
    }
    
    @Override
    public void destroy() {
        removeFromRenderList();
        super.destroy();
    }
    
}
