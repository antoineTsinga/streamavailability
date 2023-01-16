package com.streamavailability.Model;

import java.util.List;

public class MovieWatchlist {

    private String id;
    private String title;
    private String image;
    private String description;
    private List<String> platforms;
    private String tmdb_id;


    public MovieWatchlist() {}

    public MovieWatchlist(String id, String title, String image, String description, List<String> platforms, String tmdb_id) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.platforms = platforms;
        this.tmdb_id = tmdb_id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTmdb_id() {
        return tmdb_id;
    }

    public void setTmdb_id(String tmdb_id) {
        this.tmdb_id = tmdb_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }


    public static MovieWatchlist movieToMovieWatchlist(Movie movie){

        MovieWatchlist movieWatchlist = new MovieWatchlist();
        movieWatchlist.setDescription(movie.getOverview());
        movieWatchlist.setTmdb_id(movie.getId());
        movieWatchlist.setImage(movie.getPoster_path());
        movieWatchlist.setTitle(movie.getTitle());

        return movieWatchlist;
    }
}
