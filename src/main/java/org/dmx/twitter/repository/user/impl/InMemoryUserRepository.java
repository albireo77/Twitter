package org.dmx.twitter.repository.user.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dmx.twitter.error.TwitterError;
import org.dmx.twitter.error.EntityNotFoundException;
import org.dmx.twitter.model.User;
import org.dmx.twitter.repository.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private static final Logger LOG = LogManager.getLogger(InMemoryUserRepository.class);

    private Map<String, User> userRepository = new ConcurrentHashMap<>();
    private Map<User, Set<User>> followerRepository = new ConcurrentHashMap<>();

    @Override
    public User get(String userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.get(userId);
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setUserId(String.valueOf(userRepository.size()));
        }
        userRepository.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Set<User> getAll() {
        return Collections.unmodifiableSet(new HashSet<>(userRepository.values()));
    }

    @Override
    public void addFollower(String userId, String followerId) {
        User user = find(userId);
        User follower = find(followerId);
        Set<User> followers = followerRepository.computeIfAbsent(user, k -> new HashSet<>());
        if (!followers.add(follower)) {
            LOG.warn("User {} is already followed by {}", userId, followerId);
        }
    }

    @Override
    public Set<User> getFollowers(String userId) {
        User user = find(userId);
        Set<User> followers = followerRepository.get(user);
        if (followers == null) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(followers);
        }
    }

    @Override
    public Set<User> getUsersFollowedBy(String followerId) {
        User follower = find(followerId);
        Set<User> users = new HashSet<>();
        for (Map.Entry<User, Set<User>> entry : followerRepository.entrySet()) {
            if (entry.getValue().contains(follower)) {
                users.add(entry.getKey());
            }
        }
        return Collections.unmodifiableSet(users);
    }

    private User find(String userId) {
        User user = get(userId);
        if (user == null) {
            throw new EntityNotFoundException("User " + userId + " not found", TwitterError.USER_NOT_FOUND);
        }
        return user;
    }
}
