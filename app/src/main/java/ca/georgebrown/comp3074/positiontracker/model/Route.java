package ca.georgebrown.comp3074.positiontracker.model;

import java.util.ArrayList;

public class Route {

    private int id;
    private String routeName;
    private int rating;
    private long date;
    private ArrayList<String> tags;
    private ArrayList<Coordinates> route;

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<Coordinates> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Coordinates> route) {
        this.route = route;
    }
}
