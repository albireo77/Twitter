package org.dmx.twitter.repository.tweet.impl;

import org.dmx.twitter.model.Tweet;
import org.dmx.twitter.repository.tweet.TweetRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTweetRepository implements TweetRepository {

    private Map<String, List<Tweet>> repository = new ConcurrentHashMap<>();

    @Override
    public Tweet save(Tweet tweet) {
        List<Tweet> tweetsByUser = repository.computeIfAbsent(tweet.getUserId(), k -> new ArrayList<>());
        tweet.setCreatedAt(LocalDateTime.now());
        tweetsByUser.add(tweet);
        return tweet;
    }

    @Override
    public List<Tweet> getByUser(String userId) {
        List<Tweet> tweetsByUser = repository.get(userId);
        if (tweetsByUser == null) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(tweetsByUser);
        }
    }
}
