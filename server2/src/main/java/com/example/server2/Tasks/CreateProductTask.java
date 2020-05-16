package com.example.server2.Tasks;

import Task.Task;
import com.example.server2.repositories.ProductRepository;
import models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class CreateProductTask extends ProductTask {
    private final Logger logger = LoggerFactory.getLogger(CreateProductTask.class);

    public CreateProductTask(Task task, ProductRepository productRepository, String token) {
        super(task, productRepository, token);
    }

    public void run() {
        logger.debug("Task started with id: " + task.getId() + "; Type: " + task.getTaskType());
        try {
            List<Product> products = task.getProductList();
            List<Product> result = (List<Product>) productRepository.saveAll(products);
//            Optional.ofNullable(products)
//                    .orElse(null)
//                    .forEach(product -> {
//                        Optional<Product> product1 = productRepository.findById(product.getId());
//                        if(product1.isPresent()) {
//                            result.add(product1.orElse(null));
//                        }
//                    });
            returnResponse(result);
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
        }
        logger.debug("Task end with id: " + task.getId() + "; Type: " + task.getTaskType());
    }
}
