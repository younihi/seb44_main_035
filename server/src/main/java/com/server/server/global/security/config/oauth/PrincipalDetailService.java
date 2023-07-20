package com.server.server.global.security.config.oauth;

import com.server.server.global.security.config.oauth.dto.SessionUser;
import com.server.server.domain.user.entity.User;
import com.server.server.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository repository;

    public PrincipalDetailService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = repository.findByEmail(username)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다");
                });
        return new SessionUser(principal);
    }
}