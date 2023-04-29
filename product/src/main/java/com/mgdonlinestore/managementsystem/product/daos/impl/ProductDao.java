package com.mgdonlinestore.managementsystem.product.daos.impl;

import com.mgdonlinestore.managementsystem.product.daos.ProductPaginationSortingMongoRepo;
import com.mgdonlinestore.managementsystem.product.dtos.ProductDto;
import com.mgdonlinestore.managementsystem.product.model.Product;
import com.mgdonlinestore.managementsystem.product.services.ProductService;
import com.mgdonlinestore.managementsystem.utils.BeanMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Khaled ElGohary
 */
@Slf4j
@Service(value = "productDao")
public class ProductDao implements ProductService {

    @Qualifier("productRepo")
    @Autowired
    private ProductPaginationSortingMongoRepo productPaginationSortingMongoRepo;


    @Override
    public List<ProductDto> getProducts(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = null;
        List<ProductDto> products = new ArrayList<ProductDto>();
        if(sortBy==null || sortBy.isEmpty()){
            paging = PageRequest.of(pageNo, pageSize);
        }else{
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        }
        List<Product> productModels = productPaginationSortingMongoRepo.findAll(paging).getContent();

        productModels.forEach(product -> {
            products.add( new ProductDto(product.getId(), product.getProductName()));
        });
        return products;
    }

    @Override
    public boolean deleteProduct(String productId) {
        try {
            Product product = new Product(productId,"");
            productPaginationSortingMongoRepo.delete(product);
        }catch(Exception ex){
            log.error("Error during deleting the product with ID {}",productId,ex);
            return false;
        }
        return true;
    }

    @Override
    public ProductDto addProduct(ProductDto product) {
        Product productModel = new Product(product.getProductId(), product.getProductName());
        productModel = productPaginationSortingMongoRepo.save(productModel);
        product.setProductId(productModel.getId());
        return product;
    }

    @Override
    public ProductDto findOrCreateProduct(ProductDto product) {
        Product productModel = null;
        productModel = productPaginationSortingMongoRepo.findByIdOrProductName(product.getProductId(), product.getProductName());
        if (productModel == null) {
            productModel = productPaginationSortingMongoRepo.save(BeanMapper.to(product, Product.class));
        }
        return BeanMapper.to(productModel, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product productModel = new Product(productDto.getProductId(), productDto.getProductName());
        productModel = productPaginationSortingMongoRepo.save(productModel);
        productDto.setProductId(productModel.getId());
        return productDto;
    }
}
