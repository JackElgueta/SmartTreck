package com.example.jack.smarttreck;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class PerfilActivity extends AppCompatActivity {
    String nombreApellido, nivel, token, sessid, session_name, fid_image, url_image, uid_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            token = extras.getString("token");
            sessid = extras.getString("sessid");
            session_name = extras.getString("session_name");
        }

        Log.d("token", token);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setTitle("Perfil");


        final ProgressDialog progressDialog = new ProgressDialog(PerfilActivity.this);
        progressDialog.setMessage("Cargando Perfil...");
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-CSRF-Token", token);
        client.addHeader("Cookie", session_name + "=" + sessid);


        client.post("http://itfactory.cl/smartTrekking/api/system/connect", new AsyncHttpResponseHandler() {
            TextView tvNombreCompleto = (TextView) findViewById(R.id.nombreApellidoUsuario);
            TextView tvNivelUsuario = (TextView) findViewById(R.id.LevelUsuario);
            ImageView ivProfilePicture = (ImageView) findViewById(R.id.profileImage);

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {

                    try {
                        JSONObject userJson = new JSONObject(new String(responseBody));

                        Log.d("firstResponse", new String(responseBody));
                        if(!"0".equals(userJson.getJSONObject("user").getString("picture"))){
                            fid_image = userJson.getJSONObject("user").getString("picture");

                            uid_user = userJson.getJSONObject("user").getString("uid");

                            AsyncHttpClient client_image = new AsyncHttpClient();
                            client_image.addHeader("X-CSRF-Token", token);
                            client_image.addHeader("Cookie", session_name + "=" + sessid);

                            client_image.get("http://itfactory.cl/smartTrekking/api/file/" + fid_image, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBodyImage) {
                                    try {
                                        JSONObject imageFileJson = new JSONObject(new String(responseBodyImage));
                                        url_image = imageFileJson.getString("uri_full");
                                        Picasso.with(getApplicationContext()).load(url_image).into(ivProfilePicture);

                                        AsyncHttpClient client_user = new AsyncHttpClient();
                                        client_user.addHeader("X-CSRF-Token", token);
                                        client_user.addHeader("Cookie", session_name + "=" + sessid);

                                        client_user.get("http://itfactory.cl/smartTrekking/api/user/" + uid_user, new AsyncHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBodyUser) {


                                                try {
                                                    JSONObject userObject = new JSONObject(new String(responseBodyUser));
                                                    JSONObject nameObject = new JSONObject(new String(userObject.getJSONObject("field_nombre_completo").getJSONArray("und").getString(0)));

                                                    nombreApellido = nameObject.getString("value");
                                                    tvNombreCompleto.setText(nombreApellido);

                                                    AsyncHttpClient client_level = new AsyncHttpClient();
                                                    client_level.addHeader("X-CSRF-Token", token);
                                                    client_level.addHeader("Cookie", session_name + "=" + sessid);
                                                    RequestParams params = new RequestParams();
                                                    params.add("uid", uid_user);

                                                    client_level.post("http://itfactory.cl/smartTrekking/getLevel", params, new AsyncHttpResponseHandler() {
                                                        @Override
                                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBodyLevel) {
                                                            Log.d("responseLevel", new String(responseBodyLevel));
                                                            try {
                                                                JSONObject userLevelObject = new JSONObject(new String(responseBodyLevel));
                                                                nivel = userLevelObject.getString("level");
                                                                tvNivelUsuario.setText(nivel);

                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }

                                                        }

                                                        @Override
                                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBodyLevel, Throwable error) {

                                                        }
                                                    });


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, byte[] responseBodyUser, Throwable error) {

                                            }
                                        });


                                        progressDialog.dismiss();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBodyImage, Throwable error) {

                                }
                            });


                        }
                        else{

                            uid_user = userJson.getJSONObject("user").getString("uid");
                            Log.d("uid_user", uid_user);
                            AsyncHttpClient client_user = new AsyncHttpClient();
                            client_user.addHeader("X-CSRF-Token", token);
                            client_user.addHeader("Cookie", session_name + "=" + sessid);
                            client_user.get("http://itfactory.cl/smartTrekking/api/user/" + uid_user, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBodyUser) {


                                    try {
                                        JSONObject userObject = new JSONObject(new String(responseBodyUser));
                                        Log.d("responseUser", new String(responseBodyUser));
                                        JSONObject nameObject = new JSONObject(new String(userObject.getJSONObject("field_nombre_completo").getJSONArray("und").getString(0)));

                                        nombreApellido = nameObject.getString("value");
                                        tvNombreCompleto.setText(nombreApellido);

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
                                                    tvNivelUsuario.setText(nivel);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, byte[] responseBodyLevel, Throwable error) {

                                            }
                                        });

                                        progressDialog.dismiss();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBodyUser, Throwable error) {

                                }
                            });

                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


            }
        });


    }
}
