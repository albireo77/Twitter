package org.dmx.twitter.web;

import org.dmx.twitter.error.ApplicationError;
import org.dmx.twitter.model.Tweet;
import org.dmx.twitter.service.tweet.TweetService;
import org.dmx.twitter.validation.ValidTweet;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Validated
@RestController
public class TweetController {

    private static final Logger LOG = LogManager.getLogger(TweetController.class);

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @ApiOperation(value = "Save tweet and its author if necessary")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tweet saved successfully", response = Tweet.class),
            @ApiResponse(code = 400, message = "Tweet text invalid", response = ApplicationError.class)
    })
    @PostMapping("/tweets")
    public ResponseEntity<Tweet> saveTweet(@RequestBody @ValidTweet Tweet tweet) {
        LOG.info("Saving tweet by user {}", tweet.getUserId());
        return ResponseEntity.ok(tweetService.save(tweet));
    }

    @ApiOperation(value = "View a list of all tweets by user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of tweets retrieved successfully", response = Tweet.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "User not found", response = ApplicationError.class)
    })
    @ApiImplicitParam(value = "User identifier", name = "userId", required = true, dataType = "string", paramType = "path")
    @GetMapping("/tweets/byUser/{userId}")
    public ResponseEntity<List<Tweet>> getTweetsByUser(@PathVariable String userId) {
        LOG.info("Getting tweets by user {}", userId);
        List<Tweet> tweets = tweetService.getByUser(userId);
        return ResponseEntity.ok(tweets);
    }

    @ApiOperation(value = "View a list of all tweets from users followed by follower")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of tweets retrieved successfully", response = Tweet.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Follower not found", response = ApplicationError.class)
    })
    @ApiImplicitParam(value = "Follower identifier", name = "followerId", required = true, dataType = "string", paramType = "path")
    @GetMapping("/tweets/forFollower/{followerId}")
    public ResponseEntity<List<Tweet>> getTweetsForFollower(@PathVariable String followerId) {
        LOG.info("Getting tweets for follower {}", followerId);
        List<Tweet> tweets = tweetService.getForFollower(followerId);
        return ResponseEntity.ok(tweets);
    }


}
