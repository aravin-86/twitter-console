package co.uk.social.media.twitter.service;

import co.uk.social.media.twitter.domain.Tweet;
import co.uk.social.media.twitter.domain.User;
import co.uk.social.media.twitter.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTwitterServiceTest {

    @Mock
    private UserRepository userRepository;

    private TwitterService twitterService;

    @Before
    public void setUp() throws Exception {
        twitterService = new DefaultTwitterService(userRepository);
    }

    @Test
    public void post() throws Exception {
        //Arrange
        String tweet = "This is a test tweet";
        String userName = "Test";
        User user = new User(userName);
        when(userRepository.get(userName)).thenReturn(user);
        doNothing().when(userRepository).post(any(User.class), any(Tweet.class));

        //Act
        twitterService.post(userName, tweet);

        //Act
        verify(userRepository).get(userName);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Tweet> tweetArgumentCaptor = ArgumentCaptor.forClass(Tweet.class);
        verify(userRepository).post(userArgumentCaptor.capture(), tweetArgumentCaptor.capture());
        assertEquals(user, userArgumentCaptor.getValue());
        assertEquals(tweet, tweetArgumentCaptor.getValue().getMessage());
        assertNotNull(tweetArgumentCaptor.getValue().getPostedTime());
        assertTrue(tweetArgumentCaptor.getValue().getPostedTime().isBefore(LocalDateTime.now()));

    }

    @Test
    public void read() throws Exception {
        //Arrange
        String userName = "Test";
        User user = new User(userName);
        when(userRepository.get(userName)).thenReturn(user);

        List<Tweet> tweets = new ArrayList<>();
        tweets.add(new Tweet("This is a test tweet", LocalDateTime.now()));
        tweets.add(new Tweet("This is a test tweet", LocalDateTime.now()));
        when(userRepository.read(user)).thenReturn(tweets);

        //Act
        List<String> tweetLines = twitterService.read(userName);

        //Assert
        verify(userRepository).read(user);
        Assert.assertNotNull(tweetLines);
        Assert.assertEquals(2, tweetLines.size());
    }

    @Test
    public void follow() throws Exception {
        //Arrange
        String userName1 = "Test1";
        User user1 = new User(userName1);
        String userName2 = "Test2";
        User user2 = new User(userName2);
        when(userRepository.get(userName1)).thenReturn(user1);
        when(userRepository.get(userName2)).thenReturn(user2);

        //Act
        twitterService.follow(userName1, userName2);

        //Assert
        verify(userRepository).get(userName1);
        verify(userRepository).get(userName2);
        verify(userRepository).follow(user1, user2);
    }

    @Test
    public void getWall() throws Exception {
        //Arrange
        String userName1 = "Test1";
        String userName2 = "Test2";
        User user1 = new User(userName1);
        User user2 = new User(userName2);
        user1.follows(user2);
        when(userRepository.get(userName1)).thenReturn(user1);
        Map<User, List<Tweet>> tweetsMap = new HashMap<>();
        List<Tweet> user1Tweets = new ArrayList<>();
        user1Tweets.add(new Tweet("This is test tweet", LocalDateTime.now()));
        List<Tweet> user2Tweets = new ArrayList<>();
        user2Tweets.add(new Tweet("This is test tweet1", LocalDateTime.now()));
        user2Tweets.add(new Tweet("This is test tweet2", LocalDateTime.now()));
        tweetsMap.put(user1, user1Tweets);
        tweetsMap.put(user2, user2Tweets);

        when(userRepository.getWall(any(User.class))).thenReturn(tweetsMap);

        //Act
        List<String> wall = twitterService.getWall(userName1);

        //Assert
        verify(userRepository).getWall(user1);
        Assert.assertNotNull(wall);
        Assert.assertEquals(3, wall.size());
    }

}