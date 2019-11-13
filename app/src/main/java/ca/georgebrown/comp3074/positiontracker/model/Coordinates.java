package ca.georgebrown.comp3074.positiontracker.model;

import java.io.Serializable;

public class Coordinates implements Serializable {

    private int idCoord;
    private int idRoute;
    private float longitude;
    private float latitude;
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

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
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
