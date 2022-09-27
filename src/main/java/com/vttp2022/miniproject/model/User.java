package com.vttp2022.miniproject.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    private Map<Movie, String> watchList = new HashMap<>();

    public Map<Movie, String> getWatchList() {
        return watchList;
    }

    public void setWatchList(Map<Movie, String> watchList) {
        this.watchList = watchList;
    }

}
