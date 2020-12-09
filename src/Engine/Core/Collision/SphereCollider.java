/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core.Collision;

import javafx.geometry.Point2D;

/**
 *
 * @author toufik.issad
 */
public class SphereCollider {
    private Point2D position;
    private double radius;
    private boolean isTrigger;
    
    public SphereCollider(Point2D position, double radius, boolean isTrigger) {
        this.position = position;
        this.radius = radius;
        this.isTrigger = isTrigger;
    }
    
    public boolean inside(Point2D position)
    {
        if(this.position.distance(position) < this.radius)
            return true;
        else
            return false;
    }
    
    public boolean intersect(Point2D position, float radius)
    {
        if(this.position.distance(position) <= (this.radius-radius))
            return true;
        else
            return false;
    }
    
    public Point2D collide(Point2D position, Point2D direction, float radius)
    {
        if(intersect(position, radius))
        {
//            Point2D pointVector = this.position.subtract(position);
//            Point2D normalVector = new Point2D(pointVector.getY(), -pointVector.getX());
//            double dot = normalVector.dotProduct(new Point2D(direction.getY(), -direction.getX()));
//            //System.out.println("dot : "+dot);
//
//            int factor = 1;
//            if(dot >0)
//                factor = 1;
//            else
//                factor = -1;
//            //System.out.println("factor : "+factor);
//            //System.out.println("normal : "+normalVector);
//            Point2D projection = normalVector.multiply(position.dotProduct(normalVector)/normalVector.dotProduct(normalVector)).multiply(factor);
//            //System.out.println("projected : "+projection.normalize());
//            projection = projection.add(pointVector.multiply(-1*2).normalize());
//            return projection.normalize();
            
            Point2D pointVector = this.position.subtract(position);
            return direction.subtract(pointVector).normalize().multiply(-direction.magnitude());
        }
        else
            return null;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean isIsTrigger() {
        return isTrigger;
    }

    public void setIsTrigger(boolean isTrigger) {
        this.isTrigger = isTrigger;
    }
    
    
}
