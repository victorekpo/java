package com.example.demo;

import com.example.demo.service.opensearch.OpenSearchService;
import com.fasterxml.jackson.databind.JsonNode;
import org.opensearch.client.opensearch._types.OpenSearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.util.Arrays;
import java.util.List;

@Component
public class OpenSearchInitializer implements CommandLineRunner {

    @Autowired
    private OpenSearchService openSearchService;

    @Override
    public void run(String... args) throws Exception {
        // https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/security-java-tls.html
        System.out.println("SSL Context, Available TLS: " + Arrays.toString(SSLContext.getDefault().getSupportedSSLParameters().getProtocols()));
        System.out.println("Creating OpenSearch index and adding documents...");
        try {
            try {
                System.out.println("Clearing index...");
                openSearchService.deleteIndex();
            } catch (OpenSearchException e) {
                System.out.println("Index doesn't exist.");
            }

            try {
                // Example usage of createIndex
                System.out.println("Creating index...");
                openSearchService.createIndex();

                // Example usage of createDocument
                System.out.println("Creating document...");
                openSearchService.createDocument();

                // Example usage of addDocuments
                System.out.println("Adding documents...");
                openSearchService.addDocuments();

                // Example usage of getDocument
                System.out.println("Retrieving document...");
                JsonNode tech = openSearchService.getDocument("example_id");
                System.out.println("Retrieved document: " + tech.properties());

                // Example usage of bulkGetDocuments
                List<String> ids = List.of("JavaScript", "Java", "Golang");
                List<JsonNode> techs = openSearchService.bulkGetDocuments(ids);
                techs.forEach(t -> System.out.println("Bulk retrieved document: " + t.properties()));

                // Example usage of bulkCreateTechDocuments
                System.out.println("Creating more tech documents...");
                List<OpenSearchService.Technology> newTechnologies = List.of(
                        new OpenSearchService.Technology("Python", "A high-level, interpreted programming language."),
                        new OpenSearchService.Technology("Ruby", "A dynamic, open source programming language with a focus on simplicity and productivity.")
                );
                openSearchService.bulkCreateTechDocuments(newTechnologies);
                System.out.println("Bulk created tech documents.");

                // Example usage of bulkCreateDocuments
                System.out.println("Creating more documents...");
                List<String> newDocuments = List.of(
                        "{\"name\": \"SQL\", \"description\": \"A domain-specific language used in programming and designed for managing data held in a relational database management system (RDBMS).\"}",
                        "{\"name\": \"R\", \"description\": \"A programming language and free software environment for statistical computing and graphics supported by the R Foundation for Statistical Computing.\"}"
                );
                openSearchService.bulkCreateDocuments(newDocuments);
                System.out.println("Bulk created documents.");

                // Example usage of bulkDeleteDocuments
                List<String> deleteIds = List.of("Python", "Ruby");
                openSearchService.bulkDeleteDocuments(deleteIds);
                System.out.println("Bulk deleted documents.");

                // Example usage of bulkUpdateDocuments
                List<String> updatedDocuments = List.of(
                        "{\"name\": \"JavaScript\", \"description\": \"An updated description for JavaScript.\"}",
                        "{\"name\": \"Java\", \"description\": \"An updated description for Java.\"}"
                );
                openSearchService.bulkUpdateDocuments(updatedDocuments);
                System.out.println("Bulk updated documents.");
                List<String> ids2 = List.of("JavaScript", "Java", "Golang");
                List<JsonNode> techs2 = openSearchService.bulkGetDocuments(ids2);
                techs2.forEach(t -> System.out.println("Bulk retrieved document: " + t.properties()));


                // Example usage of bulkUpdateTechDocuments
                List<OpenSearchService.Technology> updatedTechnologies = List.of(
                        new OpenSearchService.Technology("JavaScript", "A versatile, high-level programming language commonly used for web development to create interactive effects within web browsers."),
                        new OpenSearchService.Technology("Java", "A high-level, class-based, object-oriented programming language designed to have as few implementation dependencies as possible, widely used for building enterprise-scale applications.")
                );
                openSearchService.bulkUpdateTechDocuments(updatedTechnologies);
                System.out.println("Bulk updated documents.");
                List<String> ids3 = List.of("JavaScript", "Java", "Golang");
                List<JsonNode> techs3 = openSearchService.bulkGetDocuments(ids3);
                techs3.forEach(t -> System.out.println("Bulk retrieved document: " + t.properties()));

            } catch (OpenSearchException e) {
                System.out.println("OpenSearch error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Exception: " + e.getClass().getName());
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            System.out.println("Exception: " + e.getClass().getName());
        }
    }
}

// String... args is a varargs (variable-length argument) parameter in Java.
// It allows you to pass a variable number of arguments of the specified type (in this case, String) to a method. It is syntactic sugar for an array, making it easier to call the method with multiple arguments without explicitly creating an array.
// It is essentially the same as String[] args, but with more flexibility in how you call the method.
// Example
//public void run(String... args) throws Exception {
//    for (String arg : args) {
//        System.out.println(arg);
//    }
//}
//
//// You can call it like this:
//run("arg1", "arg2", "arg3");
//
//// Or like this:
//run(new String[]{"arg1", "arg2", "arg3"});

// More examples: https://github.com/opensearch-project/opensearch-java/blob/main/samples/src/main/java/org/opensearch/client/samples/Bulk.java