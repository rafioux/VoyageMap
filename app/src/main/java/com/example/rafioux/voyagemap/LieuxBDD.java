package com.example.rafioux.voyagemap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class LieuxBDD {

    public static final String TAG = "VoyageBDD";

    private SQLiteDatabase mDatabase;
    private Database maBaseSQLite;  //mDbHelper
    private Context mContext;

    public LieuxBDD(Context context){
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

    public SQLiteDatabase getBDD(){
        return mDatabase;
    }


    //pour la table lieux
    public long insertLieux(Lieux lieux){
        ContentValues values = new ContentValues();
        values.put(maBaseSQLite.COL_NOM_lIEUX, lieux.getNom_lieux());
        values.put(maBaseSQLite.COL_COMMENTAIRE, lieux.getCommentaire());
        values.put(maBaseSQLite.COL_LONGITUDE, lieux.getLongitude());
        values.put(maBaseSQLite.COL_LATITUDE, lieux.getLatitude());
        values.put(maBaseSQLite.COL_ID_VOYAGE_LIEUX, lieux.getId_Lieux_Voyage());
        //on insère l'objet dans la BDD via le ContentValues
        return mDatabase.insert(Database.TABLE_LIEUX, null, values);

    }



    //pour la table lieux
    public int updateLieux(int id, Lieux lieux){
        //il faut simplement préciser quel lieux on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(maBaseSQLite.COL_NOM_lIEUX, lieux.getNom_lieux());
        values.put(maBaseSQLite.COL_COMMENTAIRE, lieux.getCommentaire());
        values.put(maBaseSQLite.COL_LONGITUDE, lieux.getLongitude());
        values.put(maBaseSQLite.COL_LATITUDE, lieux.getLatitude());
        values.put(maBaseSQLite.COL_ID_VOYAGE_LIEUX, lieux.getId_Lieux_Voyage());
        return mDatabase.update(Database.TABLE_LIEUX,  values, maBaseSQLite.COL_ID_LIEUX + " = " +id, null);
    }

    //lieux
    public int removeLieuxWithID(int id){
        //Suppression d'un lieux de la BDD grâce à l'ID
        return mDatabase.delete(Database.TABLE_LIEUX, maBaseSQLite.COL_ID_LIEUX + " = " +id, null);
    }



    //lieux
    public Lieux getLieuxWithNom(String nom){
        //Récupère dans un Cursor les valeurs correspondant à un lieux contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = mDatabase.query(Database.TABLE_LIEUX, new String[] {maBaseSQLite.COL_ID_LIEUX, maBaseSQLite.COL_NOM_lIEUX, maBaseSQLite.COL_COMMENTAIRE, maBaseSQLite.COL_LONGITUDE, maBaseSQLite.COL_LATITUDE, maBaseSQLite.COL_ID_VOYAGE_LIEUX}, maBaseSQLite.COL_NOM_lIEUX + " LIKE \"" + nom +"\"", null, null, null, null);
        return cursorToLieux(c);
    }

    public Lieux getLieuxWithLatLon(Double lat, Double lon){
        //Récupère dans un Cursor les valeurs correspondant à un lieux contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = mDatabase.query(Database.TABLE_LIEUX, new String[] {maBaseSQLite.COL_ID_LIEUX, maBaseSQLite.COL_NOM_lIEUX, maBaseSQLite.COL_COMMENTAIRE, maBaseSQLite.COL_LONGITUDE, maBaseSQLite.COL_LATITUDE, maBaseSQLite.COL_ID_VOYAGE_LIEUX}, maBaseSQLite.COL_LONGITUDE + " = \"" + lon +"\" and " + maBaseSQLite.COL_LATITUDE + " = \"" + lat +"\"", null, null, null, null);
        return cursorToLieux(c);
    }

    //lieux
    public Lieux getLieuxWithID(int id){
        //Récupère dans un Cursor les valeurs correspondant à un lieux contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = mDatabase.query(Database.TABLE_LIEUX, new String[] {maBaseSQLite.COL_ID_LIEUX, maBaseSQLite.COL_NOM_lIEUX, maBaseSQLite.COL_COMMENTAIRE, maBaseSQLite.COL_LONGITUDE, maBaseSQLite.COL_LATITUDE, maBaseSQLite.COL_ID_VOYAGE_LIEUX}, maBaseSQLite.COL_ID_LIEUX + " = \"" + id +"\"", null, null, null, null);
        return cursorToLieux(c);
    }

    //lieux
    public ArrayList<Lieux> getLieuxWithIdVoyage(String id){
        //Récupère dans un Cursor les valeurs correspondant à un lieux contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = mDatabase.query(Database.TABLE_LIEUX, new String[] {maBaseSQLite.COL_ID_LIEUX, maBaseSQLite.COL_NOM_lIEUX, maBaseSQLite.COL_COMMENTAIRE, maBaseSQLite.COL_LONGITUDE, maBaseSQLite.COL_LATITUDE, maBaseSQLite.COL_ID_VOYAGE_LIEUX}, maBaseSQLite.COL_ID_VOYAGE_LIEUX + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorAllLieux(c);
    }

    //retourne tous les voyages
    public ArrayList<Lieux> getLieuxAll(){
        //Récupère dans un Cursor les valeurs correspondant à tous les étudiants contenus dans la BDD
        Cursor c = mDatabase.query(Database.TABLE_LIEUX, new String[] {"*"}, null, null, null, null, null);
        return cursorAllLieux(c);
    }


    //Cette méthode permet de convertir un cursor en un Voyage
    private Lieux cursorToLieux(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Lieux lieux = new Lieux();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        lieux.setId_lieux(c.getInt(maBaseSQLite.NUM_COL_ID));
        lieux.setNom_lieux(c.getString(maBaseSQLite.NUM_COL_NOM));
        lieux.setCommentaire(c.getString(maBaseSQLite.NUM_COL_COMMENTAIRE));
        lieux.setLongitude(c.getString(maBaseSQLite.NUM_COL_LONGITUDE));
        lieux.setLatitude(c.getString(maBaseSQLite.NUM_COL_LATITUDE));
        lieux.setId_Lieux_Voyage(c.getString(maBaseSQLite.NUM_COL_ID_VOYAGE_LIEUX));
        //On ferme le cursor
        c.close();


        return lieux;
    }

    private ArrayList<Lieux> cursorAllLieux(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        ArrayList<Lieux> listL = new ArrayList<Lieux>();
        //Sinon on se place sur le premier élément
        c.moveToFirst();

        while(!c.isAfterLast()) {


            //On créé un étudiant
            Lieux lieux = new Lieux();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            lieux.setId_lieux(c.getInt(maBaseSQLite.NUM_COL_ID));
            lieux.setNom_lieux(c.getString(maBaseSQLite.NUM_COL_NOM));
            lieux.setCommentaire(c.getString(maBaseSQLite.NUM_COL_COMMENTAIRE));
            lieux.setLongitude(c.getString(maBaseSQLite.NUM_COL_LONGITUDE));
            lieux.setLatitude(c.getString(maBaseSQLite.NUM_COL_LATITUDE));
            lieux.setId_Lieux_Voyage(c.getString(maBaseSQLite.NUM_COL_ID_VOYAGE_LIEUX));


            listL.add(lieux);
            c.moveToNext();
        }

        //On ferme le cursor
        c.close();

        //TODO :quoi?
        /*if(listL.isEmpty()) {
            Lieux bidon = new Lieux();
            bidon.setId_lieux(0);
            bidon.setId_Lieux_Voyage("1");
            bidon.setPointX_lieux("0");
            bidon.setPointY("0");


            //Lieux bidon = new Lieux("bbd vide" , "0","0","0");
            listL.add(bidon);
            listL.add(bidon);
        }*/


        return listL;
    }
    //TODO : supp?
    private ArrayList<Lieux> cursorAllLieuxVoyage(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        ArrayList<Lieux> listL = new ArrayList<Lieux>();
       /* //Sinon on se place sur le premier élément
        c.moveToFirst();

        while(!c.isAfterLast()) {


            //On créé un étudiant
            Lieux lieux = new Lieux();
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            lieux.setId_lieux(c.getInt(maBaseSQLite.NUM_COL_ID));
            lieux.setNom_lieux(c.getString(maBaseSQLite.NUM_COL_NOM));
            lieux.setCommentaire(c.getString(maBaseSQLite.NUM_COL_COMMENTAIRE));
            lieux.setPointX_lieux(c.getString(maBaseSQLite.NUM_COL_POINTX));
            lieux.setPointY(c.getString(maBaseSQLite.NUM_COL_POINTY));
            lieux.setId_Lieux_Voyage(c.getString(maBaseSQLite.NUM_COL_ID_VOYAGE_LIEUX));


            listL.add(lieux);
            c.moveToNext();
        }

        //On ferme le cursor
        c.close();

        if(listL.isEmpty()) {
            Lieux bidon = new Lieux("bbd_vide" ,"", "0","0","0");
            listL.add(bidon);
            Toast.makeText(mContext, bidon.toString(), LENGTH_SHORT).show();
        }
*/

        return listL;
    }
}