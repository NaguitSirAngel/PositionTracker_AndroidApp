package ca.georgebrown.comp3074.positiontracker.sql;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ca.georgebrown.comp3074.positiontracker.model.Coordinates;
import ca.georgebrown.comp3074.positiontracker.model.Route;
import ca.georgebrown.comp3074.positiontracker.model.Tag;

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

        long addReturn = db.insert(DbContract.RouteEntity.TABLE_NAME,null,cv);
        db.close();
        return addReturn;
    }

    //adds Tags
    public long addTags(Tag tag, Route route){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(DbContract.TagEntity.COLUMN_TAG, tag.getTagName());
        cv.put(DbContract.TagEntity.COLUMN_ROUTEID, route.getId());
        long addReturn = db.insert(DbContract.TagEntity.TABLE_NAME,null,cv);
        db.close();
        return addReturn;
    }

    //adds Coordinate
    public long addCoordinate(Coordinates cor, Route route){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(DbContract.CoordinatesEntity.COLUMN_LONGITUDE, cor.getLongitude());
        cv.put(DbContract.CoordinatesEntity.COLUMN_LATITUDE, cor.getLatitude());
        cv.put(DbContract.CoordinatesEntity.COLUMN_ACCURACY, cor.getAccuracy());
        cv.put(DbContract.CoordinatesEntity.COLUMN_TIMESTAMP, cor.getTimestamp());
        cv.put(DbContract.CoordinatesEntity.COLUMN_ROUTEID, route.getId());

        long addReturn = db.insert(DbContract.CoordinatesEntity.TABLE_NAME,null,cv);
        db.close();
        return addReturn;
    }

    public ArrayList<Route> getAllRoutes(){
        ArrayList<Route> routes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {DbContract.RouteEntity._ID,
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
            route.setId(Integer.valueOf(cursor.getColumnIndex(DbContract.RouteEntity._ID)));
            route.setRouteName(cursor.getString(cursor.getColumnIndex(DbContract.RouteEntity.COLUMN_NAME)));
            route.setRating(Integer.valueOf(cursor.getString(cursor.getColumnIndex(DbContract.RouteEntity.COLUMN_RATING))));
            route.setDate(cursor.getString(cursor.getColumnIndex(DbContract.RouteEntity.COLUMN_DATE)));
            routes.add(route);
        }
        return routes;
    }
}
