package com.iconiq.demo.security;

import com.google.common.collect.ImmutableSet;
import com.iconiq.demo.model.entity.StockUser;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@EqualsAndHashCode
public class AppUser extends org.springframework.security.core.userdetails.User {

    public AppUser(StockUser stockUser) {
        super(stockUser.getUserName(),
                stockUser.getPassword(),
                true,
                true,
                true,
                true,
                ImmutableSet.of(new SimpleGrantedAuthority("create")));
    }
}
