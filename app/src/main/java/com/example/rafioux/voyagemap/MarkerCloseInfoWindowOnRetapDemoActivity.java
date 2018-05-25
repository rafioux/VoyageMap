/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.rafioux.voyagemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * This shows how to close the info window when the currently selected marker is re-tapped.
 */
public class MarkerCloseInfoWindowOnRetapDemoActivity extends AppCompatActivity implements
        OnMarkerClickListener,
        OnMapClickListener,
        OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    ArrayList<Lieux> test;
    //private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);
    //private static final LatLng MELBOURNE = new LatLng(-37.81319, 144.96298);
    //private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    //private static final LatLng ADELAIDE = new LatLng(-34.92873, 138.59995);
    //private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    String i;
    private GoogleMap mMap = null;

    /**
     * Keeps track of the selected marker.
     */
    private Marker mSelectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_close_info_window_on_retap_demo);

        //on récupère l'id du voyage que l'on veux
        Intent monItent = getIntent();
        i = monItent.getStringExtra(mainActivity.i);
        Toast.makeText(this,"voyage : " + i.toString(),Toast.LENGTH_SHORT).show();



        /*on recupère tous les lieux */
        ArrayList<Lieux> li = new ArrayList<Lieux>();

        LieuxBDD lieuxBDD = new LieuxBDD(this);

        lieuxBDD.open();
        li = lieuxBDD.getLieuxWithIdVoyage(i.toString());
        if(li != null  ) {
        }else{
            Toast.makeText(this, (CharSequence) li, Toast.LENGTH_SHORT).show();
        }
        lieuxBDD.close();

       /* Intent monItent = getIntent();
        String i = monItent.getStringExtra(String.valueOf(mainActivity.i));
        Toast.makeText(this,i, Toast.LENGTH_SHORT).show();*/

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        new OnMapAndViewReadyListener(mapFragment, this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // Hide the zoom controls.
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Add lots of markers to the map.
        //addMarkersToMap();

        LieuxBDD lieuxBDD = new LieuxBDD(this);
        lieuxBDD.open();

        test = lieuxBDD.getLieuxWithIdVoyage(i);


        //Toast.makeText(this,test.toString(),Toast.LENGTH_SHORT).show();


        for(int i = 0 ; i < test.size(); i++) {

            Toast.makeText(this, test.get(i).toString(), Toast.LENGTH_SHORT).show();


            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng( Double.parseDouble(test.get(i).getPointY_lieux()), Double.parseDouble(test.get(i).getPointX_lieux())))
                    .title(test.get(i).getNom_lieux().toString())
                    .draggable(true)
                    .visible(true)
                    .snippet(test.get(i).getNom_lieux().toString()));
        }

        lieuxBDD.close();

        // Set listener for marker click event.  See the bottom of this class for its behavior.
        mMap.setOnMarkerClickListener(this);

        // Set listener for map click event.  See the bottom of this class for its behavior.
        mMap.setOnMapClickListener(this);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localized.
        map.setContentDescription("Demo showing how to close the info window when the currently"
            + " selected marker is re-tapped.");

        /*LatLngBounds bounds = new LatLngBounds.Builder()
                .include(BRISBANE)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));*/
    }

    private void addMarkersToMap() {
      //on récupère l'ensemble des marqueurs pour le voyage


        /*mMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane")
               .draggable(true)
               .snippet("Population: 2,074,200"));*/

/*
        mMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney")
                .draggable(true)
                .snippet("Population: 4,627,300"));

        mMap.addMarker(new MarkerOptions()
                .position(MELBOURNE)
                .title("Melbourne")
                .draggable(true)
                .snippet("Population: 4,137,400"));

        mMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Perth")
                .draggable(true)
                .snippet("Population: 1,738,800"));

        mMap.addMarker(new MarkerOptions()
                .position(ADELAIDE)
                .title("Adelaide")
                .draggable(true)
                .snippet("Population: 1,213,000"));*/
    }

    @Override
    public void onMapClick(final LatLng point) {
        // Any showing info window closes when the map is clicked.
        // Clear the currently selected marker.
        mSelectedMarker = null;
        Toast.makeText(this, "je passe 1" , Toast.LENGTH_LONG).show();

        Double lat = point.latitude;
        Double lon = point.longitude;
        EditText tp = (EditText) findViewById (R.id.tap_text);
        String tps = String.valueOf(tp.getText());


        String lat1 =  String.valueOf(lat);
        String lon1 =  String.valueOf(lon);

        Lieux l = new Lieux(tps, lon1,lat1,i);


        //on enregoistre le lieux dans la bdd
        LieuxBDD lieuxBDD = new LieuxBDD(this);
        lieuxBDD.open();
        lieuxBDD.insertLieux(l);
        lieuxBDD.close();



        String chaine = tp.getText().toString();

        Toast.makeText(this, "le futur lieux : lat" + lat + " lon : " + lon + "description : " + chaine , Toast.LENGTH_LONG).show();

        mMap.addMarker(new MarkerOptions()
                .position(point)
                .title("1")
                .draggable(true)
                .snippet(point.latitude +" " + point.longitude ));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        // The user has re-tapped on the marker which was already showing an info window.

        Toast.makeText(this, marker.getSnippet() , Toast.LENGTH_LONG).show();

        if (marker.equals(mSelectedMarker)) {
            Toast.makeText(this, "je passe 3" , Toast.LENGTH_LONG).show();

            // The showing info window has already been closed - that's the first thing to happen
            // when any marker is clicked.
            // Return true to indicate we have consumed the event and that we do not want the
            // the default behavior to occur (which is for the camera to move such that the
            // marker is centered and for the marker's info window to open, if it has one).
            mSelectedMarker = null;
            Toast.makeText(this, "je passe 4" , Toast.LENGTH_LONG).show();

            return true;
        }

        mSelectedMarker = marker;
        Toast.makeText(this, "je passe 5" , Toast.LENGTH_LONG).show();

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur.
        return false;
    }
}
