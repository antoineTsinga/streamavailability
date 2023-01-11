package com.streamavailability.ui.home.data;

public class Movie {

    private String id;
    private String title;
    private String overview;
    private String poster_path;
    private String release_date;



    private String name;

    public Movie() {
    }

    public Movie(String id, String title, String overview, String poster_path, String release_date, String name) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster_path =poster_path;
        this.release_date = release_date;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
