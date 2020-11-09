/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.RaycastRenderer;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Jeffrey
 */
public class Renderer {
    
    private int mapX =5, mapY =5;
    private int[][] map = {
        {1, 1, 1, 1, 1},
        {1, 0, 0, 0, 1},
        {1, 0, 2, 0, 1},
        {1, 0, 0, 0, 1},
        {1, 1, 1, 1, 1}
    };
    
    private int screenWidth = 800, screenHeight = 450;
    public Canvas frame = new Canvas(screenWidth, screenHeight);
    private GraphicsContext gc = frame.getGraphicsContext2D();
    
    private Point2D cam;
    private float camA, fov;
    
    public Renderer(){
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, screenWidth, screenHeight);
        gc.setFill(Color.GREEN);
        gc.fillRect(0, screenHeight/2, screenWidth, screenHeight/2);
        
        cam = new Point2D(1.5, 2.5);
        camA = 0f;
        fov = 60f;
    }
    
    //renders one frame
    public void render(){
        renderLevel();
    }
    
    //renders level
    private void renderLevel(){
        final int tileX, tileY; //player grid position
        final float offX, offY; //offset within tile (0 <= off < 1)
        //note: posX = tileX + offX and posY = tileY + offY
        tileX = (int)Math.floor(cam.getX());
        tileY = (int)Math.floor(cam.getY());
        offX = (int)Math.floor(cam.getX()-tileX);
        offY = (int)Math.floor(cam.getY()-tileY);
        
        Point2D ray = new Point2D(cam.getX(), cam.getY());
        float rayA = camA - (fov/2);
        
        for(int r=0; r<screenWidth;r++){
            if(rayA < 0)  rayA+=360;
            if(rayA >=360)rayA-=360;
            
            boolean upward, rightward;
            upward = rayA<180; rightward = (rayA<90 || rayA>270);
            
            final double tan = Math.tan(Math.toRadians(rayA));
            final double cotan = 1/tan;
            
            double stepX=0, stepY=0;
            
            Point2D rH = Point2D.ZERO, rV = Point2D.ZERO;
            
            //intersects with vertical
            if(rightward){//looking right (+x)
                rV = new Point2D(tileX+1, ray.getY()+(1-offX)*tan);
                stepX = 1; stepY = -tan;
                if(upward) stepY = tan;
            }
            if(!rightward){//looking left (-x)
                rV = new Point2D(tileX  , ray.getY()+(1-offX)*tan);
                stepX = -1; stepY = -tan;
                if(upward) stepY = tan;
            }
            if(rayA == 90 || rayA ==270){//looking directly up or down
                rV = new Point2D(ray.getX(), ray.getY());
                stepX = 0; stepY = 1;
                if(rayA == 270) stepY = -1;
            }
            for(int i=0;i<8;i++){
                int x = (int)Math.floor(rV.getX());
                int y = (int)Math.floor(rV.getY());
                if((x<0 || y<0) || (x>=mapX || y>=mapY)){ rV = Point2D.ZERO; break;}
                if(map[y][x]>0){break;
                }else rV = rV.add(stepX, stepY);
            }
            
            //intersects with horizontal
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
                rH = new Point2D(ray.getX(), ray.getY());
                stepX = 1; stepY = 0;
                if(rayA == 180) stepX = -1;
            }
            for(int i=0;i<8;i++){
                int x = (int)Math.floor(rH.getX());
                int y = (int)Math.floor(rH.getY());
                if((x<0 || y<0) || (x>=mapX || y>=mapY)){ rH = Point2D.ZERO; break;}
                if(map[y][x]>0){break;
                }else rH = rH.add(stepX, stepY);
            }
            
            if(rH.magnitude()<rV.magnitude() && !rH.equals(Point2D.ZERO)){
                int x = (int)Math.floor(rH.getX());
                int y = (int)Math.floor(rH.getY());
                drawWallLine(r, rH.magnitude(), getColor(map[y][x]));
            }else{
                int x = (int)Math.floor(rV.getX());
                int y = (int)Math.floor(rV.getY());
                drawWallLine(r, rV.magnitude(), getColor(map[y][x]).darker());
            }
            
            rayA += (fov/(float)screenWidth);
            
        }
    }
    
    static Color getColor(int id){
        switch(id){
            case 0: return Color.RED;
            case 1: return Color.DIMGRAY;
            case 2: return Color.AQUA;
            default: return Color.RED;
        }
    }
    private void drawWallLine(int x, double distance, Color color){
        double maxHeight = screenHeight;
        double height = maxHeight/distance;
        double lineTop = (screenHeight-height)/2;
        
        gc.setFill(color);
        gc.fillRect(x, lineTop, screenWidth/360, height);
    }
    
}
