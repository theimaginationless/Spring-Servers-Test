package com.example.server2.Tasks;

import Task.Task;
import com.example.server2.repositories.ProductRepository;
import models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class GetAllProductsTask extends ProductTask {
    private final Logger logger = LoggerFactory.getLogger(GetAllProductsTask.class);

    public GetAllProductsTask(Task task, ProductRepository productRepository, String token) {
        super(task, productRepository, token);
    }

    public void run() {
        logger.debug("Task started with id: " + task.getId() + "; Type: " + task.getTaskType());
        List<Product> result = productRepository.findAll();
        returnResponse(result);
        logger.debug("Task end with id: " + task.getId() + "; Type: " + task.getTaskType());
    }
}
