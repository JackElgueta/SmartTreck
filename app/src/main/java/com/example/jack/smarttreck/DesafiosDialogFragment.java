package com.example.jack.smarttreck;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class DesafiosDialogFragment extends DialogFragment {
    String token, sessid, session_name, fid_image, url_image, uid_user, uid;
    MyCustomAdapter dataAdapter = null;
    private Spinner ruta1, ruta2, ruta3, ruta4;
    public DesafiosDialogFragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDesafiosDialogFragment();
    }

    /**
     * Crea un diálogo de alerta sencillo
     * @return Nuevo diálogo
     */
    public AlertDialog createDesafiosDialogFragment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle("Calculando tu nivel")
                .setMessage("Por favor indica las ultimas 4 rutas realizadas por ti, en caso de no haber hecho ninguna, presiona cancelar")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Acciones
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Acciones
                            }
                        });

        return builder.create();
    }

    private void ListarRutas() {
        final ArrayList<Rutas> rutaList = new ArrayList<Rutas>();
        final JSONObject[] rutasUser = new JSONObject[1];
//        final ProgressDialog progressDialog = new ProgressDialog(EquipamientoActivity.this);
//        progressDialog.setMessage("Cargando Equipamiento...");
//        progressDialog.show();


        AsyncHttpClient client_user = new AsyncHttpClient();
        client_user.addHeader("X-CSRF-Token", token);
        client_user.addHeader("Cookie", session_name + "=" + sessid);

        client_user.get("http://itfactory.cl/smartTrekking/api/user/" + uid, new AsyncHttpResponseHandler() {


            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    try {
                        JSONObject userJson = new JSONObject(new String(responseBody));
                        if (userJson.get("field_rutas").toString().equals("[]")) {

                            AsyncHttpClient client = new AsyncHttpClient();
                            client.addHeader("X-CSRF-Token", token);
                            client.addHeader("Cookie", session_name + "=" + sessid);

                            client.get("http://itfactory.cl/smartTrekking/api/views/rutas", new AsyncHttpResponseHandler() {

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    if (statusCode == 200) {
                                    //    progressDialog.dismiss();
                                        try {
                                            JSONArray jsonArray = new JSONArray(new String(responseBody));
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                Rutas rutas = new Rutas(jsonArray.getJSONObject(i).getString("node_title"), jsonArray.getJSONObject(i).getString("lat"), jsonArray.getJSONObject(i).getString("lon"), jsonArray.getJSONObject(i).getString("distancia"), jsonArray.getJSONObject(i).getString("tiempo_estimado"));
                                                rutaList.add(rutas);
                                            }



                                            ruta1.setAdapter(new ArrayAdapter<String>(this,R.layout.fragment_desafios_dialog,rutaList));


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

    private class MyCustomAdapter extends ArrayAdapter<Rutas> {

        private ArrayList<Rutas> rutaList;

        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<Rutas> rutaList) {
            super(context, textViewResourceId, rutaList);
            this.rutaList = new ArrayList<Rutas>();
            this.rutaList.addAll(rutaList);
        }



        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MyCustomAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.lista_equipo, null);

                holder = new MyCustomAdapter.ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Equipo equipos = (Equipo) cb.getTag();

                        equipos.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (MyCustomAdapter.ViewHolder) convertView.getTag();
            }

            Equipo equipos = equipoList.get(position);
            holder.code.setText(" (" +  equipos.getCode() + ")");
            holder.name.setText(equipos.getName());
            holder.name.setChecked(equipos.isSelected());
            holder.name.setTag(equipos);

            return convertView;

        }

    }
}
