package com.utec.grupalsemana2.presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.interfaces.FormularioApi;
import com.utec.grupalsemana2.interfaces.OnlineApi;
import com.utec.grupalsemana2.interfaces.UsuarioApi;
import com.utec.grupalsemana2.logica.UsuarioDTO;
import com.utec.grupalsemana2.models.ActividadDeCampoViewModel;
import com.utec.grupalsemana2.models.UsuarioViewModel;
import com.utec.grupalsemana2.servicios.RestAppClient;
import com.utec.grupalsemana2.servicios.ServicioInternet;
import com.utec.grupalsemana2.sesion.Sesion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    private UsuarioApi usuarioApi = RestAppClient.getClient().create(UsuarioApi.class);
    private EditText txtNombreUsuario;
    private EditText txtContrasenia;
    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            txtNombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
            txtContrasenia = (EditText) findViewById(R.id.txtContrasenia);
            startService(new Intent(getBaseContext(), ServicioInternet.class));
            usuarioViewModel = new UsuarioViewModel(getApplication());
    }

    public void login(View view) {
        try{

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNombreUsuario(txtNombreUsuario.getText().toString());
            usuarioDTO.setContrasenia(txtContrasenia.getText().toString());
            if(validarCampos(usuarioDTO)) {
                try {
                    iniciarSesion(usuarioDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validarCampos(UsuarioDTO usuarioDTO) {

        boolean retorno = true;
        if(usuarioDTO.getNombreUsuario().isEmpty()) {
            txtNombreUsuario.setError("No puede quedar vacío");
            retorno = false;
        } else {
            txtNombreUsuario.setError(null);
        }

        if(usuarioDTO.getContrasenia().isEmpty()) {
            txtContrasenia.setError("No puede quedar vacío");
            retorno = false;
        } else {
            txtContrasenia.setError(null);
        }

        return retorno;
    }

    private void iniciarSesion(UsuarioDTO usuarioLoguear) {
        if(Sesion.isHayInternet() && Sesion.isHayRest()) {
            iniciarSesionOnline(usuarioLoguear);
        } else {
            iniciarSesionLocal(usuarioLoguear);
        }
    }

    private void irAListaActividades() {
        Intent intentLista = new Intent(this, ListarActividadesDeCampo.class);
        startActivity(intentLista);
        finish();
    }

    @Override
    protected void onResume() {
        if(Sesion.getInstancia().getUsuarioLogueado()==null) {
            super.onResume();
        } else if(Sesion.getInstancia().getUsuarioLogueado().getIdUsuario()!=0) {
            super.onResume();
            irAListaActividades();
        } else {
            super.onResume();
        }
    }


    private void iniciarSesionOnline(UsuarioDTO usuarioLoguear) {
        Call<UsuarioDTO> call = usuarioApi.login(usuarioLoguear);
        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if(response.isSuccessful()) {
                    Sesion.getInstancia().setUsuarioLogueado(response.body());
                    System.out.println("Se logueo correctamente el usuario " + Sesion.getInstancia().getUsuarioLogueado().getNombreUsuario() + " con id " + String.valueOf(Sesion.getInstancia().getUsuarioLogueado().getIdUsuario()));

                    // evaluar si el usuario existe en bd local, si no existe: lo creamos, si existe: lo actualizamos
                    if(usuarioViewModel.login(Sesion.getInstancia().getUsuarioLogueado())!=0) {
                        usuarioViewModel.update(Sesion.getInstancia().getUsuarioLogueado());
                    } else {
                        usuarioViewModel.insert(Sesion.getInstancia().getUsuarioLogueado());
                    }

                    Toast.makeText(getApplicationContext(),"Usuario logueado correctamente",Toast.LENGTH_LONG).show();
                    irAListaActividades();

                } else if (response.code()==404){
                    Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"No se pudo ingresar",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                System.out.println("Fallo la conexion");
                Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void iniciarSesionLocal(UsuarioDTO usuarioLoguear) {
        if (usuarioViewModel.login(usuarioLoguear)!=0) {
            usuarioLoguear.setIdUsuario(usuarioViewModel.login(usuarioLoguear));
            Sesion.getInstancia().setUsuarioLogueado(usuarioLoguear);
            Toast.makeText(getApplicationContext(),"Usuario logueado correctamente",Toast.LENGTH_LONG).show();
            irAListaActividades();
        } else {
            System.out.println(usuarioLoguear.toString());
            Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show();
        }
    }

}