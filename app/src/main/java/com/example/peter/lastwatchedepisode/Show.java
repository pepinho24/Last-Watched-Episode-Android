package com.example.peter.lastwatchedepisode;

public class Show {
    private int id;
    private String title;
    private String description;

    public Show(String title, String description, String airWeekDay) {
        this.setTitle(title);
        this.setDescription(description);
        this.setAirWeekDay(airWeekDay);
    }

    private String airWeekDay;

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
        return this.getTitle() +" airs on "+ this.getAirWeekDay();
    }
}
