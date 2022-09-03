package com.vttp2022.miniproject.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Movie {

    private String id;
    private String title;
    private int runTimeInMins;
    private String type;
    private int year;
    private String posterUrl;
    private float rating;
    private BigDecimal ratingCount;
    private String genres;
    private String classification;

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

    public int getRunTimeInMins() {
        return runTimeInMins;
    }

    public void setRunTimeInMins(int runTimeInMins) {
        this.runTimeInMins = runTimeInMins;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public static Movie JsonGetMovie(String movieId, String json) throws IOException {

        Movie movie = new Movie();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonObject jo = jr.readObject();
            JsonObject mainObj = jo.getJsonObject(movieId);
            JsonObject titleObj = mainObj.getJsonObject("title");
            JsonObject imgObj = titleObj.getJsonObject("image");
            JsonObject ratingObj = mainObj.getJsonObject("ratings");

            String id = titleObj.getString("id");
            String title = titleObj.getString("title");
            int runTimeInMins = titleObj.getInt("runningTimeInMinutes");
            String type = titleObj.getString("titleType");
            int year = titleObj.getInt("year");
            String posterUrl = imgObj.getString("url");
            boolean canRate = ratingObj.getBoolean("canRate");
            float rating = ratingObj.getInt("rating");
            BigDecimal ratingCount = ratingObj.getJsonNumber("ratingCount").bigDecimalValue();

            JsonArray ja = mainObj.getJsonArray("genres");
            String classification = mainObj.getString("certificate");

            int numOfGenres = ja.size();
            String[] genresArr = new String[numOfGenres];
            for (int i = 0; i < numOfGenres; i++) {
                genresArr[i] = ja.getString(i);
            }

            movie.setId(id);
            movie.setTitle(title);
            movie.setRunTimeInMins(runTimeInMins);
            movie.setType(type);
            movie.setYear(year);
            movie.setPosterUrl(posterUrl);
            movie.setRating(rating);
            movie.setRatingCount(ratingCount);
            movie.setGenres(Arrays.toString(genresArr));
            movie.setClassification(classification);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }

}
