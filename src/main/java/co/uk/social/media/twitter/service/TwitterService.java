package co.uk.social.media.twitter.service;

import java.util.List;

public interface TwitterService {

    void post(String userName, String message);

    List<String> read(String userName);

    void follow(String userName, String userNameToFollow);

    List<String> getWall(String userName);
}
