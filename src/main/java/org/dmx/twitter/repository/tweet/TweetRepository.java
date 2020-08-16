package org.dmx.twitter.repository.tweet;

import org.dmx.twitter.model.Tweet;

import java.util.List;

public interface TweetRepository {

    Tweet save(Tweet tweet);

    List<Tweet> getByUser(String userId);
}
