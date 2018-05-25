package com.example.rafioux.voyagemap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rafioux on 29/04/2018.
 */

public class Database extends SQLiteOpenHelper {

    public static final String TAG = "Database";

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "voyages.db";


    protected static final String TABLE_VOYAGES = "Voyage";
    protected static final String TABLE_LIEUX = "Lieux";



    protected static final String COL_ID_LIEUX = "id_lieux";
    protected static final int NUM_COL_ID_LIEUX = 0;
    protected static final String COL_NOM_lIEUX = "nom_lieux";
    protected static final int NUM_COL_NOM_LIEUX = 1;
    public static final String COL_POINTX = "pointX_lieux";
    protected static final int NUM_COL_POINTX = 2;
    public static final String COL_POINTY = "pointY_lieux";
    protected static final int NUM_COL_POINTY = 3;
    public static final String COL_ID_VOYAGE_LIEUX = "id_Lieux_Voyage";
    protected static final int NUM_COL_ID_VOYAGE_LIEUX = 4;


    public static final String COL_ID_VOYAGE = "id_voyage";
    protected static final int NUM_COL_ID = 0;
    public static final String COL_NOM_VOYAGE = "nom_voyage";
    protected static final int NUM_COL_NOM = 1;
    protected static final String COL_DESCRIPTION = "description_voyage";
    protected static final int NUM_COL_DESCRIPTION_VOYAGE = 2;


    private SQLiteDatabase bdd;

    private static final String CREATE_BDD = "CREATE TABLE IF NOT EXISTS " + TABLE_VOYAGES + " ("
            + COL_ID_VOYAGE + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + COL_NOM_VOYAGE + " TEXT NOT NULL, "
            + COL_DESCRIPTION + " TEXT NOT NULL )";


    private static final String CREATE_BDD_Lieux = "CREATE TABLE " + TABLE_LIEUX + " ("
            + COL_ID_LIEUX + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NOM_lIEUX + " TEXT NOT NULL, "
            + COL_POINTX + " TEXT NOT NULL, " + COL_POINTY + " TEXT NOT NULL, "
            + COL_ID_VOYAGE_LIEUX + " TEXT NOT NULL)";

    public Database(Context context ) {
        super(context, NOM_BDD, null, VERSION_BDD);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_BDD);
        database.execSQL(CREATE_BDD_Lieux);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_VOYAGES + ";");
        db.execSQL("DROP TABLE " + TABLE_LIEUX + ";");
        onCreate(db);
    }

}
