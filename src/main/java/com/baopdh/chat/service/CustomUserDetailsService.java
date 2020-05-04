package com.baopdh.chat.service;

import com.baopdh.chat.model.CustomPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserIdService userIdService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int id = userIdService.getUserId(username);
        if (id == -1) {
            throw new UsernameNotFoundException("User not found");
        }
        
        com.baopdh.thrift.gen.User user = profileService.getUser(id);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }        
        
        CustomPrincipal customPrincipal = new CustomPrincipal(User.withUsername(username)
                .password(user.getPassword())
                .roles("USER")
                .build());
        customPrincipal.setId(id);
        
        return customPrincipal;
    }
}
