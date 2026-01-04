package com.jasons.coffeewiki.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface ConsumerService {

    UserDetails loadUserByUsername(String username);
}
