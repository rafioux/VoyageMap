package com.example.rafioux.voyagemap;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class addVoyage extends Activity {

    EditText mVoyage;
    EditText description;
/* 1 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addvoyage);
    }

    //Ajoute le Voyage dans la bdd
    protected void add(View v){
        mVoyage = (EditText) findViewById(R.id.titre);
        description = (EditText) findViewById(R.id.description);

        VoyageBDD voyageBdd = new VoyageBDD(this);
        Voyage voyage = new Voyage(mVoyage.getText().toString(),description.getText().toString());

        voyageBdd.open();
        voyageBdd.insertVoyage(voyage);
        voyageBdd.close();

        this.finish();
    }
}