/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author Jeffrey
 */
public class Renderer {
    int mapX, mapY, mapSize; //width, height & area
    
    public int[][] map = {
        {1, 1, 1, 1, 1},
        {1, 0, 0, 0, 1},
        {1, 0, 2, 0, 1},
        {1, 0, 0, 0, 1},
        {1, 1, 1, 1, 1}
    };
    
    private int screenHeight = 800, screenWidth = 450;
    public WritableImage frame = new WritableImage(screenWidth, screenHeight);
    private PixelWriter writer = frame.getPixelWriter();
    
    private float posX, posY, camA, fov;
    
    public Renderer(){
        posX = 1.5f;
        posY = 1.5f;
        camA = 315f;
        fov = 10f;
    }
    
    //renders one frame
    public void render(){
        int tileX, tileY; //player grid position
        float offX, offY; //offset within tile
        //note: posX = tileX + offX and posY = tileY + offY
        
        float rayX, rayY, rayA; //position & direction of the ray
        double inWithH, inWithV; //intersect with line
        
        int stepX =0, stepY=0;//depends on angle of ray
        rayA = camA; //in degrees
        
        
        
        //ray for each pixel in the width
        for(int ray=0; ray<screenWidth;ray++){
            rayA -= (fov/2f);
            
            if(rayA<0) ray+=360;
            if(rayA>=360)ray-=360;
            
            if(rayA<90 || rayA>270)stepX = 1; else {stepX = -1;}
            if(rayA>0 && rayA<180)stepY = -1; else stepY = 1;
            
            int tile = 0;
            double tan = Math.tan(Math.toRadians(rayA));
            
            tileX = (int)Math.floor(posX);
            tileY = (int)Math.floor(posY);
            
            
            
            //detect h-line hit
            offY = posY-(float)tileY;
            inWithH = offY/tan;
            if(tan==0) inWithH = 0;
            if(inWithH!=0){
                while(0<tileY || tileY<mapY){
                    tile = map[(int)Math.floor(inWithH)][tileY];
                    if(tile>0){ break;}
                    else{
                        if(tan!=0)inWithH+=1/tan;
                        tileY+=stepY;
                    }
                }
            }
            //detect v-line hit
            offX = posX-(float)tileX;
            inWithV = offX*tan;
            if(inWithV!=0){
                while(0<tileX || tileX<mapX){
                    tile = map[tileX][(int)Math.floor(inWithV)];
                    if(tile>0){ break;}
                    else{
                        inWithV+=tan;
                        tileX+=stepX;
                    }
                }
            }
            //find shortest distance
            double Hlength = Math.sqrt(Math.pow(inWithH, 2)+Math.pow(tileY, 2));
            double Vlength = Math.sqrt(Math.pow(inWithV, 2)+Math.pow(tileX, 2));;
            switch(Double.compare(Hlength, Vlength)){
                case -1: drawWallLine(ray, Vlength, getColor(tile), true); break;
                case 0: drawWallLine(ray, Hlength, getColor(tile), false); break;
                case 1: drawWallLine(ray, Hlength, getColor(tile), false); break;
            }
            
            rayA += fov/frame.getWidth();
        }
    }
    
    static Color getColor(int id){
        switch(id){
            case 1: return Color.BLACK;
            case 2: return Color.AQUA;
            default: return Color.WHITE;
        }
    }
    void drawWallLine(int x, double distance, Color color, boolean isV){
        double aHeight = 400.0;
        int pHeight = (int)Math.floor(aHeight/distance);
        if(isV) color = color.darker();
        for(int y = 0; y<pHeight; y++){
            writer.setColor(x, y, color);
        }
        
    }
    
}
