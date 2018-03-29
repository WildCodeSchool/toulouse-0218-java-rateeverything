package fr.wildcodeschool.rateeverything;

import java.util.Date;

/**
 * Created by wilder on 27/03/18.
 */

public class MainPhotoModel {
    private int photo;
    private String username;
    private Date date;
    private float note;

    public MainPhotoModel(int photo, String username, Date date, float note) {
        this.username = username;
        this.photo = photo;
        this.date = date;
        this.note = note;
    }
    
    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }
}


