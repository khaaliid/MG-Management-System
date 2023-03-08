package com.mgdonlinestore.managementsystem.product.services;

import com.mgdonlinestore.managementsystem.product.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    public  List<ProductDto> getProducts(Integer pageNo, Integer pageSize, String sortBy);
    public boolean deleteProduct(ProductDto product);
    public ProductDto addProduct(ProductDto product);
}
