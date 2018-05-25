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

import java.util.ArrayList;

public class mainActivity extends AppCompatActivity /*implements AdapterView.OnItemSelectedListener*/ {
///TOdo : a supp


    /* 1 */  /*https://developers.google.com/maps/documentation/android-api/signup?hl=fr*/
    ListView mListView;
    static String i = "1";
    //ArrayAdapter<String> adapter;


    private ArrayList<Voyage> voy = new ArrayList<Voyage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        //Création d'une instance de ma classe VoyageBDD et
        VoyageBDD voyageBdd = new VoyageBDD(this);


        voyageBdd.open();
        voy = voyageBdd.getVoyageAll();
        //Toast.makeText(this, voyageBdd.getVoyageAll().toString(), Toast.LENGTH_LONG).show();
        //Pour vérifier que l'on a bien créé notre voyage dans la BDD
        //on extrait le voyage de la BDD grâce au titre du voyage que l'on a créé précédemment

         //adapter = new ArrayAdapter<String>(this,android.R.layout.list_content) ;

         voyageBdd.close();

        //ArrayAdapter<Voyage> adapter = new ArrayAdapter<Voyage>(this, android.R.layout.simple_list_item_1, voy);



        //Affiche les éléments dans la liste
        //TODO : liste des noms de voyage
        mListView = (ListView) findViewById(R.id.listView);
        //mListView.setAdapter(adapter);

        /*adapter = new ArrayAdapter<String>(mainActivity.this,
                android.R.layout.simple_list_item_1, voy);*/
                //android.R.layout.simple_list_item_1, voy);


        //mListView.setOnItemSelectedListener(this);
        setListView(voy);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        Toast.makeText(this,"position" + position,Toast.LENGTH_SHORT).show();

    }


    //clic sur un element de la liste
   /* protected void item(View v){
        String txt = (String) mListView.getSelectedItem().toString();
        Toast.makeText(this,"bonjour",Toast.LENGTH_SHORT).show();


       Intent intentNom = new Intent(this,MapsActivity.class);
        intentNom.putExtra("idVoyage",txt);//envoyer le score
        startActivityForResult(intentNom,1);
    }*/

    /*public void onItemClick(AdapterView<?> l) {
        Toast.makeText(this,"bonjour",Toast.LENGTH_SHORT).show();

        // Then you start a new Activity via Intent
        //Intent intent = new Intent();

       // intent.putExtra("position", position);
        // Or / And
       // intent.putExtra("id", id);
       // startActivity(intent);
    }*/



    //Ajouter un élément de la liste
    protected void add(View v){
        Intent monIntent = new Intent( this,addVoyage.class);
        startActivityForResult(monIntent,1);
    }

    //Ajouter un élément de la liste
    protected void test(View v){

        Intent monIntent = new Intent( this,MarkerCloseInfoWindowOnRetapDemoActivity.class);
        monIntent.putExtra(mainActivity.i,i);
        startActivityForResult(monIntent,1);
    }

    //Convertit une ArrayList<Voyage> en Adapter pour l'affichage
    private void setListView(ArrayList<Voyage> ale) {
        String[] tab;

        if(ale != null) {
            tab = new String[ale.size()];

            for(int i = 0; i < ale.size(); i++) {
                Voyage v = ale.get(i);

                String s = v.getId_voyage() + " " + v.getNom_voyage();

                tab[i] = s;
            }

            ArrayAdapter<String> aas = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tab);
            mListView.setAdapter(aas);

            //setContentView(mListView);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
                {

                    if(position == '1') {
                        Toast.makeText(mainActivity.this, "1", Toast.LENGTH_SHORT).show();
                    }else if(position == '2') {
                        Toast.makeText(mainActivity.this, "2", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
        else {
            mListView.setAdapter(null);
        }

       /* setOnItemClickListener(mListView);

        //setContentView(mListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Toast.makeText(mainActivity.this,"position",Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    /*public void setOnItemClickListener(ListView onItemClickListener) {
        Log.i("HelloListView", "You clicked Item: ");
        Toast.makeText(this,"fuck",Toast.LENGTH_SHORT).show();
    }*/



}
