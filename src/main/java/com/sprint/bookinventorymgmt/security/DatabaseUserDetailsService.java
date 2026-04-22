package com.sprint.bookinventorymgmt.security;

import com.sprint.bookinventorymgmt.usermgmt.entity.User;
import com.sprint.bookinventorymgmt.usermgmt.repository.IUserMgmtRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final IUserMgmtRepository userRepository;

    public DatabaseUserDetailsService(IUserMgmtRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole() != null && user.getRole().getPermRole() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getPermRole().toUpperCase()));
        }
        for (String authority : SecurityAuthorities.moduleAuthoritiesFor(user.getUserName())) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
