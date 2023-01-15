package com.streamavailability.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {


    private String id;
    private String title;
    private String overview;
    private String poster_path;
    private String release_date;
    private Boolean adult;
    private String media_type;
    private List<Genre> genres;
    private String vote_average;
    private String runtime ;
    private Credits credits;


    @SerializedName("watch/providers")
    private WatchProvider watchProvider;


    private String name;

    public Movie() {
    }

    public Movie(String id, String title, String overview, String poster_path, String release_date, Boolean adult, String media_type, List<Genre> genres, String vote_average, String runtime, Credits credits, WatchProvider watchProvider, String name) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster_path =poster_path;
        this.release_date = release_date;
        this.adult = adult;
        this.media_type = media_type;
        this.genres = genres;
        this.vote_average = vote_average;
        this.runtime = runtime;
        this.credits = credits;
        this.watchProvider = watchProvider;

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

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }


    public WatchProvider getWatchProvider() {
        return watchProvider;
    }

    public void setWatchProvider(WatchProvider watchProvider) {
        this.watchProvider = watchProvider;
    }
}
