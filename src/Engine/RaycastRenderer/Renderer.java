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
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Jeffrey
 */

/*  TODO FEATURES:
*   Render Level        +
*   Render Entities     started
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
    
    //for enabling test messages
    public static boolean test = false, pV = false, pH = false;
    
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
        
        ArrayList<Point2D> hPoints = renderLevel();
        
        renderEntities(hPoints);
    }
    
    //renders level
    private static ArrayList<Point2D> renderLevel(){
        ArrayList<Point2D> hPoints = new ArrayList<>((int)screenWidth);
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
            
            if(pV)System.out.printf("%3.3f rV: %1.4f, %1.4f %n",rayA, rV.getX(), rV.getY()); //prints the point of first hit with a vertical line
            Color v = Color.TRANSPARENT;
            for(int i=0;i<8;i++){
                int x = (int)Math.floor(rV.getX());
                int y = (int)Math.floor(rV.getY());
                if(x>=level.width || y>= level.height)break;
                if(level.isWall(y, x-1) && !rightward){v=level.getCellColor(y, x-1);break;}
                if(level.isWall(y, x)){v=level.getCellColor(y, x);break;}
                rV = rV.add(stepX, stepY);
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
            
            if(pH)System.out.printf("%3.3f rH: %1.4f, %1.4f %n",rayA, rH.getX(), rH.getY());//prints the point of first hit with a horizontal line
            Color h = Color.TRANSPARENT;
            for(int i=0;i<8;i++){
                int x = (int)Math.floor(rH.getX());
                int y = (int)Math.floor(rH.getY());
                if(x>=level.width || y>= level.height)break;
                if(level.isWall(y-1, x) && !upward){h = level.getCellColor(y-1, x);break;}
                if(level.isWall(y, x)){h = level.getCellColor(y, x);break;}
                rH = rH.add(stepX, stepY);
            }
            
            double hLength = rH.subtract(cam).magnitude();
            double vLength = rV.subtract(cam).magnitude();
            
            // createLine() is not to be used when running the game
            if(hLength<vLength && hLength!=0){
                double dist = hLength*Math.cos(Math.toRadians(rayA-camA));
                drawWallLine(r, dist, h);
//                if(test)MiniMap.createLine(rH, h.brighter());
            }else{
                double dist = vLength*Math.cos(Math.toRadians(rayA-camA));
                drawWallLine(r, dist, v.darker());
//                if(test)MiniMap.createLine(rV, v.darker());
            }
            rayA += (fov/screenWidth);
        }
        return hPoints;
    }
    private static void renderEntities(ArrayList<Point2D> hPoints){
        Point2D cam = player.getPosition();
        float camA = player.getRotation();
        
        Point2D dir = new Point2D(Math.cos(Math.toRadians(camA)), Math.sin(Math.toRadians(camA)));
        spriteEntities.forEach(((k, e) -> {
            Point2D ePos = e.getPosition().subtract(cam); //vector from player to entity
            if(dir.angle(ePos)<=(double)(fov/2f)){
                //Image sprite = e.getTexture();
                double dist = ePos.magnitude();
                double scPos = screenWidth*dir.angle(ePos)/fov;
                double relX, relY; //relX = scPos - (entityWidth/2), relY = (screenHeight-entityHeight)/2;
                
                //gc.drawImage(sprite, relX, relY, sprite/dist , sprite/dist );
                
            }
        }));
        
    }
    
    private static Color getColor(int id){
        switch(id){
            case 0: return Color.TRANSPARENT;
            case 1: return Color.GRAY;
            case 2: return Color.GOLD;
            case 3: return Color.VIOLET;
            case 4: return Color.BLUEVIOLET;
            default: return Color.RED; //indicates error
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
/*    
    static class MiniMap{//minimap that shows the level and the rays (for debugging)
        private final static GridPane grid = new GridPane(),gridLines = new GridPane();
        private final static Pane rayPane = new Pane(), layer = new Pane();
        public static StackPane minimap = new StackPane(layer, grid, rayPane, gridLines);
        private static int sq = (int)screenHeight/mapY;//size of 1 grid square, is used for scaling
        private static Point2D cam = player.getPosition();
        private static float camA = player.getRotation();
        
        //generate the minimap
        public static void generate(){
            
            rayPane.getChildren().clear();
            createGrid();
            //create a triangle that shows a 90 deg fov
            Polygon fov3 = new Polygon();
            fov3.getPoints().addAll(0.0, 0.0, (double)sq*mapX, (double)sq*mapY, (double)sq*mapX, (double)-sq*mapY);
            fov3.setOpacity(1.0); fov3.getTransforms().add(new Rotate(camA)); fov3.relocate(sq*cam.getX(), sq*(cam.getY()-mapY));
            layer.getChildren().add(fov3); layer.setMaxSize(sq*mapX, sq*mapY);
            fov3.setVisible(false);
        }
        //creates the grid squares and the grid lines
        private static void createGrid(){
            for(int y=0;y<mapY;y++){
                for(int x=0;x<mapX;x++){
                    Rectangle rect = new Rectangle(sq, sq);
                    Rectangle rect1 = new Rectangle(sq-1, sq-1);
                    rect.setFill(getColor(map[y][x])); rect.setOpacity(0.5);
                    rect1.setFill(Color.TRANSPARENT); rect1.setStroke(Color.BLACK);
                    grid.add(rect, x, y); gridLines.add(rect1, x, y);
                }
            }
        }
        //draws a line from the camera to the hit points
        private static void createLine(Point2D hitP, Color color){
            Line line = new Line(sq*cam.getX(), sq*cam.getY(), sq*hitP.getX(), sq*hitP.getY());
            line.setStroke(color); line.setOpacity(0.75);
            rayPane.getChildren().add(line);
        }
    }
  */  
}
