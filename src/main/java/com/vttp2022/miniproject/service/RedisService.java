package com.vttp2022.miniproject.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.vttp2022.miniproject.model.Movie;
import com.vttp2022.miniproject.model.User;

@Service
public class RedisService {

    public static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // REST Controller method
    public Optional<User> getUserByUsername(String username) {

        User user = (User) redisTemplate.opsForValue().get(username);
        if (null != user) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public void instantiateUser(String username) {

        boolean userExists = redisTemplate.hasKey(username);
        logger.info(">>> " + username + " exists? >>> " + userExists);
        if (!userExists) {
            User user = new User();
            redisTemplate.opsForValue().set(username, user);
        }
    }

    public Map<Movie, String> getWatchList(String username) {

        Optional<User> optCurrUser = this.getUserByUsername(username);
        User currUser = optCurrUser.get();
        Map<Movie, String> currWatchList = currUser.getWatchList();
        List<Entry<Movie, String>> entryList = new ArrayList<>(currWatchList.entrySet());
        entryList.sort(Entry.comparingByValue());

        Map<Movie, String> sortedCurrWatchList = new LinkedHashMap<>();
        for (int i = entryList.size() - 1; i >= 0; i--) {
            sortedCurrWatchList.put(entryList.get(i).getKey(), entryList.get(i).getValue());
        }

        return sortedCurrWatchList;
    }

    public void addMovie(String username, Movie movie) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        boolean isSGP = TimeZone.getDefault().getID().equals("Asia/Singapore");
        Date date = new Date();
        if (isSGP) {
            date.setTime(date.getTime() + TimeUnit.HOURS.toMillis(8));
        }

        String dateTime = dateFormat.format(date);
        logger.info(">>> dateTime >>>" + dateTime);
        movie.setAddedDateTime(dateTime);

        boolean userExists = redisTemplate.hasKey(username);
        logger.info(">>> " + username + " exists? >>> " + userExists);

        if (!userExists) {
            Map<Movie, String> watchList = new HashMap<>();
            watchList.put(movie, dateTime);
            User user = new User();
            user.setWatchList(watchList);
            redisTemplate.opsForValue().set(username, user);
        } else {
            Optional<User> optCurrUser = this.getUserByUsername(username);
            User currUser = optCurrUser.get();
            Map<Movie, String> currWatchList = currUser.getWatchList();
            List<Movie> movieList = new ArrayList<Movie>(currWatchList.keySet());
            boolean movieExists = false;
            for (Movie m : movieList) {
                logger.info(movie.getId());
                logger.info(m.getId());
                if (m.getId().equals(movie.getId())) {
                    movieExists = true;
                }
            }
            logger.info("movie exists? >>>" + movieExists);
            if (!movieExists) {
                currWatchList.put(movie, dateTime);
                currUser.setWatchList(currWatchList);
                redisTemplate.opsForValue().set(username, currUser);
            }
        }
    }

    public void deleteMovie(String username, Movie movie) {

        logger.info("movie to be deleted >>>" + movie.toString());
        Optional<User> optCurrUser = this.getUserByUsername(username);
        User currUser = optCurrUser.get();
        Map<Movie, String> currWatchList = currUser.getWatchList();
        List<Movie> movieList = new ArrayList<Movie>(currWatchList.keySet());
        for (Movie m : movieList) {
            if (m.getId().equals(movie.getId())) {
                currWatchList.remove(m);
                logger.info(">>> movie removed >>> " + m.toString());
            }
        }
        currUser.setWatchList(currWatchList);
        redisTemplate.opsForValue().setIfPresent(username, currUser);
    }

}
