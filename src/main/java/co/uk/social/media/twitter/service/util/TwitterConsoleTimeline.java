package co.uk.social.media.twitter.service.util;

import co.uk.social.media.twitter.domain.Tweet;
import co.uk.social.media.twitter.domain.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TwitterConsoleTimeline {

    private static final String TIME_UNIT_MINUTE = "minute";
    private static final String TIME_UNIT_SECOND = "second";

    public static List<String> getUserTweets(List<Tweet> tweets, LocalDateTime currentTime) {

        return tweets.stream().map(tweet -> tweet.getMessage() + getTweetTimeSince(tweet.getPostedTime(), currentTime))
                .collect(Collectors.toList());
    }

    public static List<String> getWallTimeline(Map<User, List<Tweet>> userWall, LocalDateTime currentTime) {

        return userWall.entrySet().stream().map(entry ->
                entry.getValue().stream().map(tweet -> entry.getKey().getName() + " - " + tweet.getMessage() + getTweetTimeSince(tweet.getPostedTime(), currentTime)).collect(Collectors.toList())
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private static String getTweetTimeSince(LocalDateTime tweetedTime, LocalDateTime currentTime) {

        long secondsSinceTweeted = ChronoUnit.SECONDS.between(tweetedTime, currentTime);
        if (secondsSinceTweeted >= 60) {
            long minutesSinceTweeted = ChronoUnit.MINUTES.between(tweetedTime, currentTime);
            return String.format(" (%d %s ago)", minutesSinceTweeted, getTimeUnitString(minutesSinceTweeted, TIME_UNIT_MINUTE));
        }

        return String.format(" (%d %s ago)", secondsSinceTweeted, getTimeUnitString(secondsSinceTweeted, TIME_UNIT_SECOND));
    }

    private static String getTimeUnitString(long value, String timeUnit) {
        return value == 1 ? timeUnit : timeUnit.concat("s");
    }
}
