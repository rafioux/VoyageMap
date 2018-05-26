package com.example.rafioux.voyagemap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addDescription extends Activity {

    EditText description;
    String i;
    String j;
    String lon;
    String lat;
    Boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddescription);

        Intent monItent = getIntent();
        i = monItent.getStringExtra("idVoyage");
        j = monItent.getStringExtra("idLieux");
        lat = monItent.getStringExtra("lat");
        lon = monItent.getStringExtra("lon");
        edit = false;


        LieuxBDD lieuxBdd = new LieuxBDD(this);
        lieuxBdd.open();
        Lieux l = lieuxBdd.getLieuxWithID(Integer.parseInt(j));
        lieuxBdd.close();

        if(l.getNom_lieux() == null || l.getNom_lieux() != ""){
            edit = true;
            Toast.makeText(this, l.getNom_lieux(), Toast.LENGTH_LONG).show();
            description = (EditText) findViewById(R.id.description);
            description.setText(l.getNom_lieux());
        }
    }

    //Ajoute les commentaires aux curseurs
    protected void save(View v){

        description = (EditText) findViewById(R.id.description);
        LieuxBDD lieuxBdd = new LieuxBDD(this);
        Lieux lieux = new Lieux(description.getText().toString(),lon,lat,i);
        lieuxBdd.open();
        lieuxBdd.updateLieux(Integer.parseInt(j),lieux);
        lieuxBdd.close();



        this.finish();
    }
}