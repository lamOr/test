package GOLF;

import GOLF.*;

public class Hole {
    private Point holeLocation;
    private double distance;
    private final double inRange = 6;

    public Hole(){
        this(0,0);
    }
    public Hole(Point location){
        holeLocation = new Point(location);
    }

    public Hole(double lat,double log){
        holeLocation = new Point(lat,log);
    }

    public void setPosition(double lat,double log){
        holeLocation.setLat(lat);
        holeLocation.setLog(log);
    }

    public void setPosition(Point location){
        holeLocation.setPoint(location);
    }

    public double getLat(){
        return holeLocation.getLat();
    }

    public double getLog(){
        return holeLocation.getLog();
    }
    
    public Point getLocation(){
        return holeLocation;
    }

    public boolean comparePosition(Ball ball){
        boolean result=false;
        if(this.getLocation()==null)
            return false;
        distance=DistanceCaculation.D_jw(getLat(),getLog(),ball.getLat(),ball.getLog());// unit is m
        if(distance<inRange)
            result=true;
        return result;
    }

}
