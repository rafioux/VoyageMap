package com.example.rafioux.voyagemap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class VoyageBDD
{
    private SQLiteDatabase mDatabase;
    private Database maBaseSQLite;

    VoyageBDD(Context context){
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


    //on insere un voyage
    public void insertVoyage(Voyage voyage){
        ContentValues values = new ContentValues();
        values.put(Database.COL_NOM_VOYAGE, voyage.getNom_voyage());
        values.put(Database.COL_DESCRIPTION, voyage.getDescription());
        //on insère l'objet dans la BDD via le ContentValues
        mDatabase.insert(Database.TABLE_VOYAGES, null, values);
    }

    //on modifie un voyage grace a son id
    public void updateVoyage(int id, Voyage voyage){
        ContentValues values = new ContentValues();
        values.put(Database.COL_NOM_VOYAGE, voyage.getNom_voyage());
        values.put(Database.COL_DESCRIPTION, voyage.getDescription());
        mDatabase.update(Database.TABLE_VOYAGES,  values, Database.COL_ID_VOYAGE + " = " +id, null);
    }

    //Supprime un voyage grace a son id
    public int removeVoyageWithID(int id){
        return mDatabase.delete(Database.TABLE_VOYAGES, Database.COL_ID_VOYAGE + " = " +id, null);
    }

    //Trouve un lieu grace a son nom
    public Voyage getVoyageWithNom(String nom){
        Cursor c = mDatabase.query(Database.TABLE_VOYAGES, new String[] {Database.COL_ID_VOYAGE, Database.COL_NOM_VOYAGE, Database.COL_DESCRIPTION}, Database.COL_NOM_VOYAGE + " LIKE \"" + nom +"\"", null, null, null, null);
        return cursorToVoyage(c);
    }

    //retourne tous les voyages
    public ArrayList<Voyage> getVoyageAll(){
        Cursor c = mDatabase.query(Database.TABLE_VOYAGES, new String[] {"*"}, null, null, null, null, null);
        return cursorAllVoyage(c);
    }

    //permet de convertir un cursor en un voyage
    private Voyage cursorToVoyage(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        Voyage voyage = new Voyage();
        voyage.setId_voyage(c.getInt(Database.NUM_COL_ID));
        voyage.setNom(c.getString(Database.NUM_COL_NOM));
        voyage.setVoyage_Description(c.getString(Database.NUM_COL_DESCRIPTION_VOYAGE));
        //On ferme le cursor
        c.close();

        return voyage;
    }

    //Cette méthode permet de convertir un cursor en un voyage
    private ArrayList<Voyage> cursorAllVoyage(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        ArrayList<Voyage> listV = new ArrayList<>();
        //Sinon on se place sur le premier élément
        c.moveToFirst();

        while(!c.isAfterLast()) {
            Voyage voyage = new Voyage();
            voyage.setId_voyage(c.getInt(Database.NUM_COL_ID));
            voyage.setNom(c.getString(Database.NUM_COL_NOM));
            voyage.setVoyage_Description(c.getString(Database.NUM_COL_DESCRIPTION_VOYAGE));

            listV.add(voyage);
            c.moveToNext();
        }
        //On ferme le cursor
        c.close();

        return listV;
    }

}
