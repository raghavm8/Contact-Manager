package org.raghav.smartcontactmanager.Implementations;

import org.raghav.smartcontactmanager.Entities.User;
import org.raghav.smartcontactmanager.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {

    private UserRepository UserRepository;

    SecurityCustomUserDetailService(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UserRepository.findByEmail(username);
        if (user != null)
            return user;
        else
            throw new UsernameNotFoundException("User " + username + " not found.");
    }
}