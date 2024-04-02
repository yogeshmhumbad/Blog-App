package com.blogApp.services;

import com.blogApp.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer userId);
    UserDto getUserById(Integer userId);

    List<UserDto> getAllUser();

    void deleteUser(Integer userId);


}
