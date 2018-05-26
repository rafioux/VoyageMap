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
///TOdo : a supp

    ListView mListView;
    static String i = "1";
    ArrayAdapter<String> adapter;


    private ArrayList<Voyage> voy = new ArrayList<Voyage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        jeu();

    }

    //récupération des résultats
    public void onActivityResult(int code, int resultCode, Intent donnees){
        jeu();
    }

    protected void jeu(){
        //Création d'une instance de ma classe VoyageBDD et
        VoyageBDD voyageBdd = new VoyageBDD(this);
        voyageBdd.open();
        voy = voyageBdd.getVoyageAll();
        adapter = new ArrayAdapter<String>(this, android.R.layout.list_content);
        voyageBdd.close();

        //Affiche les éléments dans la liste
        //TODO : liste des noms de voyage

        String[] tab;
        if (voy != null) {
            tab = new String[voy.size()];
            for (int i = 0; i < voy.size(); i++) {
                Voyage v = voy.get(i);
                String s = v.getNom_voyage();
                tab[i] = s;
            }

            mListView = (ListView) findViewById(R.id.listView);
            adapter = new ArrayAdapter<String>(mainActivity.this, android.R.layout.simple_list_item_1, tab);
            mListView.setAdapter(adapter);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(getApplicationContext(), "Test" + id+position, Toast.LENGTH_SHORT).show();

                Intent intentNom = new Intent(mainActivity.this, MarkerCloseInfoWindowOnRetapDemoActivity.class);
                intentNom.putExtra("id","" + id);
                startActivityForResult(intentNom,1);
            }
        });
    }


    //Ajouter un élément de la liste
    protected void add(View v){
        Intent monIntent = new Intent( this,addVoyage.class);
        startActivityForResult(monIntent,1);
    }
}
