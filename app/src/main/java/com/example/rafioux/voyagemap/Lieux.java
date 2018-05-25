package com.example.rafioux.voyagemap;

import android.content.ContentValues;

/**
 * Created by Rafioux on 29/04/2018.
 */

public class Lieux {

    /* 1 */
    private int id_lieux;
    private String nom_lieux;
    private String pointX_lieux;
    private String pointY_lieux;
    private String id_Lieux_Voyage;


    public Lieux(){
    }

    public Lieux (ContentValues cv){
        this.nom_lieux = (String) cv.get("nom_lieux");
        this.pointX_lieux = (String) cv.get("pointX_lieux");
        this.pointY_lieux = (String) cv.get("pointY_lieux");
        this.id_Lieux_Voyage = (String) cv.get("id_Lieux_Voyage");

    }

    public Lieux(String nom_lieux, String pointX_lieux, String pointY_lieux, String id_Lieux_Voyage ){
        this.nom_lieux = nom_lieux;
        this.pointX_lieux = pointX_lieux;
        this.pointY_lieux = pointY_lieux;
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

    public String getPointX_lieux() {
        return pointX_lieux;
    }

    public void setPointX_lieux(String pointX_lieux) {
        this.pointX_lieux = pointX_lieux;
    }

    public String getPointY_lieux() {
        return pointY_lieux;
    }

    public void setPointY(String pointY_lieux) {
        this.pointY_lieux = pointY_lieux;
    }

    public String toString(){
        return id_lieux + " : " + nom_lieux + " " + pointX_lieux + " " + pointY_lieux + " " + id_Lieux_Voyage ;
    }

}
