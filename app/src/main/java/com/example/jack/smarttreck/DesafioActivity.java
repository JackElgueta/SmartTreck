package com.example.jack.smarttreck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DesafioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio);

        final Button bUnirse = (Button) findViewById(R.id.bUnirse);
        final Button bCrear = (Button) findViewById(R.id.bCrear);

        bUnirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nuevoform = new Intent(DesafioActivity.this,Desafios_disp.class);
                startActivity(nuevoform);
            }
        });

        /*bCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nuevoform = new Intent(DesafioActivity.this,Crear_DesafioActivity.class);
                startActivity(nuevoform);
            }
        });*/
    }
}
