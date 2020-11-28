/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.RaycastRenderer;

import Engine.Core.Game;
import Engine.Entity.AbstractEntity.SpriteEntity;
import Engine.Entity.GameEntity.Entity_Player;
import Engine.Level.Level;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author Jeffrey
 */

/*  TODO FEATURES:
*   Render Level        +
*   Render Entities     +
*   Render Textures     -
*   Game Minimap        -
*/
public class Renderer {
    
    private static HashMap<String, SpriteEntity> spriteEntities = new HashMap<>();
    
    private static Canvas frame = new Canvas();
    private static GraphicsContext gc = frame.getGraphicsContext2D();
    private static double screenWidth = frame.getWidth(), screenHeight = frame.getHeight();
    
    private static Entity_Player player;
    private static float fov=70f; //default field of view (degrees)
    private static double viewD=8.0; //default view distance
    
    //LINE ADDED BY LOGITHSS
    public static float heightOffset = 0;
    
    private Renderer(){}
    
    public static void setPlayer(Entity_Player player){Renderer.player = player;}
    
    public static void setCanvas(Canvas canvas){
        frame = canvas;
        gc = canvas.getGraphicsContext2D();
        screenWidth = canvas.getWidth();
        screenHeight = canvas.getHeight();
    }
    
    
    
    public static void setEntityList(HashMap<String, SpriteEntity> list){spriteEntities = list;}
    public static void addEntity(SpriteEntity spriteEntity){spriteEntities.put(spriteEntity.getName(), spriteEntity);}
    public static void removeEntity(String name){spriteEntities.remove(name);}
    
    public static void setFov(float angdeg){fov = angdeg;} // set the field of view
    public static void setViewDistance(double dist){viewD = dist;} // set the view distance
    
    public static void resize(){
        screenWidth = frame.getWidth();
        screenHeight = frame.getHeight();
    }
    
    //renders one frame
    public static void render(){
        //change colors here
        gc.clearRect(0, 0, screenWidth, screenHeight);
        //ceiling
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, screenWidth, screenHeight);
        //floor
        gc.setFill(Color.GREEN);
        gc.fillRect(0, screenHeight/2.0, screenWidth, screenHeight/2.0);
        
        ArrayList<HitPoint> hPoints = renderLevel();
        
        renderEntities(hPoints);
    }
    
    //renders level
    private static ArrayList<HitPoint> renderLevel(){
        ArrayList<HitPoint> hPoints = new ArrayList((int) screenWidth);
        Level level = Game.getCurrentLevel();
        Point2D cam = player.getPosition();
        float camA = player.getRotation();
        
        final double tileX, tileY; //player grid position
        tileX = Math.floor(cam.getX());
        tileY = Math.floor(cam.getY());
        
        float rayA = camA - fov/2f*(float)(1.0-1.0/screenWidth);
        
        for(int r=0; r<screenWidth;r++){
            while (rayA<0 || rayA>=360) {                
                if(rayA < 0)  rayA+=360;
                if(rayA >=360)rayA-=360;
            }
            
            boolean upward, rightward;
            upward = rayA<180; rightward = (rayA<90 || rayA>270);
            
            final double tan = Math.tan(Math.toRadians(rayA));
            final double cotan = 1.0/tan;
            
            double stepX=0, stepY=0;
            
            Point2D rH = Point2D.ZERO, rV = Point2D.ZERO;
            //intersects with vertical
            if(rightward){//looking right (+x)
                rV = rV.add(tileX+1, cam.getY());
                rV = rV.subtract(0, (cam.getX()-rV.getX())*tan);
                stepX = 1; stepY = tan;
            }
            if(!rightward){//looking left (-x)
                rV = new Point2D(tileX  , cam.getY());
                if(cam.getX() == tileX)rV = rV.add(-1,0);
                rV = rV.subtract(0, (cam.getX()-rV.getX())*tan);
                stepX = -1; stepY = -tan;
            }
            if(tan>80.0 || tan<-80.0){//looking directly up or down
                rV = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            }
            
            Color v = Color.TRANSPARENT;
            for(int i=0;i<viewD;i++){
                int x = (int)Math.floor(rV.getX());
                int y = (int)Math.floor(rV.getY());
                if(x>=level.width || y>= level.height || x<0 || y<0)break;
                if(level.isWall(x-1, y) && !rightward){v=level.getCellColor(x-1, y);break;}
                if(level.isWall(x, y)){v=level.getCellColor(x, y);break;}
                rV = rV.add(stepX, stepY);
                if(i+1.0==viewD){v=Color.WHITESMOKE;rV = cam.add(viewD*Math.cos(Math.toRadians(rayA)), viewD*Math.sin(Math.toRadians(rayA)));}
            }
            
            //intersects with horizontal
            stepX =0; stepY=0;
            if(upward){//looking up (+y)
                rH = rH.add(cam.getX(), tileY+1);
                rH = rH.subtract((cam.getY()-rH.getY())*cotan, 0);
                stepX = cotan; stepY = 1;
            }
            if(!upward){//looking down (-y)
                rH = rH.add(cam.getX(), tileY);
                if(cam.getY() == tileY)rH = rH.add(0, -1);
                rH = rH.subtract((cam.getY()-rH.getY())*cotan, 0);
                stepX = -cotan; stepY = -1;
            }
            if(cotan>80.0 || cotan <-80.0){//looking directly left or right
                rH = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            }
            
            Color h = Color.TRANSPARENT;
            for(int i=0;i<viewD;i++){
                int x = (int)Math.floor(rH.getX());
                int y = (int)Math.floor(rH.getY());
                if(x>=level.width || y>= level.height || x<0 || y<0)break;
                if(level.isWall(x, y-1) && !upward){h = level.getCellColor(x, y-1);break;}
                if(level.isWall(x, y)){h = level.getCellColor(x, y);break;}
                rH = rH.add(stepX, stepY);
                if(i+1.0==viewD){h = Color.WHITESMOKE;rH = cam.add(viewD*Math.cos(Math.toRadians(rayA)), viewD*Math.sin(Math.toRadians(rayA)));}
            }
            
            double hLength = cam.distance(rH);
            double vLength = cam.distance(rV);
            if(rH.equals(rV)){
                drawWallLine(r, viewD*Math.cos(Math.toRadians(rayA-camA)), h);
                hPoints.add(new HitPoint(rH, h));
            }
            else if(hLength<vLength){
                double dist = hLength*Math.cos(Math.toRadians(rayA-camA));
                drawWallLine(r, dist, h);
                hPoints.add(new HitPoint(rH, h));
            }
            else{
                double dist = vLength*Math.cos(Math.toRadians(rayA-camA));
                drawWallLine(r, dist, v.darker());
                hPoints.add(new HitPoint(rV, v.darker()));
            }
            rayA += (fov/screenWidth);
        }
        return hPoints;
    }
    
    private static void renderEntities(ArrayList<HitPoint> hPoints){
        Point2D cam = player.getPosition();
        float camA = player.getRotation();
        final Point2D dir = new Point2D(Math.cos(Math.toRadians(camA)), Math.sin(Math.toRadians(camA)));
        PriorityQueue<SpriteEntity> sprites = new PriorityQueue(new EntityDistanceComparator());
        
        //sort entities by distance to player
        for(String name: spriteEntities.keySet()){
            SpriteEntity entity = spriteEntities.get(name);
            sprites.offer(entity);
        }
        
        //draw entities
        for(SpriteEntity e: sprites)
        {
            Point2D ePos = e.getPosition().subtract(cam); //vector from player to entity
            if(dir.angle(ePos)<(0.1+fov/2.0) && ePos.magnitude()<viewD){ //if entity is within the player's fov and within view range
                
                double fovR = -Math.toRadians(fov/2f);
                Point2D fovLeft = new Point2D( //vector representing the left edge of the fov triangle
                        dir.getX()*Math.cos(fovR)-dir.getY()*Math.sin(fovR),
                        dir.getX()*Math.sin(fovR)+dir.getY()*Math.cos(fovR)
                );
                
                Image sprite = e.getTexture();
                double dist = ePos.magnitude() +.25;
                double screenPos = screenWidth*fovLeft.angle(ePos)/fov;
                double scale = (e.getSize()*screenHeight/sprite.getHeight())/dist;
                double height = scale*sprite.getHeight();
                double width = scale*sprite.getWidth();
                
                
                boolean hidden = true; //if the entity is completely obscured by a wall
                for(int i = (int)(screenPos-(width/2.0)); i<(screenPos+(width/2.0)) && i<screenWidth; i++){
                    if(i<0){i=0;}
                    double pDist = cam.distance(hPoints.get(i));
                    double eDist = cam.distance(e.getPosition());
                    if(eDist<pDist){hidden = false;break;}
                }
                if(!hidden){
                    double relX =screenPos-(width/2.0), relY = (screenHeight-height)/2.0; //coordinates of the top left corner of the image
                    relY -= (e.getHeight()-0.5)*screenHeight/dist; //height offsett
                    gc.drawImage(sprite, relX, relY, width, height );

                    //draw walls that are in front of the entity
                    for(int i = (int)(screenPos-(width/2.0)); i<(screenPos+(width/2.0)) && i<screenWidth; i++)
                    {
                        if(i<0){i=0;}
                        double pDist = cam.distance(hPoints.get(i));
                        double eDist = cam.distance(e.getPosition());
                        if(pDist<eDist){
                            double rayA = dir.angle(hPoints.get(i));
                            drawWallLine(i, pDist, hPoints.get(i).getColor());
                        }
                    }
                    
                }
            }
        }
        
    }

    //draws the line representing a slice of a wall
    private static void drawWallLine(int x, double distance, Color color){
        double maxHeight = screenHeight;
        double height = (maxHeight/(distance +.25));
        double lineTop = ((screenHeight-height)/2.0);
        
        //LINE ADDED BY LOGITHSS
        lineTop += -(heightOffset);
        
        gc.setFill(color);
        gc.fillRect(x, lineTop, 1, height);
    }

    static class EntityDistanceComparator implements Comparator<SpriteEntity>{

        @Override
        public int compare(SpriteEntity o1, SpriteEntity o2) {
            Point2D cam = Game.getCurrentLevel().getPlayer().getPosition();
            double d1 = cam.distance(o1.getPosition());
            double d2 = cam.distance(o2.getPosition());
            
            if(d1<d2) return 1;
            if(d1>d2) return -1;
            return 0;
        }

    }
    
}
class HitPoint extends Point2D{
    final private Color color;
    protected HitPoint(Point2D point, Color color){
        super(point.getX(), point.getY());
        this.color = color;
    }
    protected Color getColor(){return this.color;}
}
