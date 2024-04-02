package com.blogApp.controllers;

import com.blogApp.exceptions.ApiException;
import com.blogApp.payloads.JwtAuthRequest;
import com.blogApp.payloads.JwtAuthResponse;
import com.blogApp.payloads.UserDto;
import com.blogApp.security.JwtTokenHelper;
import com.blogApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest){

        this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());

        UserDetails userDetails =this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());

        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
    private void authenticate(String username,String password){
        UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username,password);
        try{
            this.authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            System.out.println("Invalid Details ....");
            throw new ApiException("Invalid username or password  !!");

        }
    }
    //register ner user api

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){

        return new ResponseEntity<UserDto>(this.userService.registerNewUser(userDto),HttpStatus.CREATED);
    }
}
