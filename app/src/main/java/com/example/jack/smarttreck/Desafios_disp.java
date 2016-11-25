package com.example.jack.smarttreck;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImage;
import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Desafios_disp extends AppCompatActivity {

    private ListView listView;
    ArrayList imagen_ruta = new ArrayList();
    ArrayList ruta = new ArrayList();
    ArrayList team = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafios_disp);
        listView= (ListView) findViewById(R.id.lista_desafios);
        descargar_desafios();



    }

    private void descargar_desafios() {
        imagen_ruta.clear();
        ruta.clear();
        team.clear();

        final ProgressDialog progressDialog = new ProgressDialog(Desafios_disp.this);
        progressDialog.setMessage("Cargando Datos...");
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("1826.34535", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++) {
                            imagen_ruta.add(jsonArray.getJSONObject(i).getString("imagen_ruta"));
                            ruta.add(jsonArray.getJSONObject(i).getString("ruta"));
                            team.add(jsonArray.getJSONObject(i).getString("ruta"));
                        }
                        listView.setAdapter(new ImagenAdapter(getApplicationContext()));

                        }catch (JSONException e){

                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private class ImagenAdapter extends BaseAdapter{
        Context ctx;
        LayoutInflater layoutInflanter;
        SmartImageView smartImageView;
        TextView tvRuta, tvTeam;

        public ImagenAdapter(Context applicationContext) {
            this.ctx= applicationContext;
            layoutInflanter=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imagen_ruta.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewGroup viewGroup = (ViewGroup)layoutInflanter.inflate(R.layout.activity_desafios_disp_item,null);

            smartImageView=(SmartImageView)viewGroup.findViewById(R.id.imagen_ruta);
            tvRuta=(TextView)viewGroup.findViewById(R.id.ruta);
            tvTeam=(TextView)viewGroup.findViewById(R.id.team);

            //para imagenes
            String urlfinal ="http..."+imagen_ruta.get(position).toString();
            Rect rect = new Rect(smartImageView.getLeft(), smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());

            smartImageView.setImageUrl(urlfinal,rect);

            tvRuta.setText(ruta.get(position).toString());
            tvTeam.setText(team.get(position).toString());
            return viewGroup;
        }
    }
}
