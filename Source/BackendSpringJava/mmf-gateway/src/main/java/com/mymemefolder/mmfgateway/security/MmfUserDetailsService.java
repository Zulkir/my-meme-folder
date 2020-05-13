package com.mymemefolder.mmfgateway.security;

import com.mymemefolder.mmfgateway.users.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MmfUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public MmfUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findUserByName(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
