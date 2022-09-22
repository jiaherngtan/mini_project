package com.vttp2022.miniproject.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Movie {

    private String id;
    private String imdbId;
    private String title;
    private String overview;
    private int runtime;
    private String releaseDate;
    private String releaseYear;
    private String posterUrl;
    private float rating;
    private BigDecimal ratingCount;
    private List<String> genres;
    private List<String> countries;
    private List<String> languages;
    private List<String> selectedMovieList;
    private BigDecimal budget;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public BigDecimal getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(BigDecimal ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getSelectedMovieList() {
        return selectedMovieList;
    }

    public void setSelectedMovieList(List<String> selectedMovieList) {
        this.selectedMovieList = selectedMovieList;
    }

    public static final Logger logger = LoggerFactory.getLogger(Movie.class);

    public static List<Movie> createJsonGetMovies(String json) throws IOException {

        List<Movie> movieList = new LinkedList<>();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            JsonArray ja = jo.getJsonArray("results");

            for (int i = 0; i < ja.size(); i++) {
                Movie movie = new Movie();
                String url = "https://image.tmdb.org/t/p/original/";

                JsonObject item = ja.getJsonObject(i);
                String id = item.getJsonNumber("id").toString();
                String title = item.getString("title");
                logger.info(">>>" + title);
                String overview = item.getString("overview");
                String posterUrl = item.getString("poster_path");
                String releaseDate = item.getString("release_date");
                float rating = item.getJsonNumber("vote_average").bigDecimalValue().floatValue();
                BigDecimal ratingCount = item.getJsonNumber("vote_count").bigDecimalValue();

                movie.setId(id);
                movie.setTitle(title);
                movie.setOverview(overview);
                movie.setReleaseDate(releaseDate);
                movie.setPosterUrl(url + posterUrl);
                movie.setRating(rating);
                movie.setRatingCount(ratingCount);

                movieList.add(movie);
            }
            logger.info("MOVIE LIST >>> " + movieList);

        }

        return movieList;
    }

    public static Movie createJsonGetMovieDetails(String json) throws IOException {

        Movie movie = new Movie();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            JsonArray jaGenres = jo.getJsonArray("genres");
            List<String> genres = new LinkedList<>();
            JsonArray jaCountries = jo.getJsonArray("production_countries");
            List<String> countries = new LinkedList<>();
            JsonArray jaLang = jo.getJsonArray("spoken_languages");
            List<String> languages = new LinkedList<>();

            for (int i = 0; i < jaGenres.size(); i++) {
                JsonObject item = jaGenres.getJsonObject(i);
                String genre = item.getString("name");
                genres.add(genre);
            }

            for (int i = 0; i < jaCountries.size(); i++) {
                JsonObject item = jaCountries.getJsonObject(i);
                String country = item.getString("name");
                countries.add(country);
            }

            for (int i = 0; i < jaLang.size(); i++) {
                JsonObject item = jaLang.getJsonObject(i);
                String lang = item.getString("name");
                languages.add(lang);
            }

            String url = "https://image.tmdb.org/t/p/original/";

            BigDecimal budget = jo.getJsonNumber("budget").bigDecimalValue();
            String id = jo.getJsonNumber("id").toString();
            String imdbId = jo.getString("imdb_id");
            String title = jo.getString("title");
            String overview = jo.getString("overview");
            String posterUrl = jo.getString("poster_path");
            String releaseDate = jo.getString("release_date");
            String[] releaseDateArr = releaseDate.split("-");
            String releaseYear = releaseDateArr[0];
            int runtime = jo.getInt("runtime");
            float rating = jo.getJsonNumber("vote_average").bigDecimalValue().floatValue();
            BigDecimal ratingCount = jo.getJsonNumber("vote_count").bigDecimalValue();

            movie.setId(id);
            movie.setImdbId(imdbId);
            movie.setTitle(title);
            movie.setOverview(overview);
            movie.setRuntime(runtime);
            movie.setReleaseDate(releaseDate);
            movie.setReleaseYear(releaseYear);
            movie.setPosterUrl(url + posterUrl);
            movie.setRating(rating);
            movie.setRatingCount(ratingCount);
            movie.setGenres(genres);
            movie.setLanguages(languages);
            movie.setCountries(countries);
            movie.setBudget(budget);
        }

        return movie;
    }

}
