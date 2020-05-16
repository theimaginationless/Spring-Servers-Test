package com.example.server2.controllers;

import Task.Task;
import models.Product;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@RestController
@RequestMapping("api/")
public class ProductController {
    ConcurrentLinkedDeque<Task> tasksQueue;
    ConcurrentHashMap<Integer, List<Product>> results;

    @PostMapping("/execute")
    public List<Product> getProducts(Task task) throws InterruptedException {
        tasksQueue.add(task);
        this.wait(10000); // Max processing time is 10 sec
        return results.get(task.hashCode());
    }

    @GetMapping("/getATask")
    public Task getTask() {
        return tasksQueue.poll();
    }

    @PostMapping("/putAResult")
    public void putResult(@Valid @RequestBody List<Product> productList) {
        results.put(productList.hashCode(), productList);
        this.notify();
    }
}
