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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * @author Jeffrey
 */
public class Renderer {
    
    
    private static int[][] map = {
        {3, 4, 3, 4, 3},
        {4, 0, 0, 0, 4},
        {3, 0, 0, 0, 3},
        {4, 0, 0, 0, 4},
        {3, 4, 3, 4, 3}
    };
    private static int mapX=map[0].length, mapY=map.length;
    
    private static Canvas frame = new Canvas();
    private static GraphicsContext gc = frame.getGraphicsContext2D();
    private static double screenWidth = frame.getWidth(), screenHeight = frame.getHeight();
    
    //!!These will be private in the future!! \/
    //---Use setters to set up initial values---
    public static Point2D cam = Point2D.ZERO; //camera position (player position)
    public static float camA=0f, fov=90f; //camera orientation & field of view (degrees)
    
    public static boolean test = false;
    
    private Renderer(){}
    
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
    public static void addPos(double x, double y){cam = cam.add(x, y);}
    public static void addPos(Point2D point){cam = cam.add(point);}
    
    public static float getDir(){return camA;}
    public static void addDir(float angInc){camA += angInc;} //increases camera direction
    public static void setDir(float angdeg){camA = angdeg;} // set the camera direction
    public static void setFov(float angdeg){fov = angdeg;} // set the field of view
    
    public static void resize(){
        screenWidth = frame.getWidth();
        screenHeight = frame.getHeight();
    }
    
    //renders level
    private static void renderLevel(){
        final int tileX, tileY; //player grid position
        final double offX, offY; //offset within tile (0 <= off < 1)
        //note: posX = tileX + offX and posY = tileY + offY
        tileX = (int)Math.floor(cam.getX());
        tileY = (int)Math.floor(cam.getY());
        offX = cam.getX()-tileX;
        offY = cam.getY()-tileY;
        
        float rayA = camA - (fov/(2f));
        
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
                rV = new Point2D(tileX+1, cam.getY()+(1-offX)*tan);
                stepX = 1; stepY = tan;
            }
            if(!rightward){//looking left (-x)
                rV = new Point2D(tileX  , cam.getY()-(1-offX)*tan);
                if(cam.getY()/tileY == 1.0)rV = rV.add(-1,0);
                stepX = -1; stepY = -tan;
            }
            if(tan>80.0 || tan<-80.0){//looking directly up or down
                rV = new Point2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
            }
            
            //System.out.printf("%3.3f rV: %1.4f, %1.4f %n",rayA, rV.getX(), rV.getY());
            int v=-1;
            for(int i=0;i<8;i++){
                int x = (int)Math.floor(rV.getX());
                int y = (int)Math.floor(rV.getY());
                if((x<0 || y<0) || (x>=mapX || y>=mapY)){; break;}
                if(map[y][x]>0){v=map[y][x];break;}
                if(map[y][x-1]>0 && !rightward){v=map[y][x-1];break;}
                rV = rV.add(stepX, stepY);
            }
            
            //intersects with horizontal
            stepX =0; stepY=0;
            if(upward){//looking up (+y)
                rH = new Point2D(cam.getX()+(1-offY)*cotan, tileY+1);
                stepX = cotan; stepY = 1;
            }
            if(!upward){//looking down (-y)
                rH = new Point2D(cam.getX()-(1-offY)*cotan, tileY);
                if(cam.getX()/tileX == 1.0)rH = rH.add(0, -1);
                stepX = -cotan; stepY = -1;
            }
            if(cotan>80.0 || cotan <-80.0){//looking directly left or right
                rH = new Point2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
            }
            
            //System.out.printf("%3.3f rH: %1.4f, %1.4f %n",rayA, rH.getX(), rH.getY());
            int h = -1;
            for(int i=0;i<8;i++){
                int x = (int)(rH.getX());
                int y = (int)(rH.getY());
                if((x<0 || y<0) || (x>=mapX || y>=mapY)){; break;}
                if(map[y][x]>0){h=map[y][x];break;}
                if(map[y-1][x]>0 && !upward){h=map[y-1][x];break;}
                rH = rH.add(stepX, stepY);

            }
            
            double hLength = rH.subtract(cam).magnitude();
            double vLength = rV.subtract(cam).magnitude();
            
            // createLine() is not to be used when running the game
            // it's for debugging only
            if(hLength<vLength && hLength!=0){
                int x = (int)(rH.getX());
                int y = (int)(rH.getY());
                double dist = hLength*Math.cos(Math.toRadians(rayA-camA));
                drawWallLine(r, dist, getColor(h));
                if(test)MiniMap.createLine(rH, getColor(h).brighter());
            }else{
                int x = (int)(rV.getX());
                int y = (int)(rV.getY());
                double dist = vLength*Math.cos(Math.toRadians(rayA-camA));
                drawWallLine(r, dist, getColor(v).darker());
                if(test)MiniMap.createLine(rV, getColor(v).darker());
            }
            rayA += (fov/screenWidth);
            
        }
    }
    private static void renderEntities(){
        //todo
    }
    
    private static Color getColor(int id){
        switch(id){
            case 0: return Color.WHITE;
            case 1: return Color.GRAY;
            case 2: return Color.GOLD;
            case 3: return Color.VIOLET;
            case 4: return Color.BLUEVIOLET;
            default: return Color.RED; //indicates error
        }
    }
    private static void drawWallLine(int x, double distance, Color color){
        double maxHeight = screenHeight;
        double height = maxHeight/(distance +.25);
        double lineTop = (screenHeight-height)/2.0;
        
        gc.setFill(color);
        gc.fillRect(x, lineTop, 1, height);
    }
    
    static class MiniMap{//minimap that shows the level and the rays (for debugging)
        private final static GridPane grid = new GridPane();
        private final static GridPane gridLines = new GridPane();
        private final static Pane rayPane = new Pane();
        public static StackPane minimap = new StackPane(grid, rayPane, gridLines);
        
        //private static int sq = 40; //size of 1 grid square, is used for scaling
        private static int sq = (int)screenHeight/mapY;
        
        public static void generate(){
            rayPane.getChildren().clear();
            createGrid();
        }
        
        private static void createGrid(){
            for(int y=0;y<mapY;y++){
                for(int x=0;x<mapX;x++){
                    Rectangle rect = new Rectangle(sq, sq);
                    Rectangle rect1 = new Rectangle(sq-1, sq-1);
                    rect.setFill(getColor(map[y][x])); rect.setOpacity(0.8);
                    rect1.setFill(Color.TRANSPARENT); rect1.setStroke(Color.BLACK);
                    grid.add(rect, x, y); gridLines.add(rect1, x, y);
                }
            }
        }
        private static void createLine(Point2D hitP, Color color){
            Line line = new Line(sq*cam.getX(), sq*cam.getY(), sq*hitP.getX(), sq*hitP.getY());
            line.setStroke(color); line.setOpacity(0.5);
            rayPane.getChildren().add(line);
        }
    }
}
