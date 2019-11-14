package ca.georgebrown.comp3074.positiontracker.sql;

import android.provider.BaseColumns;

public final class DbContract {

    public static class RouteEntity implements BaseColumns {
        public static final String TABLE_NAME = "routes"; // name of your table
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_DATE = "date";
    }

    public static class CoordinatesEntity implements BaseColumns {
        public static final String TABLE_NAME = "coordinates"; // name of your table
        public static final String COLUMN_ROUTEID = "route_id";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_ACCURACY = "accuracy";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }

    public static class TagEntity implements BaseColumns {
        public static final String TABLE_NAME = "tags"; // name of your table
        public static final String COLUMN_ROUTEID = "route_id";
        public static final String COLUMN_TAG = "tag_name";
    }

    public static final String SQL_CREATE_TAGS= "CREATE TABLE "+
            TagEntity.TABLE_NAME+" ( "+
            TagEntity._ID+" INTEGER PRIMARY KEY, "+
            TagEntity.COLUMN_TAG+" TEXT, "+
            TagEntity.COLUMN_ROUTEID+" INTEGER NOT NULL, " +
            "FOREIGN KEY ("+TagEntity.COLUMN_ROUTEID+") " +
            "REFERENCES "+RouteEntity.TABLE_NAME+" ("+RouteEntity._ID+") )";

    public static final String SQL_DROP_TAGS =
            "DROP TABLE IF EXISTS "+TagEntity.TABLE_NAME;



    public static final String SQL_CREATE_ROUTES= "CREATE TABLE "+
            RouteEntity.TABLE_NAME+" ( "+
            RouteEntity._ID+" INTEGER PRIMARY KEY, "+
            RouteEntity.COLUMN_NAME+" TEXT, "+
            RouteEntity.COLUMN_RATING+" INTEGER, "+
            RouteEntity.COLUMN_DATE+" TEXT ) ";

    public static final String SQL_DROP_ROUTES =
            "DROP TABLE IF EXISTS "+RouteEntity.TABLE_NAME;

    public static final String SQL_CREATE_COORDINATES= "CREATE TABLE "+
            CoordinatesEntity.TABLE_NAME+" ( "+
            CoordinatesEntity._ID+" INTEGER PRIMARY KEY, "+
            CoordinatesEntity.COLUMN_LONGITUDE+" REAL, "+
            CoordinatesEntity.COLUMN_LATITUDE+" REAL, "+
            CoordinatesEntity.COLUMN_ACCURACY+" REAL, "+
            CoordinatesEntity.COLUMN_TIMESTAMP+" TEXT,"+
            CoordinatesEntity.COLUMN_ROUTEID+" INTEGER NOT NULL, " +
            "FOREIGN KEY ("+CoordinatesEntity.COLUMN_ROUTEID+") " +
            "REFERENCES "+RouteEntity.TABLE_NAME+" ("+RouteEntity._ID+") )";

    public static final String SQL_DROP_COORDINATE =
            "DROP TABLE IF EXISTS "+CoordinatesEntity.TABLE_NAME;

}
