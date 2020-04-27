package com.baopdh.chat.service;

import com.baopdh.chat.repository.ProfileRepository;
import com.baopdh.chat.repository.UserIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserIdRepository userIdRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int id = userIdRepository.getId(username);
        if (id == -1)
            throw new UsernameNotFoundException("User not found");
        
        com.baopdh.thrift.gen.User user = profileRepository.getUser(id);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        
        return User.withUsername(username)
                .password(user.getPassword())
                .build();
    }
}
