package com.vttp2022.miniproject.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vttp2022.miniproject.model.MovieList;

@Service
public class MoviesService {

    public static final Logger logger = LoggerFactory.getLogger(MoviesService.class);

    private static String URL = "https://imdb8.p.rapidapi.com/title";
    // private static String apiKey = System.getenv("CRYPTO_COMPARE_API_KEY");
    private static String apiKey = "5d1c02b6d5mshd105fcca95c7ae7p15e30bjsn4bf5d570b94b";

    public Optional<List<String>> getMostPopularMovieIds() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://imdb8.p.rapidapi.com/title/get-most-popular-movies?homeCountry=US&purchaseCountry=US&currentCountry=US"))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        List<String> movieIds = new LinkedList<>();
        HttpResponse<String> resp = null;

        try {
            resp = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("resp body >>>" + resp.body());
            movieIds = MovieList.JsonMostPopularMovieIds(resp.body());
            return Optional.of(movieIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
