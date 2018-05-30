package com.example.rafioux.voyagemap;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class addVoyage extends Activity {

    EditText mVoyage;
    EditText description;
    String titre1, commentaire1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addvoyage);
    }

    public void onResume(){
        super.onResume();

        if( mVoyage != null){
            mVoyage = findViewById(R.id.titre);
            mVoyage.setText(titre1);
        }

        if( description != null){
            description = findViewById(R.id.commentaire);
            description.setText(commentaire1);
        }

    }


    public void onPause() {

        super.onPause();
    }
    //Ajoute un voyage avec ses informations
    public void add(View v){
        mVoyage = findViewById(R.id.titre);
        description = findViewById(R.id.description);

        if(!mVoyage.getText().toString().equals("") || !description.getText().toString().equals("")) {
            VoyageBDD voyageBdd = new VoyageBDD(this);
            Voyage voyage = new Voyage(mVoyage.getText().toString(), description.getText().toString());

            voyageBdd.open();
            voyageBdd.insertVoyage(voyage);
            voyageBdd.close();

            this.finish();
        }
    }


    public void onSaveInstanceState(Bundle data) {
        data.putString("val1", String.valueOf(titre1));
        data.putString("val2", String.valueOf(commentaire1));
        super.onSaveInstanceState(data);
    }

    public void onRestoreInstanceState(Bundle data){
        super.onRestoreInstanceState(data);
        titre1 = data.getString("val1");
        commentaire1 = data.getString("val2");
    }

}