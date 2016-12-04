package com.example.jack.smarttreck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String token, sessid, session_name, uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            token = extras.getString("token");
            sessid = extras.getString("sessid");
            session_name = extras.getString("session_name");
            uid = extras.getString("uid");
        }


        final Button ibMapa = (Button) findViewById(R.id.ibMapa);
        final Button ibPerfil = (Button) findViewById(R.id.ibPerfil);
        final Button ibDesafio = (Button) findViewById(R.id.ibDesafio);
        final Button ibEquipamiento = (Button) findViewById(R.id.ibEquipamiento);


        ibMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent nuevoform = new Intent(MainActivity.this,MapsActivity.class);
                    nuevoform.putExtra("token", token);
                    nuevoform.putExtra("sessid", sessid);
                    nuevoform.putExtra("session_name", session_name);
                    nuevoform.putExtra("uid", uid);
                    startActivity(nuevoform);
            }
        });
        ibPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nuevoform = new Intent(MainActivity.this,PerfilActivity.class);
                nuevoform.putExtra("token", token);
                nuevoform.putExtra("sessid", sessid);
                nuevoform.putExtra("session_name", session_name);

                startActivity(nuevoform);
            }
        });

        ibDesafio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nuevoform = new Intent(MainActivity.this,DesafioActivity.class);
                startActivity(nuevoform);
            }
        });

        ibEquipamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nuevoform = new Intent(MainActivity.this,EquipamientoActivity.class);
                nuevoform.putExtra("token", token);
                nuevoform.putExtra("sessid", sessid);
                nuevoform.putExtra("session_name", session_name);
                nuevoform.putExtra("uid", uid);
                startActivity(nuevoform);
            }
        });



    }
}