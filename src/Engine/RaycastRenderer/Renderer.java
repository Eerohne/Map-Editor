/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.RaycastRenderer;

import Engine.Core.Game;
import Engine.Entity.AbstractEntity.SpriteEntity;
import Engine.Entity.GameEntity.Entity_Environment;
import Engine.Entity.AbstractEntity.Entity_Player;
import Engine.Level.Level;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Team Optik
 * @author Jeffrey Gan
 */

public class Renderer {
    private Renderer(){}
    
    private static Canvas frame = new Canvas();
    private static GraphicsContext gc = frame.getGraphicsContext2D();
    private static double screenWidth = frame.getWidth(), screenHeight = frame.getHeight();
    
    private static Entity_Player player;
    private static Entity_Environment env;
    
    private static HashMap<String, SpriteEntity> spriteEntities = new HashMap<>();
    
    private static float fov=70f; //default field of view (degrees)
    private static double viewD=32.0; //default view distance (determines the number of ray step calculations)
    private static int res = 3; //resolution (determines the increment of loops) (bigger res means lower quality image)
    
    public static void setCanvas(Canvas canvas){
        frame = canvas;
        gc = canvas.getGraphicsContext2D();
        resize();
    }
    public static void resize(){
        screenWidth = frame.getWidth();
        screenHeight = frame.getHeight();
    }
    
    public static void setPlayer(Entity_Player player){Renderer.player = player;}
    public static void setEnvironment(Entity_Environment env){Renderer.env=env;};
    
    public static void setEntityList(HashMap<String, SpriteEntity> list){spriteEntities = list;}
    public static void addEntity(SpriteEntity spriteEntity){spriteEntities.put(spriteEntity.getName(), spriteEntity);}
    public static void removeEntity(String name){spriteEntities.remove(name);}
    
    public static void setFov(float angdeg){fov = angdeg;} // set the field of view
    public static void setViewDistance(double dist){viewD = dist;} // set the view distance
    public static void setResolution(int resolution){res = resolution;} //set the resolution
    
    private static boolean doSkip = false; //render the ceiling and floor every other frame
    public static void setDoSkip(boolean doSkip) {Renderer.doSkip = doSkip;}
    
    private static boolean other = true;
    
    //renders one frame
    public static void render(){
        
        ArrayList<HitPoint> hPoints = getHitPoints();
        
        if(doSkip){
            if(other){
                other = false;
                gc.clearRect(0, 0, screenWidth, screenHeight);
                renderFloorCeiling(hPoints);
            }
            else{other = true;}
        }
        else{
            gc.clearRect(0, 0, screenWidth, screenHeight);
            renderFloorCeiling(hPoints);
        }
        
        renderWalls(hPoints);
        ArrayList<VisibleSprite> sprites = renderEntities(hPoints);
        
        if(env.isFoggy()) renderFog(hPoints, sprites);
    }
    
    //calculates all the of the points of intersection with a wall
    private static ArrayList<HitPoint> getHitPoints(){
        ArrayList<HitPoint> hPoints = new ArrayList((int) screenWidth);
        Level level = Game.getCurrentLevel();
        Point2D cam = player.getPosition();
        float camA = player.getRotation();
        
        final double tileX, tileY; //player grid position
        tileX = Math.floor(cam.getX());
        tileY = Math.floor(cam.getY());
        
        //creates all the hit points
        for(int r=0; r<screenWidth;r+=res){
            //angle
            double rayA = Math.toDegrees(Math.atan
            ((
               2.0*Math.tan( Math.toRadians(fov/2.0) )  
               *( (r/screenWidth)-0.5 )
            )));
            rayA += camA;
            
            while (rayA<0 || rayA>=360) {                
                if(rayA < 0)  rayA+=360;
                if(rayA >=360)rayA-=360;
            }
            
            boolean upward, rightward;
            upward = rayA<180; rightward = (rayA<90 || rayA>270);
            
            rayA = Math.toRadians(rayA);
            final double tan = Math.tan(rayA);
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
            
            HitPoint v = HitPoint.ZERO;
            for(int i=0;i<viewD;i++){
                int x = (int)Math.floor(rV.getX());
                int y = (int)Math.floor(rV.getY());
                if(x>=level.width || y>= level.height || x<0 || y<0){
                    v = new HitPoint(cam, rayA, viewD);
                    break;
                }
                
                if(level.isWall(x-1, y) && !rightward){ v = new HitPoint(rV, 3, x-1, y);break;}
                if(level.isWall(x, y)){                 v = new HitPoint(rV, 4, x, y)  ;break;}
                rV = rV.add(stepX, stepY);
                if(i+1==viewD){
                    v = new HitPoint(cam, rayA, viewD);
                }
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
            
            HitPoint h = HitPoint.ZERO;
            for(int i=0;i<viewD;i++){
                int x = (int)Math.floor(rH.getX());
                int y = (int)Math.floor(rH.getY());
                if(x>=level.width || y>= level.height || x<0 || y<0){
                    h = new HitPoint(cam, rayA, viewD);
                    break;
                }
                
                if(level.isWall(x, y-1) && !upward){h = new HitPoint(rH, 1, x, y-1);break;}
                if(level.isWall(x, y)){             h = new HitPoint(rH, 2, x, y)  ;break;}
                
                rH = rH.add(stepX, stepY);
                if(i==viewD){
                    h = new HitPoint(cam, rayA, viewD);
                }
            }
            
            double hLength = cam.distance(rH);
            double vLength = cam.distance(rV);
            if(rH.equals(rV)){
                hPoints.add(h);
            }
            else if(hLength<vLength){
                hPoints.add(h);
            }
            else{
                hPoints.add(v);
            }
            
        }
        return hPoints;
    }
    
    //renders floor/ceiling
    private static void renderFloorCeiling(ArrayList<HitPoint> hPoints){
        Level level = Game.getCurrentLevel();
        double camA = Math.toRadians(player.getRotation());
        double sin = Math.sin(camA);
        double cos = Math.cos(camA);
        
        //vector perpendicular (90 deg rotated) to the dir vertor
        Point2D perpDir = new Point2D(-sin, cos);
        
        //the hit point furthest away from the player (smallest height)
        Point2D toWall = Point2D.ZERO;
        for(HitPoint hPoint: hPoints){
            if (toWall.magnitude()<hPoint.subtract(player.getPosition()).magnitude()) {
                toWall = hPoint.subtract(player.getPosition());
            }
        }
        
        double dist = toWall.magnitude();
        double lineTop = 0.5*(screenHeight-screenHeight/dist) + player.getHeight()/dist;
        
        double y = 0.0; //vertical pixel position
        
        if(env.hasSky()){ //color the sky, skip to floor
            y = lineTop+screenHeight/dist;
            gc.setFill(env.getSkyColor());
            gc.fillRect(0, 0, screenWidth, screenHeight/2.0);
        }
        
        while( y<screenHeight ) //for every horizontal line of pixels on the screen
        { 
            //skip the smallest wall
            if(y>=lineTop && y<(lineTop+res)) y+=screenHeight/dist;
            
            
            double ry = y/screenHeight;             //position on screen (0 -> 1)
            double fdist = Math.abs(1/(ry-0.5) );   //distance on the map plane from the player to a point in the direction of the player (flat distance)
            double ph = (0.5-player.getHeight());   //player height, centered on horizon
           
            if(ry<0.5){ //top half of the screen
                fdist*=(ph+env.getWallHeight()-1);
            }
            else{ //bottom half of the screen
                fdist*=(1-ph);
            }
            
            if( (env.isFoggy() && fdist<env.getFogFarDistance()) || (!env.isFoggy() && fdist<=(viewD+0.5)) )
            {
                //point on grid that is fdist from the player, in the direction of the player
                Point2D gridPos = player.getPosition().add(fdist*cos, fdist*sin);
                //leftmost point on the grid that will be rendered, line from gridLeft to gridPos is perpendicular to player's direction
                Point2D gridLeft = gridPos.subtract(perpDir.multiply(fdist*Math.tan(Math.toRadians(fov/2.0))));
                //step along the perpendicular line
                Point2D perpStep = perpDir.multiply(res*2*gridLeft.distance(gridPos)/screenWidth );

                gridPos = gridLeft;

                for(int x=0; x<screenWidth; x+=res){
                    //cell position
                    int cx = (int)gridPos.getX(), cy = (int)gridPos.getY();
                    
                    //in bounds and isn't a wall
                    if( !(cx<0 || cy<0 || cx>=level.width || cy>=level.height) && !level.isWall(cx, cy) )
                    {
                        Image texture = level.getCellTexture(cx, cy);
                        int tx = (int)(texture.getWidth() *(Math.abs(gridPos.getX()%1.0)));
                        int ty = (int)(texture.getHeight()*(Math.abs(gridPos.getY()%1.0)));
                        
                        gc.drawImage(texture, tx, ty, 1, 1, x, y, res, res);
                        
                    }
                    
                    //increment
                    gridPos = gridPos.add(perpStep);
                }
            }
            y+=res;
        }
        
        
    }
    
    //renders level
    private static void renderWalls(ArrayList<HitPoint> hPoints){
        for(int i=0;i<screenWidth/res;i++){
            drawWallLine(i, hPoints.get(i));
        }
    }
    
    //renders entities and returns the list of entities that aren't behind fog
    private static ArrayList<VisibleSprite> renderEntities(ArrayList<HitPoint> hPoints){
        Point2D cam = player.getPosition();
        float camA = player.getRotation();
        final Point2D dir = new Point2D(Math.cos(Math.toRadians(camA)), Math.sin(Math.toRadians(camA)));
        PriorityQueue<SpriteEntity> sprites = new PriorityQueue(new EntityDistanceComparator());
        
        //entities that aren't in fog
        ArrayList<VisibleSprite> fogless = new ArrayList<>();
        
        //sort entities by distance to player
        spriteEntities.keySet().forEach((name) -> {
            sprites.offer(spriteEntities.get(name));
        });
        
        //draw entities
        while(!sprites.isEmpty())
        {
            SpriteEntity e = sprites.poll(); //entity
            Point2D toEntity = e.getPosition().subtract(cam); //vector from player to entity
            
            //if entity is within the player's fov and within view range
            if(dir.angle(toEntity)<(fov/2) && (toEntity.magnitude()<viewD || (env.isFoggy() && toEntity.magnitude()<=env.getFogFarDistance())) )
            { 
                
                double fovRad = -Math.toRadians(fov/2f);
                Point2D fovLeft = new Point2D( //vector representing the left edge of the fov 
                        dir.getX()*Math.cos(fovRad) - dir.getY()*Math.sin(fovRad),
                        dir.getX()*Math.sin(fovRad) + dir.getY()*Math.cos(fovRad)
                );
                
                Image sprite = e.getTexture(); //sprite image
                
                double dist = toEntity.magnitude()*Math.cos(Math.toRadians(dir.angle(toEntity))); //distance of the entity from the player
                double h    = screenHeight/dist; //height of a wall, used to scale other values with distance
                
                double scale    = e.getSize()*h/sprite.getHeight(); //used for scaling the sprite
                double height   = scale*sprite.getHeight(); //image height
                double width    = scale*sprite.getWidth(); //image width
                
                //position on screen of the leftmost pixel of the sprite
                double screenXPos = (screenWidth*fovLeft.angle(toEntity)/fov) - (width/2.0);
                
                //check if the entity is completely obscured by a wall
                boolean hidden = true; 
                for(int i = (int)(screenXPos/res); i<(screenXPos+width)/res && i<hPoints.size(); i++){
                    if(i<0){i=0;}
                    double eDist = cam.distance(e.getPosition());
                    
                    //if the entity is behind opaque fog
                    if( env.isFoggy() && eDist>env.getFogFarDistance())break;
                    
                    double pDist = cam.distance(hPoints.get(i));
                    
                    //if the entity is in front of a wall
                    if(eDist<pDist){ hidden = false; break; }
                }
                
                if(!hidden)
                {
                    //position of the top pixel of the sprite
                    double screenYPos = (screenHeight-height)/2.0; //center
                    screenYPos -= h*(e.getHeight()-0.5); //height offset of entity
                    screenYPos += h*(player.getHeight()); //height offset of player
                    
                    //draw the sprite
                    gc.drawImage(sprite, screenXPos, screenYPos, width, height);
                    
                    //pixels of the sprite that are hidden
                    int pointer = 0;
                    int startHide = -1, endHide = -1;
                    
                    //draw walls that are in front of the entity
                    for(int i = (int)(screenXPos/res); i<(screenXPos+width)/res && i<hPoints.size(); i++)
                    {
                        if(i<0){i=0;}
                        double wDist = cam.distance(hPoints.get(i)); //wall distance
                        double eDist = cam.distance(e.getPosition());//entity distance
                        
                        if(wDist<eDist){
                            
                            drawWallLine(i, hPoints.get(i));
                            
                            if(startHide == -1){
                                startHide = pointer;
                            }
                            else{
                                endHide = pointer;
                            }
                            
                        }
                        pointer+=res;
                    }
                    
                    //adds entities that are in front of the nearest fog layer
                    if(env.isFoggy()&&dist+0.01<env.getFogNearDistance())
                    {
                        switch(startHide)
                        {
                            
                            case(-1): //not obscured
                            {
                                fogless.add(new VisibleSprite(sprite, screenXPos, screenYPos, width, height));
                                break;
                            } 
                            case( 0): //right side of sprite is visible, left side hidden
                            {
                                screenXPos += endHide+1;
                                double spriteStart = sprite.getWidth()*(endHide)/width;
                                width -= endHide;

                                fogless.add(new VisibleSprite
                                        (sprite, spriteStart, 0, sprite.getWidth()-spriteStart, sprite.getHeight(),
                                        screenXPos, screenYPos, width, height));
                                
                                break;
                            }
                            default: //left side of sprite is visible, right side hidden
                            {
                                if(endHide == -1)endHide = startHide;
                                double hideLength = endHide-startHide;
                                double spriteWidth = sprite.getWidth()*(width-hideLength)/width;
                                width -= hideLength+1;

                                fogless.add(new VisibleSprite
                                        (sprite, 0, 0, spriteWidth, sprite.getHeight(),
                                        screenXPos, screenYPos, width, height));
                                
                            }
                        }
                    }
                    
                }
                
            }
        }
        
        return fogless;
    }

    //draws the line representing a slice of a wall
    private static void drawWallLine(int r, HitPoint hPoint){
        
        Point2D toWall = hPoint.subtract(player.getPosition());
        
        int x = r*res;
        
        //does the hitPoint represent opaque fog?
        boolean isFog = false;
        
        double distance = toWall.magnitude();
        
        //if is a no-hit or if the hit is in fog
        if( hPoint.getType()==0 || (env.isFoggy() && (distance-0.001)>=env.getFogFarDistance()) || (distance-0.001)>=viewD)
        {
            isFog = true;
            if(env.isFoggy()){
                distance = env.getFogFarDistance();
            }
            else{
                distance = viewD;
            }
        }
        
        double fixDist = Math.cos(Math.atan
            ((
               2.0*Math.tan( Math.toRadians(fov/2.0) )  
               *( (x/screenWidth)-0.5 )
            )));
        
        distance*=fixDist;
        
        double height = screenHeight/distance;
        
        double lineTop = 0.5*(screenHeight-height);
        lineTop += height*(player.getHeight());

        
        //if not opaque fog (is a wall)
        if(!isFog)
        {
            Image texture = Game.getCurrentLevel().getCellTexture(hPoint.getXIndex(), hPoint.getYIndex());
            int tx = 0; //x position on the texture
            switch(hPoint.getType()){
                case(1): tx = (int)(texture.getWidth()*(hPoint.getX()%1))   ; break; //horizontal hit, image normal
                case(2): tx = (int)(texture.getWidth()*(1-hPoint.getX()%1)) ; break; //horizontal hit, image inverted
                case(3): tx = (int)(texture.getWidth()*(1-hPoint.getY()%1)) ; break; //vertical hit,   image inverted
                case(4): tx = (int)(texture.getWidth()*(hPoint.getY()%1))   ; break; //vertical hit,   image normal
            }
            
            //draws the wall
            if(env.getWallHeight() == 1.0){
                gc.drawImage(texture, tx, 0, 1, texture.getHeight(), x, lineTop, res, height );
            }
            else{
                for(int y=0;y<env.getWallHeight();y++){
                    gc.drawImage(texture, tx, 0, 1, texture.getHeight(),x, lineTop-height*y, res, height);
                }
            }
        }
        else
        {
            //draws the opaque fog
            gc.setFill(env.getFogColor());
            gc.fillRect(x, lineTop-height*(env.getWallHeight()-1), res, height*env.getWallHeight());
        }
        
    }

    //draws the transparent fog
    private static void renderFog(ArrayList<HitPoint> hPoints, ArrayList<VisibleSprite> sprites){
        
        for(int x=0; x<hPoints.size(); x++){
            
            HitPoint hPoint = hPoints.get(x);
            Point2D toWall = hPoint.subtract(player.getPosition());

            double fixDist = Math.cos(Math.atan
            ((
               2.0*Math.tan( Math.toRadians(fov/2.0) )  
               *( (res*x/screenWidth)-0.5 )
            )));
            
            double distance = toWall.magnitude();
            double tFogDist = env.getFogFarDistance();
            
            //if the wall is closer than the fog
            if(distance<tFogDist)tFogDist = Math.ceil(distance);
            
            while (tFogDist>env.getFogNearDistance()) {                
                tFogDist-=1;
                
                distance = tFogDist*fixDist;
        
                double height = screenHeight/distance;
                double lineTop = 0.5*(screenHeight-height) + height*(player.getHeight());
                
                //transparent color of the fog
                gc.setFill(env.getFogColor().deriveColor(1, 1, 1, 0.5));
                
                if(env.getWallHeight() == 1.0){
                    gc.fillRect(x*res, lineTop, res, height);
                }
                else{
                    gc.fillRect(x*res, lineTop-height*(env.getWallHeight()-1), res, height*env.getWallHeight());
                }
                
            }
            
        }
        //draws the entity sprites that aren't behind any fog
        sprites.forEach((s) -> {
            gc.drawImage(s.sprite, s.sx, s.sy, s.sw, s.sh, s.dx, s.dy, s.dw, s.dh);
        });
    }
    
    static class EntityDistanceComparator implements Comparator<SpriteEntity>{
        //furthest first, closest last
        @Override
        public int compare(SpriteEntity o1, SpriteEntity o2) {
            Point2D cam = player.getPosition();
            double d1 = cam.distance(o1.getPosition());
            double d2 = cam.distance(o2.getPosition());
            
            if(d1<d2) return 1;
            if(d1>d2) return -1;
            return 0;
        }
    }
    
}

//object to retain information regarding a ray-wall intersect
class HitPoint extends Point2D{
    final private int xIn, yIn; //index to access
    final private int type; //type of wall (0: fog, 1:hor, 2: hor inv, 3:ver inv, 4: ver
    
    protected HitPoint(Point2D point, int type, int xIndex, int yIndex){
        super(point.getX(), point.getY());
        this.type = type;
        this.xIn = xIndex; this.yIn = yIndex;
    }
    
    //to simplify creating a fog hit point
    protected HitPoint(Point2D player, double rad, double dist){
        super(player.getX()+dist*Math.cos(rad), player.getY()+dist*Math.sin(rad));
        this.type = 0;
        this.xIn = -1; this.yIn = -1;
    }
    
    
    final static public HitPoint ZERO = new HitPoint(Point2D.ZERO, 0, -1, -1);
    
    public int getXIndex() {return xIn;}
    public int getYIndex() {return yIn;}
    public int getType() {return type;}
}

//object to simplify drawning entities that are not in fog
class VisibleSprite{
    final protected Image sprite;
    final protected double sx, sy, sw, sh;
    final protected double dx, dy, dw, dh;
    
    //if the sprite is partially hidden
    protected VisibleSprite(Image sprite, double sx, double sy, double sw, double sh, double dx, double dy, double dw, double dh) {
        this.sprite = sprite;
        this.sx = sx;
        this.sy = sy;
        this.sw = sw;
        this.sh = sh;
        this.dx = dx;
        this.dy = dy;
        this.dw = dw;
        this.dh = dh;
    }
    
    //if the entire sprite is visible
    public VisibleSprite(Image sprite, double dx, double dy, double dw, double dh) {
        this(sprite, 0, 0, sprite.getWidth(), sprite.getHeight(), dx, dy, dw, dh);
    }

}