package com.iconiq.demo.security;

import com.iconiq.demo.model.entity.StockUser;
import com.iconiq.demo.persistance.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        if(StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("Username cannot be empty");
        }

        StockUser stocksUser = userRepository.getByUserName(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Player " + username + " doesn't exists"));

         return new AppUser(stocksUser);
    }
}
