package com.example.demo.service.opensearch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.node.ObjectNode;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.*;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.CreateIndexResponse;
import org.opensearch.client.opensearch.indices.DeleteIndexRequest;
import org.opensearch.client.opensearch.indices.DeleteIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

    public void deleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder()
                .index("my_index")
                .build();
        DeleteIndexResponse deleteIndexResponse = openSearchClient.indices().delete(deleteIndexRequest);
        System.out.println("Index deleted: " + deleteIndexResponse.acknowledged());
    }

    public void createDocument() throws IOException {
        CreateRequest<Technology> createRequest = new CreateRequest.Builder<Technology>()
                .index("my_index")
                .id("example_id")
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

    public JsonNode getDocument(String id) throws IOException {
        System.out.println("Getting document..");
        GetRequest getRequest = new GetRequest.Builder()
                .index("my_index")
                .id(id)
                .build();
        System.out.println("Get request: " + getRequest.toString());
        GetResponse<JsonNode> getResponse = openSearchClient.get(getRequest, JsonNode.class);
        System.out.println("Get response: " + getResponse.toString());
        System.out.println("Document found: " + getResponse.found());
        System.out.println("Document source: " + getResponse.source());
        System.out.println("Document id: " + getResponse.id());
        System.out.println("Document version: " + getResponse.version());
        System.out.println("Document seqNo: " + getResponse.seqNo());
        return getResponse.source();
    }

    public List<JsonNode> bulkGetDocuments(List<String> ids) throws IOException {
        MgetRequest mgetRequest = new MgetRequest.Builder()
                .index("my_index")
                .ids(ids)
                .build();
        MgetResponse<JsonNode> mgetResponse = openSearchClient.mget(mgetRequest, JsonNode.class);
        return mgetResponse.docs().stream()
                .map(doc -> doc.result().source())
                .collect(Collectors.toList());
    }

    public void bulkCreateDocuments(List<String> technologies) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();

        for (String techJson : technologies) {
            JsonNode techNode = objectMapper.readTree(techJson);
            String id = techNode.get("name").asText(); // Assuming the id is the "name" field

            bulkRequestBuilder.operations(op -> op.index(idx -> idx
                    .index("my_index")
                    .id(id)
                    .document(techNode)
            ));
        }
        openSearchClient.bulk(bulkRequestBuilder.build());
    }

    public void bulkCreateTechDocuments(List<Technology> technologies) throws IOException {
        BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();
        for (Technology tech : technologies) {
            bulkRequestBuilder.operations(op -> op.index(idx -> idx
                    .index("my_index")
                    .id(tech.getName())
                    .document(tech)
            ));
        }
        openSearchClient.bulk(bulkRequestBuilder.build());
    }

    public void bulkDeleteDocuments(List<String> ids) throws IOException {
        BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();
        for (String id : ids) {
            bulkRequestBuilder.operations(op -> op.delete(del -> del
                    .index("my_index")
                    .id(id)
            ));
        }
        openSearchClient.bulk(bulkRequestBuilder.build());
    }

    // string -> arbitrary object (unstructured)
    public void bulkUpdateDocuments(List<String> technologies) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();

        for (String techJson : technologies) {
            JsonNode techNode = objectMapper.readTree(techJson);
            String id = techNode.get("name").asText(); // Assuming the id is the "name" field

            bulkRequestBuilder.operations(op -> op.update(upd -> upd
                    .index("my_index")
                    .id(id)
                    .document(techNode)
            ));
        }
        openSearchClient.bulk(bulkRequestBuilder.build());
    }

    // specific objects -> specific objects (structured)
    public void bulkUpdateTechDocuments(List<Technology> technologies) throws IOException {
        BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();

        for (Technology tech : technologies) {
            String id = tech.getName();
            bulkRequestBuilder.operations(op -> op.update(upd -> upd
                    .index("my_index")
                    .id(id)
                    .document(tech)
            ));
        }
        openSearchClient.bulk(bulkRequestBuilder.build());
    }

    // String -> specific objects (structured)
    public void bulkUpdateStringTechDocuments(List<String> technologies) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BulkRequest.Builder bulkRequestBuilder = new BulkRequest.Builder();

        for (String techJson : technologies) {
            Technology tech = objectMapper.readValue(techJson, Technology.class); // readTree with JsonNode to readValue with Technology JsonNode.get("prop").asText() is the same as techNode.getProp()
            String id = tech.getName();

            bulkRequestBuilder.operations(op -> op.update(upd -> upd
                    .index("my_index")
                    .id(id)
                    .document(tech)
            ));
        }
        openSearchClient.bulk(bulkRequestBuilder.build());
    }

    public static class Technology {
        private String name;
        private String description;

        public Technology(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public Technology() {}

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }
}

// Using JsonNode or ObjectNode or ArrayNode, etc
// JsonNode: This is a general-purpose node that can represent any JSON structure. It is useful when you need to handle various types of JSON data (objects, arrays, values) without modifying the structure.
// ObjectNode: This is a specific type of JsonNode that represents a JSON object. It provides methods to manipulate the JSON object, such as adding or removing fields. It is useful when we know we are dealing with a JSON object and need to modify its structure.
// In this case, since we are working with documents that are likely JSON objects, and we might need to manipulate them, using ObjectNode is appropriate. However, if we only need to read the data without modifying it, JsonNode would suffice.
// Given the context, using ObjectNode is a better choice as it provides more flexibility for potential modifications.
// ObjectNode is specifically designed to represent JSON objects, not arrays. If you need to represent an array, you should use ArrayNode, which is another subclass of JsonNode provided by Jackson.
//
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//
//public class JsonExample {
//    public static void main(String[] args) throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // Creating a JsonNode (read-only)
//        String jsonString = "{\"name\": \"JavaScript\", \"description\": \"A programming language.\"}";
//        JsonNode jsonNode = objectMapper.readTree(jsonString);
//        System.out.println("Original JsonNode: " + jsonNode.toString());
//
//        // Attempting to modify JsonNode (will not work)
//        // jsonNode.put("version", "ES6"); // This will cause a compilation error
//
//        // Creating an ObjectNode (modifiable)
//        ObjectNode objectNode = (ObjectNode) objectMapper.readTree(jsonString);
//        System.out.println("Original ObjectNode: " + objectNode.toString());
//
//        // Modifying ObjectNode
//        objectNode.put("version", "ES6");
//        System.out.println("Modified ObjectNode: " + objectNode.toString());
//    }
//}

// In this example:
// JsonNode is used to read the JSON data, but you cannot modify it.
// ObjectNode is used to read and modify the JSON data by adding a new field "version".