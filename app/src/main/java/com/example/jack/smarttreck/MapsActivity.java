package com.example.jack.smarttreck;


import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    String  nivel, token, sessid, session_name, uid_user, tid_dificultad;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            token = extras.getString("token");
            sessid = extras.getString("sessid");
            session_name = extras.getString("session_name");
            uid_user = extras.getString("uid");
        }

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this);
        progressDialog.setMessage("Cargando rutas acorde a su nivel...");
        progressDialog.show();

        AsyncHttpClient client_level = new AsyncHttpClient();
        client_level.addHeader("X-CSRF-Token", token);
        client_level.addHeader("Cookie", session_name + "=" + sessid);
        RequestParams params = new RequestParams();
        params.add("uid", uid_user);

        client_level.post("http://itfactory.cl/smartTrekking/getLevel", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBodyLevel) {

                try {
                    JSONObject userLevelObject = new JSONObject(new String(responseBodyLevel));
                    nivel = userLevelObject.getString("level");
                    tid_dificultad = userLevelObject.getString("tid_dificultad");

                    AsyncHttpClient client_map = new AsyncHttpClient();
                    client_map.addHeader("X-CSRF-Token", token);
                    client_map.addHeader("Cookie", session_name + "=" + sessid);

                    client_map.get("http://itfactory.cl/smartTrekking/api/views/rutas?filters[field_dificultad_tid]=" + tid_dificultad, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if(statusCode==200){

                                progressDialog.dismiss();
                                try {
                                    JSONArray jsonArray = new JSONArray(new String(responseBody));
                                    for (int i=0;i<jsonArray.length();i++) {
                                        mMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(Double.parseDouble(jsonArray.getJSONObject(i).getString("lat")), Double.parseDouble(jsonArray.getJSONObject(i).getString("lon"))))
                                                .title(jsonArray.getJSONObject(i).getString("node_title"))
                                                .snippet("Tiempo estimado: " + jsonArray.getJSONObject(i).getString("tiempo_estimado") + "\n" + "Distancia: " +   jsonArray.getJSONObject(i).getString("distancia"))
                                        );

                                    }

                                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                                        @Override
                                        public View getInfoWindow(Marker arg0) {
                                            return null;
                                        }

                                        @Override
                                        public View getInfoContents(Marker marker) {

                                            LinearLayout info = new LinearLayout(getApplicationContext());
                                            info.setOrientation(LinearLayout.VERTICAL);

                                            TextView title = new TextView(getApplicationContext());
                                            title.setTextColor(Color.BLACK);
                                            title.setGravity(Gravity.CENTER);
                                            title.setTypeface(null, Typeface.BOLD);
                                            title.setText(marker.getTitle());

                                            TextView snippet = new TextView(getApplicationContext());
                                            snippet.setTextColor(Color.GRAY);
                                            snippet.setText(marker.getSnippet());

                                            info.addView(title);
                                            info.addView(snippet);

                                            return info;
                                        }
                                    });



                                }catch (JSONException e){

                                    e.printStackTrace();
                                }


                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBodyLevel, Throwable error) {

            }
        });


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }


    }


}
