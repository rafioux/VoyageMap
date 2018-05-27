package com.example.rafioux.voyagemap;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class addVoyage extends Activity {

    EditText mVoyage;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addvoyage);
    }

    //Ajoute un voyage avec ses informations
    public void add(View v){
        mVoyage = findViewById(R.id.titre);
        description = findViewById(R.id.description);

        VoyageBDD voyageBdd = new VoyageBDD(this);
        Voyage voyage = new Voyage(mVoyage.getText().toString(),description.getText().toString());

        voyageBdd.open();
        voyageBdd.insertVoyage(voyage);
        voyageBdd.close();

        this.finish();
    }
}