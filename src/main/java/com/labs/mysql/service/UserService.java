package com.labs.mysql.service;

import com.labs.mysql.entity.User;
import com.labs.mysql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(String id) {
        Integer userId = Integer.valueOf(id);
        return userRepository.findById(userId).orElse(new User());
    }
}
