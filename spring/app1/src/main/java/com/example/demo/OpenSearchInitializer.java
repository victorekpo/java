package com.example.demo;

import com.example.demo.service.opensearch.OpenSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OpenSearchInitializer implements CommandLineRunner {

    @Autowired
    private OpenSearchService openSearchService;

    @Override
    public void run(String... args) throws Exception {
        openSearchService.createIndex();
        openSearchService.createDocument();
        openSearchService.addDocuments();
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
