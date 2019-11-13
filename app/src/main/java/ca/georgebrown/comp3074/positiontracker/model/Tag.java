package ca.georgebrown.comp3074.positiontracker.model;

import java.io.Serializable;

public class Tag implements Serializable {
    private int idTag;
    private int idRoute;
    private String tagName;



    public int getIdTag() {
        return idTag;
    }

    public void setIdTag(int idTag) {
        this.idTag = idTag;
    }

    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
        this.idRoute = idRoute;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
