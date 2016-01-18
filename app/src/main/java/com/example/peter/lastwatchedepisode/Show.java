package com.example.peter.lastwatchedepisode;

import java.util.Date;

public class Show {
    private int id;
    private String title;
    private String description;
    private String airWeekDay;
    private int lastWatchedEpisode;
    private String dateLastEpisodeIsWatched;

    public Show(String title, String description, String airWeekDay) {
        this.setTitle(title);
        this.setDescription(description);
        this.setAirWeekDay(airWeekDay);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAirWeekDay() {
        return airWeekDay;
    }

    public void setAirWeekDay(String airWeekDay) {
        this.airWeekDay = airWeekDay;
    }

    @Override
    public String toString() {
        return this.getTitle() + " airs on " + this.getAirWeekDay();
    }

    public int getLastWatchedEpisode() {
        return lastWatchedEpisode;
    }

    public void setLastWatchedEpisode(int lastWatchedEpisode) {
        this.lastWatchedEpisode = lastWatchedEpisode;
    }

    public String getDateLastEpisodeIsWatched() {
        return dateLastEpisodeIsWatched;
    }

    public void setDateLastEpisodeIsWatched(String dateLastEpisodeIsWatched) {
        this.dateLastEpisodeIsWatched = dateLastEpisodeIsWatched;
    }
}
