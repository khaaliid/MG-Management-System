package com.mgdonlinestore.managementsystem.vendor.daos;

import com.mgdonlinestore.managementsystem.vendor.model.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Khaled ElGohary
 */
@Service("vendorRepo")
public interface VendorPaginationSortingRepo extends PagingAndSortingRepository<Vendor, Integer> {


    public Vendor  findByIdOrVendorName(Integer id, String vendorName);
    public List<Vendor> findByIdGreaterThan(Integer id);
    public Page<Vendor> findAll(Pageable page);

}
