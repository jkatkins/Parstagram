package com.example.parstagram;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.sql.Array;
import java.util.ArrayList;

@ParseClassName("Post")
public class Post extends ParseObject {

    //TODO fix capitalization on parse to make it uniform
    public static final String KEY_DESCRIPTION = "Description";
    public static final String KEY_IMAGE = "Image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_LIKES = "Likes";

    public Post (){}

    public String getDescription() {
        String result = getString(KEY_DESCRIPTION);
        return result;
    }
    public void setDescription(String description) {
        put(KEY_DESCRIPTION,description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile image) {
        put(KEY_IMAGE,image);
    }

    public ParseUser getUser () {
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user) {
        put(KEY_USER,user);
    }

    public ArrayList<String> getLikes() {
        return (ArrayList<String>)get("Likes");
    }
    public Boolean changeLike() {
        ArrayList<String> likes = (ArrayList<String>)get("Likes");
        for (int i = 0; i < likes.size();i++) {
            if (likes.get(i).equals(ParseUser.getCurrentUser().getUsername())) {
                Log.i("Like","Already liked, now disliking");
                likes.remove(i);
                put(KEY_LIKES,likes);
                return false;
            }
        }
        Log.i("Like","adding new like");
        add(KEY_LIKES,ParseUser.getCurrentUser().getUsername());
        return true;
    }
}
