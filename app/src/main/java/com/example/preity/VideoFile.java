package com.example.preity;

import com.google.firebase.Timestamp;

// Object that we will upload in firebase


public class VideoFile {
    String uid,uri,caption;
    Timestamp timestamp;

    // Empty constructor
    public VideoFile() {
    }
    // second constructor

    public VideoFile(String uid, String uri, String caption, Timestamp timestamp) {
        this.uid = uid;
        this.uri = uri;
        this.caption = caption;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "VideoFile{" +
                "uid='" + uid + '\'' +
                ", uri='" + uri + '\'' +
                ", caption='" + caption + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    //Getter and setters

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
