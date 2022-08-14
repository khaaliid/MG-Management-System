package com.mgdonlinestore.managementsystem.vendor.controllers;

import com.mgdonlinestore.managementsystem.utils.ToStringUtils;
import com.mgdonlinestore.managementsystem.vendor.dtos.VendorDto;
import com.mgdonlinestore.managementsystem.vendor.model.Vendor;
import com.mgdonlinestore.managementsystem.vendor.services.VendorService;
import com.mgdonlinestore.managementsystem.vendor.utils.Constants;
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
@RequestMapping(Constants.BASE_PATH)
@RestController
public class VendorController implements VendorService {

    @Qualifier("vendorService")
    @Autowired
    private VendorService accountService;

    @Override
    @PostMapping("/findOrCreateVendor")
    public VendorDto findOrCreateVendor(@RequestBody VendorDto vendorDto) {
        log.info("find Vendor if not available then create Vendor based on "
                + "parameters of these params {}", ToStringUtils.toString(vendorDto));
        return accountService.findOrCreateVendor(vendorDto);
    }

    @Override
    @GetMapping
    public VendorDto[] getVendors(@RequestParam Integer offset) {
        log.info("get Vendors from ID {}", offset);
        return accountService.getVendors(offset);
    }

    @Override
    @GetMapping("/page")
    public List<Vendor> getVendors(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String sortBy) {
        log.info("get Vendors with pagination  - pageNo : {}, pageSize : {}, sortBy : {} ", pageNo, pageSize, sortBy);
        return accountService.getVendors(pageNo, pageSize, sortBy);
    }

    @Override
    @DeleteMapping(value="{vendorId}")
    public Boolean deleteVendor(@PathVariable Integer vendorId) {
        log.info("delete Vendor with ID {}", vendorId);
        return accountService.deleteVendor(vendorId);
    }

    @Override
    @PatchMapping(value="{vendorId}")
    public VendorDto updateVendor(@RequestBody VendorDto vendorDto) {
        log.info("update Vendor => {} ", ToStringUtils.toString(vendorDto));
        return accountService.updateVendor(vendorDto);
    }
}
