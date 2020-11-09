package Engine.Entity.AbstractEntity;

import Engine.Core.Exceptions.EntityCreationException;
import Engine.Core.Game;
import java.util.HashMap;
import javafx.geometry.Point2D;

//Merouane Issad
public abstract class Entity implements IEntity{ //An entity is any object that moves, has behavior or affects the game in any way. By default a simple entity cannot be seen,
                                                 //meaning that it is not take in consideration by the renderer what so ever.
    
    protected String name; //all entities have a name, the name can never change and no entity can have the same name
    protected boolean active; //if false, this entity will not be updated
    protected Point2D position; //all entities have a position in the world

    public Entity(String name, Point2D position) {
        this.name = name;
        this.active = true;
        this.position = position;
    }
    
    public Entity(String name, boolean active, Point2D position) {
        this.name = name;
        this.active = active;
        this.position = position;
    }
    
    public Entity(HashMap<String, String> propertyMap)
    {
        this.name = propertyMap.get("name");
        this.active = Boolean.parseBoolean(propertyMap.get("active"));
        this.position = new Point2D(Float.parseFloat(propertyMap.get("posX")), Float.parseFloat(propertyMap.get("psoY")));
    }
    
    @Override
    public void destroy() { //remove from the current level Entity list by name

        Game.getCurrentLevel().removeEntity(this.name); //temporary code
    }
    
    public String getName() { //if you can't understand this one then I'm sorry...
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean getActive()
    {
        return this.active;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }
    
}
