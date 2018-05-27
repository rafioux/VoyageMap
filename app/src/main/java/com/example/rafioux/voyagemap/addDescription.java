package com.example.rafioux.voyagemap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addDescription extends Activity {

    EditText titre;
    EditText commentaire;
    String idVoyage;
    String idLieux;
    String lon;
    String lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddescription);

        Intent monItent = getIntent();
        idVoyage = monItent.getStringExtra("idVoyage");
        idLieux = monItent.getStringExtra("idLieux");
        lat = monItent.getStringExtra("lat");
        lon = monItent.getStringExtra("lon");


        LieuxBDD lieuxBdd = new LieuxBDD(this);
        lieuxBdd.open();
        Lieux l = lieuxBdd.getLieuxWithID(Integer.parseInt(idLieux));
        lieuxBdd.close();

        if(l.getNom_lieux() != null || l.getNom_lieux() != ""){
            titre = (EditText) findViewById(R.id.titre);
            titre.setText(l.getNom_lieux());
        }

        if(l.getCommentaire() != null || l.getCommentaire() != ""){
            commentaire = (EditText) findViewById(R.id.commentaire);
            commentaire.setText(l.getCommentaire());
        }
    }

    //Ajoute les commentaires aux curseurs
    protected void save(View v){

        titre = (EditText) findViewById(R.id.titre);
        commentaire = (EditText) findViewById(R.id.commentaire);
        LieuxBDD lieuxBdd = new LieuxBDD(this);
        Lieux lieux = new Lieux(titre.getText().toString(), commentaire.getText().toString(),lon,lat,idVoyage);
        lieuxBdd.open();
        lieuxBdd.updateLieux(Integer.parseInt(idLieux),lieux);
        lieuxBdd.close();

        this.finish();
    }
}