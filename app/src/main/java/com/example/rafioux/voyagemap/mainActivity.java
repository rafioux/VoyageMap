package com.example.rafioux.voyagemap;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class mainActivity extends AppCompatActivity{

    ListView mListView;
    ArrayAdapter<String> adapter;
    private ArrayList<Voyage> voyages = new ArrayList<Voyage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        jeu();
    }


    //récupération des résultats
    public void onResume(){
        super.onResume();
        jeu();
    }

    protected void jeu(){
        //Création d'une instance de ma classe VoyageBDD et
        VoyageBDD voyageBdd = new VoyageBDD(this);
        voyageBdd.open();
        voyages = voyageBdd.getVoyageAll();
        adapter = new ArrayAdapter<String>(this, android.R.layout.list_content);
        voyageBdd.close();


        String[] tab;
        if (voyages != null) {
            tab = new String[voyages.size()];
            for (int i = 0; i < voyages.size(); i++) {
                Voyage voyage = voyages.get(i);
                String v = voyage.getNom_voyage();
                String d = voyage.getDescription();
                tab[i] = v + ", " + d;
            }

            mListView = (ListView) findViewById(R.id.listView);
            adapter = new ArrayAdapter<String>(mainActivity.this, android.R.layout.simple_list_item_1, tab);
            mListView.setAdapter(adapter);
        }

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


    //Ajouter un élément de la liste
    protected void add(View v){
        Intent monIntent = new Intent( this,addVoyage.class);
        startActivityForResult(monIntent,1);
    }
}
