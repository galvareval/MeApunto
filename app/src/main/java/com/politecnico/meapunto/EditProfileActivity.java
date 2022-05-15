package com.politecnico.meapunto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.politecnico.meapunto.modelos.SharedPrefManager;
import com.politecnico.meapunto.modelos.URLs;
import com.politecnico.meapunto.modelos.Usuario;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import android.util.Log;

public class EditProfileActivity extends AppCompatActivity {

        //uper.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_profile);
        EditText etFirstName, etLastName, etEmail, etContactNo, etDec;
        Button btnUpdate;
        String usuario;
    final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        viewInitializations();

        Usuario user = SharedPrefManager.getInstance(this).getUser();
        usuario = user.getCorreo();
        etFirstName.setText(user.getNombre());
        etLastName.setText(user.getApellidos());
        etEmail.setText(user.getCorreo());
        etContactNo.setText(user.getTelefono());
        etDec.setText(user.getDescripcion());
        btnUpdate=(Button) findViewById(R.id.bt_register);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput())
                {
                    updateDatos(URLs.URL_UPDATE_USER);
                    startActivity(new Intent(getApplicationContext(), MenuPrincipal.class));
                }
            }
        });

    }

    void viewInitializations() {
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etContactNo = findViewById(R.id.et_contact_no);
        etDec = findViewById(R.id.et_des);

        // To show back button in actionbar
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    // Checking if the input in form is valid
    boolean validateInput() {
        if (etFirstName.getText().toString().equals("")) {
            etFirstName.setError("Please Enter First Name");
            return false;
        }
        if (etLastName.getText().toString().equals("")) {
            etLastName.setError("Please Enter Last Name");
            return false;
        }
        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Please Enter Email");
            return false;
        }
        if (etContactNo.getText().toString().equals("")) {
            etContactNo.setError("Please Enter Contact No");
            return false;
        }
        if (etDec.getText().toString().equals("")) {
            etDec.setError("Please Enter Designation ");
            return false;
        }

        // checking the proper email format
        if (!isEmailValid(etEmail.getText().toString())) {
            etEmail.setError("Please Enter Valid Email");
            return false;
        }

        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Hook Click Event

    public void performEditProfile (View v) {
        if (validateInput()) {

            // Input is valid, here send data to your server
/*
            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String email = etEmail.getText().toString();
            String contactNo = etContactNo.getText().toString();
            String Designation = etDec.getText().toString();*/

            //Toast.makeText(this,"Profile Update Successfully",Toast.LENGTH_SHORT).show();
            // Here you can call you API

        }
    }

    /**
     * Método para updatear los datos del usuario
     * @param URL
     */
    private void updateDatos(String URL){
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String contactNo = etContactNo.getText().toString();
        String Designation = etDec.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Datos actualizados",Toast.LENGTH_SHORT).show();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("nombre",firstName);
                Log.d("myTag", "nombre"+ parametros.get("nombre"));
                parametros.put("apellidos",lastName);
                parametros.put("correo",email);
                Log.d("myTag", "correo"+ parametros.get("correo"));
                parametros.put("telefono",contactNo);
                parametros.put("descripcion",Designation);
                parametros.put("usuario",usuario);
                Log.d("myTag", "correo"+ parametros.get("usuario"));
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}


