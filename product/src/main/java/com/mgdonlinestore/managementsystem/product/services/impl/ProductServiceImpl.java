package com.mgdonlinestore.managementsystem.product.services.impl;

import com.mgdonlinestore.managementsystem.product.daos.impl.ProductDao;
import com.mgdonlinestore.managementsystem.product.dtos.ProductDto;
import com.mgdonlinestore.managementsystem.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Khaled ElGohary
 */
@Service(value = "productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public List<ProductDto> getProducts(Integer pageNo, Integer pageSize, String sortBy) {
       return productDao.getProducts(pageNo, pageSize, sortBy);
    }

    @Override
    public boolean deleteProduct(String productId) {
        return productDao.deleteProduct(productId);
    }

    @Override
    public ProductDto addProduct(ProductDto product) {
        return productDao.addProduct(product);
    }

    @Override
    public ProductDto findOrCreateProduct(ProductDto product) {
        return productDao.findOrCreateProduct(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        return productDao.updateProduct(productDto);
    }
}
