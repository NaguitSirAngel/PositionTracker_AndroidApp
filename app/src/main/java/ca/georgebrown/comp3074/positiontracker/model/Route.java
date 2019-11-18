package ca.georgebrown.comp3074.positiontracker.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Route implements Serializable {

    private int rating;
    private int id;
    private String routeName;
    private String date;
    private ArrayList<String> tags;
    private ArrayList<Coordinate> coordinates;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates) { this.coordinates = coordinates;
    }
}
