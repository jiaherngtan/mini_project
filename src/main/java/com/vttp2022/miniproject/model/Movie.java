package com.vttp2022.miniproject.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Movie {

    private String id;
    private String title;
    private String overview;
    // private int runTimeInMins;
    // private String type;
    private String releaseDate;
    private String posterUrl;
    private float rating;
    private BigDecimal ratingCount;
    private String genres;
    // private String classification;
    private List<String> selectedMovieList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    // public int getRunTimeInMins() {
    // return runTimeInMins;
    // }

    // public void setRunTimeInMins(int runTimeInMins) {
    // this.runTimeInMins = runTimeInMins;
    // }

    // public String getType() {
    // return type;
    // }

    // public void setType(String type) {
    // this.type = type;
    // }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    // public String getClassification() {
    // return classification;
    // }

    // public void setClassification(String classification) {
    // this.classification = classification;
    // }

    public List<String> getSelectedMovieList() {
        return selectedMovieList;
    }

    public void setSelectedMovieList(List<String> selectedMovieList) {
        this.selectedMovieList = selectedMovieList;
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
                BigDecimal ratingCount = item.getJsonNumber("vote_count").bigDecimalValue();

                movie.setId(id);
                movie.setTitle(title);
                movie.setOverview(overview);
                movie.setPosterUrl(url + posterUrl);
                movie.setReleaseDate(releaseDate);
                movie.setRating(rating);
                movie.setRatingCount(ratingCount);

                movieList.add(movie);
            }

        }

        return movieList;
    }

}
