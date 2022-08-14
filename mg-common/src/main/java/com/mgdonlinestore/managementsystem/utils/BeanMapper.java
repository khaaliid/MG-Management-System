package com.mgdonlinestore.managementsystem.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * @author Khaled ElGohary
 */

@Slf4j
public class BeanMapper {

    public static <SOURCE, DESTINATION> DESTINATION to(SOURCE source,
                                                       Class<DESTINATION> destinationClazz) {

        log.debug("Mapping Source Object to Destination Object of type {}",
                destinationClazz.getSimpleName());

        DESTINATION dto = getInstance(destinationClazz);
        if (dto != null && source != null) {
            BeanUtils.copyProperties(source, dto);
        }
        return dto;
    }

    @SuppressWarnings("deprecation")
    private static <T> T getInstance(Class<T> clazz) {
        try {
            log.debug("Creating new instance of type {}", clazz.getSimpleName());
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Exception while creating instance of class " + clazz.getSimpleName(), e);
        }
        return null;
    }
}
