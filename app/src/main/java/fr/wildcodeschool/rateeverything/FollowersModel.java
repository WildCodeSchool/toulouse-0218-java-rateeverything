package fr.wildcodeschool.rateeverything;

/**
 * Created by wilder on 29/03/18.
 */

public class FollowersModel {
    private String mail;
    private String userName;
    private int nbfollowers;
    private int nbphoto;
    private String photouser;
    private String photbackground;
    private String id;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNbfollowers() {
        return nbfollowers;
    }

    public void setNbfollowers(int nbfollowers) {
        this.nbfollowers = nbfollowers;
    }

    public int getNbphoto() {
        return nbphoto;
    }

    public void setNbphoto(int nbphoto) {
        this.nbphoto = nbphoto;
    }

    public String getPhotouser() {
        return photouser;
    }

    public void setPhotouser(String photouser) {
        this.photouser = photouser;
    }

    public String getPhotbackground() {
        return photbackground;
    }

    public void setPhotbackground(String photbackground) {
        this.photbackground = photbackground;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FollowersModel(String mail, String userName, int nbfollowers, int nbphoto, String photouser, String photbackground, String id) {
        this.mail = mail;
        this.userName = userName;
        this.nbfollowers = nbfollowers;
        this.nbphoto = nbphoto;
        this.photouser = photouser;
        this.photbackground = photbackground;
        this.id = id;


    }

    public void FollowersModel(){};

}
