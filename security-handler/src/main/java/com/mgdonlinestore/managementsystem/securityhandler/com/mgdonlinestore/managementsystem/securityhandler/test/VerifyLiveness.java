package com.mgdonlinestore.managementsystem.securityhandler.com.mgdonlinestore.managementsystem.securityhandler.test;

import com.mgdonlinestore.managementsystem.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author Khaled ElGohary
 */

@Slf4j
@CrossOrigin
@RequestMapping(Constants.SECURITY_BASE_PATH+"/test")
@RestController
public class VerifyLiveness {

    @GetMapping
    public String ping() {
        log.info("ping from the security MS");
        return "Ping .. ok" ;
    }
}
