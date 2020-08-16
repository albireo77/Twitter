package org.dmx.twitter.service.tweet;

import org.dmx.twitter.model.Tweet;

import java.util.List;

public interface TweetService {

    Tweet save(Tweet tweet);

    List<Tweet> getByUser(String userId);

    List<Tweet> getForFollower(String followerId);
}
