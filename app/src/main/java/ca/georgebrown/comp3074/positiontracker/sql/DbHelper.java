package ca.georgebrown.comp3074.positiontracker.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ca.georgebrown.comp3074.positiontracker.model.Coordinate;
import ca.georgebrown.comp3074.positiontracker.model.Route;

public class DbHelper extends SQLiteOpenHelper {

    public  static final int  DATABASE_VERSION=1; //current version of the dataabase
    public  static final String  DATABASE_NAME= "PositionTracker.db"; //name of the file you stored the date

    public DbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbContract.SQL_CREATE_ROUTES);
        db.execSQL(DbContract.SQL_CREATE_TAGS);
        db.execSQL(DbContract.SQL_CREATE_COORDINATES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbContract.SQL_CREATE_COORDINATES);
        db.execSQL(DbContract.SQL_CREATE_TAGS);
        db.execSQL(DbContract.SQL_CREATE_ROUTES);
        onCreate(db);
    }

    //adds Route
    public long addRoute(Route route){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(DbContract.RouteEntity.COLUMN_NAME, route.getRouteName());
        cv.put(DbContract.RouteEntity.COLUMN_RATING, route.getRating());
        cv.put(DbContract.RouteEntity.COLUMN_DATE, route.getDate());
        long l = db.insert(DbContract.RouteEntity.TABLE_NAME,null,cv);
        db.close();
        return l;
    }

    //adds Coordinate
    public long addCoordinate(Coordinate cor, Route route){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbContract.CoordinatesEntity.COLUMN_LONGITUDE, cor.getLongitude());
        cv.put(DbContract.CoordinatesEntity.COLUMN_LATITUDE, cor.getLatitude());
        cv.put(DbContract.CoordinatesEntity.COLUMN_ACCURACY, cor.getAccuracy());
        cv.put(DbContract.CoordinatesEntity.COLUMN_TIMESTAMP, cor.getTimestamp());
        cv.put(DbContract.CoordinatesEntity.COLUMN_ROUTEID, route.getId());
        long addReturn = db.insert(DbContract.CoordinatesEntity.TABLE_NAME,null,cv);
        db.close();
        return addReturn;
    }

    //returns a list of routs
    public ArrayList<Route> getAllRoutes(){
        ArrayList<Route> routes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { DbContract.RouteEntity._ID,
                                DbContract.RouteEntity.COLUMN_NAME,
                                DbContract.RouteEntity.COLUMN_RATING,
                                DbContract.RouteEntity.COLUMN_DATE};

        Cursor cursor = db.query(
                DbContract.RouteEntity.TABLE_NAME,  //table name
                projection, //colums we select
                null, //columns for WHERE clause
                null, //parameters for where clause
                null, //groupby
                null, //having
                null); //sorting

        while (cursor.moveToNext()){
            Route route = new Route();
            route.setId(cursor.getInt(cursor.getColumnIndex(DbContract.RouteEntity._ID)));
            route.setRouteName(cursor.getString(cursor.getColumnIndex(DbContract.RouteEntity.COLUMN_NAME)));
            route.setRating(cursor.getInt(cursor.getColumnIndex(DbContract.RouteEntity.COLUMN_RATING)));
            route.setDate(cursor.getString(cursor.getColumnIndex(DbContract.RouteEntity.COLUMN_DATE)));
            routes.add(route);
        }
        db.close();
        return routes;
    }

    //gets all coordinates of corresponding route
    public ArrayList<Coordinate> getAllCoordinates(int id){
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = { DbContract.CoordinatesEntity.COLUMN_ACCURACY,
                                DbContract.CoordinatesEntity.COLUMN_LATITUDE,
                                DbContract.CoordinatesEntity.COLUMN_LONGITUDE,
                                DbContract.CoordinatesEntity.COLUMN_TIMESTAMP};
        Cursor cursor = db.query(
                        DbContract.CoordinatesEntity.TABLE_NAME,
                        projection,
                DbContract.CoordinatesEntity.COLUMN_ROUTEID +" = "+ id,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()){
            Coordinate coordinate = new Coordinate();
            coordinate.setLatitude(cursor.getDouble(cursor.getColumnIndex(DbContract.CoordinatesEntity.COLUMN_LATITUDE)));
            coordinate.setLongitude(cursor.getDouble(cursor.getColumnIndex(DbContract.CoordinatesEntity.COLUMN_LONGITUDE)));
            coordinate.setAccuracy(cursor.getFloat(cursor.getColumnIndex(DbContract.CoordinatesEntity.COLUMN_ACCURACY)));
            coordinate.setTimestamp(cursor.getLong(cursor.getColumnIndex(DbContract.CoordinatesEntity.COLUMN_TIMESTAMP)));
            coordinates.add(coordinate);
        }
        db.close();
        return coordinates;
    }

    //updates a route
    public int updateRoute(Route route) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();

        cv.put(DbContract.RouteEntity.COLUMN_NAME, route.getRouteName());
        cv.put(DbContract.RouteEntity.COLUMN_RATING, route.getRating());

        // updating row
        int i = db.update(DbContract.RouteEntity.TABLE_NAME, cv, DbContract.RouteEntity._ID + " = ?",
                new String[]{String.valueOf(route.getId())});
        db.close();
        return i;
    }

    //Deletes a Route
    public void deleteRoute(Route route){
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = DbContract.RouteEntity.TABLE_NAME;
        String whereClause = "_id=" + route.getId();
        db.delete(tableName, whereClause, null);
        db.close();
    }

    //Deletes coordinates
    public void deleteCoordinates(int routeId){
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = DbContract.CoordinatesEntity.TABLE_NAME;
        String whereClause = DbContract.CoordinatesEntity.COLUMN_ROUTEID + " = " + routeId;
        db.delete(tableName, whereClause, null);
        db.close();
    }

    //get route
    public Route getRoute(long id){
        Route route = new Route();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {DbContract.RouteEntity._ID,
                DbContract.RouteEntity.COLUMN_NAME,
                DbContract.RouteEntity.COLUMN_RATING,
                DbContract.RouteEntity.COLUMN_DATE};
        Cursor cursor = db.query(
                DbContract.RouteEntity.TABLE_NAME,  //table name
                projection, //colums we select
                "_id="+id, //columns for WHERE clause
                null, //parameters for where clause
                null, //groupby
                null, //having
                null); //sorting

        if (cursor != null) {
            cursor.moveToFirst();
        }
        route.setId(cursor.getInt(cursor.getColumnIndex(DbContract.RouteEntity._ID)));
        route.setRouteName(cursor.getString(cursor.getColumnIndex(DbContract.RouteEntity.COLUMN_NAME)));
        route.setRating(cursor.getInt(cursor.getColumnIndex(DbContract.RouteEntity.COLUMN_RATING)));
        route.setDate(cursor.getString(cursor.getColumnIndex(DbContract.RouteEntity.COLUMN_DATE)));
        db.close();
        return route;
    }


    //delete tags
    public void deleteTags(Route route){
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = DbContract.TagEntity.TABLE_NAME;
        String whereClause = DbContract.TagEntity.COLUMN_ROUTEID+ " = " + route.getId();
        db.delete(tableName, whereClause, null);
        db.close();
    }

    //returns a string of tags
    public String stringTag(Route route){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {DbContract.TagEntity._ID, DbContract.TagEntity.COLUMN_TAG, DbContract.TagEntity.COLUMN_ROUTEID};
        String selection = DbContract.TagEntity.COLUMN_ROUTEID+"=?"; //WordContract.WordEntity.COLUMN_NAME_WORD1+"=?";
        String[] selectionArgs = {String.valueOf(route.getId())}; //{"test"}
        Cursor c =  db.query(
                DbContract.TagEntity.TABLE_NAME,  //table name
                projection, //colums we select
                selection, //columns for WHERE clause
                selectionArgs, //parameters for where clause
                null, //groupby
                null, //having
                null); //sorting
        String myTags;
        List l = new ArrayList();
        while (c.moveToNext()){
            String w = c.getString(c.getColumnIndex(DbContract.TagEntity.COLUMN_TAG));
            l.add(w);
        }
        myTags = android.text.TextUtils.join(",", l);
        db.close();
        return myTags;
    }

    //adds the tags in the Tags table
    public long addTag(String word1, long id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbContract.TagEntity.COLUMN_TAG, word1);
        cv.put(DbContract.TagEntity.COLUMN_ROUTEID, id);
        long l = db.insert(DbContract.TagEntity.TABLE_NAME, null, cv);
        db.close();
        return l;
    }

}
