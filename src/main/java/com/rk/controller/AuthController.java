package com.rk.controller;

import com.rk.dto.request.user.RegisterRequestDTO;
import com.rk.dto.request.user.UserRequestDTO;
import com.rk.dto.response.user.UserResponseDTO;
import com.rk.exception.CustomException;
import com.rk.model.User;
import com.rk.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?>register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) throws CustomException {
        User user=userService.register(registerRequestDTO);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?>login(@Valid @RequestBody UserRequestDTO userRequestDTO) throws CustomException {
        UserResponseDTO userResponseDTO=userService.login(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
}
