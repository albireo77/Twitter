package org.dmx.twitter.service.user;

import org.dmx.twitter.model.User;

import java.util.Set;

public interface UserService {

    User get(String userId);

    User save(User user);

    void addFollower(String userId, String followerId);

    Set<User> getFollowers(String userId);

    Set<User> getUsersFollowedBy(String followerId);

    Set<User> getAll();

}
