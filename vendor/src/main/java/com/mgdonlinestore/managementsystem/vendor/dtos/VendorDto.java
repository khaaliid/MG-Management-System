package com.mgdonlinestore.managementsystem.vendor.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Khaled ElGohary
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorDto {

    private Integer id;
    private String vendorName;
    private String vendorAddress;
}
