package com.vttp2022.miniproject.controller;

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
import com.vttp2022.miniproject.model.MovieList;
import com.vttp2022.miniproject.service.MoviesService;

@Controller
public class MoviesController {

    public static final Logger logger = LoggerFactory.getLogger(MoviesController.class);

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private MovieList movieList;

    @GetMapping("/")
    public String generateMostPopularMovies(Model model) {

        Optional<List<Movie>> optArt = moviesService.getMostPopularMovies();
        if (optArt.isEmpty()) {
            model.addAttribute("mostPopularMovieList", new LinkedList<Movie>());
            return "index";
        }

        List<Movie> mostPopularMovieList = optArt.get();
        movieList.setMovieList(mostPopularMovieList);

        model.addAttribute("mostPopularMovieList", mostPopularMovieList);
        // model.addAttribute("article", new Movie());

        return "index";
    }

    // @PostMapping("/articles")
    // public String selectedArticles(@ModelAttribute Article a) {

    // List<String> selectedListId = a.getSelectedArticlesList();

    // logger.info("output of form submission >>> " + selectedListId);
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
    // ns.saveArticles(selectedList);

    // return "redirect:/?";
    // }

}
