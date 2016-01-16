package com.example.peter.lastwatchedepisode;

import java.util.ArrayList;
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

    public void deleteShow(Show show) {
        long id = show.getId();
        System.out.println("Show deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_SHOWS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
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
        Show show = new Show(cursor.getString(1),cursor.getString(2),cursor.getString(3));
        show.setId(cursor.getInt(0));
        return show;
    }
}
