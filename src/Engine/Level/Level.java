/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Level;

import Engine.Entity.AbstractEntity.Entity;
import Engine.Entity.GameEntity.Entity_Player;
import Engine.Util.RessourceManager.ResourceLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

//Merouane Issad
public class Level {
    public String path;
    public int width, height;
    private int[][] levelData; //2d array with wall data
    private Map<Integer, PaletteEntry> palette; //Look-up table
    private Map<String, Entity> entities; //Entities stored by their name
    
    private Entity_Player playerEntity;
    //flags
    private boolean firstUpdate = true;
    
    public Level(){
        palette = new HashMap<Integer, PaletteEntry>();
        entities = new HashMap<String, Entity>();
    }
    
    public boolean isWall(int x, int y)
    {
        try{
            int index = getCellValue(x, y);
            return getPaletteEntry(index).getFlag() == 1;
        }
        catch(NullPointerException e)
        {
            return true;
        }
    }
    
    public Color getCellColor(int x, int y)
    {
        int index = getCellValue(x, y);
        try{
        getPaletteEntry(index);
        return getPaletteEntry(index).getColor();
        }
        catch(NullPointerException e)
        {
            System.out.println("palette entry "+index+" does not exist");
            return Color.RED;
        }
        //return null;
    }
    
    public Image getCellTexture(int x, int y)
    {
        int index = getCellValue(x, y);
        try{
            getPaletteEntry(index);
            return getPaletteEntry(index).getTexture();
        }
        catch(NullPointerException e)
        {
            return ResourceLoader.error_image;
        }
    }
    
    //LevelData code
    public void setLevelData(int[][] data)
    {
        this.width = data[0].length;
        this.height = data.length;
        this.levelData = data;
    }
    
    public int getCellValue(int x, int y)
    {
        try{
        return levelData[y][x];
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return 0;
        }
    }
    
    public Point2D checkCollision(Point2D position, Point2D dir, float radius)
    {
        Point2D newDirection = dir;
        for(int x = -1; x <= 1; x++)
        {
            for(int y = -1; y <= 1; y++)
            {
                int posX = (int) position.getX()+x;
                int posY = (int) position.getY()+y;
                if(posX>=0 && posX<width && posY >=0 && posY<height)
                {
                    if(isWall(posX, posY))
                    {
                        if(y !=0 && x==0) {//horizontal case
                            Point2D p;
                            if(y < 0){
                                p = new Point2D(position.getX(), Math.floor(position.getY()));
                                float distance = (float) p.distance(position);
                                if(distance <= radius){
                                    if(newDirection.getY() < 0)
                                        newDirection = new Point2D(newDirection.getX(), 0);
                                }
                            }else if(y >0){
                                p = new Point2D(position.getX(), Math.ceil(position.getY()));
                                float distance = (float) p.distance(position);
                                if(distance <= radius){
                                    if(newDirection.getY() > 0)
                                        newDirection = new Point2D(newDirection.getX(), 0);
                                }
                            }
                        }
                        else if(x !=0 && y==0) //horizontal case
                        {
                            Point2D p;
                            if(x < 0){
                                p = new Point2D(Math.floor(position.getX()), position.getY());
                                float distance = (float) p.distance(position);
                                if(distance <= radius){
                                    if(newDirection.getX() < 0)
                                        newDirection = new Point2D(0, newDirection.getY());
                                }
                            }else if(x >0){
                                p = new Point2D(Math.ceil(position.getX()), position.getY());
                                float distance = (float) p.distance(position);
                                if(distance <= radius){
                                    if(newDirection.getX() > 0)
                                        newDirection = new Point2D(0, newDirection.getY());
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if(isWall((int)position.getX(), (int)position.getY())) //this is a fail-safe in case the collision detection fails
        {
            System.out.println("inn");
            double x = position.getX()%1;
            if(x<=0.5)
                newDirection = new Point2D(-x, 0);
            else
                newDirection = new Point2D((1-x), 0);
            
            /*double y = position.getY()%1;
            if(x<=0.5)
                newDirection = newDirection.add(0, -y);
            else
                newDirection = newDirection.add(0, (1-y));*/
            newDirection = newDirection.multiply(100);
        }
            
        return newDirection;
    }
    
    //Palette code
    public PaletteEntry getPaletteEntry(int index)
    {
        return palette.get(index);
    }
    public void putPaletteEntry(int index, PaletteEntry data)
    {
        palette.put(index, data);
    }
    
    //Entities code
    public void addEntity(Entity entity)
    {
        entities.put(entity.getName(), entity);
        if(entity instanceof Entity_Player){ //store player in a global variable for easy access
            playerEntity = (Entity_Player)entity;
        }
        if(!firstUpdate) //right away call the start method of the entity if it has been created mid-game
            entity.start();
    }
    
    public Entity getEntity(String entityName)
    {
        return entities.get(entityName);
    }
    
    public void removeEntity(String entityName)
    {
        entities.remove(entityName);
    }
    
    public void update() //update all entities. If this is the first update, call the entities start method instead to initialize them
    {
        Iterator<Entity> it = entities.values().iterator();
        while(it.hasNext())
        {
            Entity entity = it.next();
            if(entity.getActive()){
                if(firstUpdate)
                    entity.start();
                else
                    entity.update();
            }
        }
        firstUpdate = false;
    }
    
    public Entity_Player getPlayer()
    {
        return playerEntity;
    }
}
