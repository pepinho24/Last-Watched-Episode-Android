package com.example.peter.lastwatchedepisode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ShowsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TITLE,
            MySQLiteHelper.COLUMN_DESCRIPTION,
            MySQLiteHelper.COLUMN_AIRWEEKDAY,
            MySQLiteHelper.COLUMN_LAST_WATCHED_EPISODE,
            MySQLiteHelper.COLUMN_DATE_LAST_EPISODE_IS_WATCHED,
    };

    public ShowsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Show createShow(String title, String description, String airWeekDay) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);
        values.put(MySQLiteHelper.COLUMN_AIRWEEKDAY, airWeekDay);
        values.put(MySQLiteHelper.COLUMN_LAST_WATCHED_EPISODE, 0);
        values.put(MySQLiteHelper.COLUMN_DATE_LAST_EPISODE_IS_WATCHED, new Date().toString());

        long insertId = database.insert(MySQLiteHelper.TABLE_SHOWS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SHOWS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Show newShow = cursorToShow(cursor);
        cursor.close();
        return newShow;
    }

    public Show getShowById(long id){
        String query = "select * from " + MySQLiteHelper.TABLE_SHOWS + " where _id = " + id ;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        Show dbShow = cursorToShow(cursor);
        return dbShow;
    }

    public void watchShow(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SHOWS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + id, null,
                null, null, null);
        cursor.moveToFirst();
        Show dbShow = cursorToShow(cursor);
        ContentValues cv = new ContentValues();
        cv.put(MySQLiteHelper.COLUMN_TITLE, dbShow.getTitle());
        cv.put(MySQLiteHelper.COLUMN_DESCRIPTION, dbShow.getDescription());
        cv.put(MySQLiteHelper.COLUMN_AIRWEEKDAY, dbShow.getAirWeekDay());
        cv.put(MySQLiteHelper.COLUMN_LAST_WATCHED_EPISODE, dbShow.getLastWatchedEpisode() + 1);
        cv.put(MySQLiteHelper.COLUMN_DATE_LAST_EPISODE_IS_WATCHED, new Date().toString());
        database.update(MySQLiteHelper.TABLE_SHOWS, cv, "_id=" + id, null);
        System.out.println("Show updated with id: " + id);
    }

    public void deleteShow(Show show) {
        long id = show.getId();
        database.delete(MySQLiteHelper.TABLE_SHOWS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
        System.out.println("Show deleted with id: " + id);
    }

    public void deleteShow(long id) {
        database.delete(MySQLiteHelper.TABLE_SHOWS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
        System.out.println("Show deleted with id: " + id);
    }

    public List<Show> getAllShows() {
        List<Show> shows = new ArrayList<Show>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_SHOWS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Show show = cursorToShow(cursor);
            shows.add(show);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return shows;
    }

    private Show cursorToShow(Cursor cursor) {
        Show show = new Show(cursor.getString(1), cursor.getString(2), cursor.getString(3));
        show.setId(cursor.getInt(0));
        show.setLastWatchedEpisode(new Integer(cursor.getString(4)));
        show.setDateLastEpisodeIsWatched(cursor.getString(5));
        return show;
    }
}
