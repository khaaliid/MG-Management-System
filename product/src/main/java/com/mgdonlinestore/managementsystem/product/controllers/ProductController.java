package com.mgdonlinestore.managementsystem.product.controllers;

import com.mgdonlinestore.managementsystem.product.dtos.ProductDto;
import com.mgdonlinestore.managementsystem.product.services.ProductService;
import com.mgdonlinestore.managementsystem.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Khaled Elgohary
 */

@Slf4j
@CrossOrigin
@RequestMapping(Constants.PRODUCT_BASE_PATH)
@RestController
public class ProductController implements ProductService {

    @Qualifier("productService")
    @Autowired
    private ProductService productService;


    @Override
    @GetMapping
    public List<ProductDto> getProducts(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String sortBy) {
        log.info("get Products with pagination  - pageNo : {}, pageSize : {}, sortBy : {} ", pageNo, pageSize, sortBy);
        return productService.getProducts(pageNo, pageSize, sortBy);
    }

    @Override
    @DeleteMapping
    public boolean deleteProduct(@RequestBody  ProductDto product) {
        return productService.deleteProduct(product);
    }

    @Override
    @PostMapping
    public ProductDto addProduct(@RequestBody ProductDto product) {
        return productService.addProduct(product);
    }

}
