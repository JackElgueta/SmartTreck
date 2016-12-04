package com.example.jack.smarttreck;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class EquipamientoActivity extends AppCompatActivity {
    String token, sessid, session_name, fid_image, url_image, uid_user, uid;
    private ListView listView;
    ArrayList equipo = new ArrayList();

    MyCustomAdapter dataAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            token = extras.getString("token");
            sessid = extras.getString("sessid");
            session_name = extras.getString("session_name");
            uid = extras.getString("uid");
        }

        setContentView(R.layout.activity_equipamiento);
        setTitle("Mi equipamiento");
        displayListView();
        checkButtonClick();

    }


    private void displayListView() {
        final ArrayList<Equipo> equipoList = new ArrayList<Equipo>();
        final JSONObject[] equipamientoUser = new JSONObject[1];
        final ProgressDialog progressDialog = new ProgressDialog(EquipamientoActivity.this);
        progressDialog.setMessage("Cargando Equipamiento...");
        progressDialog.show();


        AsyncHttpClient client_user = new AsyncHttpClient();
        client_user.addHeader("X-CSRF-Token", token);
        client_user.addHeader("Cookie", session_name + "=" + sessid);

        client_user.get("http://itfactory.cl/smartTrekking/api/user/" + uid, new AsyncHttpResponseHandler() {


                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if (statusCode == 200) {

                            try {
                                JSONObject userJson = new JSONObject(new String(responseBody));
                                equipamientoUser[0] = userJson.getJSONObject("field_equipamiento");
                                final JSONArray equipamientoUsuario = equipamientoUser[0].getJSONArray("und");


                                AsyncHttpClient client = new AsyncHttpClient();
                                client.addHeader("X-CSRF-Token", token);
                                client.addHeader("Cookie", session_name + "=" + sessid);

                                client.get("http://itfactory.cl/smartTrekking/api/views/equipamientos", new AsyncHttpResponseHandler() {

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        if(statusCode==200){
                                            progressDialog.dismiss();
                                            try {
                                                JSONArray jsonArray = new JSONArray(new String(responseBody));
                                                for (int i=0;i<jsonArray.length();i++) {
                                                    boolean loTiene = false;
                                                    for(int j=0; j < equipamientoUsuario.length(); j++) {
                                                        if(jsonArray.getJSONObject(i).getString("nid").equals(equipamientoUsuario.getJSONObject(j).getString("target_id"))){
                                                            loTiene = true;

                                                        }

                                                    }

                                                    Equipo equipo = new Equipo(jsonArray.getJSONObject(i).getString("nid"), jsonArray.getJSONObject(i).getString("nombre_item"), loTiene);
                                                    equipoList.add(equipo);
                                                }
                                                //create an ArrayAdaptar from the String Array
                                                dataAdapter = new MyCustomAdapter(EquipamientoActivity.this, R.layout.lista_equipo, equipoList);
                                                listView = (ListView) findViewById(R.id.listView1);
                                                // Assign adapter to ListView
                                                listView.setAdapter(dataAdapter);


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

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });


    }

    private class MyCustomAdapter extends ArrayAdapter<Equipo> {

        private ArrayList<Equipo> equipoList;

        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<Equipo> equipoList) {
            super(context, textViewResourceId, equipoList);
            this.equipoList = new ArrayList<Equipo>();
            this.equipoList.addAll(equipoList);
        }



        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.lista_equipo, null);

                holder = new ViewHolder();
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
                holder = (ViewHolder) convertView.getTag();
            }

            Equipo equipos = equipoList.get(position);
            holder.code.setText(" (" +  equipos.getCode() + ")");
            holder.name.setText(equipos.getName());
            holder.name.setChecked(equipos.isSelected());
            holder.name.setTag(equipos);

            return convertView;

        }

    }

    private void checkButtonClick() {
        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final StringBuffer responseText = new StringBuffer();
                //responseText.append("The following were selected...\n");

                AsyncHttpClient client_user = new AsyncHttpClient();
                client_user.addHeader("X-CSRF-Token", token);
                client_user.addHeader("Cookie", session_name + "=" + sessid);

                client_user.get("http://itfactory.cl/smartTrekking/api/user/" + uid, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if(statusCode == 200){
                            try {
                                String nids = new String("");
                                String nids_final = new String("");
                                JSONObject userJSON = new JSONObject(new String(responseBody));
                                ArrayList<Equipo> countryList = dataAdapter.equipoList;


                                for(int i=0;i<countryList.size();i++){
                                    Equipo equipos = countryList.get(i);
                                    if(equipos.isSelected()){
                                        nids = nids + equipos.getCode().toString() + ",";
                                    }
                                }

                                nids_final  = nids.substring(0,nids.length()-1);
                                Log.d("nids", nids_final);
                                AsyncHttpClient client_updateuser = new AsyncHttpClient();
                                client_updateuser.addHeader("X-CSRF-Token", token);
                                client_updateuser.addHeader("Cookie", session_name + "=" + sessid);

                                RequestParams params = new RequestParams();
                                params.add("uid", uid);
                                params.add("nids", nids_final);

                                client_updateuser.post("http://itfactory.cl/smartTrekking/updateEquipment", params, new AsyncHttpResponseHandler() {

                                     @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBodyLevel) {
                                         responseText.append("Equipamiento guardado.");
                                         finish();
                                         startActivity(getIntent());

                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBodyLevel, Throwable error) {

                                    }
                                });



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });



                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });
    }





}
