package co.uk.social.media.twitter;

import co.uk.social.media.twitter.repository.MapUserRepository;
import co.uk.social.media.twitter.service.DefaultTwitterService;
import co.uk.social.media.twitter.service.TwitterService;

import java.util.Scanner;

public class TwitterConsoleApp {

    public static void main(String[] args) {

        TwitterService twitterService = new DefaultTwitterService(new MapUserRepository());
        TwitterConsoleCommand command = new TwitterConsoleCommand(twitterService);
        Scanner scanner = new Scanner(System.in);

        System.out.print("> ");
        while (scanner.hasNextLine()) {
            String twitterCmdLine = scanner.nextLine();
            command.execute(twitterCmdLine);
            System.out.print("> ");
        }

    }
}
