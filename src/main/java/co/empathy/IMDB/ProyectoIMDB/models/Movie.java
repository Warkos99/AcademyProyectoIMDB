package co.empathy.IMDB.ProyectoIMDB.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Movie {

    String tconst;
    String titleType;
    String primaryTitle;
    String originalTitle;
    Boolean isAdult;
    int startYear;
    int endYear;
    int runtimeMinutes;
    String[] genres;
    double averageRating;
    int numVotes;
    List<Aka> akas;
    List<Director> directors;
    List<Starring> starring;
}
