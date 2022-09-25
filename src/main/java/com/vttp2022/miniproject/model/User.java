package com.vttp2022.miniproject.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class User {

    public static final Logger logger = LoggerFactory.getLogger(User.class);

    private String username;
    private Map<String, Movie> watchList = new HashMap<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Movie> getWatchList() {
        return watchList;
    }

    public void setWatchList(Map<String, Movie> watchList) {
        this.watchList = watchList;
    }

    public boolean addMovie(Movie movie) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        boolean movieExists = true;
        for (Movie m : watchList.values()) {
            if (!m.equals(movie)) {
                watchList.put(dateTime, movie);
                movieExists = false;
            }
        }
        return movieExists;
    }

    public void deleteMovie(Movie movie) {
        String movieDateTime = null;
        // remove from hashmap by key
        for (Entry<String, Movie> entry : watchList.entrySet()) {
            if (Objects.equals(movie, entry.getValue())) {
                movieDateTime = entry.getKey();
            }
            watchList.remove(movieDateTime);
        }
    }

}
