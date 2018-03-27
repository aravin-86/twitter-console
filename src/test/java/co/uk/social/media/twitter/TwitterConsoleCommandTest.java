package co.uk.social.media.twitter;

import co.uk.social.media.twitter.service.TwitterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterConsoleCommandTest {

    @Mock
    private TwitterService twitterService;

    @Test
    public void executePost() throws Exception {
        //Arrange
        TwitterConsoleCommand command = new TwitterConsoleCommand(twitterService);
        String postCmdLine = "Charlie -> Hi, How are you!";
        doNothing().when(twitterService).post(anyString(), anyString());

        //Act
        command.execute(postCmdLine);

        //Assert
        verify(twitterService).post("Charlie", "Hi, How are you!");
    }

    @Test
    public void executeRead() throws Exception {
        //Arrange
        TwitterConsoleCommand command = new TwitterConsoleCommand(twitterService);
        String postCmdLine = "Charlie";
        when(twitterService.read(anyString())).thenReturn(Collections.EMPTY_LIST);

        //Act
        command.execute(postCmdLine);

        //Assert
        verify(twitterService).read("Charlie");
    }

    @Test
    public void executeFollows() throws Exception {
        //Arrange
        TwitterConsoleCommand command = new TwitterConsoleCommand(twitterService);
        String postCmdLine = "Charlie follows Alice";
        doNothing().when(twitterService).follow(anyString(), anyString());

        //Act
        command.execute(postCmdLine);

        //Assert
        verify(twitterService).follow("Charlie", "Alice");
    }

    @Test
    public void executeWall() throws Exception {
        //Arrange
        TwitterConsoleCommand command = new TwitterConsoleCommand(twitterService);
        String postCmdLine = "Charlie wall";
        when(twitterService.getWall(anyString())).thenReturn(Collections.EMPTY_LIST);

        //Act
        command.execute(postCmdLine);

        //Assert
        verify(twitterService).getWall("Charlie");
    }
}