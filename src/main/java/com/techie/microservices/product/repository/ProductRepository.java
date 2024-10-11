package com.techie.microservices.product.repository;

import com.techie.microservices.product.modal.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
