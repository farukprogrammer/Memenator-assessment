package com.abdulrahman.memenator.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abdulrahman on 8/5/2016.
 */

@IgnoreExtraProperties
public class Meme {
    public String memeName;
    public String memeUrl;
    public int numberUsed;

    public Meme() {
    }

    public Meme(String memeName, String memeUrl, int numberUsed) {
        this.memeName = memeName;
        this.memeUrl = memeUrl;
        this.numberUsed = numberUsed;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("memeName", memeName);
        result.put("memeUrl", memeUrl);
        result.put("numberUsed", numberUsed);

        return result;
    }
}
