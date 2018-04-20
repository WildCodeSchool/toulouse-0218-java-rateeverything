package fr.wildcodeschool.rateeverything;

import java.util.Date;

/**
 * Created by wilder on 27/03/18.
 */

public class MainPhotoModel {
    String description;
    int nbnote;
    long timestamp;
    String title;
    int totalnote;
    String photo;

    public MainPhotoModel() {
    }

    public String getDescription() {
        return description;

    }

    public void setDescription(String description) {
        this.description = description;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalnote() {
        return totalnote;
    }

    public void setTotalnote(int totalnote) {
        this.totalnote = totalnote;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getNbnote() {
        return nbnote;
    }

    public void setNbnote(int nbnote) {
        this.nbnote = nbnote;
    }

    public MainPhotoModel(String description, long timestamp, String title, int totalnote, String photo, int nbnote) {
        this.description = description;
        this.timestamp = timestamp;
        this.title = title;
        this.totalnote = totalnote;
        this.photo = photo;
        this.nbnote = nbnote;
    }
}

