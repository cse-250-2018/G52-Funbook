package com.example.preity;

import com.google.firebase.Timestamp;

public class VideoFile {
    String uid,uri;
    Timestamp timestamp;

    public VideoFile() {
    }

    public VideoFile(String uid, String uri, Timestamp timestamp) {
        this.uid = uid;
        this.uri = uri;
        this.timestamp = timestamp;
    }

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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "VideoFile{" +
                "uid='" + uid + '\'' +
                ", uri='" + uri + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
