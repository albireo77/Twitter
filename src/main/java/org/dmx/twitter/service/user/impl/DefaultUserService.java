package org.dmx.twitter.service.user.impl;

import org.dmx.twitter.model.User;
import org.dmx.twitter.repository.user.UserRepository;
import org.dmx.twitter.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DefaultUserService implements UserService {

    private UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User get(String userId) {
        return userRepository.get(userId);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void addFollower(String userId, String followerId) {
        userRepository.addFollower(userId, followerId);
    }

    @Override
    public Set<User> getFollowers(String userId) {
        return userRepository.getFollowers(userId);
    }

    @Override
    public Set<User> getUsersFollowedBy(String followerId) {
        return userRepository.getUsersFollowedBy(followerId);
    }

    @Override
    public Set<User> getAll() {
        return userRepository.getAll();
    }
}
