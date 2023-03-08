package com.mgdonlinestore.managementsystem.product.services.impl;

import com.mgdonlinestore.managementsystem.product.daos.ProductPaginationSortingMongoRepo;
import com.mgdonlinestore.managementsystem.product.daos.impl.ProductDao;
import com.mgdonlinestore.managementsystem.product.dtos.ProductDto;
import com.mgdonlinestore.managementsystem.product.model.Product;
import com.mgdonlinestore.managementsystem.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public boolean deleteProduct(ProductDto product) {
        return productDao.deleteProduct(product);
    }

    @Override
    public ProductDto addProduct(ProductDto product) {
        return productDao.addProduct(product);
    }
}
