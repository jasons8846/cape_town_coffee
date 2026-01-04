package com.jasons.coffeewiki.services.Impl;

import com.jasons.coffeewiki.configs.ConsumerInfoUserDetails;
import com.jasons.coffeewiki.entities.Consumer;
import com.jasons.coffeewiki.repositories.ConsumerRepository;
import com.jasons.coffeewiki.services.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

@Service
public class ConsumerServiceImpl implements ConsumerService, UserDetailsService{

    @Autowired
    private ConsumerRepository consumerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Consumer consumer = consumerRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(consumer.getUsername())
                .password(consumer.getPassword()) // BCrypt hash
                .authorities(consumer.getRoles())
                .build();


    }
}
