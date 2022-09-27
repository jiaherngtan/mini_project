package com.vttp2022.miniproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vttp2022.miniproject.model.Movie;

@Service
public class MovieService {

    public static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    // private static String apiKey = System.getenv("CRYPTO_COMPARE_API_KEY");
    private static String apiKey = "e11b4dc364918cc9f69fdd305d85ff8c";

    public Optional<List<Movie>> getPopularMovies() {

        String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/movie/popular?")
                .queryParam("api_key", apiKey)
                .queryParam("language", "en")
                .queryParam("page", "1")
                .toUriString();

        List<Movie> popularMovieList = new LinkedList<>();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.getForEntity(url, String.class);
            // logger.info("resp body >>> " + resp.getBody());
            popularMovieList = Movie.createJsonGetMovies(resp.getBody());

            return Optional.of(popularMovieList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    public Optional<List<Movie>> getTopRatedMovies() {

        String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/movie/top_rated?")
                .queryParam("api_key", apiKey)
                .queryParam("language", "en")
                .queryParam("page", "1")
                .toUriString();

        List<Movie> topMovieList = new LinkedList<>();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.getForEntity(url, String.class);
            topMovieList = Movie.createJsonGetMovies(resp.getBody());

            return Optional.of(topMovieList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<Movie>> getNowPlayingMovies() {
        String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/movie/now_playing?")
                .queryParam("api_key", apiKey)
                .queryParam("language", "en")
                .queryParam("page", "1")
                .toUriString();

        List<Movie> topMovieList = new LinkedList<>();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.getForEntity(url, String.class);
            topMovieList = Movie.createJsonGetMovies(resp.getBody());

            return Optional.of(topMovieList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<Movie>> getSimilarMovies(String id) {

        String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/movie/" + id + "/similar?")
                .queryParam("api_key", apiKey)
                .queryParam("language", "en")
                .queryParam("page", "1")
                .toUriString();

        List<Movie> similarMovieList = new LinkedList<>();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.getForEntity(url, String.class);
            similarMovieList = Movie.createJsonGetMovies(resp.getBody());

            return Optional.of(similarMovieList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Movie> getMovie(String id) {

        String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/movie/" + id + "?")
                .queryParam("api_key", apiKey)
                .queryParam("language", "en")
                .toUriString();

        Movie movie = new Movie();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.getForEntity(url, String.class);
            movie = Movie.createJsonGetMovieDetails(resp.getBody());

            return Optional.of(movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public HashMap<Integer, String> getGenres() {

        HashMap<Integer, String> genres = new HashMap<>();

        genres.put(28, "Action");
        genres.put(12, "Adventure");
        genres.put(16, "Animation");
        genres.put(35, "Comedy");
        genres.put(80, "Crime");
        genres.put(99, "Documentary");
        genres.put(18, "Drama");
        genres.put(10751, "Family");
        genres.put(14, "Fantasy");
        genres.put(36, "History");
        genres.put(27, "Horror");
        genres.put(10402, "Music");
        genres.put(9648, "Mystery");
        genres.put(10749, "Romance");
        genres.put(878, "Science Fiction");
        genres.put(10770, "TV Movie");
        genres.put(53, "Thriller");
        genres.put(10752, "War");
        genres.put(37, "Western");

        List<Entry<Integer, String>> entryList = new ArrayList<>(genres.entrySet());
        entryList.sort(Entry.comparingByValue());

        HashMap<Integer, String> sortedGenre = new LinkedHashMap<>();
        for (Entry<Integer, String> entry : entryList) {
            sortedGenre.put(entry.getKey(), entry.getValue());
        }

        return sortedGenre;

    }

    public Optional<List<Movie>> getMoviesByGenre(String genre) {

        HashMap<Integer, String> genres = new HashMap<>();
        String id = null;
        genres = this.getGenres();

        for (Entry<Integer, String> entry : genres.entrySet()) {
            if (entry.getValue().equals(genre)) {
                id = entry.getKey().toString();
            }
        }

        String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/movie/" + id + "/similar?")
                .queryParam("api_key", apiKey)
                .queryParam("language", "en")
                .queryParam("page", "1")
                .toUriString();

        List<Movie> similarMovieList = new LinkedList<>();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.getForEntity(url, String.class);
            similarMovieList = Movie.createJsonGetMovies(resp.getBody());

            return Optional.of(similarMovieList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<Movie>> getMoviesBySearch(String queryString) {

        String url = UriComponentsBuilder.fromUriString("https://api.themoviedb.org/3/search/movie?")
                .queryParam("api_key", apiKey)
                .queryParam("language", "en")
                .queryParam("query", queryString)
                .queryParam("page", "1")
                .queryParam("include_adult", "false")
                .toUriString();

        String urlUpdate = url.replace("%20", " ");

        List<Movie> searchMovieList = new LinkedList<>();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.getForEntity(urlUpdate, String.class);
            // logger.info("resp body >>> " + resp.getBody());
            searchMovieList = Movie.createJsonGetSearchMovies(resp.getBody());

            return Optional.of(searchMovieList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
