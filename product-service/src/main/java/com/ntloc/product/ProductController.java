package com.ntloc.product;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping(path = "/{id}")
    public ProductDTO getProduct(@PathVariable("id") Long id) {
        log.info("ProductId {}", id);
        return productService.getProduct(id);
    }

}
