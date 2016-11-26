package com.example.jack.smarttreck;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    ArrayList nombre = new ArrayList();
    ArrayList ruta = new ArrayList();
    ArrayList cantidadPersonas = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafios_disp);
        setTitle("Unirse a un desafio");
        listView= (ListView) findViewById(R.id.lista_desafios);
        descargar_desafios();



    }

    private void descargar_desafios() {
        nombre.clear();
        ruta.clear();
        cantidadPersonas.clear();

        final ProgressDialog progressDialog = new ProgressDialog(Desafios_disp.this);
        progressDialog.setMessage("Cargando Datos...");
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://itfactory.cl/smartTrekking/api/views/desafios", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++) {
                            nombre.add(jsonArray.getJSONObject(i).getString("node_title"));
                            Log.d("nombre_desafio", jsonArray.getJSONObject(i).getString("node_title"));
                            ruta.add(jsonArray.getJSONObject(i).getString("ruta"));
                            cantidadPersonas.add(jsonArray.getJSONObject(i).getString("cantidad de personas"));
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
        TextView tvRuta, tvCantidadPersonas, tvNombre;

        public ImagenAdapter(Context applicationContext) {
            this.ctx= applicationContext;
            layoutInflanter=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return nombre.size();
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

            //smartImageView=(SmartImageView)viewGroup.findViewById(R.id.imagen_ruta);
            tvRuta=(TextView)viewGroup.findViewById(R.id.ruta);
            tvCantidadPersonas=(TextView)viewGroup.findViewById(R.id.cantidadPersonas);
            tvNombre=(TextView)viewGroup.findViewById(R.id.nombreDesafio);

            //para imagenes
            //String urlfinal ="http..."+n.get(position).toString();
            //Rect rect = new Rect(smartImageView.getLeft(), smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());

            //smartImageView.setImageUrl(urlfinal,rect);

            tvRuta.setText(ruta.get(position).toString());
            tvCantidadPersonas.setText(cantidadPersonas.get(position).toString());
            tvNombre.setText(nombre.get(position).toString());

            return viewGroup;
        }
    }
}
