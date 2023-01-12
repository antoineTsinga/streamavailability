package com.streamavailability.Model;

import java.util.List;

public class MovieWatchlist {

    private String movie_name;
    private String movie_image;
    private String movie_description;
    private List<String> movie_type_platform;

    public MovieWatchlist() {}

    public MovieWatchlist(String movie_name, String movie_image, String movie_description, List<String> movie_type_platform) {
        this.movie_name = movie_name;
        this.movie_image = movie_image;
        this.movie_description = movie_description;
        this.movie_type_platform = movie_type_platform;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getMovie_image() {
        return movie_image;
    }

    public void setMovie_image(String movie_image) {
        this.movie_image = movie_image;
    }

    public String getMovie_description() {
        return movie_description;
    }

    public void setMovie_description(String movie_description) {
        this.movie_description = movie_description;
    }

    public List<String> getMovie_type_platform() {
        return movie_type_platform;
    }

    public void setMovie_type_platform(List<String> movie_type_platform) {
        this.movie_type_platform = movie_type_platform;
    }
}
