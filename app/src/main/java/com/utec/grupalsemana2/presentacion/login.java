package com.utec.grupalsemana2.presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.interfaces.FormularioApi;
import com.utec.grupalsemana2.interfaces.UsuarioApi;
import com.utec.grupalsemana2.logica.UsuarioDTO;
import com.utec.grupalsemana2.servicios.RestAppClient;
import com.utec.grupalsemana2.sesion.Sesion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    private UsuarioApi usuarioApi = RestAppClient.getClient().create(UsuarioApi.class);
    private EditText txtNombreUsuario;
    private EditText txtContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            txtNombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
            txtContrasenia = (EditText) findViewById(R.id.txtContrasenia);
    }

    public void login(View view) {
        try{
            long id = 0;
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNombreUsuario(txtNombreUsuario.getText().toString());
            usuarioDTO.setContrasenia(txtContrasenia.getText().toString());
            if(validarCampos(usuarioDTO)) {
                iniciarSesion(usuarioDTO);
                id = (long)Sesion.getInstancia().getUsuarioLogueado().getIdUsuario();
                if(id==0) {
                    Toast.makeText(this,"Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this,"Usuario logueado correctamente",Toast.LENGTH_LONG).show();
                    Intent intentLista = new Intent(this, ListarActividadesDeCampo.class);
                    startActivity(intentLista);
                    finish();
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
        Call<UsuarioDTO> call = usuarioApi.login(usuarioLoguear);
        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if(response.isSuccessful()) {
                    Sesion.getInstancia().setUsuarioLogueado(response.body());
                    System.out.println("Se logueo correctamente el usuario " + Sesion.getInstancia().getUsuarioLogueado().getNombreUsuario() + " con id " + String.valueOf(Sesion.getInstancia().getUsuarioLogueado().getIdUsuario()));
                } else {
                    System.out.println("no anda");
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                System.out.println("Fallo la conexion");
            }
        });
    }
}