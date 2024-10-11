package com.techie.microservices.product.service;

import com.techie.microservices.product.dto.ProductRequest;
import com.techie.microservices.product.dto.ProductResponse;
import com.techie.microservices.product.modal.Product;
import com.techie.microservices.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .skuCode(productRequest.skuCode())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product created successfully");

        return new ProductResponse(product.getId(),
             product.getSkuCode(),product.getName(), product.getDescription(), product.getPrice());
    }

    public List<ProductResponse> getAllProduct() {
       return productRepository.findAll()
               .stream().map(product -> new ProductResponse(product.getId(),
              product.getSkuCode() ,product.getName(), product.getDescription(), product.getPrice()
               )).toList();
    }
}
