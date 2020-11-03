
package Engine.Entity.AbstractEntity;
import Engine.Core.Game;
import javafx.geometry.Point2D;

//Merouane Issad

public abstract class Entity implements IEntity{
    protected String name; //all entities have a name
    protected Point2D position; //all entities have a position in the world

    @Override
    public void destroy() {
        //remove from Entity list
        Game.getCurrentLevel().removeEntity(this.name); //temporary code
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
