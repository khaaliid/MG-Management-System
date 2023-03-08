package com.mgdonlinestore.managementsystem.product.daos;//package com.mgdonlinestore.managementsystem.product.daos;

import com.mgdonlinestore.managementsystem.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Khaled ElGohary
 */
@Repository(value = "productRepo")
public interface ProductPaginationSortingMongoRepo extends MongoRepository<Product, Integer> {

    public Page<Product> findAll(Pageable page);
}
