package co.empathy.IMDB.ProyectoIMDB.utils;

import co.empathy.IMDB.ProyectoIMDB.models.Movie;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class IMDbReaderTest {
    public static void main(String[] args) throws IOException {
        MultipartFile basics = new MockMultipartFile("basics.tsv", new FileInputStream(new File("src/test/java/co/empathy/academy/IMDb/data/title.basics.tsv")));
        MultipartFile principals = new MockMultipartFile("principals.tsv", new FileInputStream(new File("src/test/java/co/empathy/academy/IMDb/data/title.principals.tsv")));
        MultipartFile ratings = new MockMultipartFile("ratings.tsv", new FileInputStream(new File("src/test/java/co/empathy/academy/IMDb/data/title.ratings.tsv")));
        MultipartFile akas = new MockMultipartFile("akas.tsv", new FileInputStream(new File("src/test/java/co/empathy/academy/IMDb/data/title.akas.tsv")));
        MultipartFile crew = new MockMultipartFile("crew.tsv", new FileInputStream(new File("src/test/java/co/empathy/academy/IMDb/data/title.crew.tsv")));
        IMDbReader reader = new IMDbReader(basics, ratings, akas, crew, principals);
//        BufferedReader line = reader.reader(basics);
//        line.readLine();
//        System.out.println(line.readLine());
        reader.initializeLines();
        Movie movie= reader.readMovie();
        System.out.println(movie);
    }
}
