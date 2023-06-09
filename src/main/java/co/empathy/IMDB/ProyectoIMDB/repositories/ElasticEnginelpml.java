package co.empathy.IMDB.ProyectoIMDB.repositories;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.empathy.IMDB.ProyectoIMDB.models.Movie;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ElasticEnginelpml implements ElasticEngine{

    private final ElasticsearchClient client;

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticEnginelpml.class);

    @Override
    public Movie getDocFromIndex(String name) {

        try {
            if (name==null) {
                throw new RuntimeException("Index name is null");
            }
            GetResponse<Movie> response = client.get(g -> g
                            .index(name)
                            .id("tt0000001"),
                    Movie.class
            );
            Movie movie = response.source();
            if (movie != null) {
                LOGGER.info("Movie title " + movie.getPrimaryTitle());
            }
            return movie;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean createIndex(String name) {

        try {
            if (name==null) {
                return false;
            }
            CreateIndexResponse createIndexResponse = client.indices().create(c -> c.index(name));
            LOGGER.info("Index with name: "+name+" has been created");
            return createIndexResponse.acknowledged();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }


    @Override
    public Boolean deleteIndex(String indexName) {
        try {

            DeleteIndexResponse deleteIndexResponse = client.indices().delete(c -> c.index(indexName));
            if (deleteIndexResponse.acknowledged()){
                LOGGER.info("Deleted");
                return true;
            }
            else
                return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public Boolean indexDoc(String indexName, Movie movie) {
        try {

            GetResponse<Movie> existsResp = client.get(g -> g
                            .index(indexName)
                            .id(movie.getTconst()),
                    Movie.class
            );
            //checks if the movie's id already exists
            if (existsResp.found()) {
                return false;
            } else {
                IndexResponse response = client.index(i -> i
                        .index(indexName)
                        .id(movie.getTconst())
                        .document(movie)
                );
                LOGGER.info("Indexed with version " + response.version());
                return true;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override

    public Boolean indexMultipleDocs(String indexName, List<Movie> movies) {
        boolean response=false;
        if (!movies.isEmpty()) {
            try {
                BulkRequest.Builder br = new BulkRequest.Builder();

                for (Movie movie : movies) {
                    br.operations(op -> op
                            .index(idx -> idx
                                    .index(indexName)
                                    .id(movie.getTconst())
                                    .document(movie)
                            )
                    );
                }

                BulkResponse result = client.bulk(br.build());


                if (result.errors()) {
                    LOGGER.info("Bulk error indexing multiple docs");

                } else response=true;
            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        }
        return response;
    }
}