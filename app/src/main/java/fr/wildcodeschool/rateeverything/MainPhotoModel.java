package fr.wildcodeschool.rateeverything;

import java.util.Date;

/**
 * Created by wilder on 27/03/18.
 */

public class MainPhotoModel {
    String description;
    String idvotant;
    int nbphoto;
    String photo;
    int timestamp;
    String titre;
    int totalnote;

    public MainPhotoModel() {
    }

    public MainPhotoModel(String description, String idvotant, int nbphoto, String photo, int timestamp, String titre, int totalnote) {
        this.description = description;
        this.idvotant = idvotant;
        this.nbphoto = nbphoto;
        this.photo = photo;
        this.timestamp = timestamp;
        this.titre = titre;
        this.totalnote = totalnote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdvotant() {
        return idvotant;
    }

    public void setIdvotant(String idvotant) {
        this.idvotant = idvotant;
    }

    public int getNbphoto() {
        return nbphoto;
    }

    public void setNbphoto(int nbphoto) {
        this.nbphoto = nbphoto;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getTotalnote() {
        return totalnote;
    }

    public void setTotalnote(int totalnote) {
        this.totalnote = totalnote;
    }
}




