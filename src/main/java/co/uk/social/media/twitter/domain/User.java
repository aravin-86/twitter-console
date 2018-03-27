package co.uk.social.media.twitter.domain;

import java.util.LinkedList;

public class User {
    private String name;
    private LinkedList<User> follows = new LinkedList<>();

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LinkedList<User> getFollows() {
        return follows;
    }

    public void follows(User userToFollow) {
        if (!follows.contains(userToFollow)) {
            follows.addFirst(userToFollow);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
