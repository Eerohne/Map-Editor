/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.RaycastRenderer;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jeffrey
 */
public class Renderer {
    
    private static int mapX =5, mapY =5;
    private static int[][] map = {
        {2, 2, 2, 2, 2},
        {1, 0, 0, 0, 1},
        {1, 0, 0, 0, 1},
        {1, 0, 0, 0, 1},
        {1, 1, 1, 1, 1}
    };
    
    
    private static Canvas frame = new Canvas();
    private static GraphicsContext gc = frame.getGraphicsContext2D();
    private static double screenWidth = frame.getWidth(), screenHeight = frame.getHeight();
    
    public static Point2D cam = Point2D.ZERO; //camera position (player position)
    public static float camA=0f, fov=60f; //camera orientation & field of view (degrees)
    
    private Renderer(){
    }
    
    //renders one frame
    public static void render(){
        gc.clearRect(0, 0, screenWidth, screenHeight);
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, screenWidth, screenHeight);
        gc.setFill(Color.GREEN);
        gc.fillRect(0, screenHeight/2.0, screenWidth, screenHeight/2.0);
        
        renderLevel();
        renderEntities();
    }
    
    public static void setCanvas(Canvas canvas){
        frame = canvas;
        gc = canvas.getGraphicsContext2D();
        screenWidth = canvas.getWidth();
        screenHeight = canvas.getHeight();
    }
    
    public static void setMap(int[][] nMap){
        map = nMap;
        mapX = nMap[0].length; mapY = nMap.length;
    }
    
    public static Point2D getPos(){return cam;}
    public static void setPos(double x, double y){setPos(new Point2D(x, y));} //set camera position
    public static void setPos(Point2D point){cam = point;} //set camera position
    
    public static float getDir(){return camA;}
    public static void setDir(float angle){camA = angle;} // set the camera direction
    public static void setFov(float angle){fov = angle;} // set the field of view
    
    public static void resize(){
        screenWidth = frame.getWidth();
        screenHeight = frame.getHeight();
    }
    
    //renders level
    private static void renderLevel(){
        final int tileX, tileY; //player grid position
        final float offX, offY; //offset within tile (0 <= off < 1)
        //note: posX = tileX + offX and posY = tileY + offY
        tileX = (int)Math.floor(cam.getX());
        tileY = (int)Math.floor(cam.getY());
        offX = (int)Math.floor(cam.getX()-tileX);
        offY = (int)Math.floor(cam.getY()-tileY);
        
        Point2D ray = new Point2D(cam.getX(), cam.getY());
        float rayA = camA - (fov/2f);
        
        for(int r=0; r<screenWidth;r++){
            while (rayA<0 || rayA>=360) {                
                if(rayA < 0)  rayA+=360;
                if(rayA >=360)rayA-=360;
            }
            
            boolean upward, rightward;
            upward = rayA<180; rightward = (rayA<90 || rayA>270);
            
            final double tan = Math.tan(Math.toRadians(rayA));
            final double cotan = 1/tan;
            
            double stepX=0, stepY=0;
            
            Point2D rH = Point2D.ZERO, rV = Point2D.ZERO;
            
            //intersects with vertical
            if(rightward){//looking right (+x)
                rV = new Point2D(tileX+1, (1-offX)*tan+ray.getY());
                stepX = 1; stepY = -tan;
                if(upward) stepY = tan;
            }
            if(!rightward){//looking left (-x)
                rV = new Point2D(tileX  , (1-offX)*tan+ray.getY());
                stepX = -1; stepY = -tan;
                if(upward) stepY = tan;
            }
            if(rayA == 90 || rayA ==270){//looking directly up or down
                rV = ray.add(Point2D.ZERO);
                stepX = 0; stepY = -1;
                if(rayA==90) stepY = 1;
            }
            for(int i=0;i<8;i++){
                int x = (int)Math.floor(rV.getX());
                int y = (int)Math.floor(rV.getY());
                if((x<0 || y<0) || (x>=mapX || y>=mapY)){ rV = Point2D.ZERO; break;}
                if(map[y][x]>0){break;
                }else rV = rV.add(stepX, stepY);
            }
            
            //intersects with horizontal
            stepX =0; stepY=0;
            if(upward){//looking up (+y)
                rH = new Point2D(ray.getX()+(1-offY)*cotan, tileY+1);
                stepX = -cotan; stepY = 1;
                if(rightward) stepX = cotan;
            }
            if(!upward){//looking down (-y)
                rH = new Point2D(ray.getX()+(1-offY)*cotan, tileY);
                stepX = -cotan; stepY = -1;
                if(rightward) stepX = cotan;
            }
            if(rayA == 0 || rayA == 180){//looking directly left or right
                rH = ray.add(Point2D.ZERO);
                stepX = -1; stepY = 0;
                if(rayA==0) stepX = 1;
            }
            for(int i=0;i<8;i++){
                int x = (int)Math.floor(rH.getX());
                int y = (int)Math.floor(rH.getY());
                if((x<0 || y<0) || (x>=mapX || y>=mapY)){ rH = Point2D.ZERO; break;}
                if(map[y][x]>0){break;
                }else rH = rH.add(stepX, stepY);
            }
            
            if(rH.magnitude()<=rV.magnitude() && !rH.equals(Point2D.ZERO)){
                int x = (int)Math.floor(rH.getX());
                int y = (int)Math.floor(rH.getY());
                double dist = rH.magnitude();
                drawWallLine(r, dist, getColor(map[y][x]));
                MiniMap.createLine(rH, getColor(map[y][x]).brighter());
            }else{
                int x = (int)Math.floor(rV.getX());
                int y = (int)Math.floor(rV.getY());
                double dist = rV.magnitude();
                drawWallLine(r, dist, getColor(map[y][x]).darker());
                MiniMap.createLine(rV, getColor(map[y][x]).darker());
            }
            
            rayA += (fov/(float)screenWidth);
            
        }
    }
    private static void renderEntities(){
        //todo
    }
    
    private static Color getColor(int id){
        switch(id){
            case 0: return Color.GREEN;
            case 1: return Color.GRAY;
            case 2: return Color.GOLD;
            default: return Color.RED;
        }
    }
    private static void drawWallLine(int x, double distance, Color color){
        double maxHeight = screenHeight*1.5;
        double height = maxHeight/distance;
        double lineTop = (screenHeight-height)/2.0;
        
        gc.setFill(color);
        gc.fillRect(x, lineTop, 1, height);
    }
    
    static class MiniMap{//minimap that shows the level and the rays (for debugging)
        private static GridPane grid = new GridPane();
        private static Pane rayPane = new Pane();
        public static StackPane minimap = new StackPane(grid, rayPane);
        
        private static int sq = 40; //size of 1 grid square, is used for scaling
        
        public static void generate(){
            createGrid();
        }
        private static void createGrid(){
            for(int y=0;y<mapY;y++){
                for(int x=0;x<mapX;x++){
                    Rectangle rect = new Rectangle(sq, sq);
                    rect.setFill(getColor(map[y][x])); rect.setStroke(Color.BLACK);
                    grid.add(rect, x, y);
                }
            }
        }
        private static void createLine(Point2D hitP, Color color){
            Line line = new Line(sq*cam.getX(), sq*cam.getY(), sq*hitP.getX(), sq*hitP.getY());
            line.setStroke(color);
            rayPane.getChildren().add(line);
        }
    }
}
