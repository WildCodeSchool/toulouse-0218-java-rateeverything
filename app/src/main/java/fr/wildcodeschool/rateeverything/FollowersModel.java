package fr.wildcodeschool.rateeverything;

/**
 * Created by wilder on 29/03/18.
 */

public class FollowersModel {
    private int userPhoto;
    private int nbPhoto;
    private int nbFollowers;
    private String userName;

    public int getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(int userPhoto) {
        this.userPhoto = userPhoto;
    }

    public int getNbPhoto() {
        return nbPhoto;
    }

    public void setNbPhoto(int nbPhoto) {
        this.nbPhoto = nbPhoto;
    }

    public int getNbFollowers() {
        return nbFollowers;
    }

    public void setNbFollowers(int nbFollowers) {
        this.nbFollowers = nbFollowers;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FollowersModel(int userPhoto, int nbPhoto, int nbFollowers, String userName) {
        this.userPhoto = userPhoto;
        this.nbPhoto = nbPhoto;
        this.nbFollowers = nbFollowers;
        this.userName = userName;
    }
}
