package com.example.jack.smarttreck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton ibMapa = (ImageButton) findViewById(R.id.ibMapa);
        final ImageButton ibPerfil = (ImageButton) findViewById(R.id.ibPerfil);
        final ImageButton ibDesafio = (ImageButton) findViewById(R.id.ibDesafio);
        final ImageButton ibEquipamiento = (ImageButton) findViewById(R.id.ibEquipamiento);
        final ImageButton ibAmigos = (ImageButton) findViewById(R.id.ibAmigos);

        ibMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent nuevoform = new Intent(MainActivity.this,MapsActivity.class);
                    startActivity(nuevoform);
            }
        });
        ibPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nuevoform = new Intent(MainActivity.this,PerfilActivity.class);
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
                startActivity(nuevoform);
            }
        });

        ibAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nuevoform = new Intent(MainActivity.this,AmigosActivity.class);
                startActivity(nuevoform);
            }
        });

    }
}