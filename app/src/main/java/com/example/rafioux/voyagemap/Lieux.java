package com.example.rafioux.voyagemap;

import android.content.ContentValues;

/**
 * Created by Rafioux on 29/04/2018.
 */

public class Lieux {

    /* 1 */
    private int id_lieux;
    private String nom_lieux;
    private String commentaire;
    private String longitude;
    private String latitude;
    private String id_Lieux_Voyage;


    public Lieux(){
    }

    public Lieux (ContentValues cv){
        this.nom_lieux = (String) cv.get("nom_lieux");
        this.commentaire = (String) cv.get("commentaire");
        this.longitude = (String) cv.get("longitude");
        this.latitude = (String) cv.get("latitude");
        this.id_Lieux_Voyage = (String) cv.get("id_Lieux_Voyage");

    }

    public Lieux(String nom_lieux, String commentaire, String longitude, String latitude, String id_Lieux_Voyage ){
        this.nom_lieux = nom_lieux;
        this.commentaire = commentaire;
        this.longitude = longitude;
        this.latitude = latitude;
        this.id_Lieux_Voyage = id_Lieux_Voyage;
    }

    public int getId_lieux() {
        return id_lieux;
    }

    public String getId_Lieux_Voyage() {
        return id_Lieux_Voyage;
    }

    public void setId_lieux(int id_lieux) {
        this.id_lieux = id_lieux;
    }

    public void setId_Lieux_Voyage(String id_Lieux_Voyage) {
        this.id_Lieux_Voyage = id_Lieux_Voyage;
    }

    public String getNom_lieux() {
        return nom_lieux;
    }

    public void setNom_lieux(String nom_lieux) {
        this.nom_lieux = nom_lieux;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String toString(){
        return id_lieux + " : " + nom_lieux + " " + commentaire + " " + longitude + " " + latitude + " " + id_Lieux_Voyage ;
    }

}
