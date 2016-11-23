package com.example.jack.smarttreck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EquipamientoActivity extends AppCompatActivity {

    Spinner zapatos;
    String[] listZapatos= {"Zapatos", "Blandos", "Semirígidos", "Rígidos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipamiento);

        zapatos= (Spinner)findViewById(R.id.Zapatos);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listZapatos);
        zapatos.setAdapter(adaptador);
    }


}
