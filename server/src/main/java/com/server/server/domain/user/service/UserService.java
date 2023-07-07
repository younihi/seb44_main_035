package com.server.server.domain.user.service;

import com.server.server.domain.user.entity.User;
import com.server.server.domain.user.repository.UserRepository;
import com.server.server.global.exception.BusinessLogicException;
import com.server.server.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
    public User findUser(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

}
