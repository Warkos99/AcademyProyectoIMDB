package co.empathy.IMDB.ProyectoIMDB.services;

import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.empathy.IMDB.ProyectoIMDB.models.Movie;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ElasticService {

    /**
     *
     * @param name, the index name
     * @return the index if it exists
     */
    Movie getDocFromIndex(String name);

    /**
     *
     * @param name, the index name that will be deleted
     * @return true is the index was successfully deleted
     */
    Boolean deleteIndex(String name);
    /**
     *
     * @param indexName, the index name
     * @param movie, the movie that will be added to the index
     * @return true if it was successfully added
     */

    Boolean indexDoc(String indexName, Movie movie);

    /**
     * @param basicsFile,     the tsv file which contains basic´s info
     * @param ratingFile,     the tsv file which contains rating´s info
     * @param akaFile,        the tsv file which contains akas`s info
     * @param crewFile,       the tsv file which contains crew´s info
     * @param principalsFile, he tsv file which contains principal´s info
     * @throws IOException
     */
    void indexIMDbData(MultipartFile basicsFile, MultipartFile ratingFile, MultipartFile akaFile, MultipartFile crewFile, MultipartFile principalsFile) throws IOException;

}

