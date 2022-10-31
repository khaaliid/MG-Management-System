package com.mgdonlinestore.managementsystem.user.auth.services;

import com.mgdonlinestore.managementsystem.user.auth.dtos.AuthResponse;
import com.mgdonlinestore.managementsystem.user.auth.dtos.UserDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticatorHelperI {

    ResponseEntity<AuthResponse> loginHelper(UserDto userDto);
}
