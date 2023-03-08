package com.mgdonlinestore.managementsystem.vendor.daos.impl;

import com.mgdonlinestore.managementsystem.utils.BeanMapper;
import com.mgdonlinestore.managementsystem.vendor.daos.VendorPaginationSortingRepo;
import com.mgdonlinestore.managementsystem.vendor.dtos.VendorDto;
import com.mgdonlinestore.managementsystem.vendor.model.Vendor;
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
@Service
public class VendorDao{

    @Qualifier("vendorRepo")
    @Autowired
    private VendorPaginationSortingRepo vendorPaginationSortingRepo;


    public VendorDto findOrCreateVendor(VendorDto vendor) {
        Vendor theVendor = null;
        theVendor = vendorPaginationSortingRepo.findByIdOrVendorName(vendor.getId(), vendor.getVendorName());
        if (theVendor == null) {
            theVendor = vendorPaginationSortingRepo.save(BeanMapper.to(vendor, Vendor.class));
        }
        return BeanMapper.to(theVendor, VendorDto.class);
    }


    public VendorDto[] getVendors(Integer offset) {
        List<Vendor> resultVendors = vendorPaginationSortingRepo.findByIdGreaterThan(offset);
        VendorDto vendors[] = null;
        if (resultVendors != null && resultVendors.size() > 0) {
            vendors = new VendorDto[resultVendors.size()];
            for (int i = 0; i < resultVendors.size(); i++) {
                vendors[i] = BeanMapper.to(resultVendors.get(i), VendorDto.class);
            }
        }
        return vendors;
    }


    public Boolean deleteVendor(Integer vendorId) {
        try {
            vendorPaginationSortingRepo.deleteById(vendorId);
        } catch (Exception ex) {
            log.error("Error during deleting Vendor !, {}", ex);
            return false;
        }
        return true;
    }


    public VendorDto updateVendor(VendorDto vendorDto) {
        try {
            Vendor updatedVendor = vendorPaginationSortingRepo.save(BeanMapper.to(vendorDto, Vendor.class));
            vendorDto = BeanMapper.to(updatedVendor, VendorDto.class);
        } catch (Exception ex) {
            log.error("Error during deleting Vendor !, {}", ex);
            return null;
        }
        return vendorDto;
    }

    public List<VendorDto> getVendors(Integer pageNo,Integer pageSize, String sortBy) {
        Pageable paging = null;
        List<VendorDto> vendors = new ArrayList<VendorDto>();
        if(sortBy==null || sortBy.isEmpty()){
            paging = PageRequest.of(pageNo, pageSize);
        }else{
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        }

        vendorPaginationSortingRepo.findAll(paging).getContent().forEach(vendor -> {
                vendors.add(new VendorDto(vendor.getId(),vendor.getVendorName(), vendor.getVendorAddress()));
        });

        return vendors;
    }
}
