package com.politecnico.meapunto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


        EditText etFirstName, etLastName, etEmail, etContactNo, etDec, etDni, etAddres, et_oPsw, etNpsw;
        TextView tvTxt_spin_gen, tvTxt_spin_nv, tvTxt_spin_prefer;
        Spinner sp_gener, sp_nv_juego, sp_preferencia;
        Button btnUpdate;

        String usuario;
        String psw;
        Usuario user;
        String genero;
        String nivel;
        String preferencia;

        ArrayList<String> listaG = new ArrayList<String>();
        ArrayList<String> listaL = new ArrayList<String>();
        ArrayList<String> listaP = new ArrayList<String>();

        Context cont=this;

    final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        viewInitializations();

        user = SharedPrefManager.getInstance(this).getUser();
        usuario = user.getCorreo();
        psw = user.getContraseña();
        etFirstName.setText(user.getNombre());
        etLastName.setText(user.getApellidos());
        etEmail.setText(user.getCorreo());
        etContactNo.setText(user.getTelefono());
        etDec.setText(user.getDescripcion());
        etDni.setText(user.getDNI());
        etAddres.setText(user.getDireccion());

        genero = user.getGenero();
        nivel = user.getNivelDeJuego();
        preferencia = user.getPreferencia();

        tvTxt_spin_gen.setText(genero);
        tvTxt_spin_nv.setText(nivel);
        tvTxt_spin_prefer.setText(preferencia);

        Log.d("myTag", "genero: "+ genero);
        Log.d("myTag", "preferencia: "+ preferencia);
        btnUpdate=(Button) findViewById(R.id.bt_register);

        listaG.add("Hombre");
        listaG.add("Mujer");

        listaL.add("Iniciacion");
        listaL.add("Intermedio");
        listaL.add("Avanzado");
        listaL.add("Competicion");
        listaL.add("Profesional");

        listaP.add("Mismo genero");
        listaP.add("Mixto");
        listaP.add("Indiferente");

        addListaG();
        addListaL();
        addListaP();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput())
                {
                    if (!etNpsw.getText().toString().equals("")) {
                        psw= etNpsw.getText().toString();
                    }
                    updateDatos(URLs.URL_UPDATE_USER);
                    startActivity(new Intent(getApplicationContext(), MenuPrincipal.class));
                }
            }
        });

    }

    public void viewInitializations() {
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etContactNo = findViewById(R.id.et_contact_no);
        etDec = findViewById(R.id.et_des);
        etDni = findViewById(R.id.et_dni);
        etAddres = findViewById(R.id.et_addres);
        etNpsw = findViewById(R.id.et_nPsw);
        et_oPsw = findViewById(R.id.et_oPsw);

        sp_gener = findViewById(R.id.spiner);
        sp_nv_juego = findViewById(R.id.spiner_nv_juego);
        sp_preferencia = findViewById(R.id.spiner_preferencia);

        tvTxt_spin_gen = findViewById (R.id.txt_spin_gen);
        tvTxt_spin_nv = findViewById (R.id.txt_spin_nv_juego_din);
        tvTxt_spin_prefer = findViewById(R.id.txt_spin_preferencia_din);

    }
    // spin
    private void addListaG()
    {
        sp_gener.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaG);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gener.setAdapter(adapter);
        //p_gener.setSelection(0);

    }

    private void addListaL()
    {
        sp_nv_juego.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapterL = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaL);
        adapterL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_nv_juego.setAdapter(adapterL);
        //sp_nv_juego.setSelection(0);
    }

    private void addListaP()
    {
        sp_preferencia.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapterP = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaP);
        adapterP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_preferencia.setAdapter(adapterP);
        //sp_preferencia.setSelection(0);
    }

    //
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        adapterView.getSelectedItem();
        switch (adapterView.getId())
        {

            case R.id.spiner:
                {
                    if(!genero.equals(adapterView.getSelectedItem().toString()))
                    {

                        //Toast.makeText(cont, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        genero = adapterView.getSelectedItem().toString();
                        tvTxt_spin_gen.setText(genero);

                    }
                }
                break;
            case R.id.spiner_nv_juego:
                {
                    if(!nivel.equals(adapterView.getSelectedItem().toString()))
                    {

                        //Toast.makeText(cont, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        nivel = adapterView.getSelectedItem().toString();
                        tvTxt_spin_nv.setText(nivel);

                    }
                }
                break;
            case R.id.spiner_preferencia:
            {
                if(!preferencia.equals(adapterView.getSelectedItem().toString()))
                {

                    //Toast.makeText(cont, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    preferencia = adapterView.getSelectedItem().toString();
                    tvTxt_spin_prefer.setText(preferencia);

                }
            }
            break;
            default:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Checking if the input in form is valid
    boolean validateInput() {
        if (etDni.getText().toString().equals("")) {
            etDni.setError("Please Enter DNI");
            return false;
        }
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
        if (etAddres.getText().toString().equals("")) {
            etAddres.setError("Please Enter DNI");
            return false;
        }
        if (!et_oPsw.getText().toString().equals("")) {
            if(etNpsw.getText().toString().equals(""))
            {
                etNpsw.setError("Please introduce a new password");
                return false;
            }
            if(!et_oPsw.getText().toString().equals(psw))
            {
                etNpsw.setError("Old PSW invalid");
                return false;
            }

        }
        if (et_oPsw.getText().toString().equals("") && (!etNpsw.getText().toString().equals(""))) {
            etNpsw.setError("Old PSW requiered");
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
         //validateInput();
    }

    /**
     * Método para updatear los datos del usuario
     * @param URL
     */
    public void updateDatos(String URL){
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String contactNo = etContactNo.getText().toString();
        String Designation = etDec.getText().toString();
        String addres = etAddres.getText().toString();
        String dni = etDni.getText().toString();
        String newPsw = psw;
        String newGen = genero;
        String newNvJuego = nivel;
        String newPreferencia = preferencia;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Datos actualizados",Toast.LENGTH_SHORT).show();
                user.setNombre(firstName);
                user.setApellidos(lastName);
                user.setCorreo(email);
                user.setTelefono(contactNo);
                user.setDescripcion(Designation);
                user.setDireccion(addres);
                user.setDNI(dni);
                user.setContraseña(newPsw);
                user.setGenero(newGen);
                user.setNivelDeJuego(newNvJuego);
                user.setPreferencia(newPreferencia);
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
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
                parametros.put("dni",dni);
                parametros.put("nombre",firstName);
                Log.d("myTag", "nombre"+ parametros.get("nombre"));
                parametros.put("apellidos",lastName);
                parametros.put("correo",email);
                parametros.put("nivel_juego",newNvJuego);
                parametros.put("preferencia",newPreferencia);
                Log.d("myTag", "correo"+ parametros.get("correo"));
                Log.d("myTag", "preferenciaphp: "+ parametros.get("preferencia"));
                parametros.put("telefono",contactNo);
                parametros.put("genero",newGen);
                parametros.put("descripcion",Designation);
                parametros.put("direccion",addres);
                parametros.put("contraseña",newPsw);
                parametros.put("usuario",usuario);
                Log.d("myTag", "correo"+ parametros.get("usuario"));
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}


