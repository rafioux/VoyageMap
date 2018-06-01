package com.example.rafioux.voyagemap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class LieuxBDD {

    private SQLiteDatabase mDatabase;
    private Database maBaseSQLite;  //mDbHelper

    LieuxBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new Database(context);
        try {
            open();
        } catch (SQLException e) {
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


    //on insere un lieux
    public void insertLieux(Lieux lieux){
        ContentValues values = new ContentValues();
        values.put(Database.COL_NOM_lIEUX, lieux.getNom_lieux());
        values.put(Database.COL_COMMENTAIRE, lieux.getCommentaire());
        values.put(Database.COL_LONGITUDE, lieux.getLongitude());
        values.put(Database.COL_LATITUDE, lieux.getLatitude());
        values.put(Database.COL_ID_VOYAGE_LIEUX, lieux.getId_Lieux_Voyage());//id du voyage
        //on insère l'objet dans la BDD via le ContentValues
        mDatabase.insert(Database.TABLE_LIEUX, null, values);
    }

    //on modifie un lieu grace a son id
    public void updateLieux(int id, Lieux lieux){
        ContentValues values = new ContentValues();
        values.put(Database.COL_NOM_lIEUX, lieux.getNom_lieux());
        values.put(Database.COL_COMMENTAIRE, lieux.getCommentaire());
        values.put(Database.COL_LONGITUDE, lieux.getLongitude());
        values.put(Database.COL_LATITUDE, lieux.getLatitude());
        values.put(Database.COL_ID_VOYAGE_LIEUX, lieux.getId_Lieux_Voyage());
        mDatabase.update(Database.TABLE_LIEUX,  values, Database.COL_ID_LIEUX + " = " +id, null);
    }

    //Supprime un lieu grace a son id
    public int removeLieuWithID(int id){
        return mDatabase.delete(Database.TABLE_LIEUX, Database.COL_ID_LIEUX + " = " +id, null);
    }

    //Trouve un lieu grace a ses coordonnees
    public Lieux getLieuxWithLatLon(Double lat, Double lon){
        Cursor c = mDatabase.query(Database.TABLE_LIEUX, new String[] {Database.COL_ID_LIEUX, Database.COL_NOM_lIEUX, Database.COL_COMMENTAIRE, Database.COL_LONGITUDE, Database.COL_LATITUDE, Database.COL_ID_VOYAGE_LIEUX}, Database.COL_LONGITUDE + " = \"" + lon +"\" and " + Database.COL_LATITUDE + " = \"" + lat +"\"", null, null, null, null);
        return cursorToLieux(c);
    }

    //Trouve un lieu grace a son id
    public Lieux getLieuxWithID(int id){
        Cursor c = mDatabase.query(Database.TABLE_LIEUX, new String[] {Database.COL_ID_LIEUX, Database.COL_NOM_lIEUX, Database.COL_COMMENTAIRE, Database.COL_LONGITUDE, Database.COL_LATITUDE, Database.COL_ID_VOYAGE_LIEUX}, Database.COL_ID_LIEUX + " = \"" + id +"\"", null, null, null, null);
        return cursorToLieux(c);
    }

    //Trouve tous les lieux associes a un id
    public ArrayList<Lieux> getLieuxWithIdVoyage(String id){
        Cursor c = mDatabase.query(Database.TABLE_LIEUX, new String[] {Database.COL_ID_LIEUX, Database.COL_NOM_lIEUX, Database.COL_COMMENTAIRE, Database.COL_LONGITUDE, Database.COL_LATITUDE, Database.COL_ID_VOYAGE_LIEUX}, Database.COL_ID_VOYAGE_LIEUX + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorAllLieux(c);
    }

    //Cette méthode permet de convertir un cursor en un Lieux
    private Lieux cursorToLieux(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        Lieux lieux = new Lieux();
        lieux.setId_lieux(c.getInt(Database.NUM_COL_ID));
        lieux.setNom_lieux(c.getString(Database.NUM_COL_NOM));
        lieux.setCommentaire(c.getString(Database.NUM_COL_COMMENTAIRE));
        lieux.setLongitude(c.getString(Database.NUM_COL_LONGITUDE));
        lieux.setLatitude(c.getString(Database.NUM_COL_LATITUDE));
        lieux.setId_Lieux_Voyage(c.getString(Database.NUM_COL_ID_VOYAGE_LIEUX));
        //On ferme le cursor
        c.close();

        return lieux;
    }

    //Cette méthode permet de convertir un cursor en une liste de Lieux
    private ArrayList<Lieux> cursorAllLieux(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        ArrayList<Lieux> listL = new ArrayList<>();
        //Sinon on se place sur le premier élément
        c.moveToFirst();

        while(!c.isAfterLast()){
            Lieux lieux = new Lieux();
            lieux.setId_lieux(c.getInt(Database.NUM_COL_ID));
            lieux.setNom_lieux(c.getString(Database.NUM_COL_NOM));
            lieux.setCommentaire(c.getString(Database.NUM_COL_COMMENTAIRE));
            lieux.setLongitude(c.getString(Database.NUM_COL_LONGITUDE));
            lieux.setLatitude(c.getString(Database.NUM_COL_LATITUDE));
            lieux.setId_Lieux_Voyage(c.getString(Database.NUM_COL_ID_VOYAGE_LIEUX));

            listL.add(lieux);
            c.moveToNext();
        }
        //On ferme le cursor
        c.close();
        return listL;
    }
}