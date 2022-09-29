package com.vttp2022.miniproject.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vttp2022.miniproject.model.Movie;
import com.vttp2022.miniproject.service.MovieService;
import com.vttp2022.miniproject.service.RedisService;

@Controller
@RequestMapping(path = "/user")
public class UserMoviesController {

    public static final Logger logger = LoggerFactory.getLogger(UserMoviesController.class);

    @Autowired
    private MovieService ms;

    @Autowired
    private RedisService rs;

    @GetMapping("/{username}")
    public String generateTopRatedMovies(@PathVariable String username, Model model) {

        Optional<List<Movie>> optPopularMovies = ms.getPopularMovies();
        Optional<List<Movie>> optTopRatedMovies = ms.getTopRatedMovies();
        Optional<List<Movie>> optNowPlayingMovies = ms.getNowPlayingMovies();
        if (optPopularMovies.isEmpty()) {
            model.addAttribute("popularMovies", new LinkedList<Movie>());
            return "index";
        }
        if (optTopRatedMovies.isEmpty()) {
            model.addAttribute("topRatedMovies", new LinkedList<Movie>());
            return "index";
        }
        if (optNowPlayingMovies.isEmpty()) {
            model.addAttribute("nowPlayingMovies", new LinkedList<Movie>());
            return "index";
        }

        List<String> genreList = new LinkedList<>();
        HashMap<Integer, String> sortedGenre = ms.getGenres();
        for (String i : sortedGenre.values()) {
            genreList.add(i);
        }
        List<Movie> popularMovieList = optPopularMovies.get();
        List<Movie> topRatedMovieList = optTopRatedMovies.get();
        List<Movie> nowPlayingMovieList = optNowPlayingMovies.get();
        model.addAttribute("genreList", genreList);
        model.addAttribute("popularMovies", popularMovieList);
        model.addAttribute("topRatedMovies", topRatedMovieList);
        model.addAttribute("nowPlayingMovies", nowPlayingMovieList);
        model.addAttribute("movieObj", new Movie());
        model.addAttribute("username", username);

        return "userIndex";
    }

    @GetMapping("/{username}/subject/{id}")
    public String generateMovieDetails(Model model,
            @PathVariable(name = "username", required = true) String username,
            @PathVariable(name = "id", required = true) String id) {

        Optional<Movie> optMovie = ms.getMovie(id);
        Optional<List<Movie>> optSimilarMovies = ms.getSimilarMovies(id);
        Optional<List<Movie>> optPopularMovies = ms.getPopularMovies();
        Optional<List<Movie>> optTopRatedMovies = ms.getTopRatedMovies();

        if (optMovie.isEmpty()) {
            model.addAttribute("movie", new Movie());
            return "index";
        }
        if (optSimilarMovies.isEmpty()) {
            model.addAttribute("similarMovies", new LinkedList<Movie>());
            return "index";
        }
        if (optPopularMovies.isEmpty()) {
            model.addAttribute("popularMovies", new LinkedList<Movie>());
            return "index";
        }
        if (optTopRatedMovies.isEmpty()) {
            model.addAttribute("topRatedMovies", new LinkedList<Movie>());
            return "index";
        }

        Movie movie = optMovie.get();
        List<Movie> similarMovieList = optSimilarMovies.get();
        List<Movie> popularMovieList = optPopularMovies.get();
        List<Movie> topRatedMovieList = optTopRatedMovies.get();
        List<String> genreList = new LinkedList<>();
        HashMap<Integer, String> sortedGenre = ms.getGenres();
        for (String i : sortedGenre.values()) {
            genreList.add(i);
        }
        List<String> movieGenreList = movie.getGenres();
        List<String> movieCountryList = movie.getCountries();
        List<String> movieLangList = movie.getLanguages();
        model.addAttribute("genreList", genreList);
        model.addAttribute("movieCountryList", movieCountryList);
        model.addAttribute("movieGenreList", movieGenreList);
        model.addAttribute("movieLangList", movieLangList);
        model.addAttribute("movie", movie);
        model.addAttribute("similarMovies", similarMovieList);
        model.addAttribute("popularMovies", popularMovieList);
        model.addAttribute("topRatedMovies", topRatedMovieList);
        model.addAttribute("username", username);

        return "userMovie";
    }

    @GetMapping("/{username}/genre/{genre}")
    public String generateMoviesByGenre(Model model,
            @PathVariable(name = "username", required = true) String username,
            @PathVariable(name = "genre", required = true) String genre) {

        Optional<List<Movie>> optMoviesByGenre = ms.getMoviesByGenre(genre);

        if (optMoviesByGenre.isEmpty()) {
            model.addAttribute("moviesByGenre", new LinkedList<Movie>());
            return "index";
        }

        List<Movie> moviesByGenreList = optMoviesByGenre.get();

        List<String> genreList = new LinkedList<>();
        HashMap<Integer, String> sortedGenre = ms.getGenres();
        for (String i : sortedGenre.values()) {
            genreList.add(i);
        }
        model.addAttribute("genre", genre);
        model.addAttribute("genreList", genreList);
        model.addAttribute("moviesByGenre", moviesByGenreList);
        model.addAttribute("movieObj", new Movie());
        model.addAttribute("username", username);

        return "userGenre";
    }

    @GetMapping(value = { "/{username}/search", "/{username}/genre/search", "/{username}/subject/search" })
    public String searchMovie(Model model,
            @PathVariable(name = "username", required = true) String username,
            @RequestParam(required = true) String query) {

        String queryString = query;
        logger.info("Query String >>>" + queryString);

        Optional<List<Movie>> optMoviesBySearch = ms.getMoviesBySearch(queryString);
        Optional<List<Movie>> optNowPlayingMovies = ms.getNowPlayingMovies();
        Optional<List<Movie>> optTopRatedMovies = ms.getTopRatedMovies();

        if (optMoviesBySearch.isEmpty()) {
            model.addAttribute("moviesBySearch", new LinkedList<Movie>());
            return "index";
        }
        if (optNowPlayingMovies.isEmpty()) {
            model.addAttribute("nowPlayingMovies", new LinkedList<Movie>());
            return "index";
        }
        if (optTopRatedMovies.isEmpty()) {
            model.addAttribute("topRatedMovies", new LinkedList<Movie>());
            return "index";
        }

        List<Movie> moviesBySearchList = optMoviesBySearch.get();
        List<Movie> nowPlayingMovieList = optNowPlayingMovies.get();
        List<Movie> topRatedMovieList = optTopRatedMovies.get();

        List<String> genreList = new LinkedList<>();
        HashMap<Integer, String> sortedGenre = ms.getGenres();
        for (String i : sortedGenre.values()) {
            genreList.add(i);
        }

        model.addAttribute("queryString", queryString);
        model.addAttribute("genreList", genreList);
        model.addAttribute("moviesBySearch", moviesBySearchList);
        model.addAttribute("nowPlayingMovies", nowPlayingMovieList);
        model.addAttribute("topRatedMovies", topRatedMovieList);
        model.addAttribute("username", username);

        return "userSearch";
    }

    // add to watchlist button
    @PostMapping("/{username}/index/favourite")
    public String addMovieFromIndex(@PathVariable(name = "username", required = true) String username,
            @ModelAttribute Movie m, Model model) {

        String id = m.getId();

        Optional<Movie> optMovie = ms.getMovie(id);
        if (optMovie.isEmpty()) {
            model.addAttribute("movie", new Movie());
            return "index";
        }
        Movie movie = optMovie.get();

        rs.addMovie(username, movie);

        return "redirect:/user/" + username;
    }

    @PostMapping("/{username}/{genre}/favourite")
    public String addMovieFromGenre(Model model,
            @PathVariable(name = "username", required = true) String username,
            @PathVariable(name = "genre", required = true) String genre,
            @ModelAttribute Movie m) {

        String id = m.getId();

        Optional<Movie> optMovie = ms.getMovie(id);
        if (optMovie.isEmpty()) {
            model.addAttribute("movie", new Movie());
            return "index";
        }
        Movie movie = optMovie.get();

        rs.addMovie(username, movie);

        return "redirect:/user/" + username + "/genre/" + genre;
    }

    @PostMapping("/{username}/subject/{id}/favourite")
    public String addMovieFromMovie(Model model,
            @PathVariable(name = "username", required = true) String username,
            @PathVariable(name = "id", required = true) String id) {

        Optional<Movie> optMovie = ms.getMovie(id);
        if (optMovie.isEmpty()) {
            model.addAttribute("movie", new Movie());
            return "index";
        }
        Movie movie = optMovie.get();

        rs.addMovie(username, movie);

        return "redirect:/user/" + username + "/subject/" + id;
    }

    @PostMapping("/{username}/search/favourite")
    public String addMovieFromSearch(Model model, @ModelAttribute Movie m,
            @PathVariable(name = "username", required = true) String username) {

        String id = m.getId();
        String query = m.getQueryString();
        logger.info("id >>>" + id);
        logger.info("query >>>" + query);

        Optional<Movie> optMovie = ms.getMovie(id);
        if (optMovie.isEmpty()) {
            model.addAttribute("movie", new Movie());
            return "index";
        }
        Movie movie = optMovie.get();

        rs.addMovie(username, movie);

        return "redirect:/user/" + username + "/search?query=" + query;
    }

    // remove from watchlist button
    @PostMapping("/{username}/unfavourite")
    public String deleteMovie(@PathVariable(name = "username", required = true) String username,
            @ModelAttribute Movie m, Model model) {

        String id = m.getId();
        logger.info(">>> movie id to delete >>>" + id);

        Optional<Movie> optMovie = ms.getMovie(id);
        if (optMovie.isEmpty()) {
            model.addAttribute("movie", new Movie());
            return "index";
        }
        Movie movie = optMovie.get();

        rs.deleteMovie(username, movie);

        return "redirect:/user/" + username + "/watchlist";
    }

    @RequestMapping(value = "/{username}/watchlist", method = { RequestMethod.POST, RequestMethod.GET })
    public String generateWatchList(@PathVariable(name = "username", required = true) String username,
            @ModelAttribute Movie m, Model model) {

        Map<Movie, String> watchList = rs.getWatchList(username);
        List<Movie> movieList = new ArrayList<Movie>(watchList.keySet());
        List<String> dateTimeList = new ArrayList<String>(watchList.values());

        Optional<List<Movie>> optNowPlayingMovies = ms.getNowPlayingMovies();
        Optional<List<Movie>> optTopRatedMovies = ms.getTopRatedMovies();

        if (optNowPlayingMovies.isEmpty()) {
            model.addAttribute("nowPlayingMovies", new LinkedList<Movie>());
            return "index";
        }
        if (optTopRatedMovies.isEmpty()) {
            model.addAttribute("topRatedMovies", new LinkedList<Movie>());
            return "index";
        }

        List<Movie> nowPlayingMovieList = optNowPlayingMovies.get();
        List<Movie> topRatedMovieList = optTopRatedMovies.get();

        List<String> genreList = new LinkedList<>();
        HashMap<Integer, String> sortedGenre = ms.getGenres();
        for (String i : sortedGenre.values()) {
            genreList.add(i);
        }

        model.addAttribute("genreList", genreList);
        model.addAttribute("nowPlayingMovies", nowPlayingMovieList);
        model.addAttribute("topRatedMovies", topRatedMovieList);
        model.addAttribute("movieObj", new Movie());
        model.addAttribute("username", username);
        model.addAttribute("movieList", movieList);
        model.addAttribute("dateTimeList", dateTimeList);

        return "userWatchlist";
    }

}
