package com.example.parstagram;

import com.parse.ParseClassName;
import com.parse.ParseUser;
import com.parse.ParseObject;


@ParseClassName("Comment")
public class Comment extends ParseObject {
    public static final String KEY_TEXT = "Text";
    public static final String KEY_POST = "Post";
    public static final String KEY_USER = "User";
    public static final String KEY_CREATED_AT = "createdAt";

    public Comment (){}

    public String getText() {
        String result = getString(KEY_TEXT);
        return result;
    }
    public void setText(String text) {
        put(KEY_TEXT,text);
    }

    public ParseUser getUser() {
        return (getParseUser(KEY_USER));
    }
    public void setUser(ParseUser user) {
        put(KEY_USER,user);
    }

    public Post getPost() {
        return((Post)getParseObject(KEY_POST));
    }
    public void setPost(Post post) {
        put(KEY_POST,post);
    }

}
