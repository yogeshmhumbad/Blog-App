package com.blogApp.security;

import com.blogApp.entities.User;
import com.blogApp.exceptions.ResourceNotFoundException;
import com.blogApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByEmail(username).orElseThrow(
                            ()->new ResourceNotFoundException("user","email",username));
        return user;
    }
}
