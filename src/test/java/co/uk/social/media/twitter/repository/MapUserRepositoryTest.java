package co.uk.social.media.twitter.repository;

import co.uk.social.media.twitter.domain.Tweet;
import co.uk.social.media.twitter.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapUserRepositoryTest {

    private UserRepository userRepository = new MapUserRepository();

    @Test
    public void getUser() throws Exception {
        //Arrange
        String userName = "Test";
        User expectedUser = new User(userName);
        userRepository.post(new User(userName), new Tweet("This is a test tweet", LocalDateTime.now()));

        //Act
        User actualUser = userRepository.get(userName);

        //Assert
        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void post() throws Exception {
        //Arrange
        String tweet1 = "This is a test tweet one";
        String tweet2 = "This is a test tweet two";
        LocalDateTime tweet1PostTime = LocalDateTime.now();
        LocalDateTime tweet2PostTime = LocalDateTime.now();
        User user = new User("Test");

        //Act
        userRepository.post(user, new Tweet(tweet1, tweet1PostTime));
        userRepository.post(user, new Tweet(tweet2, tweet2PostTime));

        //Assert
        List<Tweet> tweets = userRepository.read(user);
        Assert.assertNotNull(tweets);
        Assert.assertEquals(2, tweets.size());
        Assert.assertEquals(tweet2, tweets.get(0).getMessage());
        Assert.assertEquals(tweet1, tweets.get(1).getMessage());
        Assert.assertEquals(tweet2PostTime, tweets.get(0).getPostedTime());
        Assert.assertEquals(tweet1PostTime, tweets.get(1).getPostedTime());
    }

    @Test
    public void read() throws Exception {
        //Arrange
        String tweet1 = "This is a test tweet one";
        String tweet2 = "This is a test tweet two";
        LocalDateTime tweet1PostTime = LocalDateTime.now();
        LocalDateTime tweet2PostTime = LocalDateTime.now();
        User user = new User("Test");
        userRepository.post(user, new Tweet(tweet1, tweet1PostTime));
        userRepository.post(user, new Tweet(tweet2, tweet2PostTime));

        //Act
        List<Tweet> tweets = userRepository.read(user);

        //Assert
        Assert.assertNotNull(tweets);
        Assert.assertEquals(2, tweets.size());
        Assert.assertEquals(tweet2, tweets.get(0).getMessage());
        Assert.assertEquals(tweet1, tweets.get(1).getMessage());
        Assert.assertEquals(tweet2PostTime, tweets.get(0).getPostedTime());
        Assert.assertEquals(tweet1PostTime, tweets.get(1).getPostedTime());
    }

    @Test
    public void follow() throws Exception {
        //Arrange
        User user1 = new User("Test1");
        User user2 = new User("Test2");
        User user3 = new User("Test3");
        userRepository.post(user1, new Tweet("Test Tweet", LocalDateTime.now()));
        userRepository.post(user2, new Tweet("Test Tweet", LocalDateTime.now()));
        userRepository.post(user3, new Tweet("Test Tweet", LocalDateTime.now()));

        //Act
        userRepository.follow(user1, user2);
        userRepository.follow(user1, user3);

        //Arrange
        User user = userRepository.get(user1.getName());
        Assert.assertNotNull(user.getFollows());
        Assert.assertEquals(2, user.getFollows().size());
        Assert.assertEquals(user3, user.getFollows().get(0));
        Assert.assertEquals(user2, user.getFollows().get(1));
    }

    @Test
    public void followWithSameUserTwice() throws Exception {
        //Arrange
        User user1 = new User("Test1");
        User user2 = new User("Test2");
        userRepository.post(user1, new Tweet("Test Tweet", LocalDateTime.now()));
        userRepository.post(user2, new Tweet("Test Tweet", LocalDateTime.now()));

        //Act
        userRepository.follow(user1, user2);
        userRepository.follow(user1, user2);

        //Arrange
        User user = userRepository.get(user1.getName());
        Assert.assertNotNull(user.getFollows());
        Assert.assertEquals(1, user.getFollows().size());
        Assert.assertEquals(user2, user.getFollows().get(0));
    }

    @Test
    public void getWall() throws Exception {
        //Arrange
        String tweet1 = "This is a test tweet one";
        String tweet2 = "This is a test tweet two";
        String tweet3 = "This is a test tweet three";
        LocalDateTime tweet1PostTime = LocalDateTime.now();
        LocalDateTime tweet2PostTime = LocalDateTime.now();
        LocalDateTime tweet3PostTime = LocalDateTime.now();

        User user1 = new User("Test1");
        User user2 = new User("Test2");
        User user3 = new User("Test3");
        userRepository.post(user1, new Tweet(tweet1, tweet1PostTime));
        userRepository.post(user2, new Tweet(tweet2, tweet2PostTime));
        userRepository.post(user3, new Tweet(tweet3, tweet3PostTime));
        userRepository.follow(user1, user2);
        userRepository.follow(user1, user3);

        //Act
        Map<User, List<Tweet>> wall = userRepository.getWall(user1);

        //Assert
        Assert.assertEquals(3, wall.size());
        Assert.assertNotNull(wall.get(user1));
        Assert.assertNotNull(wall.get(user2));
        Assert.assertNotNull(wall.get(user3));
        //Assertions on List<Tweet> may not be required as it is already tested in read()

    }

}