package com.example.rafioux.voyagemap;

import android.content.ContentValues;

public class Voyage {

    private int id_voyage;
    private String nom_voyage;
    private String voyage_Description;

    public Voyage(){}

    //Constructeur
    public Voyage (ContentValues cv){
        this.nom_voyage = (String) cv.get("nom_voyage");
        this.voyage_Description = (String) cv.get("nom_Description");
    }

    //Constructeur
    public Voyage(String nom_voyage, String nom_Description ){
        this.nom_voyage = nom_voyage;
        this.voyage_Description = nom_Description;
    }

    //Getter et setter
    public int getId_voyage() {
        return id_voyage;
    }

    public void setId_voyage(int id_voyage) {
        this.id_voyage = id_voyage;
    }


    public String getNom_voyage() {
        return nom_voyage;
    }

    public void setNom(String nom) {
        this.nom_voyage = nom;
    }


    public String getDescription() {
        return voyage_Description;
    }

    public void setVoyage_Description(String nom_Description) {
        this.voyage_Description = nom_Description;
    }

    public String toString(){
        return id_voyage+ " : " + id_voyage + " " + nom_voyage + " " + voyage_Description ;
    }
}