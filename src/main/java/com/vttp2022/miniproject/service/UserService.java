package com.vttp2022.miniproject.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.vttp2022.miniproject.model.Movie;
import com.vttp2022.miniproject.model.User;

@Service
public class UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private String username;
    // private Map<String, String> watchList = new HashMap<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // public Map<String, String> getWatchList() {
    // return watchList;
    // }

    // public void setWatchList(Map<String, String> watchList) {
    // this.watchList = watchList;
    // }

    public void createOrLoginUser(String username) {

        boolean userExists = redisTemplate.hasKey(username);
        logger.info(">>> " + username + " exists? >>> " + userExists);

    }

    public void addMovie(String username, Movie movie) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        logger.info(">>> dateTime >>>" + dateTime);

        boolean userExists = redisTemplate.hasKey(username);
        logger.info(">>> " + username + " exists? >>> " + userExists);

        if (!userExists) {
            Map<Movie, String> watchList = new HashMap<>();
            watchList.put(movie, dateTime);
            User user = new User();
            user.setWatchList(watchList);
            redisTemplate.opsForValue().set(username, user);
        } else {
            User currUser = (User) redisTemplate.opsForValue().get(username);
            Map<Movie, String> currWatchList = currUser.getWatchList();
            currWatchList.put(movie, dateTime);
            currUser.setWatchList(currWatchList);
            redisTemplate.opsForValue().set(username, currUser);
        }

    }

}
