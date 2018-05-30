package com.example.rafioux.voyagemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class mainActivity extends AppCompatActivity{

    ListView mListView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        afficher();
    }

    //récupération des résultats
    public void onResume(){
        super.onResume();
        afficher();
    }

    protected void afficher(){
        //recupere tous les voyages
        VoyageBDD voyageBdd = new VoyageBDD(this);
        voyageBdd.open();
        ArrayList<Voyage> voyages = voyageBdd.getVoyageAll();
        voyageBdd.close();

        //convertit la liste en tableau
        String[] tab;
        if (voyages != null) {
            tab = new String[voyages.size()];
            for (int i = 0; i < voyages.size(); i++) {
                Voyage voyage = voyages.get(i);
                String v = voyage.getNom_voyage();
                String d = voyage.getDescription();
                tab[i] = v + ", " + d;
            }

            mListView = findViewById(R.id.listView);
            adapter = new ArrayAdapter<>(mainActivity.this, android.R.layout.simple_list_item_1, tab);
            mListView.setAdapter(adapter);
        }

        //si la liste est nn vide, on la rend cliquable
        if(mListView != null) {
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intentNom = new Intent(mainActivity.this, MarkerCloseInfoWindowOnRetapDemoActivity.class);
                    intentNom.putExtra("id", "" + id);
                    startActivityForResult(intentNom, 1);
                }
            });
        }
    }

    //Ajouter un élément a la liste
    public void add(View v){
        Intent monIntent = new Intent( this,addVoyage.class);
        startActivityForResult(monIntent,1);
    }
}
