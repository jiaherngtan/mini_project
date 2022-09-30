package com.vttp2022.miniproject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vttp2022.miniproject.model.Movie;
import com.vttp2022.miniproject.model.User;
import com.vttp2022.miniproject.service.RedisService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
public class MoviesRESTController {

    @Autowired
    RedisService rs;

    @GetMapping(path = "user/{username}/item", produces = "application/json")
    public ResponseEntity<Map<String, String>> getUserByUsername(@PathVariable String username) {

        try {
            Optional<User> optUser = rs.getUserByUsername(username);
            User user = optUser.get();
            Map<Movie, String> watchList = user.getWatchList();
            List<Movie> movieList = new ArrayList<Movie>(watchList.keySet());
            Map<String, String> movieMap = new HashMap<>();

            for (Movie m : movieList) {
                movieMap.put(m.getAddedDateTime(), m.getTitle());
            }

            ResponseEntity<Map<String, String>> resp = ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(movieMap);

            return resp;

        } catch (Exception e) {

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put(username, "User or user content not found");

            ResponseEntity<Map<String, String>> errResp = ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorMap);

            return errResp;
        }

    }

    @GetMapping(path = "user/{username}/itemV2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getUserByUsername2(@PathVariable String username) {

        try {
            Optional<User> optUser = rs.getUserByUsername(username);
            User user = optUser.get();
            Map<Movie, String> watchList = user.getWatchList();
            List<Movie> movieList = new ArrayList<Movie>(watchList.keySet());
            List<String> result = new LinkedList<>();

            for (Movie m : movieList) {

                JsonObject resp = Json.createObjectBuilder()
                        .add(username, Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("addedDateTime", m.getAddedDateTime())
                                        .add("title", m.getTitle())))
                        .build();

                result.add(resp.toString());
            }
            return ResponseEntity.ok(result);

        } catch (Exception e) {

            List<String> errResult = new LinkedList<>();

            JsonObject errResp = Json.createObjectBuilder()
                    .add("error", "User or user content not found")
                    .build();

            errResult.add(errResp.toString());

            return ResponseEntity.internalServerError().body(errResult);
        }
    }
}
