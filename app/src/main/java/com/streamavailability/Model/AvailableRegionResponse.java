package com.streamavailability.Model;

import java.util.ArrayList;

public class AvailableRegionResponse {

    private ArrayList<AvailableRegion> results;


    public AvailableRegionResponse(ArrayList<AvailableRegion> results) {
        this.results = results;
    }

    public AvailableRegionResponse() {
    }

    public ArrayList<AvailableRegion> getResults() {
        return results;
    }

    public void setResults(ArrayList<AvailableRegion> results) {
        this.results = results;
    }
}
