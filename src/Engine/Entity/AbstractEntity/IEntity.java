package Engine.Entity.AbstractEntity;

//Merouane Issad
public interface IEntity {
    void start();   /*entity start method. This method is called the first time an entity is created in the level. It is generally used to initialize logic and properties
                      at the start of the level. The differance between it and a constructor is that this method is called once all entities are present in the level, which
                      avoid any null pointer errors (example, if an entity wants to access another entity but that entity is not created yet in the level.)*/
    
    void update();  /*entity update method. This method is called once every frame (meaning continuisly, as quickly as possible) to update the entitie's properties and logic.
                      Example, the player always need to know if it can move, so it continuisly checks for if the 'W' key is pressed. This logic would be in the update method.*/
    
    void destroy(); /*entity destoy method. This method is called when we want to get rid of the entity from the level. So the minimum thing to do in this method it for the entity 
                      to remove itself from the level entity list (the list used to update all entities). This behavior is described in the Entity abstract class.*/
}
