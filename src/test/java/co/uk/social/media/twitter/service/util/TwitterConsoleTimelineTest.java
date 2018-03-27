package co.uk.social.media.twitter.service.util;

import co.uk.social.media.twitter.domain.Tweet;
import co.uk.social.media.twitter.domain.User;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TwitterConsoleTimelineTest {

    @Test
    public void getUserTweetsWithSeconds() throws Exception {
        //Arrange
        LinkedList<Tweet> tweets = new LinkedList<>();
        String tweet1 = "This is a test tweet one";
        String tweet2 = "This is a test tweet two";
        LocalDateTime tweet1PostedTime = LocalDateTime.now();
        LocalDateTime tweet2PostedTime = LocalDateTime.now();
        tweets.add(new Tweet(tweet1, tweet1PostedTime));
        tweets.add(new Tweet(tweet2, tweet2PostedTime));
        LocalDateTime currentTime = LocalDateTime.now().plusSeconds(2);

        //Act
        List<String> userTweets = TwitterConsoleTimeline.getUserTweets(tweets, currentTime);

        //Assert
        Assert.assertNotNull(userTweets);
        Assert.assertEquals(2, userTweets.size());
        Assert.assertEquals(String.format("%s (%d %s ago)", tweet1, Duration.between(tweet1PostedTime, currentTime).getSeconds(), "seconds"), userTweets.get(0));
        Assert.assertEquals(String.format("%s (%d %s ago)", tweet2, Duration.between(tweet2PostedTime, currentTime).getSeconds(), "seconds"), userTweets.get(1));

    }

    @Test
    public void getUserTweetsWithinASecond() throws Exception {
        //Arrange
        LinkedList<Tweet> tweets = new LinkedList<>();
        String tweet1 = "This is a test tweet one";
        String tweet2 = "This is a test tweet two";
        LocalDateTime tweet1PostedTime = LocalDateTime.now();
        LocalDateTime tweet2PostedTime = LocalDateTime.now();
        tweets.add(new Tweet(tweet1, tweet1PostedTime));
        tweets.add(new Tweet(tweet2, tweet2PostedTime));
        LocalDateTime currentTime = LocalDateTime.now().plusSeconds(1);

        //Act
        List<String> userTweets = TwitterConsoleTimeline.getUserTweets(tweets, currentTime);

        //Assert
        Assert.assertNotNull(userTweets);
        Assert.assertEquals(2, userTweets.size());
        Assert.assertEquals(String.format("%s (%d %s ago)", tweet1, Duration.between(tweet1PostedTime, currentTime).getSeconds(), "second"), userTweets.get(0));
        Assert.assertEquals(String.format("%s (%d %s ago)", tweet2, Duration.between(tweet2PostedTime, currentTime).getSeconds(), "second"), userTweets.get(1));

    }

    @Test
    public void getUserTweetsWithMinutes() throws Exception {
        //Arrange
        LinkedList<Tweet> tweets = new LinkedList<>();
        String tweet1 = "This is a test tweet one";
        String tweet2 = "This is a test tweet two";
        LocalDateTime tweet1PostedTime = LocalDateTime.now();
        LocalDateTime tweet2PostedTime = LocalDateTime.now();
        tweets.add(new Tweet(tweet1, tweet1PostedTime));
        tweets.add(new Tweet(tweet2, tweet2PostedTime));
        LocalDateTime currentTime = LocalDateTime.now().plusMinutes(2);

        //Act
        List<String> userTweets = TwitterConsoleTimeline.getUserTweets(tweets, currentTime);

        //Assert
        Assert.assertNotNull(userTweets);
        Assert.assertEquals(2, userTweets.size());
        Assert.assertEquals(String.format("%s (%d %s ago)", tweet1, Duration.between(tweet1PostedTime, currentTime).toMinutes(), "minutes"), userTweets.get(0));
        Assert.assertEquals(String.format("%s (%d %s ago)", tweet2, Duration.between(tweet2PostedTime, currentTime).toMinutes(), "minutes"), userTweets.get(1));

    }

    @Test
    public void getUserTweetsWithinAMinute() throws Exception {
        //Arrange
        LinkedList<Tweet> tweets = new LinkedList<>();
        String tweet1 = "This is a test tweet one";
        String tweet2 = "This is a test tweet two";
        LocalDateTime tweet1PostedTime = LocalDateTime.now();
        LocalDateTime tweet2PostedTime = LocalDateTime.now();
        tweets.add(new Tweet(tweet1, tweet1PostedTime));
        tweets.add(new Tweet(tweet2, tweet2PostedTime));
        LocalDateTime currentTime = LocalDateTime.now().plusMinutes(1);

        //Act
        List<String> userTweets = TwitterConsoleTimeline.getUserTweets(tweets, currentTime);

        //Assert
        Assert.assertNotNull(userTweets);
        Assert.assertEquals(2, userTweets.size());
        Assert.assertEquals(String.format("%s (%d %s ago)", tweet1, Duration.between(tweet1PostedTime, currentTime).toMinutes(), "minute"), userTweets.get(0));
        Assert.assertEquals(String.format("%s (%d %s ago)", tweet2, Duration.between(tweet2PostedTime, currentTime).toMinutes(), "minute"), userTweets.get(1));

    }

    @Test
    public void getWallTimeline() throws Exception {
        //Arrange
        String userName1 = "Test1";
        String userName2 = "Test2";
        User user1 = new User(userName1);
        User user2 = new User(userName2);
        user1.follows(user2);
        Map<User, List<Tweet>> tweetsMap = new HashMap<>();
        List<Tweet> user1Tweets = new ArrayList<>();
        String user1Tweet = "This is test tweet";
        LocalDateTime user1TweetPostedTime = LocalDateTime.now();
        user1Tweets.add(new Tweet(user1Tweet, user1TweetPostedTime));
        List<Tweet> user2Tweets = new ArrayList<>();
        String user2Tweet1 = "This is test tweet1";
        LocalDateTime user2Tweet1PostedTime = LocalDateTime.now();
        user2Tweets.add(new Tweet(user2Tweet1, user2Tweet1PostedTime));
        String user2Tweet2 = "This is test tweet2";
        LocalDateTime user2Tweet2PostedTime = LocalDateTime.now();
        user2Tweets.add(new Tweet(user2Tweet2, user2Tweet2PostedTime));
        tweetsMap.put(user1, user1Tweets);
        tweetsMap.put(user2, user2Tweets);
        LocalDateTime currentTime = LocalDateTime.now().plusMinutes(5);

        //Act
        List<String> wall = TwitterConsoleTimeline.getWallTimeline(tweetsMap, currentTime);

        //Assert
        Assert.assertNotNull(wall);
        Assert.assertEquals(3, wall.size());
        Assert.assertEquals(String.format("%s - %s (%d %s ago)", userName1, user1Tweet, Duration.between(user1TweetPostedTime, currentTime).toMinutes(), "minutes"), wall.get(0));
        Assert.assertEquals(String.format("%s - %s (%d %s ago)", userName2, user2Tweet1, Duration.between(user2Tweet1PostedTime, currentTime).toMinutes(), "minutes"), wall.get(1));
        Assert.assertEquals(String.format("%s - %s (%d %s ago)", userName2, user2Tweet2, Duration.between(user2Tweet2PostedTime, currentTime).toMinutes(), "minutes"), wall.get(2));
    }

}