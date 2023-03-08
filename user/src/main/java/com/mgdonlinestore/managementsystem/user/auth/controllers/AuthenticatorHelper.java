package com.mgdonlinestore.managementsystem.user.auth.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgdonlinestore.managementsystem.user.auth.dtos.AuthResponse;
import com.mgdonlinestore.managementsystem.user.auth.dtos.UserDto;
import com.mgdonlinestore.managementsystem.user.auth.services.AuthenticatorHelperI;
import com.mgdonlinestore.managementsystem.utils.Constants;
import com.mgdonlinestore.managementsystem.utils.ToStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * @author Khaled ElGohary
 */
@Slf4j
@CrossOrigin
@RequestMapping(Constants.USER_BASE_PATH)
@RestController
public class AuthenticatorHelper implements AuthenticatorHelperI {

    private RestTemplate restTemplate;

    @Value("${KEYCLOAK_BASIC_AUTH_HEADER}")
    private String keyCloackBasicAuthHeader;

    @Value("${KEYCLOAK_TOKEN_URL}")
    String getTokenUrl;

    @Value("${KEYCLOAK_USER_INFO_URL}")
    String getUserUrl;

    @Override
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> loginHelper(@RequestBody UserDto userDto) {
        log.info("> authinticate and get the bearer token for user :  {}", ToStringUtils.toString(userDto));

        restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", keyCloackBasicAuthHeader);

        MultiValueMap<String, String> GetTokenHeadersForm = new LinkedMultiValueMap<>();
        GetTokenHeadersForm.add("username",userDto.getUserName());
        GetTokenHeadersForm.add("password",userDto.getPassword());
        GetTokenHeadersForm.add("grant_type","password");


        HttpEntity<MultiValueMap<String, String>> getTokenRequestEntity = new HttpEntity<>(GetTokenHeadersForm, headers);
        ResponseEntity<AuthResponse> response=null;
        try{
            ResponseEntity<AuthResponse> responseObj = this.restTemplate.exchange(getTokenUrl, HttpMethod.POST, getTokenRequestEntity, AuthResponse.class);

            String token = responseObj.getBody().getAccess_token();
            log.debug("Got the token "+token);

            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer "+token);
            ResponseEntity<String> userDetailsResponse = this.restTemplate.exchange(getUserUrl, HttpMethod.GET,new HttpEntity<>( headers ), String.class);
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(userDetailsResponse.getBody());
            responseObj.getBody().setRoles(objectMapper.convertValue(jsonNode.get("roles"), ArrayList.class));
            response = ResponseEntity.status(responseObj.getStatusCode()).body(responseObj.getBody());

        }catch ( HttpClientErrorException e) {
            log.error("Error during authentication ..", e);
            if(!e.getResponseBodyAsString().isEmpty()) {
                AuthResponse authResponse = new AuthResponse();
                authResponse.setDescription(e.getResponseBodyAsString());
                response = ResponseEntity.status(e.getStatusCode()).body(authResponse);
            }else{
                response = ResponseEntity.status(e.getStatusCode()).body(null);
            }

        }catch(Exception ex){
            AuthResponse authResponse = new AuthResponse();
            authResponse.setDescription(ex.getMessage());
            response=ResponseEntity.status(500).body(authResponse);

        }
        return response;
    }
}
