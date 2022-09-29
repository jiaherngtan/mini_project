package com.vttp2022.miniproject.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Movie implements Serializable {

    public static final Logger logger = LoggerFactory.getLogger(Movie.class);

    private String id;
    private String imdbId;
    private String imdbUrl;
    private String title;
    private String overview;
    private int runtime;
    private String releaseDate;
    private String releaseYear;
    private String posterUrl;
    private float rating;
    private String ratingCount;
    private List<String> genres;
    private List<String> countries;
    private List<String> languages;
    private String queryString;
    private String addedDateTime;

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

    public String getImdbUrl() {
        return imdbUrl;
    }

    public void setImdbUrl(String imdbUrl) {
        this.imdbUrl = imdbUrl;
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

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
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

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getAddedDateTime() {
        return addedDateTime;
    }

    public void setAddedDateTime(String addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    @Override
    public String toString() {
        return "Movie [countries=" + countries + ", genres=" + genres + ", id=" + id + ", imdbId=" + imdbId
                + ", imdbUrl=" + imdbUrl + ", languages=" + languages + ", overview=" + overview + ", posterUrl="
                + posterUrl + ", queryString=" + queryString + ", rating=" + rating + ", ratingCount=" + ratingCount
                + ", releaseDate=" + releaseDate + ", releaseYear=" + releaseYear + ", runtime=" + runtime + ", title="
                + title + "]";
    }

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
                String overview = item.getString("overview");
                String posterUrl = item.getString("poster_path");
                String releaseDate = item.getString("release_date");
                float rating = item.getJsonNumber("vote_average").bigDecimalValue().floatValue();
                int scale = (int) Math.pow(10, 1);
                rating = (float) Math.round(rating * scale) / scale;
                BigDecimal ratingCountBigDec = item.getJsonNumber("vote_count").bigDecimalValue();
                String ratingCount = String.format("%1$,.0f", ratingCountBigDec);

                movie.setId(id);
                movie.setTitle(title);
                movie.setOverview(overview);
                movie.setReleaseDate(releaseDate);
                movie.setPosterUrl(url + posterUrl);
                movie.setRating(rating);
                movie.setRatingCount(ratingCount);

                movieList.add(movie);
            }
        }

        return movieList;
    }

    public static List<Movie> createJsonGetSearchMovies(String queryString, String json) throws IOException {

        logger.info(">>> query 2 >>>" + queryString);
        List<Movie> movieList = new LinkedList<>();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            JsonArray ja = jo.getJsonArray("results");

            int length;
            if (ja.size() < 2) {
                length = ja.size();
            } else {
                length = 2;
            }
            logger.info("length >>> " + length);

            for (int i = 0; i < length; i++) {
                Movie movie = new Movie();
                String url = "https://image.tmdb.org/t/p/original/";

                JsonObject item = ja.getJsonObject(i);
                String id = item.getJsonNumber("id").toString();
                String title = item.getString("title");
                String overview = item.getString("overview");
                String posterUrl = item.getString("poster_path");
                String releaseDate = item.getString("release_date");
                float rating = item.getJsonNumber("vote_average").bigDecimalValue().floatValue();
                int scale = (int) Math.pow(10, 1);
                rating = (float) Math.round(rating * scale) / scale;
                BigDecimal ratingCountBigDec = item.getJsonNumber("vote_count").bigDecimalValue();
                String ratingCount = String.format("%1$,.0f", ratingCountBigDec);

                movie.setId(id);
                movie.setTitle(title);
                movie.setOverview(overview);
                movie.setReleaseDate(releaseDate);
                movie.setPosterUrl(url + posterUrl);
                movie.setRating(rating);
                movie.setRatingCount(ratingCount);
                movie.setQueryString(queryString);

                movieList.add(movie);
                logger.info("movie added: " + movie.toString());
            }
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
            int scale = (int) Math.pow(10, 1);
            rating = (float) Math.round(rating * scale) / scale;
            BigDecimal ratingCountBigDec = jo.getJsonNumber("vote_count").bigDecimalValue();
            String ratingCount = String.format("%1$,.0f", ratingCountBigDec);

            movie.setId(id);
            movie.setImdbId(imdbId);
            movie.setImdbUrl("https://www.imdb.com/title/" + imdbId);
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
        }
        return movie;
    }

}
