package org.pronsky.transfer_service.service.security;

import lombok.RequiredArgsConstructor;
import org.pronsky.transfer_service.data.entity.User;
import org.pronsky.transfer_service.data.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityService {

    private final UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User create(User user) {
        if (user.getRole().equals(Role.ROLE_USER) && repository.existsByPrimaryEmail(user.getUsername())) {
            throw new SecurityException("The user with this email already exists");
        }
        return save(user);
    }

    public User getByUsername(String username) {
        return repository.findByPrimaryEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var primaryEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(primaryEmail);
    }

}
