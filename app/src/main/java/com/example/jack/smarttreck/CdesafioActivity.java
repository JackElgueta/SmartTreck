package com.example.jack.smarttreck;


import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CdesafioActivity extends AppCompatActivity {

    private static final String TAG ="Crear Desafio";
    private EditText Input_nombre, Input_ruta, Input_cantidad;
    private TextInputLayout Nombre_creardesafio, Ruta_creardesafio, Cantidad_creardesafio;
    private Button Btncrear_desafio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdesafio);

        Nombre_creardesafio = (TextInputLayout) findViewById(R.id.nombre_creardesafio);
        Ruta_creardesafio = (TextInputLayout) findViewById(R.id.ruta_creardesafio);
        Cantidad_creardesafio = (TextInputLayout) findViewById(R.id.cantidad_creardesafio);

        Input_nombre = (EditText) findViewById(R.id.input_nombre);
        Input_ruta = (EditText) findViewById(R.id.input_ruta);
        Input_cantidad = (EditText) findViewById(R.id.input_cantidad);

        Btncrear_desafio = (Button) findViewById(R.id.btncrear_desafio);

        Btncrear_desafio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void submitForm(){
        if(!checknombre()){
            return;
        }
        if(!checkruta()){
            return;
        }
        if(!checkcantidad()){
            return;
        }

        Nombre_creardesafio.setErrorEnabled(false);
        Ruta_creardesafio.setErrorEnabled(false);
        Cantidad_creardesafio.setErrorEnabled(false);
        Toast.makeText(getApplicationContext(),"Desafio creado correctamente", Toast.LENGTH_SHORT).show();
    }

    private boolean checkcantidad() {
        if(Input_cantidad.getText().toString().trim().isEmpty()){

            Cantidad_creardesafio.setErrorEnabled(true);
            Cantidad_creardesafio.setError("Por favor ingrese cantidad");
            Input_cantidad.setError("Se requiere ruta");
            return false;

        }
        Ruta_creardesafio.setErrorEnabled(false);
        return true;
    }

    private boolean checkruta() {
        if(Input_ruta.getText().toString().trim().isEmpty()){

            Ruta_creardesafio.setErrorEnabled(true);
            Ruta_creardesafio.setError("Por favor ingrese ruta");
            Input_ruta.setError("Se requiere ruta");
            return false;

        }
        Ruta_creardesafio.setErrorEnabled(false);
        return true;
    }

    private boolean checknombre() {
        if(Input_nombre.getText().toString().trim().isEmpty()){

            Nombre_creardesafio.setErrorEnabled(true);
            Nombre_creardesafio.setError("Por favor ingrese nombre");
            Input_nombre.setError("Se requiere nombre");
            return false;

        }
        Nombre_creardesafio.setErrorEnabled(false);
        return true;
    }
    private void requestFocus(View view){
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
