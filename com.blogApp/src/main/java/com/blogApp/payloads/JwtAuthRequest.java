package com.blogApp.payloads;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class JwtAuthRequest {
    private String username;
    private String password;
}
