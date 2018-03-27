package co.uk.social.media.twitter.repository;

import co.uk.social.media.twitter.domain.Tweet;
import co.uk.social.media.twitter.domain.User;

import java.util.*;

public class MapUserRepository implements UserRepository {

    private Map<User, LinkedList<Tweet>> tweetsMap = new HashMap<>();
    private Map<String, User> userMap = new HashMap<>();

    @Override
    public User get(String userName) {
        return userMap.computeIfAbsent(userName, user -> new User(userName));
    }

    @Override
    public void post(User user, Tweet tweet) {
        userMap.putIfAbsent(user.getName(), user);
        tweetsMap.computeIfAbsent(user, tweetList -> new LinkedList<>()).addFirst(tweet);
    }

    @Override
    public List<Tweet> read(User user) {
        return tweetsMap.get(user);
    }

    @Override
    public void follow(User user, User userToFollow) {
        userMap.get(user.getName()).follows(userToFollow);
    }

    @Override
    public Map<User, List<Tweet>> getWall(User user) {
        Map<User, List<Tweet>> userTweetsMap = new LinkedHashMap<>();
        userTweetsMap.put(user, tweetsMap.get(user));
        user.getFollows().forEach(userToFollow -> userTweetsMap.put(userToFollow, tweetsMap.get(userToFollow)));
        return userTweetsMap;
    }
}
