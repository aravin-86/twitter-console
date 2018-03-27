package co.uk.social.media.twitter.service;

import co.uk.social.media.twitter.domain.Tweet;
import co.uk.social.media.twitter.domain.User;
import co.uk.social.media.twitter.repository.UserRepository;
import co.uk.social.media.twitter.service.util.TwitterConsoleTimeline;

import java.time.LocalDateTime;
import java.util.List;

public class DefaultTwitterService implements TwitterService {

    private UserRepository userRepository;

    public DefaultTwitterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void post(String userName, String message) {
        User user = getUser(userName);
        Tweet tweet = new Tweet(message, LocalDateTime.now());
        userRepository.post(user, tweet);
    }

    @Override
    public List<String> read(String userName) {
        User user = getUser(userName);
        return TwitterConsoleTimeline.getUserTweets(userRepository.read(user), LocalDateTime.now());
    }

    @Override
    public void follow(String userName, String userNameToFollow) {
        User user = getUser(userName);
        User userToFollow = getUser(userNameToFollow);
        userRepository.follow(user, userToFollow);
    }

    @Override
    public List<String> getWall(String userName) {
        User user = getUser(userName);
        return TwitterConsoleTimeline.getWallTimeline(userRepository.getWall(user), LocalDateTime.now());
    }

    private User getUser(String userName) {
        return userRepository.get(userName);
    }
}
