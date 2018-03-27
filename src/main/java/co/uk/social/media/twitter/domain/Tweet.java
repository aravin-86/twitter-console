package co.uk.social.media.twitter.domain;

import java.time.LocalDateTime;

public class Tweet {

    private String message;
    private LocalDateTime postedTime;

    public Tweet(String message, LocalDateTime postedTime) {
        this.message = message;
        this.postedTime = postedTime;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getPostedTime() {
        return postedTime;
    }
}

