package com.example.rafioux.voyagemap;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addVoyage extends Activity {

    EditText mVoyage;
/* 1 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addvoyage);
    }

    //Ajoute le Voyage dans la bdd
    protected void add(View v){
        mVoyage = (EditText) findViewById(R.id.nom_voyage);


        VoyageBDD voyageBdd = new VoyageBDD(this);

        Voyage voyage = new Voyage(mVoyage.getText().toString(),  "c'est jolie");

        voyageBdd.open();
        voyageBdd.insertVoyage(voyage);


        //on test la présence dans la base
       // Voyage voyageFromBdd = voyageBdd.getVoyageWithNom(mVoyage.getText().toString());

        //Toast.makeText(this, voyageFromBdd.toString(), Toast.LENGTH_LONG).show();

        voyageBdd.close();

        LieuxBDD lieuxBdd = new LieuxBDD(this);
        Lieux lieux = new Lieux("1","0","0","1");
        lieuxBdd.open();
        lieuxBdd.insertLieux(lieux);
        lieuxBdd.close();

        this.finish();
    }


}

/* début de test, j'ai pas fait de test pour lieux avant mais sinon voyage seul fonctionne*/
/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Création d'une instance de ma classe VoyageBDD
        VoyageBDD voyageBdd = new VoyageBDD(this);

        //Création d'un voyage
        Voyage voyage = new Voyage("Paris", "PointX", "pointY");

        //On ouvre la base de données pour écrire dedans
        voyageBdd.open();
        voyageBdd.insertVoyage(voyage);

        //Pour vérifier que l'on a bien créé notre voyage dans la BDD
        //on extrait le voyage de la BDD grâce au titre du voyage que l'on a créé précédemment
        Voyage voyageFromBdd = voyageBdd.getVoyageWithNom(voyage.getNom_voyage());
        //Si un voyage est retourné (donc si le voyage à bien été ajouté à la BDD)
        if(voyageFromBdd != null){
            //On affiche les infos du voyage dans un Toast
            Toast.makeText(this, voyageFromBdd.toString(), Toast.LENGTH_LONG).show();
            //On modifie le titre du voyage
            voyageFromBdd.setNom_voyage("J'ai modifié le nom du voyage");
            //Puis on met à jour la BDD
            voyageBdd.updateVoyage(voyageFromBdd.getId_voyage(), voyageFromBdd);
        }

        //On extrait le voyage de la BDD grâce au nouveau titre
        voyageFromBdd = voyageBdd.getVoyageWithNom("J'ai modifié le nom du voyage");
        //S'il existe un voyage possédant ce titre dans la BDD
        if(voyageFromBdd != null){
            //On affiche les nouvelles informations du voyage pour vérifier que le titre du voyage a bien été mis à jour
            Toast.makeText(this, voyageFromBdd.toString(), Toast.LENGTH_LONG).show();
            //on supprime le voyage de la BDD grâce à son ID
            voyageBdd.removeVoyageWithID(voyageFromBdd.getId_voyage());
        }

        //On essaye d'extraire de nouveau le voyage de la BDD toujours grâce à son nouveau titre
        voyageFromBdd = voyageBdd.getVoyageWithNom("J'ai modifié le nom du voyage");
        //Si aucun voyage n'est retourné
        if(voyageFromBdd == null){
            //On affiche un message indiquant que le voyage n'existe pas dans la BDD
            Toast.makeText(this, "Ce Voyage n'existe pas dans la BDD", Toast.LENGTH_LONG).show();
        }
        else{
            //on affiche un message indiquant que le voyage existe dans la BDD
            Toast.makeText(this, "Ce Voayge existe dans la BDD", Toast.LENGTH_LONG).show();
        }

        voyageBdd.close();
    }
*/