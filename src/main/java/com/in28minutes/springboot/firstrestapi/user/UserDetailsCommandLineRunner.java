package com.in28minutes.springboot.firstrestapi.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner{

    private Logger logger = LoggerFactory.getLogger(getClass());

    private UserDetailsRepository repository;

    public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(new UserDetails("Rafael", "Admin"));
        repository.save(new UserDetails("Bernardo", "Admin"));
        repository.save(new UserDetails("Joao", "User"));
        repository.save(new UserDetails("Jose", "User"));

        List<UserDetails> users = repository.findAll();
        users.forEach(user -> logger.info(user.toString()));

        List<UserDetails> admins = repository.findByRole("Admin");
        admins.forEach(user -> logger.info(user.toString()));
    }
    
}
