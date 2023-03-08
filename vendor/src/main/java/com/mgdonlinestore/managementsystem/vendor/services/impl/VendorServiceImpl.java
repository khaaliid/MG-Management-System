package com.mgdonlinestore.managementsystem.vendor.services.impl;

import com.mgdonlinestore.managementsystem.utils.BeanMapper;
import com.mgdonlinestore.managementsystem.vendor.daos.VendorPaginationSortingRepo;
import com.mgdonlinestore.managementsystem.vendor.daos.impl.VendorDao;
import com.mgdonlinestore.managementsystem.vendor.dtos.VendorDto;
import com.mgdonlinestore.managementsystem.vendor.model.Vendor;
import com.mgdonlinestore.managementsystem.vendor.services.VendorService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service(value = "vendorService")
public class VendorServiceImpl implements VendorService {

    @Autowired
    VendorDao vendorDao;

    @Override
    public VendorDto findOrCreateVendor(VendorDto vendorDto) {
        return vendorDao.findOrCreateVendor(vendorDto);
    }

    @Override
    public VendorDto[] getVendors(Integer offset) {
        return vendorDao.getVendors(offset);
    }

    @Override
    public List<VendorDto> getVendors(Integer pageNo, Integer pageSize, String sortBy) {
        return vendorDao.getVendors(pageNo,pageSize,sortBy);
    }

    @Override
    public Boolean deleteVendor(Integer vendorId) {
        return vendorDao.deleteVendor(vendorId);
    }

    @Override
    public VendorDto updateVendor(VendorDto vendorDto) {
        return vendorDao.updateVendor(vendorDto);
    }
}
