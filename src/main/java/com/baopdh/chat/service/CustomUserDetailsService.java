package com.baopdh.chat.service;

import com.baopdh.chat.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private ProfileRepository profileRepository;
    
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        int id = getUserId(s);
        com.baopdh.thrift.gen.User user = profileRepository.getUser(id);
        
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        
        return User.withUsername(user.getUserName())
                .password(user.getPassword())
                .build();
    }

    private int getUserId(String s) {
        return 1;
    }    
}
