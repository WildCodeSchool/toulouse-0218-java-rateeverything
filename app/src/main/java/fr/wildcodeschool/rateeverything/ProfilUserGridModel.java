package fr.wildcodeschool.rateeverything;

/**
 * Created by wilder on 04/04/18.
 */

public class ProfilUserGridModel {
    private int imgUser;
    private int ratingPhotoUser;

    public int getImgUser() {
        return imgUser;
    }

    public int getRatingPhotoUser() {
        return ratingPhotoUser;
    }

    public ProfilUserGridModel(int imgUser, int ratingPhotoUser) {
        this.imgUser = imgUser;
        this.ratingPhotoUser = ratingPhotoUser;

    }
}
