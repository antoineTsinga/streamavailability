package com.streamavailability.Model;

import java.io.Serializable;

public class Category implements Serializable {

    private String name;
    private String tmdb_id;

    public Category(String tmdb_id) {
        this.tmdb_id = tmdb_id;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTmdb_id() {
        return tmdb_id;
    }

    public void setTmdb_id(String tmdb_id) {
        this.tmdb_id = tmdb_id;
    }
}
