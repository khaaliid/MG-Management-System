package com.mgdonlinestore.managementsystem.vendor.services;

import com.mgdonlinestore.managementsystem.vendor.dtos.VendorDto;
import com.mgdonlinestore.managementsystem.vendor.model.Vendor;

import java.util.List;

public interface VendorService {
    public VendorDto findOrCreateVendor(VendorDto vendorDto);

    public  VendorDto[] getVendors(Integer offset);
    public  List<Vendor> getVendors(Integer pageNo,Integer pageSize, String sortBy);
    public Boolean deleteVendor(Integer vendorId);
    public VendorDto updateVendor( VendorDto vendorDto);
}
