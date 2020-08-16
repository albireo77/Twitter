package org.dmx.twitter.web;

import org.dmx.twitter.error.ApplicationError;
import org.dmx.twitter.error.Errors;
import org.dmx.twitter.error.EntityNotFoundException;
import org.dmx.twitter.model.User;
import org.dmx.twitter.service.user.UserService;

import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;


@RestController
public class UserController {

    private static final Logger LOG = LogManager.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "View a list of all users")
    @ApiResponse(code = 200, message = "List of all users retrieved successfully", response = User.class, responseContainer = "Set")
    @GetMapping("/users")
    public ResponseEntity<Set<User>> getAllUsers() {
        LOG.info("Getting all users");
        Set<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @ApiOperation(value = "View of a single user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User data retrieved successfully", response = User.class),
            @ApiResponse(code = 404, message = "User not found", response = ApplicationError.class)
    })
    @ApiImplicitParam(value = "User identifier", name = "userId", required = true, dataType = "string", paramType = "path")
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        LOG.info("Getting user {}", userId);
        User user = userService.get(userId);
        if (user == null) {
            throw new EntityNotFoundException("User " + userId + " not found", Errors.USER_NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Save user and assign userId if necessary")
    @ApiResponse(code = 200, message = "User saved successfully", response = User.class)
    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        LOG.info("Saving user {}", user.getUserId());
        User savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @ApiOperation(value = "View a list of user followers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of user followers retrieved successfully", response = User.class, responseContainer = "Set"),
            @ApiResponse(code = 404, message = "User not found", response = ApplicationError.class)
    })
    @ApiImplicitParam(value = "User identifier", name = "userId", required = true, dataType = "string", paramType = "path")
    @GetMapping("/users/{userId}/followers")
    public ResponseEntity<Set<User>> getFollowers(@PathVariable String userId) {
        LOG.info("Getting followers for user {}", userId);
        Set<User> followers = userService.getFollowers(userId);
        return new ResponseEntity<>(followers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Add follower to the user")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "User followerId now follows user userId", response = String.class),
            @ApiResponse(code = 404, message = "User or follower not found", response = ApplicationError.class)
    })
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "User identifier", name = "userId", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(value = "Follower identifier", name = "followerId", required = true, dataType = "string", paramType = "path")
    })
    @PutMapping("/users/{userId}/{followerId}")
    public ResponseEntity<String> addFollower(@PathVariable String userId, @PathVariable String followerId) {
        LOG.info("Adding follower {} for user {}", followerId, userId);
        userService.addFollower(userId, followerId);
        String message = String.format("User %s follows user %s", followerId, userId);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

}
