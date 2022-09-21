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
import org.springframework.web.bind.annotation.PostMapping;

import com.vttp2022.miniproject.model.Movie;
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
        if (optPopularMovies.isEmpty()) {
            model.addAttribute("popularMovies", new LinkedList<Movie>());
            return "index";
        }
        if (optTopRatedMovies.isEmpty()) {
            model.addAttribute("topRatedMovies", new LinkedList<Movie>());
            return "index";
        }

        List<String> genreList = new LinkedList<>();
        HashMap<Integer, String> sortedGenre = ms.getGenres();
        for (String i : sortedGenre.values()) {
            genreList.add(i);
        }
        List<Movie> popularMovieList = optPopularMovies.get();
        List<Movie> topRatedMovieList = optTopRatedMovies.get();
        logger.info("Genre List: " + genreList);
        model.addAttribute("genreList", genreList);
        model.addAttribute("popularMovies", popularMovieList);
        model.addAttribute("topRatedMovies", topRatedMovieList);
        model.addAttribute("movie", new Movie());

        return "index";
    }

    @PostMapping("/favourite")
    public String selectedMovies(@ModelAttribute Movie movie) {

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
