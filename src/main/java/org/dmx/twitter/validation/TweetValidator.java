package org.dmx.twitter.validation;

import org.dmx.twitter.error.ValidationException;
import org.dmx.twitter.model.Tweet;

import static org.dmx.twitter.error.TwitterError.TWEET_TEXT_EMPTY;
import static org.dmx.twitter.error.TwitterError.TWEET_TEXT_TOO_LONG;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TweetValidator implements ConstraintValidator<ValidTweet, Tweet> {

    private static final int TWEET_MAX_LENGTH = 140;

    @Override
    public boolean isValid(Tweet tweet, ConstraintValidatorContext context) {

        String text = tweet.getText();
        if (text == null || text.trim().isEmpty()) {
            throw new ValidationException("Tweet text is empty", TWEET_TEXT_EMPTY);
        }
        if (text.length() > TWEET_MAX_LENGTH) {
            throw new ValidationException("Tweet text length exceeds " + TWEET_MAX_LENGTH + " characters", TWEET_TEXT_TOO_LONG);
        }
        return true;
    }
}
