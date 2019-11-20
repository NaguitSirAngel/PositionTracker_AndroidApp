package ca.georgebrown.comp3074.positiontracker.model;

import java.io.Serializable;

public class Coordinate implements Serializable {

    private int idCoord;
    private int idRoute;
    private double longitude;
    private double latitude;
    private float accuracy;
    private long timestamp;


    public int getIdCoord() {
        return idCoord;
    }

    public void setIdCoord(int idCoord) {
        this.idCoord = idCoord;
    }

    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
        this.idRoute = idRoute;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
