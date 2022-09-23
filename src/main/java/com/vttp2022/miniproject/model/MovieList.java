// package com.vttp2022.miniproject.model;

// import java.io.ByteArrayInputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.LinkedList;
// import java.util.List;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;

// import jakarta.json.Json;
// import jakarta.json.JsonArray;
// import jakarta.json.JsonReader;

// @Component
// public class MovieList {

// public static final Logger logger = LoggerFactory.getLogger(MovieList.class);

// private List<Movie> movieList;

// public List<Movie> getMovieList() {
// return movieList;
// }

// public void setMovieList(List<Movie> movieList) {
// this.movieList = movieList;
// }

// public static List<String> JsonMostPopularMovieIds(String json) throws
// IOException {

// List<String> moviesId = new LinkedList<>();

// try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
// JsonReader jr = Json.createReader(is);
// JsonArray ja = jr.readArray();
// // logger.info(">>> json Array: " + ja);

// for (int i = 0; i < ja.size(); i++) {
// // put all ids into a list
// String[] arr = ja.get(i).toString().split("/");
// moviesId.add(arr[2]);
// }
// logger.info("moviesId list>>> " + moviesId);
// }

// return moviesId;
// }

// }
