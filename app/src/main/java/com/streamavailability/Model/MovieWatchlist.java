package com.streamavailability.Model;

import java.util.List;

public class MovieWatchlist {

    private String title;
    private String image;
    private String description;
    private List<String> platforms;

    public MovieWatchlist() {}

    public MovieWatchlist(String title, String image, String description, List<String> platforms) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.platforms = platforms;
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
}
