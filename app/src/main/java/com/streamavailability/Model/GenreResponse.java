package com.streamavailability.Model;

import java.util.ArrayList;

public class GenreResponse {
    private ArrayList<Genre> genres;


    public GenreResponse() {
    }
    public GenreResponse(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
}
