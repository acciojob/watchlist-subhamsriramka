package com.driver;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Component
public class MovieRepository {
    List<Movie> movies = new ArrayList<>();
    List<Director> directors = new ArrayList<>();
    Map<Director, List<Movie>> directorListMap = new HashMap<>();

    public void addMovieInDB(Movie movie) {
        movies.add(movie);
    }

    public void addDirectorInDB(Director director) {
        directors.add(director);
    }

    public void addMovieDirectorPair(String movieName, String directorName) {
        Director director = getDirectorByName(directorName);
        Movie movie = getMovieByName(movieName);

        if(director == null || movie == null) return;
        if(!directorListMap.containsKey(director))
            directorListMap.put(director, new ArrayList<>());
        directorListMap.get(director).add(movie);
    }

    public Movie getMovieByName(String name) {
        for(Movie movie: movies) {
            if(movie.getName().equals(name)) return movie;
        }

        return new Movie();
    }

    public Director getDirectorByName(String name) {
        for(Director director: directors) {
            if(director.getName().equals(name)) return director;
        }

        return new Director();
    }

    public List<String> getMoviesByDirectorName(String directorName) {
        Director director = getDirectorByName(directorName);
        List<String> movieList = new ArrayList<>();

        if(director == null || !directorListMap.containsKey(director))
            return movieList;

        for (Movie movie: directorListMap.get(director)) {
            movieList.add(movie.getName());
        }

        return movieList;
    }

    public List<String> findAllMovies() {
        List<String> movieList = new ArrayList<>();

        for (Movie movie: movies)
            movieList.add(movie.getName());

        return movieList;
    }

    public void deleteDirectorByName(String directorName) {
        Director director = getDirectorByName(directorName);
        if(directorListMap.containsKey(director) && director!= null) {
            List<Movie> movieList = directorListMap.get(director);

            for (Movie movie: movieList)
                movies.remove(movie);

            directorListMap.remove(director);
        }

        directors.remove(director);
    }

    public void deleteAllDirectors() {
        for(Director director: directors) {
            if(directorListMap.containsKey(director)) {
                for (Movie movie: directorListMap.get(director))
                    movies.remove(movie);
            }
        }

        directors.clear();
        directorListMap.clear();
    }
}
