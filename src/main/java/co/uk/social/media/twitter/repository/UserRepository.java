package co.uk.social.media.twitter.repository;

import co.uk.social.media.twitter.domain.Tweet;
import co.uk.social.media.twitter.domain.User;

import java.util.List;
import java.util.Map;

public interface UserRepository {

    User get(String userName);

    void post(User user, Tweet tweet);

    List<Tweet> read(User user);

    void follow(User user, User userToFollow);

    Map<User, List<Tweet>> getWall(User user);
}
