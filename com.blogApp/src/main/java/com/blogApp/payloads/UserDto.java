package com.blogApp.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {

    private int id;
    @NotEmpty
    @Size(min = 4 ,message = "Username must be min of 4 characters")
    private String name;
    @Email(message = "Email is not valid")
    private String email;
    @NotEmpty
    @Size(min = 3 , max = 10 ,message = "Password should be 3 to 10 characters")
    private String password;
    @NotEmpty(message = "about should not be empty")
    private String about;

    private Set<RoleDto> roles = new HashSet<>();

}
