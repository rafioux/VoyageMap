package com.example.rafioux.voyagemap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Rafioux on 29/04/2018.
 */



public class VoyageBDD
{
    public static final String TAG = "VoyageBDD";

    private SQLiteDatabase mDatabase;
    private Database maBaseSQLite;  //mDbHelper
    private Context mContext;

    public VoyageBDD(Context context){
        //On crée la BDD et sa table
        this.mContext = context;
        maBaseSQLite = new Database(context);
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public  void open(){
        //on ouvre la BDD en écriture
        mDatabase = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        maBaseSQLite.close();
    }


    //pour la table voyage
    public long insertVoyage(Voyage voyage){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(maBaseSQLite.COL_NOM_VOYAGE, voyage.getNom_voyage());
        values.put(maBaseSQLite.COL_DESCRIPTION, voyage.getDescription());
        //on insère l'objet dans la BDD via le ContentValues
        return mDatabase.insert(Database.TABLE_VOYAGES, null, values);
    }

    // pour la table voyage
    public int updateVoyage(int id, Voyage voyage){
        //il faut simplement préciser quel voyage on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(maBaseSQLite.COL_NOM_VOYAGE, voyage.getNom_voyage());
        values.put(maBaseSQLite.COL_DESCRIPTION, voyage.getDescription());
        return mDatabase.update(Database.TABLE_VOYAGES,  values, maBaseSQLite.COL_ID_VOYAGE + " = " +id, null);
    }
    //voyage
    public int removeVoyageWithID(int id){
        //Suppression d'un voyage de la BDD grâce à l'ID
        return mDatabase.delete(Database.TABLE_VOYAGES, maBaseSQLite.COL_ID_VOYAGE + " = " +id, null);
    }


    //voyage
    public Voyage getVoyageWithNom(String nom){
        //Récupère dans un Cursor les valeurs correspondant à un voyage contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = mDatabase.query(Database.TABLE_VOYAGES, new String[] {maBaseSQLite.COL_ID_VOYAGE, maBaseSQLite.COL_NOM_VOYAGE, maBaseSQLite.COL_DESCRIPTION}, maBaseSQLite.COL_NOM_VOYAGE + " LIKE \"" + nom +"\"", null, null, null, null);
        return cursorToVoyage(c);
    }

    //retourne tous les voyages
    public ArrayList<Voyage> getVoyageAll(){
        //Récupère dans un Cursor les valeurs correspondant à tous les étudiants contenus dans la BDD
        Cursor c = mDatabase.query(Database.TABLE_VOYAGES, new String[] {"*"}, null, null, null, null, null);
        return cursorAllVoyage(c);
    }

    //Cette méthode permet de convertir un cursor en un voyage
    private Voyage cursorToVoyage(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Voyage voyage = new Voyage();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        voyage.setId_voyage(c.getInt(maBaseSQLite.NUM_COL_ID));
        voyage.setNom(c.getString(maBaseSQLite.NUM_COL_NOM));
        voyage.setVoyage_Description(c.getString(maBaseSQLite.NUM_COL_DESCRIPTION_VOYAGE));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return voyage;
    }

    private ArrayList<Voyage> cursorAllVoyage(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        ArrayList<Voyage> listV = new ArrayList<Voyage>();
        //Sinon on se place sur le premier élément
        c.moveToFirst();

        while(!c.isAfterLast()) {


            //On créé un étudiant
            Voyage voyage = new Voyage();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            voyage.setId_voyage(c.getInt(maBaseSQLite.NUM_COL_ID));
            voyage.setNom(c.getString(maBaseSQLite.NUM_COL_NOM));
            voyage.setVoyage_Description(c.getString(maBaseSQLite.NUM_COL_DESCRIPTION_VOYAGE));


            listV.add(voyage);
            c.moveToNext();
        }

        //On ferme le cursor
        c.close();

        if(listV.isEmpty()) {
            Voyage bidon = new Voyage("bbd vide" , "bdd vide");
            listV.add(bidon);
        }

        //On retourne l'etudiant
        return listV;
    }

}
