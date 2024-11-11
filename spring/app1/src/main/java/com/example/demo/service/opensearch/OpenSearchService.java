package com.example.demo.service.opensearch;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.BulkRequest;
import org.opensearch.client.opensearch.core.BulkResponse;
import org.opensearch.client.opensearch.core.CreateRequest;
import org.opensearch.client.opensearch.core.CreateResponse;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class OpenSearchService {

    @Autowired
    private OpenSearchClient openSearchClient;

    public void createIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder()
                .index("my_index")
                .build();
        CreateIndexResponse createIndexResponse = openSearchClient.indices().create(createIndexRequest);
        System.out.println("Index created: " + createIndexResponse.acknowledged());
    }

    public void createDocument() throws IOException {
        CreateRequest<Technology> createRequest = new CreateRequest.Builder<Technology>()
                .index("my_index")
                .document(new Technology("Example", "An example document"))
                .build();
        CreateResponse createResponse = openSearchClient.create(createRequest);
        System.out.println("Document created: " + createResponse.result());
    }

    public void addDocuments() throws IOException {
        List<Technology> technologies = List.of(
                new Technology("JavaScript", "A high-level, just-in-time compiled, multi-paradigm programming language."),
                new Technology("Java", "A high-level, class-based, object-oriented programming language."),
                new Technology("Golang", "A statically typed, compiled programming language designed at Google."),
                new Technology("Rust", "A multi-paradigm programming language focused on performance and safety."),
                new Technology("Kotlin", "A cross-platform, statically typed, general-purpose programming language.")
        );

        BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();
        for (Technology tech : technologies) {
            bulkRequestBuilder.operations(op -> op.index(idx -> idx
                    .index("my_index")
                    .id(tech.getName())
                    .document(tech)
            ));
        }

        BulkResponse bulkResponse = openSearchClient.bulk(bulkRequestBuilder.build());
        System.out.println("Documents added: " + bulkResponse.items().size());
    }

    public static class Technology {
        private String name;
        private String description;

        public Technology(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }
}