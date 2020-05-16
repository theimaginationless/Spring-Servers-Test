package com.example.server2.repositories;

import models.Product;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
}
