package com.vttp2022.miniproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vttp2022.miniproject.model.Movie;

@Service
public class MoviesService {

    public static final Logger logger = LoggerFactory.getLogger(MoviesService.class);

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
            // logger.info("resp body >>> " + resp.getBody());
            topMovieList = Movie.createJsonGetMovies(resp.getBody());

            return Optional.of(topMovieList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // redis service method
    public void saveMovies(List<String> favouriteMovieList) {

        logger.info(">>> movies to be saved on redis: " + favouriteMovieList);
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

        logger.info(">>>" + sortedGenre);

        return sortedGenre;
    }

    // // redis service method
    // public void saveArticles(List<Article> articlesList) {

    // logger.info(">>> articles to be saved on redis: " + articlesList);

    // for (Article a : articlesList) {
    // redisTemplate.opsForValue().set(a.getId(), a);
    // }
    // }

    // // redis service method
    // public Article getArticleById(String id) {

    // Article article = (Article) redisTemplate.opsForValue().get(id);

    // return article;
    // }

}
