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

    ArrayList<Lieux> lieux;
    String idVoyage;

    private GoogleMap mMap = null;
    private Marker mSelectedMarker;
    private Marker mLieux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_close_info_window_on_retap_demo);

        //on récupère l'id du voyage
        Intent monItent = getIntent();
        idVoyage = monItent.getStringExtra("id");




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        new OnMapAndViewReadyListener(mapFragment, this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.getUiSettings().setZoomControlsEnabled(false);

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(0.0,0.0))
                .title("Brisbane")
                .draggable(true)
                .snippet("Population: 2,074,200"));

        LieuxBDD lieuxBDD = new LieuxBDD(this);
        lieuxBDD.open();
        lieux = lieuxBDD.getLieuxWithIdVoyage(idVoyage);

        if(lieux != null) {
            for (int i = 0; i < lieux.size(); i++) {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(lieux.get(i).getLatitude()), Double.parseDouble(lieux.get(i).getLongitude())))
                        .title(lieux.get(i).getNom_lieux())
                        .draggable(true)
                        .visible(true)
                        .snippet(lieux.get(i).getCommentaire()));

            }
        }
        lieuxBDD.close();


        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        map.setContentDescription("");
    }


    @Override
    public void onMapClick(final LatLng point) {
        mSelectedMarker = null;

        Double lat = point.latitude;
        Double lon = point.longitude;

        Lieux l = new Lieux("", "",""+lon,""+lat,idVoyage);

        //on enregistre le lieux dans la bdd
        LieuxBDD lieuxBDD = new LieuxBDD(this);
        lieuxBDD.open();
        lieuxBDD.insertLieux(l);
        Lieux lieuxFromBdd = lieuxBDD.getLieuxWithLatLon(lat,lon);
        lieuxBDD.close();

        mLieux = mMap.addMarker(new MarkerOptions()
                .position(point)
                .title(lieuxFromBdd.getNom_lieux())
                .draggable(true)
                .snippet(lieuxFromBdd.getCommentaire()));

        mLieux.setTag("" + lieuxFromBdd.getId_lieux());

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (marker.equals(mSelectedMarker)) {

            LatLng point = marker.getPosition();
            String lat = "" + point.latitude;
            String lon = "" + point.longitude;

            Intent intentNom = new Intent(this, addDescription.class);
            intentNom.putExtra("idVoyage","" + idVoyage);
            intentNom.putExtra("idLieux","" + marker.getTag());
            intentNom.putExtra("lat","" + lat);
            intentNom.putExtra("lon","" + lon);
            startActivityForResult(intentNom,1);

            mSelectedMarker = null;
            return true;
        }
        mSelectedMarker = marker;
        return false;
    }
}
