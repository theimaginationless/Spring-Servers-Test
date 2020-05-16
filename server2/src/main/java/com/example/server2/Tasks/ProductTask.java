package com.example.server2.Tasks;

import Requests.Response;
import Task.Task;
import com.example.server2.repositories.ProductRepository;
import models.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public abstract class ProductTask implements Runnable {
    protected RestTemplate restTemplate;
    protected ProductRepository productRepository;
    protected static final String PUT_RESULT_URL = "http://127.0.0.1:8081/api/putAResult";
    protected Task task;
    private static final String tokenHeaderName = "token";
    private String token;

    public ProductTask(Task task, ProductRepository productRepository, String token) {
        this.task = task;
        restTemplate = new RestTemplate();
        this.productRepository = productRepository;
        this.token = token;
    }

    protected void returnResponse(List<Product> result) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(tokenHeaderName, token);
        Response response = new Response(task, result);
        HttpEntity<Response> requestBody = new HttpEntity<>(response, httpHeaders);
        restTemplate.postForObject(PUT_RESULT_URL, requestBody, Response.class);
    }
}
