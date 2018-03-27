package co.uk.social.media.twitter;

import co.uk.social.media.twitter.service.TwitterService;

import java.util.List;

class TwitterConsoleCommand {

    private TwitterService twitterService;

    TwitterConsoleCommand(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    void execute(String twitterCmdLine) {
        String[] cmdLine = getExecuteCmdLine(twitterCmdLine);

        String cmd = cmdLine[1];

        switch (cmd) {
            case "->":
                twitterService.post(cmdLine[0], cmdLine[2]);
                break;
            case "read":
                List<String> userTweets = twitterService.read(cmdLine[0]);
                userTweets.forEach(System.out::println);
                break;
            case "follows":
                twitterService.follow(cmdLine[0], cmdLine[2]);
                break;
            case "wall":
                List<String> userWall = twitterService.getWall(cmdLine[0]);
                userWall.forEach(System.out::println);
                break;
            default:
                System.out.println("Invalid Command :(");
        }
    }

    private String[] getExecuteCmdLine(String twitterCmdLine) {
        String[] cmdLine = twitterCmdLine.split(" ", 3);

        if (cmdLine.length == 1) {
            cmdLine = new String[]{cmdLine[0], "read"};
        }
        return cmdLine;
    }
}
