package com.example.server2.Tasks;

import Task.Task;
import com.example.server2.repositories.ProductRepository;
import models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RemoveProductTask extends ProductTask {
    private final Logger logger = LoggerFactory.getLogger(RemoveProductTask.class);

    public RemoveProductTask(Task task, ProductRepository productRepository, String token) {
        super(task, productRepository, token);
    }

    public void run() {
        logger.debug("Task started with id: " + task.getId() + "; Type: " + task.getTaskType());
        try {
            List<Product> products = task.getProductList();
            Optional.ofNullable(products)
                    .orElse(null)
                    .forEach(product -> productRepository.deleteAll(products));
            returnResponse(new ArrayList<Product>());
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
        }
        logger.debug("Task end with id: " + task.getId() + "; Type: " + task.getTaskType());
    }
}
