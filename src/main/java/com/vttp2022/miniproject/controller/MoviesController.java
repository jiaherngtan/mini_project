package com.vttp2022.miniproject.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.vttp2022.miniproject.model.Movie;
import com.vttp2022.miniproject.model.User;
import com.vttp2022.miniproject.service.MoviesService;

@Controller
public class MoviesController {

    public static final Logger logger = LoggerFactory.getLogger(MoviesController.class);

    @Autowired
    private MoviesService ms;

    @GetMapping("/")
    public String generateTopRatedMovies(Model model) {

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
        model.addAttribute("userObj", new User());

        return "index";
    }

    @GetMapping("/subject/{id}")
    public String generateMovieDetails(Model model, @PathVariable String id) {

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
        model.addAttribute("movieObj", new Movie());
        model.addAttribute("userObj", new User());

        return "movie";
    }

    @GetMapping("/genre/{genre}")
    public String generateMoviesByGenre(Model model, @PathVariable String genre) {

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
        model.addAttribute("userObj", new User());

        return "genre";
    }

    // @PostMapping("/search")
    // public String searchMovie(@ModelAttribute Movie movie, Model model) {
    @GetMapping(value = { "/search", "/genre/search", "/subject/search" })
    public String searchMovie(@RequestParam(required = true) String query, Model model) {

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
        model.addAttribute("movieObj", new Movie());
        model.addAttribute("userObj", new User());

        return "search";
    }

    @PostMapping("/user")
    public String redirectToUser(@RequestParam String username) {
        return "redirect:/user/" + username;
    }

    @PostMapping("/main")
    public String redirectToMain() {
        return "redirect:/";
    }

    @PostMapping("/favourite")
    public String selectedMovies(@ModelAttribute Movie movie, Model model) {

        List<String> favouriteMovieList = movie.getSelectedMovieList();

        logger.info("output of form submission >>> " + favouriteMovieList);
        // List<String> allListId = new LinkedList<>();
        // List<Article> allList = al.getArticlesList();
        // logger.info(">>> all list: " + allList);
        // for (Article article : allList) {
        // allListId.add(article.getId());
        // }
        // logger.info("all list ids >>> " + allListId);
        // List<Article> selectedList = new LinkedList<>();

        // logger.info(">>> " + selectedListId.size());
        // logger.info(">>> " + allList.size());

        // for (int i = 0; i < selectedListId.size(); i++) {
        // for (int j = 0; j < allList.size(); j++) {
        // logger.info(">>> " + selectedListId.get(i) + "===" + allList.get(j).getId());
        // if (selectedListId.get(i) == allList.get(j).getId()) {
        // logger.info(">>> to add: " + allList.get(j));
        // selectedList.add(allList.get(j));
        // }
        // }
        // }
        // // why selectedList is empty although there is a match?
        // logger.info(">>> selected list: " + selectedList);
        ms.saveMovies(favouriteMovieList);

        return "redirect:/?";
    }

}
