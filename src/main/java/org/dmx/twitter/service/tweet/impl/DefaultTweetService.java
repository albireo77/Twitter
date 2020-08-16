package org.dmx.twitter.service.tweet.impl;

import org.dmx.twitter.error.EntityNotFoundException;
import org.dmx.twitter.error.TwitterError;
import org.dmx.twitter.model.Tweet;
import org.dmx.twitter.model.User;
import org.dmx.twitter.repository.tweet.TweetRepository;
import org.dmx.twitter.service.tweet.TweetService;
import org.dmx.twitter.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DefaultTweetService implements TweetService {

    private TweetRepository tweetRepository;
    private UserService userService;

    public DefaultTweetService(TweetRepository tweetRepository, UserService userService) {
        this.tweetRepository = tweetRepository;
        this.userService = userService;
    }

    @Override
    public Tweet save(Tweet tweet) {
        User user = userService.get(tweet.getUserId());
        if (user == null) {
            user = new User();
            if (tweet.getUserId() != null) {
                user.setUserId(tweet.getUserId());
            }
            user = userService.save(user);
            tweet.setUserId(user.getUserId());
        }
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> getByUser(String userId) {
        User user = findUser(userId);
        List<Tweet> tweets = tweetRepository.getByUser(user.getUserId());
        return sortTweets(tweets);
    }

    @Override
    public List<Tweet> getForFollower(String followerId) {
        User follower = findUser(followerId);
        List<Tweet> tweets = new ArrayList<>();
        Set<User> users = userService.getUsersFollowedBy(follower.getUserId());
        for (User user : users) {
            tweets.addAll(tweetRepository.getByUser(user.getUserId()));
        }
        return sortTweets(tweets);
    }

    private User findUser(String userId) {
        User user = userService.get(userId);
        if (user == null) {
            throw new EntityNotFoundException("User " + userId + " not found", TwitterError.USER_NOT_FOUND);
        }
        return user;
    }

    private List<Tweet> sortTweets(List<Tweet> tweets) {
        return tweets.stream()
                .sorted(Comparator.comparing(Tweet::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
}
