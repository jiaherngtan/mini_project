package com.vttp2022.miniproject.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Component
public class MovieList {

    public static final Logger logger = LoggerFactory.getLogger(MovieList.class);

    private List<String> movieList;

    public List<String> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<String> movieList) {
        this.movieList = movieList;
    }

    public static List<String> JsonMostPopularMovieIds(String json) throws IOException {

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader jr = Json.createReader(is);
            JsonArray ja = jr.readArray();
            // JsonObject jo = jr.readObject();
            logger.info(">>> json Object: " + ja);
            // JsonArray jsonArr = jo.getJsonArray();
            // logger.info(">>> json Array: " + jsonArr);

            // for (int i = 0; i < jsonArr.size(); i++) {
            // // generate new article
            // Article article = new Article();

            // JsonObject item = jsonArr.getJsonObject(i);
            // String id = item.getString("id");
            // BigDecimal publishedOn =
            // item.getJsonNumber("published_on").bigDecimalValue();
            // String title = item.getString("title");
            // String url = item.getString("url");
            // String imageUrl = item.getString("imageurl");
            // String body = item.getString("body");
            // String tags = item.getString("tags");
            // String categories = item.getString("categories");

            // article.setId(id);
            // article.setPublishedOn(publishedOn);
            // article.setTitle(title);
            // article.setUrl(url);
            // article.setImageUrl(imageUrl);
            // article.setBody(body);
            // article.setTags(tags);
            // article.setCategories(categories);

            // // add article to list
            // articlesList.add(article);
            // }
        }

        return null;
    }

}
